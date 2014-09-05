package joshie.mariculture.diving;

import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.lib.ArmorSlot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ArmorClientHandler {
    @SubscribeEvent
    public void onFogRender(FogDensity event) {
        if (event.block.getMaterial() == Material.water) {
            if (event.entity instanceof EntityPlayer && event.entity == ClientHelper.getPlayer()) {
                EntityPlayer player = ClientHelper.getPlayer();
                if (player.inventory.armorInventory[3] != null) {
                    if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.divingHelmet)) {
                        event.density = 0.0F;
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
