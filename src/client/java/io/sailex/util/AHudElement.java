package io.sailex.util;

import io.sailex.screens.HudElementScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public abstract class AHudElement implements IHudElement {

    protected final MinecraftClient client = MinecraftClient.getInstance();
    protected int elementHeight;
    protected int elementWidth;
    protected int elementX;
    protected int elementY;

    @Override
    public abstract void drawElement(DrawContext drawContext, ClientPlayerEntity player);

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.getDebugHud().shouldShowDebugHud()) {
            return;
        }
        if (client.currentScreen instanceof HudElementScreen) {
            return;
        }
        drawElement(drawContext, player);
    }

    public void setElementY(int elementY) {
        this.elementY = elementY;
    }

    public void setElementX(int elementX) {
        this.elementX = elementX;
    }

}
