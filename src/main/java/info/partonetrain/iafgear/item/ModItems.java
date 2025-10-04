package info.partonetrain.iafgear.item;

import com.github.alexthe666.iceandfire.IceAndFire;
import info.partonetrain.iafgear.IAFGear;
import info.partonetrain.iafgear.item.ShinyScaleItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IAFGear.MOD_ID);

    public static final RegistryObject<Item> SHINY_SCALE_BLUE = ITEMS.register("shiny_scale_blue",
            () -> new ShinyScaleItem("blue"));

    public static final RegistryObject<Item> SHINY_SCALE_BRONZE = ITEMS.register("shiny_scale_bronze",
            () -> new ShinyScaleItem("bronze"));

    public static final RegistryObject<Item> SHINY_SCALE_DEEP_BLUE = ITEMS.register("shiny_scale_deep_blue",
            () -> new ShinyScaleItem("deep_blue"));

    public static final RegistryObject<Item> SHINY_SCALE_GREEN = ITEMS.register("shiny_scale_green",
            () -> new ShinyScaleItem("green"));

    public static final RegistryObject<Item> SHINY_SCALE_PURPLE = ITEMS.register("shiny_scale_purple",
            () -> new ShinyScaleItem("purple"));

    public static final RegistryObject<Item> SHINY_SCALE_RED = ITEMS.register("shiny_scale_red",
            () -> new ShinyScaleItem("red"));

    public static final RegistryObject<Item> SHINY_SCALE_TEAL = ITEMS.register("shiny_scale_teal",
            () -> new ShinyScaleItem("teal"));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}