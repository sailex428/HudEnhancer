package io.sailex.gui.screens;

import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.List;

public class ScreenManager {

    private final List<ClickableWidget> widgetList;
    private EditHudElementsScreen editHudElementsScreen;
    private AddHudElementsScreen addHudElementsScreen;
    private MoveHudElementsScreen moveHudElementsScreen;

    public ScreenManager(List<ClickableWidget> widgetList) {
        this.widgetList = widgetList;
    }

    public void registerScreens() {
        moveHudElementsScreen = new MoveHudElementsScreen(widgetList);
        editHudElementsScreen = new EditHudElementsScreen();
        addHudElementsScreen = new AddHudElementsScreen();

        editHudElementsScreen.setPreviousScreen(moveHudElementsScreen);
    }

    public AddHudElementsScreen getAddHudElementsScreen() {
        return addHudElementsScreen;
    }

    public EditHudElementsScreen getEditHudElementsScreen() {
        return editHudElementsScreen;
    }

    public MoveHudElementsScreen getMoveHudElementsScreen() {
        return moveHudElementsScreen;
    }

}
