package me.datsuns.mc100days.mixin;

import me.datsuns.mc100days.Days;
import me.datsuns.mc100days.MC100days;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Shadow @Final private MinecraftClient client;
	public Days days = new Days();
	public final int InventoryHeight = 50;

	@Inject(at = @At("TAIL"), method = "render")
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		MinecraftClient c = MinecraftClient.getInstance();
		if(c.world == null){
			return;
		}
		long tod = c.world.getTimeOfDay();
		if( this.days.tick(tod) ){
			showDayScreen(c);
		}
		drawCurrentDay(matrixStack, c.textRenderer, this.days.toString());
	}

	public void showDayScreen(MinecraftClient client){
		MC100days.LOGGER.info("changed");
		IntegratedServer s = client.getServer();
		if(s == null){
			return;
		}
		ServerCommandSource src = s.getCommandSource();
		CommandManager cm = s.getCommandManager();
		String cmd = String.format("title @a title {\"text\":\"%s\"}", this.days.toString());
		cm.executeWithPrefix(src, cmd);
	}

	public void drawCurrentDay(MatrixStack matrixStack, TextRenderer textRenderer, String dayText){
		var w = this.client.getWindow();
		float posX = (w.getScaledWidth() / 2) - (textRenderer.getWidth(dayText) / 2);
		float posY = w.getScaledHeight() - InventoryHeight;
		textRenderer.draw(matrixStack, dayText, posX, posY, 0xFFFFFF);
	}
}
