package ru.ruscalworld.itemmanager;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

public class FinderCommandExecutor implements CommandExecutor {

    private ItemManager plugin;

    public FinderCommandExecutor(ItemManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (command.getLabel().equals("finditem")) {
            if (!commandSender.hasPermission("itemmanager.finditem")) {
                commandSender.sendMessage("§c§l[!] §fУ Вас недостаточно прав для выполнения данной команды!");
                return false;
            }

            if (args.length == 0) {
                commandSender.sendMessage("§fИспользование: §9/finditem <name|enchantment|id> <параметр>");
                return false;
            }

            if (args[0].equalsIgnoreCase("name")) {
                StringBuilder builder = new StringBuilder(args[1]);
                for (int i = 2; i < args.length; i++) {
                    builder.append(" ");
                    builder.append(args[i]);
                }

                ItemFinder itemFinder = new ItemFinder(builder.toString(), commandSender);
                itemFinder.runTaskAsynchronously(plugin);
            }

            if (args[0].equalsIgnoreCase("enchantment")) {
                Enchantment enchantment = Enchantment.getByName(args[1]);

                if (enchantment != null) {
                    ItemFinder itemFinder = new ItemFinder(enchantment, commandSender);
                    itemFinder.runTaskAsynchronously(plugin);
                } else commandSender.sendMessage("§c§l[!] §fТакого зачарования не существует!");
            }

            if (args[0].equalsIgnoreCase("id")) {
                Material item = Material.getMaterial(args[1]);
                if (item != null) {
                    ItemFinder itemFinder = new ItemFinder(item, commandSender);
                    itemFinder.runTaskAsynchronously(plugin);
                } else commandSender.sendMessage("§c§l[!] §fТакого предмета не существует!");
            }
        }

        return true;
    }
}
