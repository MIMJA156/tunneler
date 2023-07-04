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
    public NamespacedKey key = new NamespacedKey(Tunneler.plugin, "mimja_tunneler_crafting");
    public ShapedRecipe tunnelerRecipe;

    public TunnelerCrafting(TunnelerEnchantment tunnelerEnchantment){
        ItemStack tunnelerItem = new ItemStack(Material.GOLDEN_PICKAXE);
        tunnelerItem.addUnsafeEnchantment(tunnelerEnchantment, 1);

        ItemMeta meta = tunnelerItem.getItemMeta();

        if (meta != null) {
            List<Component> loreList = new ArrayList<>();
            Component text = Component.text("§7Tunneler I");
            loreList.add(text);
            meta.lore(loreList);

            tunnelerItem.setItemMeta(meta);
        }

        tunnelerRecipe = new ShapedRecipe(key, tunnelerItem);
    }

    public void register(){
        tunnelerRecipe.shape("!@!", "@$@", "!@!");

        tunnelerRecipe.setIngredient('!', Material.GOLD_INGOT);
        tunnelerRecipe.setIngredient('@', Material.REDSTONE);
        tunnelerRecipe.setIngredient('$', Material.GOLDEN_PICKAXE);

        getServer().addRecipe(tunnelerRecipe);
    }
}
