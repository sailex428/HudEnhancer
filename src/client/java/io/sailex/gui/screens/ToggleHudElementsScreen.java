package io.sailex.gui.screens;

import io.sailex.gui.widgets.AWidget;
import io.sailex.gui.widgets.CheckBoxWidget;
import io.sailex.util.Textures;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ToggleHudElementsScreen extends AScreen {

    private final List<AWidget> widgetList;
    private final int[] linePadding = { 20, 70, 120 };

    public ToggleHudElementsScreen(List<AWidget> widgetList) {
        super(Text.of("Add HUD Elements"));
        this.widgetList = widgetList;
    }

    @Override
    protected void init() {
        super.init();

        this.screenX = this.width / 3;
        this.screenY = this.height / 5;

        for (ClickableWidget checkBoxWidget : createCheckBoxWidgets()) {
            this.addDrawableChild(checkBoxWidget);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderScreenBackground(context, screenX, screenY);
        renderScreenTitle(context, screenX, screenY, TranslationKeys.ADD_HUD_SCREEN_WIDGETS);
        renderScreenContent(context);
        super.render(context, mouseX, mouseY, delta);
    }

    private void renderScreenContent(DrawContext context) {
        renderScreenSection(context, Textures.CPS_PICTURE, linePadding[0], "CPS");
        renderScreenSection(context, Textures.FPS_PICTURE, linePadding[1], "FPS");
        renderScreenSection(context, Textures.POSITION_PICTURE, linePadding[2], "Position");
    }

    private void renderScreenSection(DrawContext context, Identifier texture, int linePadding, String text) {
        renderLine(context, this.screenX, this.screenY, linePadding);
        context.drawTexture(texture, this.screenX + CONTENT_PADDING, this.screenY + linePadding + 5,
                0, 0, 40, 40, 40, 40);
        context.drawText(client.textRenderer, text,this.screenX + 52, this.screenY + linePadding + 20, 0xFFFFFFFF, true);
    }

    private List<CheckBoxWidget> createCheckBoxWidgets() {
        List<CheckBoxWidget> checkBoxWidgets = new ArrayList<>();
        int i = 0;
        for (AWidget widget : widgetList) {
            checkBoxWidgets.add(createSingleCheckBoxWidget(widget, linePadding[i]));
            i++;
        }
        return checkBoxWidgets;
    }

    private CheckBoxWidget createSingleCheckBoxWidget(AWidget widget, int currentLinePadding) {
        return new CheckBoxWidget(this.width - screenX - 20, screenY + currentLinePadding + 16,
                isSelected -> widget.setIsActive(!widget.isActive()),
                widget.isActive());
    }

}
