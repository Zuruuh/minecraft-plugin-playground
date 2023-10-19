package fr.zuruh.playground.command;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class VanishCommand implements CommandExecutor {

    private Set<UUID> vanishedPlayers = new HashSet<>();
    private JavaPlugin plugin;

    public VanishCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Optional<Player> maybeTargetPlayer = Optional.empty();

        // /vanish -> toggle self vanishing status
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Please specify player name when running from the console.");

                return true;
            }

            maybeTargetPlayer = Optional.of((Player) sender);
        } else {
            // /vanish <player> -> toggle <player>'s vanishing status
            maybeTargetPlayer = Optional.ofNullable(Bukkit.getPlayer(args[0]));

            if (maybeTargetPlayer.isEmpty()) {
                sender.sendMessage(ChatColor.RED.toString() + "Player '" + args[0] + "' is not online");

                return true;
            }
        }

        Player targetPlayer = maybeTargetPlayer.get();
        UUID targetPlayerId = targetPlayer.getUniqueId();
        boolean isTargetVanished = vanishedPlayers.contains(targetPlayerId);

        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (otherPlayer.equals(targetPlayer)) {
                continue;
            }

            if (isTargetVanished) {
                otherPlayer.showPlayer(plugin, targetPlayer);
            } else {
                otherPlayer.hidePlayer(plugin, targetPlayer);
            }
        }

        sender.sendMessage(
            ChatColor.GREEN.toString() +
            "Player '" +
            targetPlayer.getName() +
            "' is now " +
            (isTargetVanished ? "visible" : "vanished") +
            "."
        );

        if (isTargetVanished) {
            vanishedPlayers.remove(targetPlayerId);
        } else {
            vanishedPlayers.add(targetPlayerId);
        }

        return true;
	}

}
