package me.datsuns.mc100days.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.datsuns.mc100days.Days;
import me.datsuns.mc100days.Mc100daysClient;


@Mixin(InGameHud.class)
public class InGameHudMixin {
    public Days days = new Days();
    public final int InventoryHeight = 50;

    @Inject(at = @At("TAIL"), method = "render")
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info) throws Exception {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c.world == null) {
            return;
        }
        long tod = c.world.getTimeOfDay();

        if (this.days.tick(tod)) {
            showDayScreen(c);
        }
        drawCurrentDay(context, c.textRenderer, c.getWindow(), this.days.toString());
    }

    public void showDayScreen(MinecraftClient client) {
        Mc100daysClient.LOGGER.info("changed");
        IntegratedServer s = client.getServer();
        if (s == null) {
            return;
        }
        ServerCommandSource src = s.getCommandSource();
        CommandManager cm = s.getCommandManager();
        String cmd = String.format("title @a title {\"text\":\"%s\"}", this.days.toString());
        cm.executeWithPrefix(src, cmd);
    }

    public void drawCurrentDay(DrawContext dc, TextRenderer textRenderer, Window window, String dayText) {
        float posX = (window.getScaledWidth() / 2) - (textRenderer.getWidth(dayText) / 2);
        float posY = window.getScaledHeight() - InventoryHeight;
        dc.drawText(textRenderer, dayText, (int) posX, (int) posY, 0xFFFFFF, false);
    }
}
