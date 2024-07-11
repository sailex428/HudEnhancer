package io.sailex.gui.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public interface IHudElement extends HudRenderCallback {

    void onHudRender(DrawContext drawContext, float tickDelta);
    void drawElement(DrawContext drawContext, ClientPlayerEntity player);
    void setPosition(int elementX, int elementY);
    void setStyling(int color, boolean shadow, boolean background);

}
