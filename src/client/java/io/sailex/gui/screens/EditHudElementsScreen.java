package io.sailex.gui.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;


public class EditHudElementsScreen extends Screen {

    private final MinecraftClient client;
    private Screen previousScreen;

    public EditHudElementsScreen() {
        super(Text.empty());
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        super.init();

        this.clearChildren();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        context.fill(screenWidth / 3, screenHeight / 5, screenWidth - screenWidth / 3, screenHeight - screenHeight / 5, 0xFF8A8A8A);
        context.drawText(client.textRenderer, "Settings", screenWidth / 3 + 5, screenHeight / 5 + 5, 0xFFFFFF, true);

    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0);
    }

    @Override
    public void close() {
        client.setScreen(previousScreen);
    }

    public void setPreviousScreen(Screen previousScreen) {
        this.previousScreen = previousScreen;
    }

}
