package io.sailex.gui.screens;

import io.sailex.config.DefaultConfig;
import io.sailex.gui.hud.IHudElement;
import io.sailex.util.ScreenUtil;
import io.sailex.util.Textures;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen for toggling HUD elements on or off.
 *
 * @author sailex
 */
public class ToggleHudElementsScreen extends AScreen {

    private final List<IHudElement> hudElementList;
    private final int[] linePadding = { DEFAULT_LINE_PADDING, 60, 100 };

    /**
     * Constructs a {@code ToggleHudElementsScreen}.
     *
     * @param hudElementList the list of HUD elements to be toggled
     */
    public ToggleHudElementsScreen(List<IHudElement> hudElementList) {
        super(Text.of("Add HUD Elements"));
        this.hudElementList = hudElementList;
    }

    /**
     * Initializes the screen and setting up its widgets.
     */
    @Override
    protected void init() {
        super.init();

        this.screenX = ScreenUtil.calculateScreenSize(this.width, 150);
        this.screenY = ScreenUtil.calculateScreenSize(this.height, 140);

        for (ClickableWidget checkBoxWidget : createCheckBoxWidgets()) {
            this.addDrawableChild(checkBoxWidget);
        }
    }

    /**
     * Renders the screen and its components.
     */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderScreenBackground(context, screenX, screenY);
        renderScreenTitle(context, screenX, screenY, TranslationKeys.ADD_HUD_SCREEN_WIDGETS);
        renderScreenContent(context);
        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * Renders the content of the screen, including different sections.
     */
    private void renderScreenContent(DrawContext context) {
        renderScreenSection(context, Textures.CPS_PICTURE, linePadding[0], DefaultConfig.CPS);
        renderScreenSection(context, Textures.FPS_PICTURE, linePadding[1], DefaultConfig.FPS);
        renderScreenSection(context, Textures.POSITION_PICTURE, linePadding[2], DefaultConfig.POSITION);
    }

    /**
     * Renders a specific section of the screen.
     *
     * @param linePadding   the padding for the line.
     * @param texture       the texture of the hud element.
     * @param text          the name of the hud element.
     */
    private void renderScreenSection(DrawContext context, Identifier texture, int linePadding, String text) {
        renderLine(context, this.screenX, this.screenY, linePadding);
        context.drawTexture(texture, this.screenX + CONTENT_PADDING, this.screenY + linePadding + 5,
                0, 0, 30, 30, 30, 30);
        context.drawText(client.textRenderer, text,this.screenX + 40, this.screenY + linePadding + 16, 0xFFFFFFFF, true);
    }

    /**
     * Creates checkbox widgets for toggling of the hud elements.
     *
     * @return the List of the created CheckboxWidgets.
     */
    private List<CheckboxWidget> createCheckBoxWidgets() {
        List<CheckboxWidget> checkBoxWidgets = new ArrayList<>();
        int i = 0;
        for (IHudElement hudElement : hudElementList) {
            checkBoxWidgets.add(
                    createCheckBoxWidget(linePadding[i] + 12,
                            hudElement.isActive(),
                            (checkbox, checked) -> hudElement.setIsActive(!hudElement.isActive())
                    )
            );
            i++;
        }
        return checkBoxWidgets;
    }

}
