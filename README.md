# Festival Timer for Fabric
[![CI](https://github.com/OakPlatinum/FestivalTimer4Fabric/actions/workflows/build.yml/badge.svg)](https://github.com/OakPlatinum/FestivalTimer4Fabric/actions/workflows/build.yml)
[![Modrinth](https://img.shields.io/modrinth/v/ft4f.svg)](https://modrinth.com/mod/ft4f)
[![Stars](https://img.shields.io/github/stars/OakPlatinum/FestivalTimer4Fabric.svg?style=social)](https://github.com/OakPlatinum/FestivalTimer4Fabric)


Here is the description of FT4F. Actually, this mod is too easy to use to say anything about it, so I list a few notes for you guys:
1. The file name of its config is festivaltimer.json, under the config folder.
2. Whenever you change the config, you should run command `/festivaltimer reload` to apply.
3. Here is an example config, in case of losing it (you can restore it by deleting it and restaring the server as well):

```json
{
  "enable": true,
  "targetDateTime": "2025-01-29 00:00:00",
  "bossBarColorName": "yellow",
  "lastTenSecondsColorName": "gold",
  "titleReminderTime": 30,
  "titleStayTime": 20,
  "prefix": "§4[FestivalTimer]§r ",
  "bossBarMessage": "Festival Timer: %dD %dH %dM %dS",
  "endBossBarMessage": "Countdown has ended",
  "reloadConfigMessage": "Configuration reloaded!",
  "endTitleMessage": "Happy New Year!",
  "enableEndSubTitleMessage": true,
  "endSubTitleMessage": "This is the subtitle after the countdown ends",
  "enableEndSound": true
}
```