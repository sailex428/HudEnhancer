package io.sailex.hud.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public abstract class AHudElement implements IHudElement {

    protected final MinecraftClient client = MinecraftClient.getInstance();
    protected int elementHeight;
    protected int elementWidth;
    protected int elementX;
    protected int elementY;
    protected int initMouseX;
    protected int initMouseY;
    protected boolean isDragging;

    @Override
    public abstract void drawElement(DrawContext drawContext, ClientPlayerEntity player);

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.getDebugHud().shouldShowDebugHud()) {
            return;
        }
        if (isDragging) {
            int deltaX = (int) (client.mouse.getX() / client.getWindow().getScaleFactor()) - initMouseX;
            int deltaY = (int) (client.mouse.getY() / client.getWindow().getScaleFactor()) - initMouseY;
            elementX += deltaX;
            elementY += deltaY;
            initMouseX += deltaX;
            initMouseY += deltaY;
        }
        drawElement(drawContext, player);
    }

    @Override
    public boolean isMouseOverElement(int mouseX, int mouseY) {
        return mouseX >= elementX && mouseX <= elementX + elementWidth
                && mouseY >= elementY && mouseY <= elementY + elementHeight;
    }

    @Override
    public void setIsDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    @Override
    public void setInitMouse(int initMouseX, int initMouseY) {
        this.initMouseX = initMouseX;
        this.initMouseY = initMouseY;
    }

}
