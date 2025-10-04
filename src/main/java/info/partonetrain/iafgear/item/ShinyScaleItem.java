package info.partonetrain.iafgear.item;

import net.minecraft.world.item.Item;

public class ShinyScaleItem extends Item {

    public ShinyScaleItem(String colorString) {
        super(new Item.Properties());
        // The color string is passed but not used directly since registration
        // is now handled in ModItems using DeferredRegister
        // The registry name is set automatically by the DeferredRegister
    }
}