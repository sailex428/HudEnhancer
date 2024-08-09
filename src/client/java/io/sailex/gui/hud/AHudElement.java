package io.sailex.gui.hud;

import io.sailex.config.ConfigElement;
import io.sailex.gui.screens.EditHudElementsScreen;
import io.sailex.util.RainbowTextUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;

/**
 * Abstract base class for HUD elements.
 *
 * @author sailex
 */
public abstract class AHudElement implements IHudElement {

    protected static final int BACKGROUND_TRANSPARENT = 0x00FFFFFF;
    protected static final int BACKGROUND_GRAY = 0x80000000;

    protected final MinecraftClient client = MinecraftClient.getInstance();
    protected final String key;

    protected int elementHeight;
    protected int elementWidth;
    protected int elementX;
    protected int elementY;

    protected int color;
    protected int hue;

    protected boolean isRainbow;
    protected boolean background;
    protected boolean shadow;
    protected boolean isActive;

    protected int initMouseX;
    protected int initMouseY;
    protected boolean isDragging;

    /**
     * Constructs a HUD element with the specified configuration.
     *
     * @param key name of the HUD element
     * @param element the configuration element
     */
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

    /**
     * Draws the HUD element on the screen.
     */
    @Override
    public abstract void drawElement(DrawContext drawContext, ClientPlayerEntity player);

    /**
     * Called to render the HUD element.
     */
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
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

    /**
     * Updates the position of the element based on mouse dragging.
     */
    private void setPosition() {
        if (!isDragging) {
            return;
        }
        int scaleFactor = (int) client.getWindow().getScaleFactor();
        int deltaX = (int) (client.mouse.getX() / scaleFactor) - initMouseX;
        int deltaY = (int) (client.mouse.getY() / scaleFactor) - initMouseY;
        elementX += deltaX;
        elementY += deltaY;
        initMouseX += deltaX;
        initMouseY += deltaY;
    }

    /**
     * Creates new ConfigElement with updated properties.
     * @return updated configElement
     */
    @Override
    public ConfigElement createUpdatedConfigElement() {
        return new ConfigElement(
                elementX, elementY,
                elementWidth, elementHeight,
                color, hue, isRainbow, background, shadow,
                isActive
        );
    }

    /**
     * Handles mouse click events.
     *
     * @param button pressed button
     * @param mouseX X coordinate of mouse click
     * @param mouseY Y coordinate of mouse click
     */
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

    /**
     * Handles mouse release events.
     *
     * @param button pressed button
     * @param mouseX X coordinate of mouse click
     * @param mouseY Y coordinate of mouse click
     */
    @Override
    public void onRelease(int button, int mouseX, int mouseY) {
        isDragging = false;
    }

    /**
     * Checks if the mouse is over the element.
     *
     * @param mouseX the X coordinate of the mouse
     * @param mouseY the Y coordinate of the mouse
     * @return {@code true} if the mouse is over the element, {@code false} otherwise
     */
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= elementX && mouseX <= elementX + elementWidth
                && mouseY >= elementY && mouseY <= elementY + elementHeight;
    }

    /**
     *  Determines if the mouse button clicked is the left button.
     *
     * @param button pressed button
     * @return {@code true} if button is left click, {@code false} otherwise
     */
    private boolean isLeftClick(int button) {
        return button == 0;
    }

    /**
     * Handles the left mouse button click.
     *
     * @param mouseX X coordinate of the mouse click
     * @param mouseY Y coordinate of the mouse click
     */
    private void handleLeftClick(double mouseX, double mouseY) {
        isDragging = true;
        initMouseX = (int) mouseX;
        initMouseY = (int) mouseY;
    }

    /**
     *  Determines if the mouse button clicked is the right button.
     *
     * @param button pressed button
     * @return {@code true} if button is right click, {@code false} otherwise
     */
    private boolean isRightClick(int button) {
        return button == 1;
    }

    /**
     * Handles the right mouse button click.
     */
    private void handleRightClick() {
        client.setScreen(new EditHudElementsScreen(this));
    }

    protected void drawText(RainbowTextUtil rainbowTextUtil, DrawContext context, String text, int x, int y) {
        if (isRainbow) {
            rainbowTextUtil.drawAnimatedRainbowText(context, text, x, y, shadow);
            return;
        }
        context.drawText(client.textRenderer, text, x, y, color, shadow);
    }

    protected void drawElementBackground(DrawContext context) {
        context.fill(elementX, elementY,
                elementX + elementWidth, elementY + elementHeight,
                background ? BACKGROUND_GRAY : BACKGROUND_TRANSPARENT
        );
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
    public boolean isRainbow() {
        return isRainbow;
    }

    @Override
    public void setIsRainbow(boolean rainbow) {
        isRainbow = rainbow;
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
