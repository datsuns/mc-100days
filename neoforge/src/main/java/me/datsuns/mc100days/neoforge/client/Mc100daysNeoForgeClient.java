package me.datsuns.mc100days.neoforge.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public final class Mc100daysNeoForgeClient {
    private Mc100daysNeoForgeClient() {
    }

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(Mc100daysNeoForgeClient::onRegisterLayers);
    }

    private static void onRegisterLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(DaysOverlay.LAYER_ID, DaysOverlay::render);
    }
}

