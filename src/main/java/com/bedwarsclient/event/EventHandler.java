package com.bedwarsclient.event;

import com.bedwarsclient.BedwarsClient;
import com.bedwarsclient.modules.ModuleManager;
import com.bedwarsclient.modules.pvp.Criticals;
import com.bedwarsclient.modules.pvp.Reach;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;

        BedwarsClient.getInstance().getKeyBindManager().onTick();
        BedwarsClient.getInstance().getModuleManager().onTick();
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (event.entity != Minecraft.getMinecraft().thePlayer) return;

        ModuleManager mm = BedwarsClient.getInstance().getModuleManager();

        Criticals crit = mm.getModule(Criticals.class);
        if (crit != null && crit.isEnabled()) {
            crit.onPreAttack();
        }
    }
}
