package com.imnotstable.coolteleport;

import lombok.Getter;

public class Config {
  
  private final CoolTeleport plugin;
  @Getter
  private long shortDelay;
  @Getter
  private long longDelay;
  
  public Config(CoolTeleport plugin) {
    this.plugin = plugin;
  }
  
  public void load() {
    plugin.saveDefaultConfig();
    plugin.reloadConfig();
    shortDelay = plugin.getConfig().getLong("short-delay", 3L) * 20;
    longDelay = plugin.getConfig().getLong("long-delay", 5L) * 20;
  }
  
}
