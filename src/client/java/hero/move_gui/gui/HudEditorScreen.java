package hero.move_gui.gui;

import hero.move_gui.config.HudManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;

public class HudEditorScreen extends Screen {

    private boolean draggingHotbar;
    private boolean draggingHearts;
    private boolean draggingHunger;
    private boolean draggingXP;

    private double offsetX, offsetY;

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x33000000);
    }

    public HudEditorScreen() {
        super(Text.literal("HUD Editor"));
    }

    @Override
    protected void init() {
        int buttonWidth = 80;
        int buttonHeight = 20;

        int buttonDifference = 8;

        // Reset HUD button
        this.addDrawableChild(
            ButtonWidget.builder(
                    Text.literal("Reset HUD"),
                    button -> {
                        HudManager.hotbarX = 0;
                        HudManager.hotbarY = 0;
                        HudManager.heartsX = 0;
                        HudManager.heartsY = 0;
                        HudManager.hungerX = 0;
                        HudManager.hungerY = 0;
                        HudManager.xpbarX = 0;
                        HudManager.xpbarY = 0;
                    }
            ).dimensions(
                    this.width - buttonWidth - 10,
                    this.height / 2 - 10,
                    buttonWidth,
                    buttonHeight
            ).build()
        );

        // Bottom Left button
        this.addDrawableChild(
            ButtonWidget.builder(
                    Text.literal("Bottom Left"),
                    button -> {
                        int baseX = this.width / 2 - 93;

                        HudManager.hotbarX = -baseX;
                        HudManager.hotbarY = 0;

                        HudManager.heartsX = -baseX;
                        HudManager.heartsY = 0;

                        HudManager.hungerX = -baseX;
                        HudManager.hungerY = 0;

                        HudManager.xpbarX = -baseX;
                        HudManager.xpbarY = 0;
                    }
            ).dimensions(
                    this.width - buttonWidth - 10,
                    this.height / 2 + buttonHeight + buttonDifference,
                    buttonWidth,
                    buttonHeight
            ).build()
        );

        // Bottom Right
        this.addDrawableChild(
            ButtonWidget.builder(
                    Text.literal("Bottom Right"),
                    button -> {
                        int baseX = this.width / 2 - 93;

                        HudManager.hotbarX = baseX;
                        HudManager.hotbarY = 0;

                        HudManager.heartsX = baseX;
                        HudManager.heartsY = 0;

                        HudManager.hungerX = baseX;
                        HudManager.hungerY = 0;

                        HudManager.xpbarX = baseX;
                        HudManager.xpbarY = 0;
                    }
            ).dimensions(
                    this.width - buttonWidth - 10,
                    this.height / 2 + 2*(buttonHeight + buttonDifference),
                    buttonWidth,
                    buttonHeight
            ).build()
        );
    }

    @Override
    public boolean mouseClicked(Click click, boolean dblClick) {
        double mouseX = click.x();
        double mouseY = click.y();
        int button = click.button();

        if (button == 0) {
            if (insideHotbar(mouseX, mouseY)) {
                draggingHotbar = true;
                offsetX = mouseX - (this.width / 2 - 91 + HudManager.hotbarX);
                offsetY = mouseY - (this.height - 22 + HudManager.hotbarY);
                return true;
            }

            if (insideHearts(mouseX, mouseY)) {
                draggingHearts = true;
                offsetX = mouseX - (this.width / 2 - 91 + HudManager.heartsX);
                offsetY = mouseY - (this.height - 22 - 18 + HudManager.heartsY);
                return true;
            }

            if (insideHunger(mouseX, mouseY)) {
                draggingHunger = true;
                offsetX = mouseX - (this.width / 2 - 91 + 101 + HudManager.hungerX);
                offsetY = mouseY - (this.height - 22 - 18 + HudManager.hungerY);
                return true;
            }

            if (insideXP(mouseX, mouseY)) {
                draggingXP = true;
                int xpHeight = 5;
                offsetX = mouseX - (this.width / 2 - 91 + HudManager.xpbarX);
                offsetY = mouseY - (this.height - 22 - xpHeight - 2 + HudManager.xpbarY);
                return true;
            }
        }

        return super.mouseClicked(click, dblClick);
    }

    @Override
    public boolean mouseDragged(Click click, double deltaX, double deltaY) {
        double mouseX = click.x();
        double mouseY = click.y();

        if (draggingHotbar) {
            HudManager.hotbarX = (int)(mouseX - offsetX - (this.width / 2 - 91));
            HudManager.hotbarY = (int)(mouseY - offsetY - (this.height - 22));
            return true;
        }

        if (draggingHearts) {
            HudManager.heartsX = (int)(mouseX - offsetX - (this.width / 2 - 91));
            HudManager.heartsY = (int)(mouseY - offsetY - (this.height - 22 - 18));
            return true;
        }

        if (draggingHunger) {
            HudManager.hungerX = (int)(mouseX - offsetX - (this.width / 2 - 91 + 101));
            HudManager.hungerY = (int)(mouseY - offsetY - (this.height - 22 - 18));
            return true;
        }

        if (draggingXP) {
            HudManager.xpbarX = (int)(mouseX - offsetX - (this.width / 2 - 91));
            HudManager.xpbarY = (int)(mouseY - offsetY - (this.height - 22 - 5 - 2));
            return true;
        }

        return super.mouseDragged(click, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            draggingHotbar = false;
            draggingHearts = false;
            draggingHunger = false;
            draggingXP = false;
        }

        return super.mouseReleased(click);
    }

    @Override
    public void resize(int width, int height) {
        int oldBaseX = this.width / 2 - 91;
        int oldBaseY = this.height - 22;

        int newBaseX = width / 2 - 91;
        int newBaseY = height - 22;

        int deltaX = oldBaseX - newBaseX;
        int deltaY = oldBaseY - newBaseY;

        // Adjust stored offsets so the HUD stays in the same visual place
        HudManager.hotbarX += deltaX;
        HudManager.hotbarY += deltaY;

        HudManager.heartsX += deltaX;
        HudManager.heartsY += deltaY;

        HudManager.hungerX += deltaX;
        HudManager.hungerY += deltaY;

        HudManager.xpbarX += deltaX;
        HudManager.xpbarY += deltaY;
        super.resize(width, height);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        float scale = 2.0f;

        context.getMatrices().pushMatrix();
        context.getMatrices().scale(scale, scale);

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal("HUD Editor"),
                (int)((this.width / 2) / scale),
                (int)(10 / scale),
                0xFFFFFFFF
        );

        context.getMatrices().popMatrix();

        int baseX = this.width / 2 - 91;
        int baseY = this.height - 22;

        int x = baseX + HudManager.hotbarX;
        int y = baseY + HudManager.hotbarY;

        // Inventory preview box
        context.fill(x, y, x + 182, y + 22, 0x55FFFFFF);

        // Hearts preview box
        int heartsX = baseX + HudManager.heartsX;
        int heartsY = baseY - 18 + HudManager.heartsY;
        context.fill(heartsX, heartsY, heartsX + 81, heartsY + 9, 0x55FF5555);

        // Hunger preview box
        int hungerX = baseX + HudManager.hungerX + 101;
        int hungerY = baseY - 18 + HudManager.hungerY;
        context.fill(hungerX, hungerY, hungerX + 81, hungerY + 9, 0x5599FF55);

        // XP preview box
        int xpbarX = baseX + HudManager.xpbarX;
        int xpbarY = baseY - 5 - 2 + HudManager.xpbarY;
        context.fill(xpbarX, xpbarY, xpbarX + 182, xpbarY + 5, 0x5500FF00);
    }

    /* 
    * Hitbox methods for all 4 GUIs
    */
    private boolean insideHotbar(double mouseX, double mouseY) {
        int baseX = this.width / 2 - 91;
        int baseY = this.height - 22;

        int x = baseX + HudManager.hotbarX;
        int y = baseY + HudManager.hotbarY;

        return mouseX >= x && mouseX <= x + 182 && mouseY >= y && mouseY <= y + 22;
    }

    private boolean insideHearts(double mouseX, double mouseY) {
        int baseX = this.width / 2 - 91;
        int baseY = this.height - 22;

        int x = baseX + HudManager.heartsX;
        int y = baseY - 18 + HudManager.heartsY;

        return mouseX >= x && mouseX <= x + 81 && mouseY >= y && mouseY <= y + 9;
    }

    private boolean insideHunger(double mouseX, double mouseY) {
        int baseX = this.width / 2 - 91;
        int baseY = this.height - 22;

        int x = baseX + HudManager.hungerX + 101;
        int y = baseY - 18 + HudManager.hungerY;

        return mouseX >= x && mouseX <= x + 81 && mouseY >= y && mouseY <= y + 9;
    }

    private boolean insideXP(double mouseX, double mouseY) {
        int baseX = this.width / 2 - 91;
        int baseY = this.height - 22;

        int xpWidth = 182;
        int xpHeight = 5;

        int x = baseX + HudManager.xpbarX;
        int y = baseY - xpHeight - 2 + HudManager.xpbarY;

        return mouseX >= x && mouseX <= x + xpWidth && mouseY >= y && mouseY <= y + xpHeight;
    }
}