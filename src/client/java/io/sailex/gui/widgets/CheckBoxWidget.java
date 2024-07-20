package io.sailex.gui.widgets;

import io.sailex.util.Textures;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class CheckBoxWidget extends ClickableWidget {

    private final OnCheckboxClick onCheckboxClick;

    private boolean isSelected;

    public CheckBoxWidget(int x, int y, OnCheckboxClick onCheckboxClick, boolean isSelected) {
        super(x, y, 15, 15, Text.of("CheckBoxWidget"));
        this.isSelected = isSelected;
        this.onCheckboxClick = onCheckboxClick;
    }

    @FunctionalInterface
    public interface OnCheckboxClick {
        void onCheckboxClick(boolean isSelected);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(isSelected ? Textures.SELECTED_CHECKBOX : Textures.UNSELECTED_CHECKBOX,
                getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.isSelected = !this.isSelected;
        this.onCheckboxClick.onCheckboxClick(isSelected);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
}
