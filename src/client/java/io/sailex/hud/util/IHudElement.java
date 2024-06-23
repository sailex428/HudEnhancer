package io.sailex.hud.util;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public interface IHudElement extends HudRenderCallback {

    void onHudRender(DrawContext drawContext, float tickDelta);
    void drawElement(DrawContext drawContext, ClientPlayerEntity player);
    boolean isMouseOverElement(int mouseX, int mouseY);
    void setIsDragging(boolean isDragging);
    void setInitMouse(int mouseX, int mouseY);
}
