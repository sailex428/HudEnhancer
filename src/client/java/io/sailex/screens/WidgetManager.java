package io.sailex.screens;

import io.sailex.config.Position;

import io.sailex.config.PositionDisplayConfig;
import io.sailex.screens.widgets.FPSWidget;
import io.sailex.screens.widgets.PositionWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WidgetManager {

    private final List<ClickableWidget> widgets;
    private final Map<String, Position> positionMap;

    public WidgetManager(Map<String, Position> positionMap) {
        this.positionMap = positionMap;
        widgets = new ArrayList<>();
    }

    public void registerWidgets() {

        Position fps = positionMap.get(PositionDisplayConfig.FPS);
        widgets.add(new FPSWidget(fps.x(), fps.y(), fps.width(), fps.height(), Text.empty()));

        Position positionDisplay = positionMap.get(PositionDisplayConfig.POSITION_DISPLAY);
        widgets.add(new PositionWidget(positionDisplay.x(), positionDisplay.y(), positionDisplay.width(), positionDisplay.height(), Text.empty()));

//        Position cps = positionMap.get(PositionDisplayConfig.FPS);
//        widgets.add(new CPSWidget(cps.x(), cps.y(), cps.width(), cps.height(), Text.empty()));

    }

    public List<ClickableWidget> getWidgets() {
        return widgets;
    }

}
