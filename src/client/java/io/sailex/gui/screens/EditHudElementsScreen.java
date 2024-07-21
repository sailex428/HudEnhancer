package io.sailex.gui.screens;

import io.sailex.gui.widgets.CheckBoxWidget;
import io.sailex.gui.widgets.colorpicker.GradientWidget;
import io.sailex.gui.widgets.colorpicker.HueBarWidget;
import io.sailex.gui.widgets.AWidget;
import io.sailex.util.ScreenUtil;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

/**
 * @author sailex
 * Screen for editing Elements in the Hud.
 */
public class EditHudElementsScreen extends AScreen {

    private final AWidget currentWidget;
    private GradientWidget gradientWidget;
    private final int[] linePadding = { DEFAULT_LINE_PADDING, 92, 114 };

    public EditHudElementsScreen(AWidget currentWidget) {
        super(Text.of(currentWidget.getMessage().getString()));
        this.currentWidget = currentWidget;
    }

    @Override
    protected void init() {
        super.init();
        this.screenX = ScreenUtil.calculateScreenSize(this.width, 150);
        this.screenY = ScreenUtil.calculateScreenSize(this.height, 137);
        this.clearChildren();

        createGradientWidget();

        List<ClickableWidget> widgets = List.of(
                gradientWidget, createHueBarWidget(),
                createBackgroundCheckbox(), createShadowCheckbox()
        );
        for (ClickableWidget widget : widgets) {
            this.addDrawableChild(widget);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderScreenBackground(context, screenX, screenY);
        renderScreenTitle(context, screenX, screenY, TranslationKeys.EDIT_HUD_SCREEN_SETTINGS);
        renderScreenContent(context, screenX, screenY);

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderScreenContent(DrawContext context, int windowX, int windowY) {
        renderScreenSection(context, windowX, windowY, linePadding[0], TranslationKeys.EDIT_HUD_SCREEN_TEXT_COLOR);
        renderScreenSection(context, windowX, windowY, linePadding[1], TranslationKeys.EDIT_HUD_SCREEN_SHADOW);
        renderScreenSection(context, windowX, windowY, linePadding[2], TranslationKeys.EDIT_HUD_SCREEN_BACKGROUND);
    }

    protected void renderScreenSection(DrawContext context, int windowX, int windowY, int linePadding, String translationKey) {
        renderLine(context, windowX, windowY, linePadding);
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
