package hero.move_gui.mixin.client.OtherMixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import hero.move_gui.config.HudManager;
import net.minecraft.client.gui.hud.bar.LocatorBar;

@Mixin(LocatorBar.class)
public class LocatorBarMixin {

    @ModifyArg(
        method = "renderBar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/client/gl/RenderPipelines;Lnet/minecraft/util/Identifier;IIII)V"
        ),
        index = 2
    )
    private int moveLocatorBarX(int x) {
        return x + HudManager.xpbarX;
    }

    @ModifyArg(
        method = "renderBar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/client/gl/RenderPipelines;Lnet/minecraft/util/Identifier;IIII)V"
        ),
        index = 3
    )
    private int moveLocatorBarY(int y) {
        return y + HudManager.xpbarY;
    }
}