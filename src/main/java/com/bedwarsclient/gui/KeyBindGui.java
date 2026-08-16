package com.bedwarsclient.gui;

import com.bedwarsclient.BedwarsClient;
import com.bedwarsclient.keybind.KeyBindEntry;
import com.bedwarsclient.keybind.KeyBindManager;
import com.bedwarsclient.keybind.ModuleType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class KeyBindGui extends GuiScreen {

    private ModuleType recordingModule = null;
    private boolean isRecording = false;

    @Override
    public void initGui() {
        KeyBindManager km = BedwarsClient.getInstance().getKeyBindManager();
        int cx = width / 2;
        int y = 40;
        int id = 0;

        for (ModuleType type : ModuleType.values()) {
            KeyBindEntry bind = km.getBinding(type);

            String btnText;
            if (isRecording && recordingModule == type) {
                btnText = "> Press a key... <";
            } else if (bind.hasKey()) {
                btnText = bind.getDisplayName();
            } else {
                btnText = "[Click to Set]";
            }

            buttonList.add(new GuiButton(id, cx + 10, y, 120, 18, btnText));

            if (bind.hasKey()) {
                buttonList.add(new GuiButton(1000 + id, cx + 135, y, 18, 18, "X"));
            }

            y += 22;
            id++;
        }

        buttonList.add(new GuiButton(900, cx - 100, height - 40, 95, 20, "Clear All"));
        buttonList.add(new GuiButton(901, cx + 5, height - 40, 95, 20, "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        KeyBindManager km = BedwarsClient.getInstance().getKeyBindManager();

        if (button.id == 900) {
            km.clearAll();
            refreshScreen();
            return;
        }
        if (button.id == 901) {
            mc.displayGuiScreen(new MainMenuGui());
            return;
        }

        if (button.id >= 1000) {
            int index = button.id - 1000;
            ModuleType type = ModuleType.values()[index];
            km.clearKeyBind(type);
            refreshScreen();
            return;
        }

        if (button.id < ModuleType.values().length) {
            ModuleType type = ModuleType.values()[button.id];
            if (isRecording && recordingModule == type) {
                stopRecording();
            } else {
                recordingModule = type;
                isRecording = true;
                refreshScreen();
            }
        }
    }

    private void stopRecording() {
        recordingModule = null;
        isRecording = false;
        refreshScreen();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (isRecording && recordingModule != null) {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                stopRecording();
                return;
            }

            KeyBindManager km = BedwarsClient.getInstance().getKeyBindManager();
            km.setKeyBind(recordingModule, keyCode);
            stopRecording();
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString(fontRendererObj,
            "KeyBind Settings",
            width / 2, 10, 0xFFFFFF);

        drawCenteredString(fontRendererObj,
            "Click a button, then press a key. ESC to cancel.",
            width / 2, 22, 0xAAAAAA);

        int cx = width / 2;
        int y = 40;
        for (ModuleType type : ModuleType.values()) {
            drawString(fontRendererObj, type.displayName, cx - 160, y + 5, 0xFFFFFF);
            y += 22;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void refreshScreen() {
        buttonList.clear();
        initGui();
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
