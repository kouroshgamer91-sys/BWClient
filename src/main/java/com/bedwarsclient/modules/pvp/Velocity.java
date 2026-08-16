package com.bedwarsclient.modules.pvp;

import com.bedwarsclient.modules.Module;

public class Velocity extends Module {

    private float horizontal = 0.0f;
    private float vertical = 0.0f;

    public Velocity() {
        super("Velocity", "Velocity", Category.PVP);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    public float getHorizontal() { return horizontal; }
    public void setHorizontal(float h) { this.horizontal = h; }
    public float getVertical() { return vertical; }
    public void setVertical(float v) { this.vertical = v; }
}
