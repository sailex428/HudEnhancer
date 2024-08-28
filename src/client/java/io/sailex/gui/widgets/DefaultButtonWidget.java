package io.sailex.gui.widgets;

import io.sailex.util.Textures;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * A widget representing a simple button.
 *
 * @author sailex
 */
public class DefaultButtonWidget extends ButtonWidget {

    private final MinecraftClient client;

    /**
     * Constructs a DefaultButtonWidget
     *
     * @param x         the x position of the widget
     * @param y         the y position of the widget
     * @param width     the width of the widget
     * @param height    the height of the widget
     * @param text      the text that holds the button
     * @param onPress   the callback interface to be called when the button gets pressed
     */
    public DefaultButtonWidget(int x, int y, int width, int height, Text text, PressAction onPress) {
        super(x, y, width, height, text, onPress, DEFAULT_NARRATION_SUPPLIER);
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        renderButtonTexture(context);
        this.drawMessage(context, client.textRenderer, 0xFFFFFF);
    }

    private void renderButtonTexture(DrawContext context) {
        context.drawTexture(Textures.DEFAULT_BUTTON, getX(), getY(),
                0, 0, getWidth(), getHeight(), getWidth(), getHeight());
    }

}
