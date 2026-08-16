package com.bedwarsclient.modules;

import com.bedwarsclient.modules.pvp.*;
import com.bedwarsclient.modules.fps.*;

import java.util.*;

public class ModuleManager {

    private final Map<String, Module> modules = new LinkedHashMap<String, Module>();

    public ModuleManager() {
        register(new NoHitDelay());
        register(new AimAssist());
        register(new Reach());
        register(new Velocity());
        register(new AutoSprint());
        register(new Criticals());
        register(new FpsBooster());

        System.out.println("[BWClient] " + modules.size() + " modules registered");
    }

    private void register(Module module) {
        modules.put(module.getName().toLowerCase(), module);
    }

    public Module getModule(String name) {
        return modules.get(name.toLowerCase());
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> clazz) {
        for (Module m : modules.values()) {
            if (clazz.isInstance(m)) return (T) m;
        }
        return null;
    }

    public Collection<Module> getAllModules() {
        return modules.values();
    }

    public void onTick() {
        for (Module m : modules.values()) {
            if (m.isEnabled()) {
                try {
                    m.onTick();
                } catch (Exception e) {
                    System.err.println("[BWClient] Error in " + m.getName());
                }
            }
        }
    }
}
