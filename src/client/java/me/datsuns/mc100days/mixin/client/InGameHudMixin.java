package me.datsuns.mc100days.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.datsuns.mc100days.Days;
import me.datsuns.mc100days.Mc100daysClient;


@Mixin(MinecraftClient.class)
public class InGameHudMixin {
	public Days days = new Days();

	@Inject(at = @At("HEAD"), method = "run")
	private void run(CallbackInfo info) {
		// This code is injected into the start of MinecraftClient.run()V
	}

	@Inject(at = @At("TAIL"), method = "render")
	public void render(boolean tick, CallbackInfo ci) {
		MinecraftClient c = MinecraftClient.getInstance();
		if(c.world == null){
			return;
		}
		long tod = c.world.getTimeOfDay();

		if( this.days.tick(tod) ){
			Mc100daysClient.LOGGER.info("changed");
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