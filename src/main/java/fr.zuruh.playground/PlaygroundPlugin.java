package fr.zuruh.playground;

import org.bukkit.plugin.java.JavaPlugin;

import fr.zuruh.playground.command.VanishCommand;

public final class PlaygroundPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        System.out.println("Hello, world!");

        getCommand("vanish").setExecutor(new VanishCommand(this));
    }
}
