package me.datsuns.mc100days;

import me.datsuns.mc100days.core.DaySnapshot;
import me.datsuns.mc100days.core.DayTracker;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.Colors;

public class DaysRenderer implements HudElement {
    private static final int INVENTORY_HEIGHT = 50;

    private final DayTracker tracker = new DayTracker();

    @Override
    public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c.world == null) {
            return;
        }
        long tod = c.world.getTimeOfDay();

        DaySnapshot update = this.tracker.tick(tod);
        if (update.changed()) {
            showDayScreen(c, update);
        }
        drawCurrentDay(drawContext, c.textRenderer, c.getWindow(), update.label());
    }

    public void showDayScreen(MinecraftClient client, DaySnapshot snapshot) {
        Mc100daysClient.LOGGER.info("changed");
        IntegratedServer s = client.getServer();
        if (s == null) {
            return;
        }
        ServerCommandSource src = s.getCommandSource();
        CommandManager cm = s.getCommandManager();
        String cmd = String.format("title @a title {\"text\":\"%s\"}", snapshot.label());
        cm.executeWithPrefix(src, cmd);
    }

    public void drawCurrentDay(DrawContext dc, TextRenderer textRenderer, Window window, String dayText) {
        float posX = (window.getScaledWidth() / 2) - (textRenderer.getWidth(dayText) / 2);
        float posY = window.getScaledHeight() - INVENTORY_HEIGHT;
        dc.drawText(textRenderer, dayText, (int) posX, (int) posY, Colors.WHITE, false);
    }
}
