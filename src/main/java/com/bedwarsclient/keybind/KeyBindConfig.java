package com.bedwarsclient.keybind;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class KeyBindConfig {

    private final File file;

    public KeyBindConfig(File clientDir) {
        File configDir = new File(clientDir, "config");
        if (!configDir.exists()) configDir.mkdirs();
        this.file = new File(configDir, "keybinds.properties");
    }

    public void save(Map<ModuleType, KeyBindEntry> bindings) {
        try {
            Properties props = new Properties();
            for (Map.Entry<ModuleType, KeyBindEntry> e : bindings.entrySet()) {
                props.setProperty(e.getKey().name(), 
                    String.valueOf(e.getValue().getKeyCode()));
            }
            FileOutputStream out = new FileOutputStream(file);
            props.store(out, "BedwarsClient KeyBinds");
            out.close();
        } catch (Exception e) {
            System.err.println("[BWClient] Save error: " + e.getMessage());
        }
    }

    public void load(Map<ModuleType, KeyBindEntry> bindings) {
        try {
            if (!file.exists()) return;
            Properties props = new Properties();
            FileInputStream in = new FileInputStream(file);
            props.load(in);
            in.close();

            for (String key : props.stringPropertyNames()) {
                try {
                    ModuleType type = ModuleType.valueOf(key);
                    int keyCode = Integer.parseInt(props.getProperty(key));
                    KeyBindEntry bind = bindings.get(type);
                    if (bind != null) bind.setKey(keyCode);
                } catch (Exception ex) {}
            }
        } catch (Exception e) {
            System.err.println("[BWClient] Load error: " + e.getMessage());
        }
    }
}
