package com.hz6826.festivaltimer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.boss.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FestivalTimer implements ModInitializer {
	public static final String MOD_ID = "festivaltimer";
	public static final String BOSS_BAR_ID = MOD_ID + "_boss_bar";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int cooldown = 0;

	@Override
	public void onInitialize() {
		Config.init(MOD_ID, Config.class);
		Config.write(MOD_ID);

		ServerLifecycleEvents.SERVER_STARTED.register(server -> server.getBossBarManager().add(Identifier.of(BOSS_BAR_ID), Text.of("Festival Timer is Loading!")));
		ServerTickEvents.END_SERVER_TICK.register(this::afterTick);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("festivaltimer")
				.then(literal("reload").requires(source -> source.hasPermissionLevel(4)).executes(context -> {
							Config.init(MOD_ID, Config.class);
							context.getSource().sendFeedback(() -> Text.of(Config.prefix + Config.reloadConfigMessage), true);
							return 1;
						})
				)
		));
	}

	public void afterTick(MinecraftServer server) {
		if (!Config.enable || Config.targetDateTime.isEmpty()) {
			return;
		}

		BossBarManager bossBarManager = server.getBossBarManager();
		// 更新 BossBar 的显示文本或进度
		CommandBossBar bossBar = Objects.requireNonNull(bossBarManager.get(Identifier.of(BOSS_BAR_ID)));

		bossBar.setColor(BossBar.Color.byName(Config.bossBarColorName));
		bossBar.setName(Text.of(getCountdown()).copy().formatted(bossBar.getColor().getTextFormat()));
		bossBar.setValue(bossBar.getMaxValue());
		bossBar.addPlayers(server.getPlayerManager().getPlayerList());

		Text[] endTitle = getEndTitle();
		if (endTitle[0] != null) {
			if(cooldown > 0) {
				cooldown--;
				return;
			}
			cooldown = 20;
			server.getPlayerManager().sendToAll(new TitleFadeS2CPacket(0, 20, 0));
			server.getPlayerManager().sendToAll(new TitleS2CPacket(endTitle[0]));
			if (Config.enableEndSubTitleMessage) {
				server.getPlayerManager().sendToAll(new SubtitleS2CPacket(endTitle[1]));
			}
		}
	}

	private String getCountdown() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date targetDate = sdf.parse(Config.targetDateTime);
			Date now = new Date();
			long diff = targetDate.getTime() - now.getTime();
			if (diff <= 0) {
				return Config.endBossBarMessage;
			} else {
				long days = diff / (24 * 60 * 60 * 1000);
				long hours = (diff / (60 * 60 * 1000)) % 24;
				long minutes = (diff / (60 * 1000)) % 60;
				long seconds = (diff / 1000) % 60;
				return String.format(Config.bossBarMessage, days, hours, minutes, seconds);
			}
		} catch (ParseException e) {
			return "Wrong Date Time Format!";
		}
	}

	private Text[] getEndTitle() {
		Text[] endTitle = new Text[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Formatting format = Formatting.byName(Config.lastTenSecondsColorName.toLowerCase());
			if(format == null) format = Formatting.GOLD;
			Date targetDate = sdf.parse(Config.targetDateTime);
			Date now = new Date();
			long diff = targetDate.getTime() - now.getTime();
			int diffSeconds = (int) (diff / 1000);
			if (diffSeconds > 0 && diffSeconds <= Config.titleReminderTime) {
				endTitle[0] = Text.of(String.valueOf(diffSeconds)).copy().formatted(format);
				endTitle[1] = Text.of(Config.endSubTitleMessage).copy().formatted(format);
			} else if (diffSeconds <= 0 && diffSeconds > -Config.titleStayTime) {
				endTitle[0] = Text.of(Config.endTitleMessage).copy().formatted(format);
				endTitle[1] = Text.of(Config.endSubTitleMessage).copy().formatted(format);
			}
		} catch (NullPointerException | ParseException e) {
			return new Text[]{Text.empty(), Text.empty()};
		}
		return endTitle;
	}
}