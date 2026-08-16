package com.bedwarsclient.keybind;

import org.lwjgl.input.Keyboard;

public class KeyBindEntry {

    private ModuleType module;
    private int keyCode;

    public static final int NONE = -1;

    public KeyBindEntry(ModuleType module) {
        this.module = module;
        this.keyCode = NONE;
    }

    public boolean isPressed(int pressedKey) {
        if (keyCode == NONE) return false;
        return pressedKey == keyCode;
    }

    public String getDisplayName() {
        if (keyCode == NONE) return "[Not Set]";
        return Keyboard.getKeyName(keyCode);
    }

    public void setKey(int key) {
        this.keyCode = key;
    }

    public void clearKey() {
        this.keyCode = NONE;
    }

    public boolean hasKey() { return keyCode != NONE; }

    public ModuleType getModule() { return module; }
    public int getKeyCode() { return keyCode; }
}
