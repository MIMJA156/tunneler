package org.mimja156.tunneler;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public final class Tunneler extends JavaPlugin implements Listener {
    public static JavaPlugin plugin;
    public static TunnelerEnchantment tunnelerEnchantment = new TunnelerEnchantment(NamespacedKey.minecraft("mimja_tunneler_enchant"));

    public static List<Material> validTools = new ArrayList<Material>(){{
        add(Material.NETHERITE_PICKAXE);
        add(Material.DIAMOND_PICKAXE);
        add(Material.GOLDEN_PICKAXE);
        add(Material.IRON_PICKAXE);
        add(Material.STONE_PICKAXE);
        add(Material.WOODEN_PICKAXE);
    }};

    @Override
    public void onEnable() {
        plugin = this;

        TunnelerCrafting.register(tunnelerEnchantment);

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
        BlockBreak.doBreak(event, tunnelerEnchantment);
    }

    @EventHandler
    public void prepareCraftItem(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null) {
            if (event.getRecipe().getResult().containsEnchantment(tunnelerEnchantment)) {
                if (validTools.contains(event.getRecipe().getResult().getType())) {
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
                if (validTools.contains(event.getInventory().getFirstItem().getType())) {
                    if (event.getResult() != null) {
                        event.getResult().addUnsafeEnchantment(tunnelerEnchantment, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void prepareGrindstoneEvent(PrepareGrindstoneEvent event) {
        if (event.getInventory().getUpperItem() != null) {
            if (event.getInventory().getUpperItem().getEnchantments().containsKey(tunnelerEnchantment)) {
                List<String> lore = event.getResult().getLore();
                lore.removeIf(loreItem -> loreItem.equalsIgnoreCase(tunnelerEnchantment.getName()));
                System.out.println(lore);
                event.getResult().setLore(lore);
            }
        }
    }
}
