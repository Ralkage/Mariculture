package joshie.mariculture.modules.abyssal;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.abyssal.block.BlockLimestone;
import joshie.mariculture.modules.abyssal.block.BlockLimestoneSlab;
import joshie.mariculture.modules.abyssal.block.BlockLimestoneSlab.Type;
import joshie.mariculture.util.BlockStairsMC;
import net.minecraft.block.state.IBlockState;

import static joshie.mariculture.helpers.ConfigHelper.*;
import static joshie.mariculture.helpers.RecipeHelper.*;
import static joshie.mariculture.modules.abyssal.block.BlockLimestone.Type.*;
import static joshie.mariculture.util.MCTab.getTab;
import static net.minecraft.init.Blocks.SAND;

/** The Abyssal (Seabed/Seafloor) Module is all about changing the ocean floor itself, to make it more interesting
 *  Instead of just the vanilla gravel oceans, it adds a limestone top, covered by sand,
 *  It also will add more interesting features, like deep sea trents, caves, and hydrothermal vents  */
@Module(name = "abyssal")
public class Abyssal {
    public static final BlockLimestone LIMESTONE = new BlockLimestone().register("limestone");
    public static BlockStairsMC LIMESTONE_STAIRS_RAW;
    public static BlockStairsMC LIMESTONE_STAIRS_SMOOTH;
    public static BlockStairsMC LIMESTONE_STAIRS_BRICK;
    public static BlockStairsMC LIMESTONE_STAIRS_SMALL_BRICK;
    public static BlockStairsMC LIMESTONE_STAIRS_THIN_BRICK;
    public static BlockStairsMC LIMESTONE_STAIRS_BORDERED;
    public static BlockLimestoneSlab LIMESTONE_SLAB;
    public static BlockLimestoneSlab LIMESTONE_SLAB_DOUBLE;

    public static void preInit() {
        getTab("core").setStack(Abyssal.LIMESTONE.getStackFromEnum(RAW));
        if (LIMESTONE_STAIRS) {
            LIMESTONE_STAIRS_RAW = new BlockStairsMC(LIMESTONE.getStateFromEnum(RAW)).register("limestone_raw_stairs");
            LIMESTONE_STAIRS_SMOOTH = new BlockStairsMC(LIMESTONE.getStateFromEnum(SMOOTH)).register("limestone_smooth_stairs");
            LIMESTONE_STAIRS_BRICK = new BlockStairsMC(LIMESTONE.getStateFromEnum(BRICK)).register("limestone_brick_stairs");
            LIMESTONE_STAIRS_SMALL_BRICK = new BlockStairsMC(LIMESTONE.getStateFromEnum(SMALL_BRICK)).register("limestone_smallbrick_stairs");
            LIMESTONE_STAIRS_THIN_BRICK = new BlockStairsMC(LIMESTONE.getStateFromEnum(THIN_BRICK)).register("limestone_thinbrick_stairs");
            LIMESTONE_STAIRS_BORDERED = new BlockStairsMC(LIMESTONE.getStateFromEnum(BORDERED)).register("limestone_bordered_stairs");
        }

        if (LIMESTONE_SLABS) {
            LIMESTONE_SLAB = new BlockLimestoneSlab.Half().registerWithoutItem("limestone_slab");
            LIMESTONE_SLAB_DOUBLE = new BlockLimestoneSlab.Double().registerWithoutItem("limestone_slab_double");
            LIMESTONE_SLAB.getItemBlock().register("limestone_slab");
            LIMESTONE.getItemBlock().register("limestone_slab_double");
        }
    }

    //Add recipes for all the limestone variants
    public static void init() {
        addSmelting(LIMESTONE.getStackFromEnum(SMOOTH), LIMESTONE.getStackFromEnum(RAW), 0.1F);
        add4x4Recipe(LIMESTONE.getStackFromEnum(BRICK), LIMESTONE.getStackFromEnum(RAW));
        add4x4Recipe(LIMESTONE.getStackFromEnum(BORDERED), LIMESTONE.getStackFromEnum(SMOOTH));
        add4x4Recipe(LIMESTONE.getStackFromEnum(SMALL_BRICK), LIMESTONE.getStackFromEnum(BRICK));
        add4x4Recipe(LIMESTONE.getStackFromEnum(CHISELED), LIMESTONE.getStackFromEnum(BORDERED));
        addShaped(LIMESTONE.getStackFromEnum(THIN_BRICK), "XY", "YX", 'X', LIMESTONE.getStackFromEnum(BRICK), 'Y', LIMESTONE.getStackFromEnum(SMALL_BRICK));
        addShaped(LIMESTONE.getStackFromEnum(PILLAR_1), "X", "X", 'X', LIMESTONE.getStackFromEnum(SMOOTH));
        addShaped(LIMESTONE.getStackFromEnum(PEDESTAL_1), "X", "Y", 'X', LIMESTONE.getStackFromEnum(PILLAR_1), 'Y', LIMESTONE.getStackFromEnum(BORDERED));

        //Add Stair Recipe if stairs were enabled
        if (LIMESTONE_STAIRS) {
            addStairRecipe(LIMESTONE_STAIRS_RAW.getStack(4), LIMESTONE.getStackFromEnum(RAW));
            addStairRecipe(LIMESTONE_STAIRS_SMOOTH.getStack(4), LIMESTONE.getStackFromEnum(SMOOTH));
            addStairRecipe(LIMESTONE_STAIRS_BRICK.getStack(4), LIMESTONE.getStackFromEnum(BRICK));
            addStairRecipe(LIMESTONE_STAIRS_SMALL_BRICK.getStack(4), LIMESTONE.getStackFromEnum(SMALL_BRICK));
            addStairRecipe(LIMESTONE_STAIRS_THIN_BRICK.getStack(4), LIMESTONE.getStackFromEnum(THIN_BRICK));
            addStairRecipe(LIMESTONE_STAIRS_BORDERED.getStack(4), LIMESTONE.getStackFromEnum(BORDERED));
        }

        //Add Slab Recipe if slabs were enable
        if (LIMESTONE_SLABS) {
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.RAW, 6), LIMESTONE.getStackFromEnum(RAW));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.SMOOTH, 6), LIMESTONE.getStackFromEnum(SMOOTH));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.BRICK, 6), LIMESTONE.getStackFromEnum(BRICK));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.SMALL_BRICK, 6), LIMESTONE.getStackFromEnum(SMALL_BRICK));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.THIN_BRICK, 6), LIMESTONE.getStackFromEnum(THIN_BRICK));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.BORDERED, 6), LIMESTONE.getStackFromEnum(BORDERED));
        }
    }

    //Configuration options
    public static boolean OCEAN_REPLACE;
    public static int OCEAN_FILLER_DEPTH;
    public static IBlockState OCEAN_FILLER;
    public static IBlockState OCEAN_SURFACE;
    public static boolean LIMESTONE_STAIRS;
    public static boolean LIMESTONE_SLABS;

    public static void configure() {
        OCEAN_REPLACE = getBoolean("Replace Ocean Blocks", true);
        OCEAN_FILLER_DEPTH = getInteger("Ocean Filler Depth", 5) * 2; //To make it fit the actual input value
        OCEAN_FILLER = getBlockState("Ocean Filler Block", LIMESTONE.getDefaultState());
        OCEAN_SURFACE = getBlockState("Ocean Surface Block", SAND.getDefaultState());
        LIMESTONE_STAIRS = getBoolean("Enable Limestone Stairs", true);
        LIMESTONE_SLABS = getBoolean("Enable Limestone Slabs", true);
    }
}