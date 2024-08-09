package io.sailex.gui.screens;

import io.sailex.HudEnhancerClient;
import io.sailex.gui.hud.IHudElement;
import io.sailex.gui.widgets.DefaultButtonWidget;
import io.sailex.gui.widgets.colorpicker.GradientWidget;
import io.sailex.gui.widgets.colorpicker.HueBarWidget;
import io.sailex.util.ScreenUtil;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Represents a screen for editing HUD elements.
 *
 * @author sailex
 */
public class EditHudElementsScreen extends AScreen {

    private final IHudElement currentElement;
    private GradientWidget gradientWidget;
    private final int[] linePadding = { DEFAULT_LINE_PADDING, 92, 114 };

    /**
     * Constructs an {@code EditHudElementsScreen} for the specified HUD element.
     *
     * @param currentElement The HUD element to be edited.
     */
    public EditHudElementsScreen(IHudElement currentElement) {
        super(Text.of(currentElement.getKey()));
        this.currentElement = currentElement;
    }

    /**
     * Initializes the screen and setting up its widgets.
     */
    @Override
    protected void init() {
        this.screenX = ScreenUtil.calculateScreenSize(this.width, 150);
        this.screenY = ScreenUtil.calculateScreenSize(this.height, 137);
        this.clearChildren();

        createGradientWidget();

        List<ClickableWidget> widgets = List.of(
                gradientWidget, createHueBarWidget(),
                createBackgroundCheckbox(), createShadowCheckbox(),
                createSetColorToAllButton()
        );
        for (ClickableWidget widget : widgets) {
            this.addDrawableChild(widget);
        }
    }

    /**
     * Renders the screen and its components.
     */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderScreenBackground(context, screenX, screenY);
        renderScreenTitle(context, screenX, screenY, TranslationKeys.EDIT_HUD_SCREEN_SETTINGS);
        renderScreenContent(context, screenX, screenY);

        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * Renders the content of the screen, including different sections.
     */
    private void renderScreenContent(DrawContext context, int windowX, int windowY) {
        renderScreenSection(context, windowX, windowY, linePadding[0], TranslationKeys.EDIT_HUD_SCREEN_TEXT_COLOR);
        renderScreenSection(context, windowX, windowY, linePadding[1], TranslationKeys.EDIT_HUD_SCREEN_SHADOW);
        renderScreenSection(context, windowX, windowY, linePadding[2], TranslationKeys.EDIT_HUD_SCREEN_BACKGROUND);
    }

    /**
     * Renders a specific section of the screen.
     *
     * @param linePadding    The padding for the line.
     * @param translationKey The translation key for the section title.
     */
    protected void renderScreenSection(DrawContext context, int windowX, int windowY, int linePadding, String translationKey) {
        renderLine(context, windowX, windowY, linePadding);
        context.drawText(client.textRenderer,
                Text.translatable(translationKey),
                windowX + CONTENT_PADDING, windowY + linePadding + 7,
                0xFFFFFFFF, true);
    }

    /**
     * Creates the gradient widget for color selection.
     */
    private void createGradientWidget() {
        gradientWidget = new GradientWidget(this.width - screenX - 80, screenY + 27, 60, 60,
                this.currentElement::setColor,  this.currentElement.getHue(), this.currentElement.getColor());
    }

    /**
     * Creates the hue bar widget for hue adjustments.
     *
     * @return The created HueBarWidget.
     */
    private HueBarWidget createHueBarWidget() {
        return new HueBarWidget(this.width - screenX - 15, screenY + 27, 10, 60,
                hue -> {
                    currentElement.setHue(hue);
                    gradientWidget.setSelectedHue(hue);
                }, currentElement.getHue()
        );
    }

    /**
     * Creates the checkbox widget for toggling the shadow setting.
     *
     * @return The created CheckboxWidget.
     */
    private CheckboxWidget createShadowCheckbox() {
        return createCheckBoxWidget(95, currentElement.isShadow(),
                (checkbox, checked) -> currentElement.setShadow(!currentElement.isShadow()));
    }

    /**
     * Creates the checkbox widget for toggling the background setting.
     *
     * @return The created CheckboxWidget.
     */
    private CheckboxWidget createBackgroundCheckbox() {
        return createCheckBoxWidget(117, currentElement.isBackground(),
            (checkbox, checked) -> currentElement.setBackground(!currentElement.isBackground())
        );
    }

    /**
     * Creates a button widget that sets the color of the current widget
     * to all other widgets.
     *
     * @return the created DefaultButtonWidget
     */
    private ButtonWidget createSetColorToAllButton() {
        return new DefaultButtonWidget(this.screenX + CONTENT_PADDING, this.screenY + linePadding[0] + 47, 61, 20,
            Text.translatable(TranslationKeys.EDIT_HUD_SCREEN_SET_COLOR_TO_ALL),
                button -> HudEnhancerClient.getHudElementsManager().getHudElements().forEach(hudElement -> {
                    hudElement.setColor(this.currentElement.getColor());
                    hudElement.setHue(this.currentElement.getHue());
            })
        );
    }

}
