package com.bedwarsclient.keybind;

public enum ModuleType {

    OPEN_MENU("Main Menu", false),
    OPEN_KEYBINDS("KeyBinds", false),

    NO_HIT_DELAY("NoHitDelay", true),
    AIM_ASSIST("AimAssist", true),
    CRITICALS("Criticals", true),
    REACH("Reach", true),
    VELOCITY("Velocity", true),
    AUTO_SPRINT("AutoSprint", true),
    FPS_BOOST("FPS Boost", true);

    public final String displayName;
    public final boolean toggleable;

    ModuleType(String d, boolean t) {
        this.displayName = d;
        this.toggleable = t;
    }
}
