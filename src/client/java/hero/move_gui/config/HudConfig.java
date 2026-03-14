package hero.move_gui.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

import net.fabricmc.loader.api.FabricLoader;

public class HudConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final File FILE = new File(
            FabricLoader.getInstance().getConfigDir().toFile(),
            "move_gui.json"
    );

    public static void save() {
        try {

            HudData data = new HudData();

            data.hotbarX = HudManager.hotbarX;
            data.hotbarY = HudManager.hotbarY;

            data.heartsX = HudManager.heartsX;
            data.heartsY = HudManager.heartsY;

            data.hungerX = HudManager.hungerX;
            data.hungerY = HudManager.hungerY;

            data.xpbarX = HudManager.xpbarX;
            data.xpbarY = HudManager.xpbarY;

            FileWriter writer = new FileWriter(FILE);
            GSON.toJson(data, writer);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {

        try {

            if (!FILE.exists()) return;

            FileReader reader = new FileReader(FILE);

            HudData data = GSON.fromJson(reader, HudData.class);

            reader.close();

            HudManager.hotbarX = data.hotbarX;
            HudManager.hotbarY = data.hotbarY;

            HudManager.heartsX = data.heartsX;
            HudManager.heartsY = data.heartsY;

            HudManager.hungerX = data.hungerX;
            HudManager.hungerY = data.hungerY;

            HudManager.xpbarX = data.xpbarX;
            HudManager.xpbarY = data.xpbarY;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}