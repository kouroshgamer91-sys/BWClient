package com.bedwarsclient.modules.fps;

import com.bedwarsclient.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class FpsBooster extends Module {

    private int tickCounter = 0;

    public FpsBooster() {
        super("FpsBooster", "FPS Boost", Category.FPS);
    }

    @Override
    public void onEnable() {
        applyOptimizations();
    }

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {
        tickCounter++;

        if (tickCounter % 1200 == 0) {
            System.gc();
            tickCounter = 0;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null) {
            mc.theWorld.setRainStrength(0.0f);
        }
    }

    public void applyOptimizations() {
        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gs = mc.gameSettings;

        gs.particleSetting = 2;
        gs.fancyGraphics = false;
        gs.ambientOcclusion = 0;
        gs.clouds = 0;
        gs.mipmapLevels = 0;
        gs.viewBobbing = false;
        gs.renderDistanceChunks = 4;
        gs.entityShadows = false;
    }
}
