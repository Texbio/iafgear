package info.partonetrain.iafgear;

import info.partonetrain.iafgear.init.IAFGearTraits;
import info.partonetrain.iafgear.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(IAFGear.MOD_ID)
public class IAFGear {
    public static final String MOD_ID = "iafgear";
    public static final Logger LOGGER = LogManager.getLogger();

    public static net.silentchaos512.gear.IProxy PROXY;

    public IAFGear() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register items using DeferredRegister
        ModItems.register(modEventBus);

        // Setup proxy
        PROXY = DistExecutor.unsafeRunForDist(
                () -> SideProxy.Client::new,
                () -> SideProxy.Server::new
        );

        // REMOVED: Manual trait serializer registration
        // Silent Gear handles this automatically when trait classes are loaded
        // IAFGearTraits.registerSerializers();

        LOGGER.info("DragonGear loading for Minecraft 1.20.1");
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}