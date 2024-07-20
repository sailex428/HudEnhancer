package io.sailex.gui.screens;

import io.sailex.HudEnhancerClient;
import io.sailex.gui.widgets.CheckBoxWidget;
import io.sailex.gui.widgets.colorpicker.GradientWidget;
import io.sailex.gui.widgets.colorpicker.HueBarWidget;
import io.sailex.gui.widgets.AWidget;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * @author sailex
 * Screen for editing Elements in the Hud.
 */
public class EditHudElementsScreen extends Screen {

    private static final int CONTENT_PADDING = 5;

    private final AWidget currentWidget;
    private final MinecraftClient client;
    private GradientWidget gradientWidget;

    private int screenX;
    private int screenY;

    public EditHudElementsScreen(AWidget currentWidget) {
        super(Text.literal(currentWidget.getMessage().getString()));
        this.currentWidget = currentWidget;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        super.init();
        this.screenX = this.width / 3;
        this.screenY = this.height / 5;
        this.clearChildren();

        createGradientWidget();

        this.addDrawableChild(gradientWidget);
        this.addDrawableChild(createHueBarWidget());
        this.addDrawableChild(createBackgroundCheckbox());
        this.addDrawableChild(createShadowCheckbox());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderScreenBackground(context, screenX, screenY);
        renderScreenTitle(context, screenX, screenY);
        renderScreenContent(context, screenX, screenY);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0);
    }

    @Override
    public void close() {
        client.setScreen(HudEnhancerClient.getScreenManager().getMoveHudElementsScreen());
    }

    private void renderScreenBackground(DrawContext context, int screenX, int screenY) {
        context.fill(screenX, screenY, width - screenX, height - screenY, 0xFF232323);
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
        gradientWidget = new GradientWidget(this.width - screenX - 80, screenY + 27, 60, 60,
                currentWidget::setColor, currentWidget.getHue(), currentWidget.getColor());
    }

    private HueBarWidget createHueBarWidget() {
        return new HueBarWidget(this.width - screenX - 15, screenY + 27, 10, 60,
                hue -> {
                    currentWidget.setHue(hue);
                    gradientWidget.setSelectedHue(hue);
                }, currentWidget.getHue()
        );
    }

    private CheckBoxWidget createShadowCheckbox() {
        return new CheckBoxWidget(this.width - screenX - 20, screenY + 96,
                currentWidget::setShadow, currentWidget.isShadow());
    }

    private CheckBoxWidget createBackgroundCheckbox() {
        return new CheckBoxWidget(this.width - screenX - 20, screenY + 118,
                currentWidget::setBackground, currentWidget.isBackground());
    }

}
