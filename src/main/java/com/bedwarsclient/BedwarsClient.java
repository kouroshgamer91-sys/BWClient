package com.bedwarsclient;

import com.bedwarsclient.event.EventHandler;
import com.bedwarsclient.hud.HudRenderer;
import com.bedwarsclient.keybind.KeyBindManager;
import com.bedwarsclient.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(
    modid = BedwarsClient.MOD_ID,
    name = BedwarsClient.MOD_NAME,
    version = BedwarsClient.VERSION,
    clientSideOnly = true,
    acceptedMinecraftVersions = "[1.8.9]"
)
public class BedwarsClient {

    public static final String MOD_ID = "bedwarsclient";
    public static final String MOD_NAME = "Bedwars Client";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MOD_ID)
    private static BedwarsClient instance;

    private ModuleManager moduleManager;
    private KeyBindManager keyBindManager;
    private HudRenderer hudRenderer;
    private File clientDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        clientDir = new File(Minecraft.getMinecraft().mcDataDir, "bedwarsclient");
        if (!clientDir.exists()) clientDir.mkdirs();

        File configDir = new File(clientDir, "config");
        if (!configDir.exists()) configDir.mkdirs();

        System.out.println("[BWClient] Pre-Init done");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("[BWClient] Loading " + MOD_NAME + " v" + VERSION);

        moduleManager = new ModuleManager();
        keyBindManager = new KeyBindManager(clientDir);
        hudRenderer = new HudRenderer();

        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(hudRenderer);

        System.out.println("[BWClient] Loaded successfully!");
    }

    public static BedwarsClient getInstance() { return instance; }
    public ModuleManager getModuleManager() { return moduleManager; }
    public KeyBindManager getKeyBindManager() { return keyBindManager; }
    public HudRenderer getHudRenderer() { return hudRenderer; }
    public File getClientDir() { return clientDir; }
}
