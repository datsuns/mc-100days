package me.datsuns.mc100days.neoforge;

import com.mojang.logging.LogUtils;
import me.datsuns.mc100days.neoforge.client.Mc100daysNeoForgeClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(Mc100daysNeoForge.MOD_ID)
public final class Mc100daysNeoForge {
    public static final String MOD_ID = "mc100days";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Mc100daysNeoForge(IEventBus modEventBus) {
        modEventBus.addListener(this::onCommonSetup);
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            Mc100daysNeoForgeClient.init(modEventBus);
        }
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Hello NeoForge world!");
    }
}
