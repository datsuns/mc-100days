package me.datsuns.mc100days.neoforge.client;

import me.datsuns.mc100days.core.DaySnapshot;
import me.datsuns.mc100days.core.DayTracker;
import me.datsuns.mc100days.neoforge.Mc100daysNeoForge;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.server.IntegratedServer;

public final class DaysOverlay {
    public static final ResourceLocation LAYER_ID = ResourceLocation.fromNamespaceAndPath(Mc100daysNeoForge.MOD_ID, "days");
    private static final int INVENTORY_HEIGHT = 50;

    private static final DayTracker TRACKER = new DayTracker();

    private DaysOverlay() {
    }

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        DaySnapshot snapshot = TRACKER.tick(minecraft.level.getDayTime());
        if (snapshot.changed()) {
            showDayTitle(minecraft, snapshot);
        }
        drawCurrentDay(guiGraphics, minecraft.font, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight(), snapshot.label());
    }

    private static void showDayTitle(Minecraft minecraft, DaySnapshot snapshot) {
        Mc100daysNeoForge.LOGGER.info("Day advanced to {}", snapshot.dayNumber());
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server == null) {
            return;
        }
        CommandSourceStack source = server.createCommandSourceStack();
        String cmd = String.format("title @a title {\"text\":\"%s\"}", snapshot.label());
        server.getCommands().performPrefixedCommand(source, cmd);
    }

    private static void drawCurrentDay(GuiGraphics graphics, Font font, int width, int height, String text) {
        int posX = (width - font.width(text)) / 2;
        int posY = height - INVENTORY_HEIGHT;
        graphics.drawString(font, text, posX, posY, 0xFFFFFFFF, false);
    }
}
