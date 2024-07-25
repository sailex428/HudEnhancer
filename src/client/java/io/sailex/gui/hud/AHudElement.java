package io.sailex.gui.hud;

import io.sailex.config.ConfigElement;
import io.sailex.gui.screens.EditHudElementsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public abstract class AHudElement implements IHudElement {

    protected final MinecraftClient client = MinecraftClient.getInstance();
    protected final String key;

    protected int elementHeight;
    protected int elementWidth;
    protected int elementX;
    protected int elementY;

    protected int color;
    protected int hue;
    protected boolean background;
    protected boolean shadow;
    protected boolean isActive;

    protected int initMouseX;
    protected int initMouseY;
    protected boolean isDragging;

    public AHudElement(String key, ConfigElement element) {
        this.key = key;
        this.elementX = element.x();
        this.elementY = element.y();
        this.elementWidth = element.width();
        this.elementHeight = element.height();
        this.color = element.color();
        this.hue = element.hue();
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
        if (!isActive) {
            return;
        }
        setPosition();
        drawElement(drawContext, player);
    }

    private void setPosition() {
        if (isDragging) {
            int deltaX = (int) (client.mouse.getX() / 2) - initMouseX;
            int deltaY = (int) (client.mouse.getY() / 2) - initMouseY;
            elementX += deltaX;
            elementY += deltaY;
            initMouseX += deltaX;
            initMouseY += deltaY;
        }
    }

    @Override
    public ConfigElement createUpdatedConfigElement() {
        return new ConfigElement(
                elementX, elementY,
                elementWidth, elementHeight,
                color, hue, background, shadow,
                isActive
        );
    }

    @Override
    public void onClick(int button, int mouseX, int mouseY) {
        if (!this.isMouseOver(mouseX, mouseY)) {
            return;
        }
        if (!this.isActive) {
            return;
        }
        if (isLeftClick(button)) {
            handleLeftClick(mouseX, mouseY);
        }
        if (isRightClick(button)) {
            handleRightClick();
        }
    }

    @Override
    public void onRelease(int button, int mouseX, int mouseY) {
        isDragging = false;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= elementX && mouseX <= elementX + elementWidth
                && mouseY >= elementY && mouseY <= elementY + elementHeight;
    }

    private boolean isLeftClick(int button) {
        return button == 0;
    }

    private void handleLeftClick(double mouseX, double mouseY) {
        isDragging = true;
        initMouseX = (int) mouseX;
        initMouseY = (int) mouseY;
    }

    private boolean isRightClick(int button) {
        return button == 1;
    }

    private void handleRightClick() {
        client.setScreen(new EditHudElementsScreen(this));
    }

    @Override
    public boolean isShadow() {
        return shadow;
    }

    @Override
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean isBackground() {
        return background;
    }

    @Override
    public void setBackground(boolean background) {
        this.background = background;
    }

    @Override
    public void setHue(int hue) {
        this.hue = hue;
    }

    @Override
    public int getHue() {
        return hue;
    }

    @Override
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String getKey() {
        return key;
    }

}
