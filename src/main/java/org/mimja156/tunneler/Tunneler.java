package org.mimja156.tunneler;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class Tunneler extends JavaPlugin implements Listener {
    public static JavaPlugin plugin;
    public static TunnelerEnchantment tunnelerEnchantment = new TunnelerEnchantment(NamespacedKey.minecraft("mimja_tunneler_enchant"));

    @Override
    public void onEnable() {
        plugin = this;

        TunnelerCrafting tunnelerCrafting = new TunnelerCrafting(tunnelerEnchantment);
        tunnelerCrafting.register();

        getServer().getPluginManager().registerEvents(this, this);

        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(tunnelerEnchantment);
        if (!registered) registerEnchantment(tunnelerEnchantment);
    }

    public static void registerEnchantment(Enchantment enchantment) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(tunnelerEnchantment)) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE) {
                    Location blockLocation = event.getBlock().getLocation();
                    Location playerLocation = event.getPlayer().getLocation();

                    int modX = event.getPlayer().getFacing().getModX();
                    int modZ = event.getPlayer().getFacing().getModZ();


                    Location block_middle_top = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX(),
                            blockLocation.getBlockY() + 1,
                            blockLocation.getBlockZ());

                    Location block_middle_bottom = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX(),
                            blockLocation.getBlockY() - 1,
                            blockLocation.getBlockZ());

                    Location block_left_top = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX() + modZ,
                            blockLocation.getBlockY() + 1,
                            blockLocation.getBlockZ() + modX);

                    Location block_left_middle = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX() + modZ,
                            blockLocation.getBlockY(),
                            blockLocation.getBlockZ() + modX);

                    Location block_left_bottom = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX() + modZ,
                            blockLocation.getBlockY() - 1,
                            blockLocation.getBlockZ() + modX);


                    Location block_right_top = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX() - modZ,
                            blockLocation.getBlockY() + 1,
                            blockLocation.getBlockZ() - modX);

                    Location block_right_middle = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX() - modZ,
                            blockLocation.getBlockY(),
                            blockLocation.getBlockZ() - modX);

                    Location block_right_bottom = new Location(
                            playerLocation.getWorld(),
                            blockLocation.getBlockX() - modZ,
                            blockLocation.getBlockY() - 1,
                            blockLocation.getBlockZ() - modX);

                    block_left_top.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                    block_left_middle.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                    block_left_bottom.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());

                    block_right_top.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                    block_right_middle.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                    block_right_bottom.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());

                    block_middle_top.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                    block_middle_bottom.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                }
            }
        }
    }

    @EventHandler
    public void prepareCraftItem(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null) {
            if (event.getRecipe().getResult().containsEnchantment(tunnelerEnchantment)) {
                if (event.getRecipe().getResult().getType() == Material.GOLDEN_PICKAXE) {
                    ItemStack givenPick = event.getInventory().getMatrix()[4];

                    assert givenPick != null;
                    HashMap<Enchantment, Integer> map = new HashMap<>(givenPick.getEnchantments());
                    event.getInventory().getResult().addUnsafeEnchantments(map);

                    if (map.containsKey(tunnelerEnchantment)) {
                        event.getInventory().setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

    @EventHandler
    public void prepareAnvilEvent(PrepareAnvilEvent event) {
        if (event.getInventory().getFirstItem() != null) {
            if (event.getInventory().getFirstItem().getEnchantments().containsKey(tunnelerEnchantment)) {
                if (event.getInventory().getFirstItem().getType() == Material.GOLDEN_PICKAXE) {
                    if (event.getResult() != null) {
                        if (event.getResult().getType() == Material.GOLDEN_PICKAXE) {
                            event.getResult().addUnsafeEnchantment(tunnelerEnchantment, 1);
                        }
                    }
                }
            }
        }
    }
}
