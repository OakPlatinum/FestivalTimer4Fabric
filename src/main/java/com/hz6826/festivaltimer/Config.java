package com.hz6826.festivaltimer;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

public class Config extends MidnightConfig {  // festival_timer.json
    public static final String BASIC = "Basic";

    @Entry(category = BASIC, name = "enable")
    public static boolean enable = true;
    @Entry(category = BASIC, name = "target-date-time")
    public static String targetDateTime = "2025-01-29 00:00:00";
    @Entry(category = BASIC, name = "boss-bar-color")  // By name
    public static String bossBarColorName = "pink";
    @Entry(category = BASIC, name = "last-ten-seconds-color-code")  // By name
    public static String lastTenSecondsColorName = "gold";

    @Entry(category = BASIC, name = "title-remidner-time")
    public static int titleReminderTime = 30;
    @Entry(category = BASIC, name = "title-stay-time")
    public static int titleStayTime = 20;

    @Entry(category = BASIC, name = "prefix")
    public static String prefix = "§4[FestivalTimer]§r ";

    @Entry(category = BASIC, name = "boss-bar-message")
    public static String bossBarMessage = "距活动开始还有：%d天 %d时 %d分 %d秒";
    @Entry(category = BASIC, name = "end-boss-bar-message")
    public static String endBossBarMessage = "倒计时已结束";

    @Entry(category = BASIC, name = "reload-config-message")
    public static String reloadConfigMessage = "配置已重新加载！";

    @Entry(category = BASIC, name = "title")
    public static String endTitleMessage = "新年快乐！";
    @Entry(category = BASIC, name = "subtitle.enable")
    public static boolean enableEndSubTitleMessage = false;
    @Entry(category = BASIC, name = "subtitle.text")
    public static String endSubTitleMessage = "这是倒计时结束后的副标题";
    @Entry(category = BASIC, name = "sound.enable")
    public static boolean enableEndSound = true;
}
