package io.sailex.util;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final KeyInputHandler INSTANCE = new KeyInputHandler();
    private final GameOptions options = MinecraftClient.getInstance().options;
    private boolean isPressed = false;

    private KeyInputHandler() {}

    public void onKey(int button, int action) {
        int attackKeyCode = KeyBindingHelper.getBoundKeyOf(this.options.attackKey).getCode();
        int useKeyCode = KeyBindingHelper.getBoundKeyOf(this.options.useKey).getCode();

        if (button != attackKeyCode && button != useKeyCode) {
            this.isPressed = false;
            return;
        }
        if (action == GLFW.GLFW_PRESS) {
            if (!this.isPressed) {
                this.isPressed = true;
                CPSCalculator.getInstance().onKeyPress();
                return;
            }
        }
        this.isPressed = false;
    }

    public static KeyInputHandler getInstance() {
        return INSTANCE;
    }

}
