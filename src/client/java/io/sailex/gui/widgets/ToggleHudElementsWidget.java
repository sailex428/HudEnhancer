package io.sailex.gui.widgets;

import io.sailex.HudEnhancerClient;
import io.sailex.util.Textures;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

/**
 * A widget representing a button to open the toggle hud elements screen.
 *
 * @author sailex
 */
public class ToggleHudElementsWidget extends ClickableWidget {

    private final MinecraftClient client;
    private boolean wasHovered;
    private long hoverStartTime = 0;

    private final int originalX;
    private final int originalY;
    private final int originalWidth;
    private final int originalHeight;

    /**
     * Constructs a {@code ToggleHudElementsWidget}.
     *
     * @param x      the x position of the widget
     * @param y      the y position of the widget
     * @param width  the width of the widget
     * @param height the height of the widget
     */
    public ToggleHudElementsWidget(int x, int y, int width, int height) {
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
        drawToggleHudElementsWidgetTexture(context);
    }

    /**
     * Opens the ToggleHudElementsScreen on click on the widget.
     */
    @Override
    public void onClick(double mouseX, double mouseY) {
        client.setScreen(HudEnhancerClient.getScreenManager().getToggleHudElementsScreen());
    }

    /**
     * Draws the texture for the button.
     *
     * @param context the draw context
     */
    private void drawToggleHudElementsWidgetTexture(DrawContext context) {
        context.drawTexture(Textures.TOGGLE_HUD_ELEMENTS_WIDGET, getX(), getY(),
                0, 0, getWidth(), getHeight(), getWidth(), getHeight());
    }

    /**
     * Handles the scaling animation of the widget when hovered.
     */
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

    /**
     * Calculates the animation progress based on hover state and elapsed time.
     *
     * @param isCurrentlyHovered whether the widget is currently hovered
     * @return the animation progress as a float between 0.0 and 1.0
     */
    private float calculateAnimation(boolean isCurrentlyHovered) {
        long elapsed = System.currentTimeMillis() - hoverStartTime;
        float progress = MathHelper.clamp((float) elapsed / 100L, 0.0f, 1.0f);
        return isCurrentlyHovered ? progress : 1.0f - progress;
    }

    /**
     * Updates the widget's position and size based on the current scale.
     *
     * @param offsetX the horizontal offset for scaling
     * @param offsetY the vertical offset for scaling
     * @param scale   the scale factor
     */
    private void setPosition(int offsetX, int offsetY, float scale) {
        setX(this.originalX - offsetX);
        setWidth((int) (originalWidth * scale));
        setY(this.originalY - offsetY);
        setHeight((int) (originalHeight * scale));
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

}
