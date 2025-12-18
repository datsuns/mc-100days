package me.datsuns.mc100days.client;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public final class Mc100daysClient {
    private Mc100daysClient() {
    }

    public static void init() {
        NeoForge.EVENT_BUS.addListener(Mc100daysClient::onRenderGui);
    }

    private static void onRenderGui(RenderGuiEvent.Post event) {
        DaysOverlay.render(event.getGuiGraphics(), event.getPartialTick());
    }
}
