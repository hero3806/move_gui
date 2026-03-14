package hero.move_gui.mixin.client.OtherMixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import hero.move_gui.config.HudManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.Alignment;

@Mixin(targets = "net.minecraft.client.gui.hud.ChatHud$Forwarder")
public class ChatHudForwarderMixin {

    @ModifyArgs(
        method = "text",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/font/DrawnTextConsumer;text(Lnet/minecraft/client/font/Alignment;IILnet/minecraft/client/font/DrawnTextConsumer$Transformation;Lnet/minecraft/text/OrderedText;)V"
        )
    )
    private void swapAlignment(Args args) {
        if (HudManager.chatRightAligned) {
            args.set(0, Alignment.RIGHT);
            int chatWidth = (int)(MinecraftClient.getInstance().options.getChatWidth().getValue() * 280.0 + 40.0);
            args.set(1, chatWidth);
        }
    }
}