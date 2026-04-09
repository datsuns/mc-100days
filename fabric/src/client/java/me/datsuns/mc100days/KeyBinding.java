package me.datsuns.mc100days;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    static KeyMapping Debug = new KeyMapping("key.mc100days.visible", GLFW.GLFW_KEY_COMMA, KeyMapping.Category.MISC);

    public static void initialize() {
        KeyMappingHelper.registerKeyMapping(Debug);
    }

    public static boolean debug() {
        return Debug.consumeClick();
    }
}
