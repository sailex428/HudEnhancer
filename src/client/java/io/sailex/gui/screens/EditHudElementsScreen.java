package io.sailex.gui.screens;

import io.sailex.gui.widgets.colorpicker.ColorGradientWidget;
import io.sailex.gui.widgets.colorpicker.ColorHueBarWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;


public class EditHudElementsScreen extends Screen {

    private final MinecraftClient client;
    private Screen previousScreen;
    private ColorHueBarWidget hueBarWidget;
    private ColorGradientWidget gradientWidget;
    private int selectedHue = 0;
    private int selectedColor = 0xFFFFFF;

    public EditHudElementsScreen() {
        super(Text.empty());
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        super.init();

        this.clearChildren();

        hueBarWidget = new ColorHueBarWidget(
                this.width / 3 + 5, this.height / 5 + 25,
                10, 200,
                newHue -> {
            this.selectedHue = newHue;
            gradientWidget.setSelectedHue(newHue);
        });

        gradientWidget = new ColorGradientWidget(
                this.width / 3 + 20, this.height / 5 + 25,
                200, 200,
                newColor -> this.selectedColor = newColor
        );

        this.addDrawableChild(this.gradientWidget);
        this.addDrawableChild(this.hueBarWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        context.fill(screenWidth / 3, screenHeight / 5, screenWidth - screenWidth / 3, screenHeight - screenHeight / 5, 0xFF232323);
        context.drawHorizontalLine(screenWidth / 3 + 5, screenWidth - screenWidth / 3 - 5, screenHeight / 5 + 20, 0xFF565656);
        context.drawHorizontalLine(screenWidth / 3 + 5 , screenWidth - screenWidth / 3 - 5, screenHeight / 5 + 90, 0xFF565656);
        context.drawText(client.textRenderer, "Settings", screenWidth / 3 + 5, screenHeight / 5 + 5, 0xFFFFFF, true);
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0);
    }

    @Override
    public void close() {
        client.setScreen(previousScreen);
    }

    public void setPreviousScreen(Screen previousScreen) {
        this.previousScreen = previousScreen;
    }

}
