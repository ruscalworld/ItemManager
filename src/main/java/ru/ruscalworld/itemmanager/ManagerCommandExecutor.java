package ru.ruscalworld.itemmanager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ManagerCommandExecutor implements CommandExecutor {
    private ItemManager plugin;

    ManagerCommandExecutor(ItemManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getLabel().equals("itemmanager")) {
            if (args.length == 0) {
                commandSender.sendMessage("§8§l=====================\n" +
                        " §9ItemManager §fis a plugin to manage item names and perform some other actions on items.\n" +
                        " §fWritten by §9RuscalWorld §ffor §6Bortexel §fMinecraft server.\n" +
                        " §fIP: §9play.bortexelmc.ru\n" +
                        " §fSource code: §9https://github.com/RuscalWorld/ItemManager\n" +
                        " §fPlugin version: §9" + plugin.getDescription().getVersion() + "\n" +
                        " §f© RuscalWorld, 2019-2020\n" +
                        "§8§l=====================");
                return true;
            } else {
                if (!(commandSender instanceof Player)) return false;
            }

            if (args[0].equalsIgnoreCase("setname")) {
                if (!commandSender.hasPermission("itemmanager.setname")) {
                    commandSender.sendMessage("§c§l[!] §fУ Вас недостаточно прав для выполнения данной команды!");
                    return false;
                }

                if (args.length < 2) {
                    commandSender.sendMessage("§fИспользование: §9/im setname <название>");
                    return false;
                }

                Player p = (Player) commandSender;
                PlayerInventory i = p.getInventory();

                if (i.getItemInMainHand().getType() != Material.AIR) {
                    ItemStack item = i.getItemInMainHand();
                    ItemMeta im = item.getItemMeta();

                    if (im != null) {
                        StringBuilder newName = new StringBuilder();
                        for (int a = 1; a < args.length; a++) {
                            newName.append(" ").append(args[a]);
                        }
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', newName.toString().substring(1)));
                    }

                    item.setItemMeta(im);
                    i.setItemInMainHand(item);

                    p.sendMessage("§aДанные предмета обновлены!");
                } else p.sendMessage("§c§l[!] §fВы должны держать предмет в руке!");
            }

            if (args[0].equalsIgnoreCase("setcolor")) {
                if (!commandSender.hasPermission("itemmanager.setname")) {
                    commandSender.sendMessage("§c§l[!] §fУ Вас недостаточно прав для выполнения данной команды!");
                    return false;
                }

                if (args.length < 2) {
                    commandSender.sendMessage("§fИспользование: §9/im setcolor <цвет>");
                    return false;
                }

                if (args[1].length() > 1) {
                    commandSender.sendMessage("§c§l[!] §fОбозначение цвета не должно быть длиннее одного символа!");
                    return false;
                }

                Player p = (Player) commandSender;
                PlayerInventory i = p.getInventory();

                if (i.getItemInMainHand().getType() != Material.AIR) {
                    ItemStack item = i.getItemInMainHand();
                    ItemMeta im = item.getItemMeta();

                    if (im != null) {
                        String displayName = im.getDisplayName();
                        if (displayName.equals("")) displayName = im.getLocalizedName();
                        if (displayName.startsWith("§")) displayName = displayName.substring(2);
                        displayName = "§" + args[1] + displayName;
                        im.setDisplayName(displayName);
                    }

                    item.setItemMeta(im);
                    i.setItemInMainHand(item);

                    p.sendMessage("§aДанные предмета обновлены!");
                } else p.sendMessage("§c§l[!] §fВы должны держать предмет в руке!");
            }

            if (args[0].equalsIgnoreCase("lore")) {
                if (args.length < 2) {
                    commandSender.sendMessage("§fИспользование: §9/im lore <set|add|remove|clear> [данные]");
                    return false;
                }
                if (commandSender.hasPermission("itemmanager.lore")) {
                    if (args[1].equalsIgnoreCase("set")) {
                        if (args.length < 3) {
                            commandSender.sendMessage("§fИспользование: §9/im lore set <строка>");
                            return false;
                        }

                        Player p = (Player) commandSender;
                        PlayerInventory i = p.getInventory();

                        if (i.getItemInMainHand().getType() != Material.AIR) {
                            ItemStack item = i.getItemInMainHand();
                            ItemMeta im = item.getItemMeta();

                            if (im != null) {
                                List<String> lore = new ArrayList<>();

                                StringBuilder newName = new StringBuilder();
                                for (int a = 2; a < args.length; a++) {
                                    newName.append(" ").append(args[a]);
                                }

                                for (String l : newName.toString().substring(1).split("%nl")) {
                                    lore.add(ChatColor.translateAlternateColorCodes('&', l));
                                }

                                im.setLore(lore);
                            }

                            item.setItemMeta(im);
                            i.setItemInMainHand(item);

                            p.sendMessage("§aДанные предмета обновлены!");
                        } else p.sendMessage("§c§l[!] §fВы должны держать предмет в руке!");
                    }

                    if (args[1].equalsIgnoreCase("add")) {
                        if (args.length < 3) {
                            commandSender.sendMessage("§fИспользование: §9/im lore add <строка>");
                            return false;
                        }

                        Player p = (Player) commandSender;
                        PlayerInventory i = p.getInventory();

                        if (i.getItemInMainHand().getType() != Material.AIR) {
                            ItemStack item = i.getItemInMainHand();
                            ItemMeta im = item.getItemMeta();

                            List<String> lore;

                            if (im != null) {
                                if (im.getLore() != null) {
                                    lore = im.getLore();
                                } else lore = new ArrayList<>();

                                StringBuilder newName = new StringBuilder();
                                for (int a = 2; a < args.length; a++) {
                                    newName.append(" ").append(args[a]);
                                }

                                lore.add(ChatColor.translateAlternateColorCodes('&', newName.toString().substring(1)));
                                im.setLore(lore);
                            }

                            item.setItemMeta(im);
                            i.setItemInMainHand(item);

                            p.sendMessage("§aДанные предмета обновлены!");
                        } else p.sendMessage("§c§l[!] §fВы должны держать предмет в руке!");
                    }

                    if (args[1].equalsIgnoreCase("remove")) {
                        if (args.length < 3) {
                            commandSender.sendMessage("§fИспользование: §9/im lore remove <№ строки>");
                            return false;
                        }

                        Player p = (Player) commandSender;
                        PlayerInventory i = p.getInventory();

                        if (i.getItemInMainHand().getType() != Material.AIR) {
                            ItemStack item = i.getItemInMainHand();
                            ItemMeta im = item.getItemMeta();

                            if (im != null && im.getLore() != null) {
                                List<String> lore = im.getLore();
                                lore.remove(Integer.parseInt(args[2]) - 1);
                                im.setLore(lore);
                            }

                            item.setItemMeta(im);
                            i.setItemInMainHand(item);

                            p.sendMessage("§aДанные предмета обновлены!");
                        } else p.sendMessage("§c§l[!] §fВы должны держать предмет в руке!");
                    }

                    if (args[1].equalsIgnoreCase("clear")) {
                        Player p = (Player) commandSender;
                        PlayerInventory i = p.getInventory();

                        if (i.getItemInMainHand().getType() != Material.AIR) {
                            ItemStack item = i.getItemInMainHand();
                            ItemMeta im = item.getItemMeta();

                            if (im != null) {
                                List<String> lore = new ArrayList<>();
                                im.setLore(lore);
                            }

                            item.setItemMeta(im);
                            i.setItemInMainHand(item);

                            p.sendMessage("§aДанные предмета обновлены!");
                        } else p.sendMessage("§c§l[!] §fВы должны держать предмет в руке!");
                    }
                }
            }
        }

        return false;
    }
}
