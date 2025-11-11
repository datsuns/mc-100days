package me.datsuns.mc100days.neoforge.client;

import me.datsuns.mc100days.neoforge.Mc100daysNeoForge;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = Mc100daysNeoForge.MOD_ID, value = Dist.CLIENT)
public final class Mc100daysNeoForgeClient {
    private Mc100daysNeoForgeClient() {
    }

    @SubscribeEvent
    public static void onRegisterLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(DaysOverlay.LAYER_ID, DaysOverlay::render);
    }
}
