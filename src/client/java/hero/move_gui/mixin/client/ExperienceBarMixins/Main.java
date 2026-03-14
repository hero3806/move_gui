package hero.move_gui.mixin.client.ExperienceBarMixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import hero.move_gui.config.HudManager;
import net.minecraft.client.gui.hud.bar.ExperienceBar;

@Mixin(ExperienceBar.class)
public class Main {

    @ModifyVariable(
        method = "renderBar",
        at = @At("STORE"),
        ordinal = 0
    )
    private int moveXpBarX(int original) {
        return original + HudManager.xpbarX;
    }

    @ModifyVariable(
        method = "renderBar",
        at = @At("STORE"),
        ordinal = 1
    )
    private int moveXpBarY(int original) {
        return original + HudManager.xpbarY;
    }
}
