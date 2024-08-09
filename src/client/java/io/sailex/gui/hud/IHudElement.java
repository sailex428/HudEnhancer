package io.sailex.gui.hud;

import io.sailex.config.ConfigElement;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

/**
 * Interface representing a HUD element.
 *
 * @author sailex
 */
public interface IHudElement extends HudRenderCallback {

    void onHudRender(DrawContext drawContext, float tickDelta);
    void drawElement(DrawContext drawContext, ClientPlayerEntity player);
    ConfigElement createUpdatedConfigElement();

    void setHue(int hue);
    int getHue();
    void setIsRainbow(boolean rainbow);
    boolean isRainbow();
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
