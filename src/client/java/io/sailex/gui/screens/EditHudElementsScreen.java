package io.sailex.gui.screens;

import io.sailex.gui.hud.IHudElement;
import io.sailex.gui.widgets.colorpicker.GradientWidget;
import io.sailex.gui.widgets.colorpicker.HueBarWidget;
import io.sailex.util.ScreenUtil;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Represents a screen for editing HUD elements.
 * <p>
 * This screen allows users to edit the properties of a HUD element, such as its color, shadow, and background settings.
 * It provides a gradient widget for color selection, a hue bar widget for hue adjustments, and checkboxes for toggling
 * shadow and background settings.
 * </p>
 * <p>
 * The screen layout includes sections for text color, shadow, and background settings, each with appropriate padding.
 * The elements and widgets are dynamically positioned based on screen size calculations.
 * </p>
 * @author sailex
 */
public class EditHudElementsScreen extends AScreen {

    private final IHudElement currentElement;
    private GradientWidget gradientWidget;
    private final int[] linePadding = { DEFAULT_LINE_PADDING, 92, 114 };

    /**
     * Constructs an EditHudElementsScreen for the specified HUD element.
     *
     * @param currentElement The HUD element to be edited.
     */
    public EditHudElementsScreen(IHudElement currentElement) {
        super(Text.of(currentElement.getKey()));
        this.currentElement = currentElement;
    }

    /**
     * Initializes the screen by setting up its components and layout.
     * <p>
     * This method calculates the screen size, clears any existing children, and creates the necessary widgets
     * for editing the HUD element. The widgets are then added to the screen.
     * </p>
     */
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

    /**
     * Renders the screen and its components.
     *
     * @param context The rendering context.
     * @param mouseX  The X coordinate of the mouse cursor.
     * @param mouseY  The Y coordinate of the mouse cursor.
     * @param delta   The time delta.
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
     *
     * @param context  The rendering context.
     * @param windowX  The X coordinate of the window.
     * @param windowY  The Y coordinate of the window.
     */
    private void renderScreenContent(DrawContext context, int windowX, int windowY) {
        renderScreenSection(context, windowX, windowY, linePadding[0], TranslationKeys.EDIT_HUD_SCREEN_TEXT_COLOR);
        renderScreenSection(context, windowX, windowY, linePadding[1], TranslationKeys.EDIT_HUD_SCREEN_SHADOW);
        renderScreenSection(context, windowX, windowY, linePadding[2], TranslationKeys.EDIT_HUD_SCREEN_BACKGROUND);
    }

    /**
     * Renders a specific section of the screen.
     *
     * @param context        The rendering context.
     * @param windowX        The X coordinate of the window.
     * @param windowY        The Y coordinate of the window.
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
                currentElement::setColor, currentElement.getHue(), currentElement.getColor());
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
}
