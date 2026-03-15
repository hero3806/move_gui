package hero.move_gui.keybinds;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.KeyBinding.Category;
import net.minecraft.client.util.InputUtil;

public class Keybind {
    public static KeyBinding OPEN_HUD_EDITOR;
        
    public static void register() {
        OPEN_HUD_EDITOR = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                "key.movegui.editor",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                Category.MISC
            )
        );
    }
    
}
