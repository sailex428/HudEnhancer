package io.sailex.hud.util;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;

import org.lwjgl.glfw.GLFW;

import java.util.List;

public class MouseInputHandler{

    private final List<IHudElement> hudElements;

    public MouseInputHandler(List<IHudElement> hudElements) {
        this.hudElements = hudElements;
    }

    public void register() {
        ScreenEvents.AFTER_INIT.register((client, currentScreen, scaledWidth, scaledHeight) -> {
            ScreenMouseEvents.afterMouseClick(currentScreen).register((screen, mouseX, mouseY, button) -> {
                if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    return;
                }
                for (IHudElement element : hudElements) {
                    if (element.isMouseOverElement((int) mouseX, (int) mouseY)) {
                        element.setInitMouse((int) mouseX , (int) mouseY);
                        element.setIsDragging(true);
                        break;
                    }
                }
            });
            ScreenMouseEvents.afterMouseRelease(currentScreen).register((screen, mouseX, mouseY, button) -> {
                if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    return;
                }
                for (IHudElement element : hudElements) {
                    element.setIsDragging(false);
                }
            });
        });

    }

}
