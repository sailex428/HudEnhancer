package io.sailex.gui.screens;

import io.sailex.PositionDisplayClient;
import io.sailex.gui.widgets.colorpicker.ColorGradientWidget;
import io.sailex.gui.widgets.colorpicker.HueBarControl;
import io.sailex.gui.widgets.colorpicker.HueBarWidget;
import io.sailex.gui.widgets.AWidget;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * @author sailex
 * Screen for editing Elements in the Hud.
 */
public class EditHudElementsScreen extends Screen {

    private static final String ON = "ON";
    private static final String OFF = "OFF";
    private static final int CONTENT_PADDING = 5;

    private final AWidget currentWidget;
    private final MinecraftClient client;
    private ColorGradientWidget gradientWidget;
    private HueBarWidget hueBarWidget;
    private HueBarControl hueBarControl;

    private int hue;

    public EditHudElementsScreen(AWidget currentWidget) {
        super(Text.literal(currentWidget.getMessage().getString()));
        this.currentWidget = currentWidget;
        this.client = MinecraftClient.getInstance();
        this.hue = 0;
    }

    @Override
    protected void init() {
        super.init();
        this.clearChildren();

        createGradientWidget();
        createHueBarWidget();
        createHueBarControl();

        this.addDrawableChild(gradientWidget);
        this.addDrawableChild(hueBarWidget);
        this.addDrawableChild(hueBarControl);
        this.addDrawableChild(buildBackgroundButton());
        this.addDrawableChild(buildShadowButton());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int windowX = this.width / 3;
        int windowY = this.height / 5;

        renderWindowBackground(context, windowX, windowY);
        renderScreenTitle(context, windowX, windowY);
        renderScreenContent(context, windowX, windowY);

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

    private void renderWindowBackground(DrawContext context, int windowX, int windowY) {
        context.fill(windowX, windowY, width - windowX, height - windowY, 0xFF232323);
    }

    private void renderScreenTitle(DrawContext context, int windowX, int windowY) {
        context.drawText(client.textRenderer,
                Text.translatable(TranslationKeys.EDIT_HUD_SCREEN_SETTINGS),
                windowX + CONTENT_PADDING, windowY + 7,
                0xFFFFFFFF, true);
    }

    private void renderScreenContent(DrawContext context, int windowX, int windowY) {
        renderScreenSection(context, windowX, windowY, 20, TranslationKeys.EDIT_HUD_SCREEN_TEXT_COLOR);
        renderScreenSection(context, windowX, windowY, 92, TranslationKeys.EDIT_HUD_SCREEN_SHADOW);
        renderScreenSection(context, windowX, windowY, 114, TranslationKeys.EDIT_HUD_SCREEN_BACKGROUND);
    }

    private void renderScreenSection(DrawContext context, int windowX, int windowY, int linePadding, String translationKey) {
        context.drawHorizontalLine(windowX + CONTENT_PADDING, width - windowX - CONTENT_PADDING,
                windowY + linePadding, 0xFF565656);
        context.drawText(client.textRenderer,
                Text.translatable(translationKey),
                windowX + CONTENT_PADDING, windowY + linePadding + 7,
                0xFFFFFFFF, true);
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
        hueBarWidget = new HueBarWidget(
                this.width - this.width / 3 - 15, this.height / 5 + 27,
                10, 60,
                newHue -> {
                    hue = newHue;
                    gradientWidget.setSelectedHue(newHue);
                }, hue);
    }

    private void createHueBarControl() {
        hueBarControl = new HueBarControl(
                this.width - this.width / 3 - 15, this.height / 5 + 27,
                10, 3,
                Text.literal("")
        );
    }

    private ButtonWidget buildShadowButton() {
        return ButtonWidget.builder(
                Text.literal(currentWidget.isShadow() ? ON : OFF),
                button -> {
                    currentWidget.setShadow(!currentWidget.isShadow());
                    button.setMessage(currentWidget.isShadow() ? Text.literal(ON) : Text.literal(OFF));
                }
        ).dimensions(this.width - this.width / 3 - 35, this.height / 5 + 96, 30, 15).build();
    }

    private ButtonWidget buildBackgroundButton() {
        return ButtonWidget.builder(
                Text.literal(currentWidget.isBackground() ? ON : OFF),
                button ->  {
                    currentWidget.setBackground(!currentWidget.isBackground());
                    button.setMessage(currentWidget.isBackground() ? Text.literal(ON) : Text.literal(OFF));
                }
        ).dimensions(this.width - this.width / 3 - 35, this.height / 5 + 118, 30, 15).build();
    }

}
