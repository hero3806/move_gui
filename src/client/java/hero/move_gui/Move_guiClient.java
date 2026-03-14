package hero.move_gui;

import hero.move_gui.gui.HudEditorScreen;
import hero.move_gui.keybinds.Keybind;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class Move_guiClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		Keybind.register();
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (Keybind.OPEN_HUD_EDITOR.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new HudEditorScreen());
			}
		});
	}
}