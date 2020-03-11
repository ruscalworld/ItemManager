package ru.ruscalworld.itemmanager;

import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileFormat;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public class ItemManager extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginCommand itemManagerCommand = Objects.requireNonNull(getCommand("itemmanager"));
        PluginCommand findItemCommand = Objects.requireNonNull(getCommand("finditem"));

        itemManagerCommand.setExecutor(new ManagerCommandExecutor(this));
        itemManagerCommand.setTabCompleter(new CommandTabCompleter());

        findItemCommand.setExecutor(new FinderCommandExecutor(this));
        findItemCommand.setTabCompleter(new CommandTabCompleter());

        if (CommodoreProvider.isSupported()) {
            Commodore commodore = CommodoreProvider.getCommodore(this);

            try {
                commodore.register(itemManagerCommand, CommodoreFileFormat.parse(
                        Objects.requireNonNull(getResource("commands/itemmanager.commodore"))));
                commodore.register(findItemCommand, CommodoreFileFormat.parse(
                        Objects.requireNonNull(getResource("commands/finditem.commodore"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
