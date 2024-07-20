package io.sailex.gui.hud;

import io.sailex.config.HudElement;
import io.sailex.gui.screens.MoveHudElementsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public abstract class AHudElement implements IHudElement {

    protected final MinecraftClient client = MinecraftClient.getInstance();
    protected int elementHeight;
    protected int elementWidth;
    protected int elementX;
    protected int elementY;
    protected int color;
    protected boolean background;
    protected boolean shadow;
    protected boolean isActive;

    public AHudElement(HudElement element) {
        this.elementX = element.x();
        this.elementY = element.y();
        this.elementWidth = element.width();
        this.elementHeight = element.height();
        this.color = element.color();
        this.shadow = element.shadow();
        this.background = element.background();
        this.isActive = element.isActive();
    }

    @Override
    public abstract void drawElement(DrawContext drawContext, ClientPlayerEntity player);

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.getDebugHud().shouldShowDebugHud()) {
            return;
        }
        if (client.currentScreen instanceof MoveHudElementsScreen) {
            return;
        }
        drawElement(drawContext, player);
    }

    @Override
    public void setFields(int elementX, int elementY, int color, boolean shadow, boolean background, boolean active) {
        this.elementX = elementX;
        this.elementY = elementY;
        this.color = color;
        this.shadow = shadow;
        this.background = background;
        this.isActive = active;
    }

}
