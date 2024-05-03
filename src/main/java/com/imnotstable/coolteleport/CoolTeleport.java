package com.imnotstable.coolteleport;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoolTeleport extends JavaPlugin {
  
  @Getter
  private static CoolTeleport instance;
  
  @Override
  public void onLoad() {
    CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
  }
  
  @Override
  public void onEnable() {
    instance = this;
    CommandAPI.onEnable();
    CoolTeleportCommand.getCoolTeleportCommand().register();
  }
  
  @Override
  public void onDisable() {
    CommandAPI.onDisable();
  }
  
}
