package io.sailex.gui.screens;

import io.sailex.util.TranslationKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class AddHudElementsScreen extends AScreen {

    public AddHudElementsScreen() {
        super(Text.of("Add HUD Elements"));
    }

    @Override
    protected void init() {
        super.init();

        this.screenX = this.width / 3;
        this.screenY = this.height / 5;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderScreenBackground(context, screenX, screenY);
        renderScreenTitle(context, screenX, screenY, TranslationKeys.ADD_HUD_SCREEN_WIDGETS);

        super.render(context, mouseX, mouseY, delta);
    }

}
