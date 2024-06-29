package io.sailex.util;

import io.sailex.PositionDisplayClient;
import io.sailex.config.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.Map;


public abstract class AWidget extends ClickableWidget {

    protected final MinecraftClient client = MinecraftClient.getInstance();
    protected Map<ClickableWidget, IHudElement> widgetToHudElement;
    protected Map<String, HudElement> positionMap;
    protected int color;
    protected int backgroundColor;
    protected boolean shadow;
    private int initMouseX;
    private int initMouseY;

    public AWidget(HudElement hudElement, Text message) {
        super(hudElement.x(), hudElement.y(), hudElement.width(), hudElement.height(), message);
        this.color = hudElement.color();
        this.backgroundColor = hudElement.backgroundColor();
        this.shadow = hudElement.shadow();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isWidgetActiveAndVisible()) {
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

    private boolean isWidgetActiveAndVisible() {
        return this.active && this.visible;
    }

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
        client.setScreen(PositionDisplayClient.getScreenManager().getEditHudElementsScreen());
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        updateElementPosition((int) mouseX, (int) mouseY);

        widgetToHudElement.get(this).setPosition(getX(), getY());
        positionMap.put(this.getMessage().getString(), createUpdatedElement());
    }

    private void updateElementPosition(int mouseX, int mouseY) {
        int offsetX = mouseX - initMouseX;
        int offsetY = mouseY - initMouseY;
        initMouseX += offsetX;
        initMouseY += offsetY;

        this.setPosition(getX() + offsetX, getY() + offsetY);
    }

    private HudElement createUpdatedElement() {
        return new HudElement(getX(), getY(), getWidth(), getHeight(), color, backgroundColor, shadow);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

}
