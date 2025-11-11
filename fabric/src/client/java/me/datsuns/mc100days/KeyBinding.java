package me.datsuns.mc100days;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    static net.minecraft.client.option.KeyBinding Debug = new net.minecraft.client.option.KeyBinding("key.mc100days.visible", GLFW.GLFW_KEY_COMMA, net.minecraft.client.option.KeyBinding.Category.MISC);

    public static void initialize() {
        KeyBindingHelper.registerKeyBinding(Debug);
    }

    public static boolean debug() {
        return Debug.wasPressed();
    }
}
