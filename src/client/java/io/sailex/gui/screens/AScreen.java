package io.sailex.gui.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

public class AScreen extends Screen {

    protected static final int CONTENT_PADDING = 5;
    protected final MinecraftClient client;

    protected int screenX;
    protected int screenY;

    protected AScreen(Text title) {
        super(title);
        this.client = MinecraftClient.getInstance();
    }

    protected void renderScreenBackground(DrawContext context, int screenX, int screenY) {
        context.fill(screenX, screenY, width - screenX, height - screenY, 0xFF232323);
    }

    protected void renderScreenTitle(DrawContext context, int windowX, int windowY, String translationKey) {
        context.drawText(client.textRenderer,
                Text.translatable(translationKey),
                windowX + CONTENT_PADDING, windowY + 7,
                0xFFFFFFFF, true);
    }

    protected void renderScreenSection(DrawContext context, int windowX, int windowY, int linePadding, String translationKey) {
        context.drawHorizontalLine(windowX + CONTENT_PADDING, width - windowX - CONTENT_PADDING,
                windowY + linePadding, 0xFF565656);
        context.drawText(client.textRenderer,
                Text.translatable(translationKey),
                windowX + CONTENT_PADDING, windowY + linePadding + 7,
                0xFFFFFFFF, true);
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0);
    }

    protected void addWidgets(List<ClickableWidget> widgets) {
        for (ClickableWidget widget : widgets) {
            this.addDrawableChild(widget);
        }
    }

}
