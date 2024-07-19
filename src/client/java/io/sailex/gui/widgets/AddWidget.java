package io.sailex.gui.widgets;

import io.sailex.PositionDisplayClient;
import io.sailex.util.Textures;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class AddWidget extends ClickableWidget {

    private final MinecraftClient client;
    private boolean wasHovered;
    private long hoverStartTime = 0;

    private final int originalX;
    private final int originalY;
    private final int originalWidth;
    private final int originalHeight;

    public AddWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.of("Add Widget"));
        client = MinecraftClient.getInstance();
        this.originalX = x;
        this.originalY = y;
        this.originalWidth = width;
        this.originalHeight = height;
        this.wasHovered = false;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        handleAnimation();
        drawAddWidgetTexture(context);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        client.setScreen(PositionDisplayClient.getScreenManager().getAddHudElementsScreen());
    }

    private void drawAddWidgetTexture(DrawContext context) {
        context.drawTexture(Textures.ADD_WIDGET, getX(), getY(),
                0, 0, getWidth(), getHeight(), getWidth(), getHeight());
    }

    private void handleAnimation() {
        boolean isCurrentlyHovered = this.isHovered();

        if (isCurrentlyHovered != wasHovered) {
            hoverStartTime = System.currentTimeMillis();
            wasHovered = this.isHovered();
        }

        float progress = calculateAnimation(isCurrentlyHovered);
        float scale = 1.0f + (0.075f * progress);

        int offsetX = (int) ((getWidth() * scale - getWidth()) / 2);
        int offsetY = (int) ((getHeight() * scale - getHeight()) / 2);

        setPosition(offsetX, offsetY, scale);
    }

    private float calculateAnimation(boolean isCurrentlyHovered) {
        long elapsed = System.currentTimeMillis() - hoverStartTime;
        float progress = MathHelper.clamp((float) elapsed / 100L, 0.0f, 1.0f);
        return isCurrentlyHovered ? progress : 1.0f - progress;
    }

    private void setPosition(int offsetX, int offsetY, float scale) {
        setX(this.originalX - offsetX);
        setWidth((int) (originalWidth * scale));
        setY(this.originalY - offsetY);
        setHeight((int) (originalHeight * scale));
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

}
