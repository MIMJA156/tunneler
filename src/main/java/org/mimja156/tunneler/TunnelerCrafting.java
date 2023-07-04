package org.mimja156.tunneler;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class TunnelerCrafting {
    public static void register(TunnelerEnchantment tunnelerEnchantment){
        for (Material material: Tunneler.validTools) {
            NamespacedKey key = new NamespacedKey(Tunneler.plugin, "mimja_tunneler_crafting_" + material.name().toLowerCase());

            ItemStack item = new ItemStack(material);
            item.addUnsafeEnchantment(tunnelerEnchantment, 1);

            List<Component> loreList = new ArrayList<>();
            Component text = Component.text(tunnelerEnchantment.getName());
            loreList.add(text);

            ItemMeta meta = item.getItemMeta();
            meta.lore(loreList);
            item.setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(key, item);
            recipe.shape("!@!", "@$@", "!@!");

            recipe.setIngredient('!', Material.GOLD_INGOT);
            recipe.setIngredient('@', Material.REDSTONE);
            recipe.setIngredient('$', material);

            getServer().addRecipe(recipe);
        }
    }
}
