package org.mimja156.tunneler;

import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockBreak {
    public static void doBreak(BlockBreakEvent event, TunnelerEnchantment tunnelerEnchantment) {
        if (event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(tunnelerEnchantment)) {
                if (Tunneler.validTools.contains(event.getPlayer().getInventory().getItemInMainHand().getType())) {
                    Location blockLocation = event.getBlock().getLocation();
                    Location playerLocation = event.getPlayer().getLocation();

                    int modX = event.getPlayer().getFacing().getModX();
                    int modZ = event.getPlayer().getFacing().getModZ();

                    if (playerLocation.getPitch() < 35 && playerLocation.getPitch() > -35) {
                        Location block_middle_top = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX(),
                                blockLocation.getBlockY() + 1,
                                blockLocation.getBlockZ());

                        Location block_middle_bottom = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX(),
                                blockLocation.getBlockY() - 1,
                                blockLocation.getBlockZ());

                        Location block_left_top = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() + modZ,
                                blockLocation.getBlockY() + 1,
                                blockLocation.getBlockZ() + modX);

                        Location block_left_middle = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() + modZ,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() + modX);

                        Location block_left_bottom = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() + modZ,
                                blockLocation.getBlockY() - 1,
                                blockLocation.getBlockZ() + modX);


                        Location block_right_top = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() - modZ,
                                blockLocation.getBlockY() + 1,
                                blockLocation.getBlockZ() - modX);

                        Location block_right_middle = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() - modZ,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() - modX);

                        Location block_right_bottom = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() - modZ,
                                blockLocation.getBlockY() - 1,
                                blockLocation.getBlockZ() - modX);

                        ItemStack itemUsed = event.getPlayer().getInventory().getItemInMainHand();

                        boolean block_left_top_did_break = block_left_top.getBlock().breakNaturally(itemUsed);
                        boolean block_left_middle_did_break = block_left_middle.getBlock().breakNaturally(itemUsed);
                        boolean block_left_bottom_did_break = block_left_bottom.getBlock().breakNaturally(itemUsed);

                        boolean block_right_top_did_break = block_right_top.getBlock().breakNaturally(itemUsed);
                        boolean block_right_middle_did_break = block_right_middle.getBlock().breakNaturally(itemUsed);
                        boolean block_right_bottom_did_break = block_right_bottom.getBlock().breakNaturally(itemUsed);

                        boolean block_middle_top_did_break = block_middle_top.getBlock().breakNaturally(itemUsed);
                        boolean block_middle_bottom_did_break = block_middle_bottom.getBlock().breakNaturally(itemUsed);

                        int damage_to_be_applied = 0;

                        if (block_left_top_did_break) damage_to_be_applied++;
                        if (block_left_middle_did_break) damage_to_be_applied++;
                        if (block_left_bottom_did_break) damage_to_be_applied++;

                        if (block_right_top_did_break) damage_to_be_applied++;
                        if (block_right_middle_did_break) damage_to_be_applied++;
                        if (block_right_bottom_did_break) damage_to_be_applied++;

                        if (block_middle_top_did_break) damage_to_be_applied++;
                        if (block_middle_bottom_did_break) damage_to_be_applied++;

                        ItemMeta meta = itemUsed.getItemMeta();
                        if (meta != null) {
                            Damageable damage = ((Damageable)meta);
                            damage.setDamage(damage.getDamage() + damage_to_be_applied);
                            itemUsed.setItemMeta(meta);
                        }

                    } else if (playerLocation.getPitch() >= 35 || playerLocation.getPitch() <= -35) {
                        Location block_middle_1 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() - 1,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ());

                        Location block_middle_2 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() + 1,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ());

                        Location block_middle_3 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX(),
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() - 1);

                        Location block_middle_4 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX(),
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() + 1);

                        Location block_corner_1 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() - 1,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() - 1);

                        Location block_corner_2 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() + 1,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() + 1);

                        Location block_corner_3 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() + 1,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() - 1);

                        Location block_corner_4 = new Location(
                                blockLocation.getWorld(),
                                blockLocation.getBlockX() - 1,
                                blockLocation.getBlockY(),
                                blockLocation.getBlockZ() + 1);

                        ItemStack itemUsed = event.getPlayer().getInventory().getItemInMainHand();

                        System.out.println(block_corner_4.getBlock().getBlockData());

                        boolean block_middle_1_did_break = block_middle_1.getBlock().breakNaturally(itemUsed);
                        boolean block_middle_2_did_break = block_middle_2.getBlock().breakNaturally(itemUsed);
                        boolean block_middle_3_did_break = block_middle_3.getBlock().breakNaturally(itemUsed);
                        boolean block_middle_4_did_break = block_middle_4.getBlock().breakNaturally(itemUsed);

                        boolean block_corner_1_did_break = block_corner_1.getBlock().breakNaturally(itemUsed);
                        boolean block_corner_2_did_break = block_corner_2.getBlock().breakNaturally(itemUsed);
                        boolean block_corner_3_did_break = block_corner_3.getBlock().breakNaturally(itemUsed);
                        boolean block_corner_4_did_break = block_corner_4.getBlock().breakNaturally(itemUsed);

                        int damage_to_be_applied = 0;

                        if (block_middle_1_did_break) damage_to_be_applied++;
                        if (block_middle_2_did_break) damage_to_be_applied++;
                        if (block_middle_3_did_break) damage_to_be_applied++;
                        if (block_middle_4_did_break) damage_to_be_applied++;

                        if (block_corner_1_did_break) damage_to_be_applied++;
                        if (block_corner_2_did_break) damage_to_be_applied++;
                        if (block_corner_3_did_break) damage_to_be_applied++;
                        if (block_corner_4_did_break) damage_to_be_applied++;


                        ItemMeta meta = itemUsed.getItemMeta();
                        if (meta != null) {
                            Damageable damage = ((Damageable)meta);
                            damage.setDamage(damage.getDamage() + damage_to_be_applied);
                            itemUsed.setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }
}
