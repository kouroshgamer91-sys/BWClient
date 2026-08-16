package com.bedwarsclient.keybind;

import com.bedwarsclient.BedwarsClient;
import com.bedwarsclient.gui.KeyBindGui;
import com.bedwarsclient.gui.MainMenuGui;
import com.bedwarsclient.modules.Module;
import com.bedwarsclient.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.*;

public class KeyBindManager {

    private final Map<ModuleType, KeyBindEntry> bindings = new LinkedHashMap<ModuleType, KeyBindEntry>();
    private final Set<Integer> pressedKeys = new HashSet<Integer>();
    private final KeyBindConfig config;

    public KeyBindManager(File clientDir) {
        for (ModuleType type : ModuleType.values()) {
            bindings.put(type, new KeyBindEntry(type));
        }

        config = new KeyBindConfig(clientDir);
        config.load(bindings);

        System.out.println("[BWClient] KeyBindManager loaded");
    }

    public void onTick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null) return;
        if (mc.thePlayer == null) return;

        for (Map.Entry<ModuleType, KeyBindEntry> entry : bindings.entrySet()) {
            KeyBindEntry bind = entry.getValue();
            if (!bind.hasKey()) continue;

            int keyCode = bind.getKeyCode();
            boolean isDown = Keyboard.isKeyDown(keyCode);

            if (isDown && !pressedKeys.contains(keyCode)) {
                pressedKeys.add(keyCode);
                trigger(entry.getKey());
            } else if (!isDown) {
                pressedKeys.remove(keyCode);
            }
        }
    }

    private void trigger(ModuleType type) {
        Minecraft mc = Minecraft.getMinecraft();
        ModuleManager mm = BedwarsClient.getInstance().getModuleManager();

        switch (type) {
            case OPEN_MENU:
                mc.displayGuiScreen(new MainMenuGui());
                break;
            case OPEN_KEYBINDS:
                mc.displayGuiScreen(new KeyBindGui());
                break;
            case NO_HIT_DELAY:
                toggleModule(mm.getModule("NoHitDelay"));
                break;
            case AIM_ASSIST:
                toggleModule(mm.getModule("AimAssist"));
                break;
            case CRITICALS:
                toggleModule(mm.getModule("Criticals"));
                break;
            case REACH:
                toggleModule(mm.getModule("Reach"));
                break;
            case VELOCITY:
                toggleModule(mm.getModule("Velocity"));
                break;
            case AUTO_SPRINT:
                toggleModule(mm.getModule("AutoSprint"));
                break;
            case FPS_BOOST:
                toggleModule(mm.getModule("FpsBooster"));
                break;
        }
    }

    private void toggleModule(Module module) {
        if (module == null) return;
        module.toggle();
    }

    public void setKeyBind(ModuleType type, int key) {
        KeyBindEntry entry = bindings.get(type);
        if (entry != null) {
            entry.setKey(key);
            config.save(bindings);
        }
    }

    public void clearKeyBind(ModuleType type) {
        KeyBindEntry entry = bindings.get(type);
        if (entry != null) {
            entry.clearKey();
            config.save(bindings);
        }
    }

    public void clearAll() {
        for (KeyBindEntry e : bindings.values()) e.clearKey();
        config.save(bindings);
    }

    public KeyBindEntry getBinding(ModuleType t) { return bindings.get(t); }
    public Map<ModuleType, KeyBindEntry> getAll() { return bindings; }
}
