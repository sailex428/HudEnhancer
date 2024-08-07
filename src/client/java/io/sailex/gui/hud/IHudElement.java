package io.sailex.gui.hud;

import io.sailex.config.ConfigElement;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;

/**
 * Interface representing a HUD element.
 *
 * @author sailex
 */
public interface IHudElement extends HudRenderCallback {

    void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter);
    void drawElement(DrawContext drawContext, ClientPlayerEntity player);
    ConfigElement createUpdatedConfigElement();

    void setHue(int hue);
    int getHue();
    void setBackground(boolean isBackground);
    boolean isBackground();
    void setShadow(boolean shadow);
    boolean isShadow();
    void setColor(int color);
    int getColor();
    void setIsActive(boolean active);
    boolean isActive();

    String getKey();

    void onClick(int button, int mouseX, int mouseY);
    void onRelease(int button, int mouseX, int mouseY);

}
