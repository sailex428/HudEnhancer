package io.sailex.mixin.client;

import io.sailex.util.KeyInputHandler;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This mixin listens for mouse button events and redirects them to the custom key input handler.
 *
 * @author sailex
 */
@Mixin(Mouse.class)
public abstract class MouseMixin {

    @Inject(method = "onMouseButton", at = @At(value="HEAD"))
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        KeyInputHandler.getInstance().onKey(button, action);
    }

}
