package joshie.maritech.extensions.modules;

import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.helpers.RecipeHelper.asStack;
import static joshie.mariculture.core.lib.ItemLib.aluminumSheet;
import static joshie.mariculture.core.lib.ItemLib.compressorBase;
import static joshie.mariculture.core.lib.ItemLib.compressorTop;
import static joshie.mariculture.core.lib.ItemLib.cooling;
import static joshie.mariculture.core.lib.ItemLib.ink;
import static joshie.mariculture.core.lib.ItemLib.ironWheel;
import static joshie.mariculture.core.lib.ItemLib.neoprene;
import static joshie.mariculture.core.lib.ItemLib.piston;
import static joshie.mariculture.core.lib.ItemLib.plastic;
import static joshie.mariculture.core.lib.ItemLib.plasticLens;
import static joshie.mariculture.core.lib.ItemLib.titaniumBattery;
import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineRenderedMultiMeta;
import joshie.mariculture.core.network.PacketHandler;
import joshie.maritech.handlers.ScubaEvent;
import joshie.maritech.items.ItemArmorScuba;
import joshie.maritech.lib.MTRenderIds;
import joshie.maritech.network.PacketCompressor;
import joshie.maritech.tile.TileAirCompressor;
import joshie.maritech.util.IModuleExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;

public class ExtensionDiving implements IModuleExtension {
    public static Item scubaMask;
    public static Item scubaTank;
    public static Item scubaSuit;
    public static Item swimfin;
    public static ArmorMaterial armorSCUBA = EnumHelper.addArmorMaterial("SCUBA", 15, new int[] { 0, 0, 1, 0 }, 0);

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new ScubaEvent());
        scubaMask = new ItemArmorScuba(ExtensionDiving.armorSCUBA, MTRenderIds.SCUBA, 0).setUnlocalizedName("scuba.mask");
        scubaTank = new ItemArmorScuba(ExtensionDiving.armorSCUBA, MTRenderIds.SCUBA, 1).setUnlocalizedName("scuba.tank").setNoRepair();
        scubaSuit = new ItemArmorScuba(ExtensionDiving.armorSCUBA, MTRenderIds.SCUBA, 2).setUnlocalizedName("scuba.suit");
        swimfin = new ItemArmorScuba(ExtensionDiving.armorSCUBA, MTRenderIds.SCUBA, 3).setUnlocalizedName("swimfin");
        RegistryHelper.registerTiles("Mariculture", new Class[] { TileAirCompressor.class });

        if (MaricultureTab.tabFactory != null) {
            MaricultureTab.tabFactory.setIcon(new ItemStack(Core.renderedMachinesMulti, 1, MachineRenderedMultiMeta.COMPRESSOR_TOP), true);
        }
    }

    @Override
    public void init() {
        addShaped(asStack(compressorTop, 2), new Object[] { "  F", " PB", "III", 'I', aluminumSheet, 'F', cooling, 'B', titaniumBattery, 'P', piston });
        addShaped(compressorBase, new Object[] { "ITT", "III", "W  ", 'I', aluminumSheet, 'W', ironWheel, 'T', "ingotTitanium" });
        addShaped(asStack(scubaMask), new Object[] { "PD", "LL", 'P', "dyeBlack", 'L', plasticLens, 'D', "dyeYellow" });
        addShaped(asStack(asStack(scubaTank), scubaTank.getMaxDamage() - 1, 1), new Object[] { "DSD", "S S", "DSD", 'S', aluminumSheet, 'D', "dyeYellow" });
        addShaped(asStack(scubaSuit), new Object[] { "NNN", " N ", "NNN", 'N', neoprene });
        addShaped(asStack(swimfin), new Object[] { "N N", "PDP", "PDP", 'N', neoprene, 'P', plastic, 'D', ink });
    }

    @Override
    public void postInit() {
        PacketHandler.registerPacket(PacketCompressor.class, Side.CLIENT);
    }
}
