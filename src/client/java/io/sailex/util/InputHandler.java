package io.sailex.util;

import io.sailex.mixin.client.BoundKeyAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

import org.lwjgl.glfw.GLFW;

public class InputHandler {

    public static final InputHandler INSTANCE = new InputHandler();
    private final GameOptions options = MinecraftClient.getInstance().options;
    private boolean isPressed = false;

    private InputHandler() {}

    public void onKey(int button, int action) {
        int attackKeyCode = ((BoundKeyAccessor) this.options.attackKey).getBoundKey().getCode();
        int useKeyCode = ((BoundKeyAccessor) this.options.useKey).getBoundKey().getCode();

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

    public static InputHandler getInstance() {
        return INSTANCE;
    }

}
