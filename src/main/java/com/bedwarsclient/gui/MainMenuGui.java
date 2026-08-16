package com.bedwarsclient.gui;

import com.bedwarsclient.BedwarsClient;
import com.bedwarsclient.modules.Module;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainMenuGui extends GuiScreen {

    private List<Module> moduleList = new ArrayList<Module>();

    @Override
    public void initGui() {
        moduleList.clear();
        int cx = width / 2;
        int y = height / 2 - 100;
        int id = 0;

        Collection<Module> modules = BedwarsClient.getInstance()
            .getModuleManager().getAllModules();

        for (Module module : modules) {
            moduleList.add(module);
            String status = module.isEnabled() ? "[ON] " : "[OFF] ";
            buttonList.add(new GuiButton(id++, cx - 100, y, 200, 20,
                status + module.getDisplayName()));
            y += 22;
        }

        y += 10;
        buttonList.add(new GuiButton(100, cx - 100, y, 200, 20,
            "KeyBind Settings"));
        y += 22;
        buttonList.add(new GuiButton(999, cx - 50, y, 100, 20, "Close"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 999) {
            mc.displayGuiScreen(null);
            return;
        }
        if (button.id == 100) {
            mc.displayGuiScreen(new KeyBindGui());
            return;
        }

        if (button.id >= 0 && button.id < moduleList.size()) {
            moduleList.get(button.id).toggle();
            buttonList.clear();
            initGui();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj,
            "Bedwars Client v" + BedwarsClient.VERSION,
            width / 2, height / 2 - 120, 0x55FFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
