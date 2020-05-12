package net.scandicraft.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.scandicraft.items.register.ScandiCraftItems;

/*
ScandiCraft: Base class: EntityPlayerMP
 */
public class ScandiPlayer {

    public static void onEntityUpdate(EntityPlayer player) {
        ItemStack helmet = player.getEquipmentInSlot(4);
        ItemStack leggings = player.getEquipmentInSlot(2);
        ItemStack boots = player.getEquipmentInSlot(1);

        //ScandiCraft : BriceFab effet sur les armures de sang
        if (helmet != null && helmet.getItem().equals(ScandiCraftItems.bloody_helmet)) {
            player.setAir(280);
        }
        if (leggings != null && leggings.getItem().equals(ScandiCraftItems.bloody_leggings)) {
            PotionEffect actPotionSpeed = player.getActivePotionEffect(Potion.moveSpeed);
            if (actPotionSpeed == null) {
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100));
            }
        }
        if (boots != null && boots.getItem().equals(ScandiCraftItems.bloody_boots)) {
            player.fallDistance = 0;
        }
    }

    public static void onDeath(DamageSource cause) {
        if (cause.getEntity() instanceof EntityPlayer) {

            EntityPlayer actualPlayer = (EntityPlayer) cause.getEntity();
            ItemStack chestplate = actualPlayer.getEquipmentInSlot(3);

            if (chestplate.getItem().equals(ScandiCraftItems.bloody_chestplate)) {
                //10 = 10 demi-coeurs = 5 coeurs
                actualPlayer.setHealth(Math.min(actualPlayer.getHealth() + 10, actualPlayer.getMaxHealth()));
                /*
                    if (actualPlayer.getHealth() + 10 > actualPlayer.getMaxHealth()) {
                    actualPlayer.setHealth(actualPlayer.getMaxHealth());
                } else {
                    actualPlayer.setHealth(actualPlayer.getHealth() + 10);
                }
                 */
            }
        }
    }

}
