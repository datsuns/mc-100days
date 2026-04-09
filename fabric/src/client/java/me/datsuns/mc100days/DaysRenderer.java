package me.datsuns.mc100days;

import me.datsuns.mc100days.core.DaySnapshot;
import me.datsuns.mc100days.core.DayTracker;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.DeltaTracker;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.resources.Identifier;

public class DaysRenderer implements HudElement {
    private static final int INVENTORY_HEIGHT = 50;

    private final DayTracker tracker = new DayTracker();

@Override
    public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft c = Minecraft.getInstance();
        if (c.level == null) {
            return;
        }
        long tod = c.level.getOverworldClockTime();

        DaySnapshot update = this.tracker.tick(tod);
        if (update.changed()) {
            showDayScreen(c, update);
        }
        drawCurrentDay(graphics, c.font, c.getWindow(), update.label());
    }

    public void showDayScreen(Minecraft client, DaySnapshot snapshot) {
        Mc100daysClient.LOGGER.info("changed");
        IntegratedServer s = client.getSingleplayerServer();
        if (s == null) {
            return;
        }
        CommandSourceStack src = s.createCommandSourceStack();
        Commands cm = s.getCommands();
        String cmd = String.format("title @a title {\"text\":\"%s\"}", snapshot.label());
        cm.performPrefixedCommand(src, cmd);
    }

    public void drawCurrentDay(GuiGraphicsExtractor dc, Font textRenderer, Window window, String dayText) {
        float posX = (window.getGuiScaledWidth() / 2.0f) - (textRenderer.width(dayText) / 2.0f);
        float posY = window.getGuiScaledHeight() - INVENTORY_HEIGHT;
        dc.text(textRenderer, dayText, (int) posX, (int) posY, 0xFFFFFFFF, false);
    }
}
