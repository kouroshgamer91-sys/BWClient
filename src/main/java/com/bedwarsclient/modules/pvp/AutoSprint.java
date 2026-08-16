package com.bedwarsclient.modules.pvp;

import com.bedwarsclient.modules.Module;
import net.minecraft.client.Minecraft;

public class AutoSprint extends Module {

    public AutoSprint() {
        super("AutoSprint", "AutoSprint", Category.PVP);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;

        if (mc.thePlayer.moveForward > 0 &&
            !mc.thePlayer.isSneaking() &&
            !mc.thePlayer.isCollidedHorizontally &&
            mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
            mc.thePlayer.setSprinting(true);
        }
    }
}
