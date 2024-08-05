package io.sailex.keybinds;

import io.sailex.gui.screens.MoveHudElementsScreen;
import io.sailex.util.TranslationKeys;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

/**
 * Manages the key binding for opening the MoveHudElement screen.
 *
 * @author sailex
 */
public class MoveHudElementsKeybind {

    /**
     * Constructs a {@code MoveHudElementsKeybind} instance.
     *
     * @param screen the screen to be opened when the key is pressed
     */
    public MoveHudElementsKeybind(MoveHudElementsScreen screen) {
        this.screen = screen;
    }

    private final MoveHudElementsScreen screen;
    private KeyBinding keyBinding;

    /**
     * Registers the key binding and sets up an event listener to open the screen.
     */
    public void register() {
        keyBinding = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        TranslationKeys.KEY_ADD_HUD_ELEMENT,
                        GLFW.GLFW_KEY_H,
                        "Position Display"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!keyBinding.wasPressed()) {
                return;
            }
            if (client.currentScreen == null) {
                client.setScreen(screen);
            }
        });
    }

}
