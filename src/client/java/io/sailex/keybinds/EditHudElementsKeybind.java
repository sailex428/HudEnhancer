package io.sailex.keybinds;

import io.sailex.screens.HudElementScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class EditHudElementsKeybind {

    public EditHudElementsKeybind(HudElementScreen screen) {
        this.screen = screen;
    }

    private final HudElementScreen screen;
    private KeyBinding keyBinding;

    public void register() {
        keyBinding =  KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "position-display.EditHudElements",
                        GLFW.GLFW_KEY_H,
                        "EditHudElements"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(screen);
                    return;
                }
                if (client.currentScreen instanceof HudElementScreen) {
                    client.setScreen(null);
                }
            }
        });
    }

}
