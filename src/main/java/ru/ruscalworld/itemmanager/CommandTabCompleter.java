package ru.ruscalworld.itemmanager;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            List<String> result = new ArrayList<>();

            if (args.length > 1) {
                String previous = args[args.length - 2];
                String current = args[args.length - 1];

                if (previous.equalsIgnoreCase("setcolor")) {
                    String[] colors = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
                    result = Arrays.asList(colors);
                }

                if (previous.equalsIgnoreCase("id")) {
                    for (Material material : Material.values()) {
                        String name = material.toString();
                        if (name.contains(current.toUpperCase())) result.add(name);
                    }
                }

                if (previous.equalsIgnoreCase("enchantment")) {
                    for (Enchantment enchantment : Enchantment.values()) {
                        String name = enchantment.getName();
                        if (name.contains(current.toUpperCase())) result.add(name);
                    }
                }
            }

            return result;
        }

        return null;
    }
}
