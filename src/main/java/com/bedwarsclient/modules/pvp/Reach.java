package com.bedwarsclient.modules.pvp;

import com.bedwarsclient.modules.Module;
import net.minecraft.client.Minecraft;

public class Reach extends Module {

    private float baseReach = 3.15f;
    private float minReach = 3.0f;
    private float maxReach = 3.5f;
    private float currentReach = 3.0f;
    private boolean expandHitbox = false;
    private float hitboxExpand = 0.1f;

    public Reach() {
        super("Reach", "Reach", Category.PVP);
    }

    @Override
    public void onEnable() {
        System.out.println("[BWClient] Reach ON - " + baseReach + " blocks");
    }

    @Override
    public void onDisable() {
        currentReach = 3.0f;
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;

        currentReach = baseReach;

        if (mc.thePlayer.isSprinting()) currentReach += 0.1f;
        if (!mc.thePlayer.onGround) currentReach += 0.05f;

        if (currentReach > maxReach) currentReach = maxReach;
        if (currentReach < minReach) currentReach = minReach;
    }

    public float getCurrentReach() { return currentReach; }
    public float getBaseReach() { return baseReach; }
    public void setBaseReach(float r) { this.baseReach = r; }
    public boolean isExpandHitbox() { return expandHitbox; }
    public void setExpandHitbox(boolean b) { this.expandHitbox = b; }
    public float getHitboxExpand() { return hitboxExpand; }
}
