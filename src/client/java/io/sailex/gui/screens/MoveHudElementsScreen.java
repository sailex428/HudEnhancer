package io.sailex.gui.screens;

import io.sailex.gui.widgets.AddWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

/**
 * Screen for moving the HUD elements and open the toggle screen.
 *
 * @author sailex
 */
public class MoveHudElementsScreen extends AScreen {

    /**
     * Constructs an {@code MoveHudElementsScreen} for the specified HUD element.
     */
    public MoveHudElementsScreen() {
        super(Text.of("Move HUD Elements"));
    }

    /**
     * Initializes the screen and setting up its widgets.
     */
    @Override
    protected void init() {
        super.init();

        this.clearChildren();
        this.addDrawableChild(createAddWidget());
    }

    /**
     * Creates AddWidget to open the toggleHudElements screen.
     *
     * @return the created AddWidget
     */
    private ClickableWidget createAddWidget() {
        Window window = client.getWindow();
        int widgetSize = 40;
        return new AddWidget(window.getScaledWidth() / 2 - (widgetSize / 2),  window.getScaledHeight() / 2 - (widgetSize / 2),
                widgetSize, widgetSize);
    }

    /**
     * Closes the current screen.
     */
    @Override
    public void close() {
        this.client.setScreen(null);
    }

}
