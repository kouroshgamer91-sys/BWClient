package com.bedwarsclient.hud;

import com.bedwarsclient.BedwarsClient;
import com.bedwarsclient.modules.Module;
import com.bedwarsclient.modules.pvp.Reach;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

public class HudRenderer extends Gui {

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;
        if (mc.gameSettings.showDebugInfo) return;

        FontRenderer fr = mc.fontRendererObj;

        int y = 4;
        Collection<Module> modules = BedwarsClient.getInstance()
            .getModuleManager().getAllModules();

        for (Module module : modules) {
            if (module.isEnabled()) {
                String text = module.getDisplayName();
                drawRect(1, y - 1, fr.getStringWidth(text) + 5, y + 10, 0x80000000);
                drawRect(0, y - 1, 1, y + 10, 0xFF55FFFF);
                fr.drawStringWithShadow(text, 3, y, 0xFFFFFF);
                y += 12;
            }
        }

        Reach reach = BedwarsClient.getInstance()
            .getModuleManager().getModule(Reach.class);
        if (reach != null && reach.isEnabled()) {
            String reachText = "Reach: " + String.format("%.2f", reach.getCurrentReach());
            int x = 4;
            int yR = 100;
            drawRect(x - 2, yR - 2, x + fr.getStringWidth(reachText) + 4, yR + 10, 0x80000000);
            fr.drawStringWithShadow(reachText, x, yR, 0xFFFF55);
        }
    }
}
