package me.datsuns.mc100days;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mc100daysClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("mc100days");
    public Days days;

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        LOGGER.info("Hello Fabric client!");
        this.days = new Days();
        HudElementRegistry.addLast(Identifier.of("mc100days", "render"), new DaysRenderer());
    }
}