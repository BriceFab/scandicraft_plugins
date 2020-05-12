package net.minecraft.enchantment;

import net.minecraft.item.*;
import net.scandicraft.items.ScandiumBow;

public enum EnumEnchantmentType {
    ALL,
    ARMOR,
    ARMOR_FEET,
    ARMOR_LEGS,
    ARMOR_TORSO,
    ARMOR_HEAD,
    WEAPON,
    DIGGER,
    FISHING_ROD,
    BREAKABLE,
    BOW;

    /**
     * Return true if the item passed can be enchanted by a enchantment of this type.
     */
    public boolean canEnchantItem(Item itemIn) {
        if (this == ALL) {
            return true;
        } else if (this == BREAKABLE && itemIn.isDamageable()) {
            return true;
        } else if (itemIn instanceof ItemArmor) {
            if (this == ARMOR) {
                return true;
            } else {
                ItemArmor itemarmor = (ItemArmor) itemIn;
                return itemarmor.armorType == 0 ? this == ARMOR_HEAD : (itemarmor.armorType == 2 ? this == ARMOR_LEGS : (itemarmor.armorType == 1 ? this == ARMOR_TORSO : (itemarmor.armorType == 3 && this == ARMOR_FEET)));
            }
        } else {
            return itemIn instanceof ItemSword ? this == WEAPON : (itemIn instanceof ItemTool ? this == DIGGER : (itemIn instanceof ItemBow ? this == BOW : (itemIn instanceof ScandiumBow ? this == BOW : (itemIn instanceof ItemFishingRod && this == FISHING_ROD))));
        }
    }
}
