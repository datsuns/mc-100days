package me.datsuns.mc100days.neoforge;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Mc100daysNeoForge.MOD_ID)
public final class Mc100daysNeoForge {
    public static final String MOD_ID = "mc100days";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Mc100daysNeoForge(IEventBus modEventBus) {
        modEventBus.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Hello NeoForge world!");
    }
}
