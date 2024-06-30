package io.sailex.gui.screens;

import io.sailex.PositionDisplayClient;
import io.sailex.gui.widgets.colorpicker.ColorGradientWidget;
import io.sailex.gui.widgets.colorpicker.ColorHueBarWidget;
import io.sailex.util.AWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class EditHudElementsScreen extends Screen {

    private final AWidget currentWidget;
    private final MinecraftClient client;
    private ColorGradientWidget gradientWidget;
    private ColorHueBarWidget hueBarWidget;
    private int hue = 0;

    public EditHudElementsScreen(AWidget currentWidget) {
        super(Text.literal(currentWidget.getMessage().getString()));
        this.currentWidget = currentWidget;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        super.init();
        this.clearChildren();

        createGradientWidget();
        createHueBarWidget();

        this.addDrawableChild(gradientWidget);
        this.addDrawableChild(hueBarWidget);
        this.addDrawableChild(createBackgroundButton());
        this.addDrawableChild(createShadowButton());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        context.fill(screenWidth / 3, screenHeight / 5, screenWidth - screenWidth / 3, screenHeight - screenHeight / 5, 0xFF232323);
        context.drawText(client.textRenderer, "Settings", screenWidth / 3 + 5, screenHeight / 5 + 7, 0xFFFFFFFF, true);

        context.drawHorizontalLine(screenWidth / 3 + 5, screenWidth - screenWidth / 3 - 5, screenHeight / 5 + 20, 0xFF565656);
        context.drawText(client.textRenderer, "Textcolor", screenWidth / 3 + 5, screenHeight / 5 + 27, 0xFFFFFFFF, true);

        context.drawHorizontalLine(screenWidth / 3 + 5 , screenWidth - screenWidth / 3 - 5, screenHeight / 5 + 92, 0xFF565656);
        context.drawText(client.textRenderer, "Shadow", screenWidth / 3 + 5, screenHeight / 5 + 99,0xFFFFFFFF, true);

        context.drawHorizontalLine(screenWidth / 3 + 5 , screenWidth - screenWidth / 3 - 5, screenHeight / 5 + 114, 0xFF565656);
        context.drawText(client.textRenderer, "Background", screenWidth / 3 + 5, screenHeight / 5 + 121,0xFFFFFFFF, true);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0);
    }

    @Override
    public void close() {
        client.setScreen(PositionDisplayClient.getScreenManager().getMoveHudElementsScreen());
    }

    private void createGradientWidget() {
        gradientWidget = new ColorGradientWidget(
                this.width - this.width / 3 - 80, this.height / 5 + 27,
                60, 60,
                currentWidget::setColor,
                hue,
                currentWidget.getColor()
        );
    }

    private void createHueBarWidget() {
        hueBarWidget = new ColorHueBarWidget(
                this.width - this.width / 3 - 15, this.height / 5 + 27,
                10, 60,
                newHue -> {
                    hue = newHue;
                    gradientWidget.setSelectedHue(newHue);
                }, hue);
    }

    private ButtonWidget createShadowButton() {
        return ButtonWidget.builder(
                Text.literal(""),
                button -> currentWidget.setShadow(!currentWidget.isShadow())
        ).dimensions(this.width - this.width / 3 - 20, this.height / 5 + 96, 15, 15).build();
    }

    private ButtonWidget createBackgroundButton() {
        return ButtonWidget.builder(
                Text.literal(""),
                button -> currentWidget.setBackground(!currentWidget.isBackground())
        ).dimensions(this.width - this.width / 3 - 20, this.height / 5 + 118, 15, 15).build();
    }

}
