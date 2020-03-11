package ru.ruscalworld.itemmanager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ItemFinder extends BukkitRunnable {

    private Material item;
    private String name;
    private Enchantment enchantment;
    private CommandSender caller;

    public ItemFinder(Material item, CommandSender caller) {
        this.item = item;
        this.caller = caller;
        this.name = null;
        this.enchantment = null;
    }

    public ItemFinder(String name, CommandSender caller) {
        this.name = name;
        this.caller = caller;
        this.item = null;
        this.enchantment = null;
    }

    public ItemFinder(Enchantment enchantment, CommandSender caller) {
        this.enchantment = enchantment;
        this.caller = caller;
        this.name = null;
        this.item = null;
    }

    @Override
    public void run() {
        caller.sendMessage("Запуск асинхронного поиска...");
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        int itemsChecked = 0;
        int itemsFound = 0;
        int playersChecked = 0;

        for (Player player : onlinePlayers) {
            PlayerInventory inventory = player.getInventory();
            ItemStack[] items = inventory.getContents();

            for (ItemStack item : items) {
                if (item == null) continue;
                if (item.getType() == Material.AIR) continue;
                ItemMeta itemMeta = item.getItemMeta();

                if (itemMeta != null) {
                    String displayName = itemMeta.getDisplayName();

                    if (this.name != null && displayName.contains(this.name)) {
                        caller.sendMessage("§fУказанный предмет был найден у §9" + player.getName());
                        itemsFound++;
                    }

                    if (this.enchantment != null && itemMeta.getEnchants().containsKey(this.enchantment)) {
                        caller.sendMessage("§fУказанный предмет был найден у §9" + player.getName());
                        itemsFound++;
                    }
                }

                if (this.item != null && item.getType() == this.item) {
                    caller.sendMessage("§fУказанный предмет был найден у §9" + player.getName());
                    itemsFound++;
                }

                itemsChecked++;
            }

            playersChecked++;
        }

        caller.sendMessage("§fПроверено §9" + itemsChecked + "§f предметов у §9" + playersChecked + "§f " +
                "игроков. §9" + itemsFound + "§f предметов найдено.");
    }
}
