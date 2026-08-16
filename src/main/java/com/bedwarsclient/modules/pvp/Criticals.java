package com.bedwarsclient.modules.pvp;

import com.bedwarsclient.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {

    public Criticals() {
        super("Criticals", "Criticals", Category.PVP);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    public void onPreAttack() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;
        if (!mc.thePlayer.onGround) return;

        mc.thePlayer.sendQueue.addToSendQueue(
            new C03PacketPlayer.C04PacketPlayerPosition(
                mc.thePlayer.posX,
                mc.thePlayer.posY + 0.0625,
                mc.thePlayer.posZ, false));

        mc.thePlayer.sendQueue.addToSendQueue(
            new C03PacketPlayer.C04PacketPlayerPosition(
                mc.thePlayer.posX,
                mc.thePlayer.posY,
                mc.thePlayer.posZ, false));
    }
}
