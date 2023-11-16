package dev.rgbmc.simpleinv.item;

import com.cryptomorin.xseries.SkullUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullBuilder {

    public static ItemStack applySkin(ItemStack itemStack, String texture) {
        if (!itemStack.hasItemMeta()) throw new IllegalArgumentException("Invalid Head Item: Miss Item Meta");
        if (!(itemStack.getItemMeta() instanceof SkullMeta))
            throw new IllegalArgumentException("Invalid Head Item: Not A Skull Item");
        itemStack.setItemMeta(SkullUtils.applySkin(itemStack.getItemMeta(), texture));
        return itemStack;
    }
}
