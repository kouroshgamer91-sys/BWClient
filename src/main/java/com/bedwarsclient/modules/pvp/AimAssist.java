package com.bedwarsclient.modules.pvp;

import com.bedwarsclient.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class AimAssist extends Module {

    private float aimSpeed = 0.15f;
    private float fov = 15.0f;
    private float maxDistance = 4.5f;

    public AimAssist() {
        super("AimAssist", "AimAssist", Category.PVP);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        EntityLivingBase target = findClosestTarget(mc);
        if (target == null) return;

        double diffX = target.posX - mc.thePlayer.posX;
        double diffZ = target.posZ - mc.thePlayer.posZ;
        double diffY = (target.posY + target.getEyeHeight()) -
                       (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());

        float targetYaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float targetPitch = (float) -Math.toDegrees(
            Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)));

        float deltaYaw = normalizeAngle(targetYaw - mc.thePlayer.rotationYaw);
        float deltaPitch = normalizeAngle(targetPitch - mc.thePlayer.rotationPitch);

        if (Math.abs(deltaYaw) < fov) {
            mc.thePlayer.rotationYaw += deltaYaw * aimSpeed;
        }
        if (Math.abs(deltaPitch) < fov) {
            mc.thePlayer.rotationPitch += deltaPitch * aimSpeed;
        }
    }

    private EntityLivingBase findClosestTarget(Minecraft mc) {
        EntityLivingBase closest = null;
        float closestDist = maxDistance;

        List<Entity> entities = mc.theWorld.loadedEntityList;
        for (Entity entity : entities) {
            if (entity == mc.thePlayer) continue;
            if (!(entity instanceof EntityPlayer)) continue;
            if (!entity.isEntityAlive()) continue;

            float dist = mc.thePlayer.getDistanceToEntity(entity);
            if (dist < closestDist) {
                closestDist = dist;
                closest = (EntityLivingBase) entity;
            }
        }

        return closest;
    }

    private float normalizeAngle(float angle) {
        while (angle < -180) angle += 360;
        while (angle > 180) angle -= 360;
        return angle;
    }
}
