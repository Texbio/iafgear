package info.partonetrain.iafgear;

import info.partonetrain.iafgear.init.IAFGearTraits;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;

public class SideProxy implements net.silentchaos512.gear.IProxy {

    private MinecraftServer server = null;

    SideProxy() {
        IAFGearTraits.registerSerializers();
    }

    @Nullable
    @Override
    public Player getClientPlayer() {
        return null;
    }

    @Nullable
    @Override
    public Level getClientLevel() {
        return null;
    }

    @Override
    public boolean checkClientInstance() {
        return false;
    }

    @Override
    public boolean checkClientConnection() {
        return false;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return server;
    }

    public static class Server extends SideProxy {
        public Server() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client extends SideProxy {
        public Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        }

        private void clientSetup(FMLClientSetupEvent event) {
        }

        @Nullable
        @Override
        public Player getClientPlayer() {
            return net.minecraft.client.Minecraft.getInstance().player;
        }

        @Nullable
        @Override
        public Level getClientLevel() {
            return net.minecraft.client.Minecraft.getInstance().level;
        }

        @Override
        public boolean checkClientInstance() {
            return net.minecraft.client.Minecraft.getInstance() != null;
        }

        @Override
        public boolean checkClientConnection() {
            return checkClientInstance() && getClientPlayer() != null;
        }
    }
}