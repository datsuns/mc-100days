package me.datsuns.mc100days.mixin;

import me.datsuns.mc100days.Days;
import me.datsuns.mc100days.MC100days;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	public Days days = new Days();

	@Inject(at = @At("TAIL"), method = "render")
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		MinecraftClient c = MinecraftClient.getInstance();
		if(c.world == null){
			return;
		}
		long tod = c.world.getTimeOfDay();

		if( this.days.tick(tod) ){
			MC100days.LOGGER.info("changed");
			IntegratedServer s = c.getServer();
			if(s == null){
				return;
			}
			ServerCommandSource src = s.getCommandSource();
			CommandManager cm = s.getCommandManager();
			String cmd = String.format("title @a title {\"text\":\"%s\"}", this.days.toString());
			cm.executeWithPrefix(src, cmd);
		}
	}
}
