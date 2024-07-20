package io.sailex.gui.widgets;

import io.sailex.config.HudElement;
import io.sailex.gui.screens.EditHudElementsScreen;
import io.sailex.gui.hud.IHudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.Map;


public abstract class AWidget extends ClickableWidget {

    protected final MinecraftClient client = MinecraftClient.getInstance();
    protected Map<AWidget, IHudElement> widgetToHudElement;
    protected Map<String, HudElement> positionMap;
    protected int color;
    protected int hue;
    protected boolean background;
    protected boolean shadow;
    private int initMouseX;
    private int initMouseY;
    private boolean isActive;

    public AWidget(HudElement hudElement, Text message) {
        super(hudElement.x(), hudElement.y(), hudElement.width(), hudElement.height(), message);
        this.color = hudElement.color();
        this.hue = hudElement.hue();
        this.background = hudElement.background();
        this.shadow = hudElement.shadow();
        this.isActive = hudElement.isActive();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isActive) {
            return false;
        }
        if (isLeftClick(button) && clicked(mouseX, mouseY)) {
            handleLeftClick(mouseX, mouseY);
            return true;
        }
        if (isRightClick(button) && clicked(mouseX, mouseY)) {
            handleRightClick();
            return true;
        }
        return false;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    private boolean isLeftClick(int button) {
        return button == 0;
    }

    private void handleLeftClick(double mouseX, double mouseY) {
        this.playDownSound(MinecraftClient.getInstance().getSoundManager());
        initMouseX = (int) mouseX;
        initMouseY = (int) mouseY;
    }

    private boolean isRightClick(int button) {
        return button == 1;
    }

    private void handleRightClick() {
        this.playDownSound(MinecraftClient.getInstance().getSoundManager());
        client.setScreen(new EditHudElementsScreen(this));
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        updateWidgetPosition((int) mouseX, (int) mouseY);
    }

    private void updateWidgetPosition(int mouseX, int mouseY) {
        int offsetX = mouseX - initMouseX;
        int offsetY = mouseY - initMouseY;
        initMouseX += offsetX;
        initMouseY += offsetY;

        this.setPosition(getX() + offsetX, getY() + offsetY);
        updateElementConfig();
    }

    private void updateElementConfig() {
        HudElement updatedElement = new HudElement(
                getX(), getY(),
                getWidth(), getHeight(),
                color, hue, background, shadow,
                isActive
        );
        positionMap.put(this.getMessage().getString(), updatedElement);
        updateHudElement();
    }

    private void updateHudElement() {
        IHudElement hudElement = widgetToHudElement.get(this);
        if (hudElement == null) {
            return;
        }
        hudElement.setFields(getX(), getY(),
                getColor(), isShadow(), isBackground(),
                isActive()
        );
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
        updateElementConfig();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        updateElementConfig();
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
        updateElementConfig();
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getHue() {
        return hue;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
        updateElementConfig();
    }

}
