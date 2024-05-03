package com.imnotstable.coolteleport;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LocationArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CoolTeleportCommand {
  
  @Getter
  private static final CommandTree coolTeleportCommand = new CommandTree("coolteleport")
    .withAliases("cooltp", "ctp")
    .then(new PlayerArgument("target")
      .executesPlayer((player, args) -> {
        if (args.get("target") instanceof Player target)
          teleport(player, target.getLocation());
      }))
    .then(new LocationArgument("location")
      .executesPlayer((player, args) -> {
        if (args.get("location") instanceof Location location)
          teleport(player, location);
      }));
  
  private static void teleport(Player teleporter, Location location) {
    if (teleporter.hasPermission("coolteleport.instant")) {
      teleporter.teleportAsync(location);
      return;
    }
    if (teleporter.hasPermission("coolteleport.short")) {
      Bukkit.getScheduler().runTaskLater(CoolTeleport.getInstance(), () -> teleporter.teleportAsync(location), 20L);
      return;
    }
    Bukkit.getScheduler().runTaskLater(CoolTeleport.getInstance(), () -> teleporter.teleportAsync(location), 100L);
  }
  
}
