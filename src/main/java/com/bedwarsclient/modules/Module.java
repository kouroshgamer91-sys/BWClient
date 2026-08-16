package com.bedwarsclient.modules;

public abstract class Module {

    private final String name;
    private final String displayName;
    private final Category category;
    private boolean enabled = false;

    public enum Category {
        PVP, FPS, RENDER, MISC
    }

    public Module(String name, String displayName, Category category) {
        this.name = name;
        this.displayName = displayName;
        this.category = category;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract void onTick();

    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean e) { this.enabled = e; }
}
