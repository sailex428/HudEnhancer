package io.sailex.gui.widgets;

import io.sailex.PositionDisplayClient;
import io.sailex.util.Textures;
import io.sailex.util.TranslationKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class AddWidget extends ClickableWidget {

    private final MinecraftClient client;

    public AddWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.of("Add Widget"));
        client = MinecraftClient.getInstance();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        drawCompassTexture(context);
        drawAddWidgetText(context);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        client.setScreen(PositionDisplayClient.getScreenManager().getAddHudElementsScreen());
    }

    private void drawCompassTexture(DrawContext context) {
        context.drawTexture(Textures.COMPASS, getX(), getY(), 0, 0,
            getWidth(), getHeight(), getWidth(), getHeight());
    }

    private void drawAddWidgetText(DrawContext context) {
        context.drawText(client.textRenderer, Text.translatable(TranslationKeys.MOVE_HUD_SCREEN_ADD_WIDGET),
                getX() - 7, getY() + 30, 0xFFFFFF, true);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

}
