package hero.move_gui.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.spongepowered.asm.mixin.Shadow;

import hero.move_gui.config.HudManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow
    private MinecraftClient client;

    @Shadow
    private ChatHud chatHud;

    // Moves the hotbar according to the invbox
    @ModifyArgs(
        method = "renderHotbar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture"
        )
    )
    private void moveHotbar(Args args) {

        int x = args.get(2);
        int y = args.get(3);

        x += HudManager.hotbarX;
        y += HudManager.hotbarY;

        args.set(2, x);
        args.set(3, y);
    }

    // Moves the hotbar ITEMS to same position as the hotbar
    @ModifyArgs(
        method = "renderHotbar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem"
        )
    )
    private void moveHotbarItems(Args args) {
        int x = args.get(1);
        int y = args.get(2);

        x += HudManager.hotbarX;
        y += HudManager.hotbarY;

        args.set(1, x);
        args.set(2, y);
    }
    
    @ModifyArgs(
        method = "renderStatusBars",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar"
        )
    )
    private void moveHealthBar(Args args) {
        int x = args.get(2);
        int y = args.get(3);

        x += HudManager.heartsX;
        y += HudManager.heartsY;

        args.set(2, x);
        args.set(3, y);
    }
    
    // Move Hunger
    @ModifyArgs(
        method = "renderFood(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;II)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture"
        ),
        require = 0
    )
    private void moveHungerBar(Args args) {
        int x = args.get(2);
        int y = args.get(3);

        x += HudManager.hungerX;
        y += HudManager.hungerY;

        args.set(2, x);
        args.set(3, y);
    }


    @ModifyArgs(
        method = "renderStatusBars",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderArmor(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;III I)V"
        ),
        require = 0
    )
    private void moveArmor(Args args) {

        int y = args.get(2);
        int x = args.get(5);

        y += HudManager.armorY;
        x += HudManager.armorX;

        args.set(2, y);
        args.set(5, x);
    }
    
    @ModifyArgs(
        method = "renderMountHealth",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture"
        ),
        require = 0
    )
    private void moveMountHealth(Args args) {

        int x = args.get(2);
        int y = args.get(3);

        x += HudManager.hungerX;
        y += HudManager.hungerY;

        args.set(2, x);
        args.set(3, y);
    }

    @ModifyArgs(
        method = "renderHeldItemTooltip",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithBackground"
        )
    )
    private void moveHeldItemTooltip(Args args) {

        int x = args.get(2);
        int y = args.get(3);

        // Move together with the hotbar
        x += HudManager.hotbarX;
        y += HudManager.hotbarY;

        args.set(2, x);
        args.set(3, y);
    }

    // EXP Bar Level
    @Inject(
        method = "renderMainHud",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/bar/Bar;drawExperienceLevel(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;I)V"
        )
    )
    private void moveXpTextStart(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(HudManager.xpbarX, HudManager.xpbarY);
    }

    @Inject(
        method = "renderMainHud",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/bar/Bar;drawExperienceLevel(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;I)V",
            shift = At.Shift.AFTER
        )
    )
    private void moveXpTextEnd(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        context.getMatrices().popMatrix();
    }


    // Move Chat Hud
    @Inject(
        method = "renderChat",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/ChatHud;render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;IIIZZ)V"
        )
    )
    private void moveChatStart(
        DrawContext context,
        RenderTickCounter tickCounter,
        CallbackInfo ci
    ) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(HudManager.chatX, HudManager.chatY);
    }

    @Inject(
        method = "renderChat",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/ChatHud;render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;IIIZZ)V",
            shift = At.Shift.AFTER
        )
    )
    private void moveChatEnd(
        DrawContext context,
        RenderTickCounter tickCounter,
        CallbackInfo ci
    ) {
        context.getMatrices().popMatrix();
    }
}