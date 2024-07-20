package io.sailex.util;

import io.sailex.PositionDisplayClient;
import net.minecraft.util.Identifier;

public class Textures {

    public static final Identifier GRADIENT_CONTROL = Identifier.of(PositionDisplayClient.MOD_ID, "textures/gui/gradient_control.png");
    public static final Identifier HUE_BAR_CONTROL = Identifier.of(PositionDisplayClient.MOD_ID, "textures/gui/hue_bar_control.png");

    public static final Identifier ADD_WIDGET = Identifier.of(PositionDisplayClient.MOD_ID, "textures/gui/add_widget.png");

    public static final Identifier SELECTED_CHECKBOX = Identifier.of("minecraft", "textures/gui/sprites/widget/checkbox_selected_highlighted.png");
    public static final Identifier UNSELECTED_CHECKBOX = Identifier.of("minecraft", "textures/gui/sprites/widget/checkbox_highlighted.png");

    public static final Identifier NONE_PICTURE = Identifier.of("minecraft", "textures/misc/unknown_pack.png");
    public static final Identifier CPS_PICTURE = Identifier.of (PositionDisplayClient.MOD_ID, "textures/misc/cps.png");
    public static final Identifier FPS_PICTURE = Identifier.of(PositionDisplayClient.MOD_ID, "textures/misc/fps.png");
    public static final Identifier POSITION_PICTURE = Identifier.of(PositionDisplayClient.MOD_ID, "textures/misc/position.png");

}
