package com.imnotstable.coolteleport;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.LocationArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CoolTeleportCommand {
  
  @Getter
  private static final CommandTree coolTeleportCommand = new CommandTree("coolteleport")
    .withAliases("cooltp", "ctp")
    .then(new LiteralArgument("reload")
      .withPermission("coolteleport.reload")
      .executes((sender, args) -> {
        CoolTeleport.getCoolConfig().load();
        sender.sendMessage(Component.text("Reloading Config...", NamedTextColor.GREEN));
      }))
    .then(new PlayerArgument("target")
      .replaceSuggestions(ArgumentSuggestions.stringCollection(info -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList()))
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
      Bukkit.getScheduler().runTaskLater(CoolTeleport.getInstance(), () -> teleporter.teleportAsync(location), CoolTeleport.getCoolConfig().getShortDelay());
      return;
    }
    Bukkit.getScheduler().runTaskLater(CoolTeleport.getInstance(), () -> teleporter.teleportAsync(location), CoolTeleport.getCoolConfig().getLongDelay());
  }
  
}
