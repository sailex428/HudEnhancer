package io.sailex.config;

public record HudElement(
        int x,
        int y,
        int width,
        int height,
        int color,
        boolean background,
        boolean shadow
) {}
