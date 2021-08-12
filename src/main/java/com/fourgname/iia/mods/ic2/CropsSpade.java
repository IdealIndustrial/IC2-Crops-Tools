package com.fourgname.iia.mods.ic2;

import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.crop.TileEntityCrop;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;

import static net.minecraft.util.StatCollector.translateToLocal;

public class CropsSpade extends ItemTool {
    private static Set BlocksAffected = Sets.newHashSet(Blocks.grass, Blocks.dirt, Blocks.snow_layer, Blocks.farmland, Blocks.mycelium, StackUtil.getBlock(ic2.core.Ic2Items.crop));

    public CropsSpade() {
        super(1.0F, Item.ToolMaterial.IRON, BlocksAffected);
        this.setUnlocalizedName("Spade");
        this.setTextureName("iia:itemSpade");
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
    }

    public static int intrandom(int max, int min) {
        if (max < min) {
            int a = max;
            int b = min;
            min = a;
            max = b;
        }
        SplittableRandom random = new SplittableRandom(System.nanoTime());
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Calculates a percentage in a coustom way
     */
    public static float csig(float input, float modifier, boolean rising) {
        return (float) ((float) 1 + Math.tanh((rising ? 1.0 : (-1.0)) * (double) input / (double) modifier));
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(translateToLocal("spade.tooltip.0"));
        list.add(translateToLocal("spade.tooltip.1"));
        list.add(translateToLocal("spade.tooltip.2"));
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!IC2.platform.isSimulating())
            return false;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCrop) {
            TileEntityCrop crop = (TileEntityCrop) te;
            if (crop.getCrop() != null) {
                if (crop.getCrop() instanceof ic2.api.crops.CropCard && crop.getCrop().tier() >= 1) {
                    float i = crop.getCrop().tier() + 5 * ((((-crop.getResistance()) / 2) + crop.getGain() + crop.getGrowth()) / 21);
                    if (intrandom(100, 0) <= 100 * csig(i, 12, false)) {
                        if (crop.getCrop().getGain(crop) != null && crop.getCrop().canBeHarvested(crop))
                            StackUtil.dropAsEntity(world, x, y, z, crop.getCrop().getGain(crop));
                        StackUtil.dropAsEntity(world, x, y, z, crop.generateSeeds(crop.getCrop(), crop.getGrowth(), crop.getGain(), crop.getResistance(), crop.getScanLevel()));
                    }
                } else {
                    StackUtil.dropAsEntity(world, x, y, z, new ItemStack(Ic2Items.weed.getItem(), crop.size));
                    if (!(crop.getCrop().name().equals("weed"))) {
                        if (crop.getSize() == crop.getCrop().maxSize())
                            StackUtil.dropAsEntity(world, x, y, z, crop.generateSeeds(crop.getCrop(), crop.getGrowth(), crop.getGain(), crop.getResistance(), crop.getScanLevel()));
                    } else if (crop.getCrop().getGain(crop) != null && crop.getCrop().canBeHarvested(crop))
                        StackUtil.dropAsEntity(world, x, y, z, crop.getCrop().getGain(crop));
                }
                crop.reset();
                return true;
            }
            crop.reset();
            return true;
        }
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (side != 0 && world.getBlock(x, y + 1, z).getMaterial() == Material.air && (world.getBlock(x, y, z) == Blocks.grass || world.getBlock(x, y, z) == Blocks.dirt || world.getBlock(x, y, z) == Blocks.mycelium)) {
            world.setBlock(x, y, z, Blocks.farmland);
            return true;
        } else if (side != 0 && world.getBlock(x, y + 1, z).getMaterial() == Material.air && (world.getBlock(x, y, z) == Blocks.farmland)) {
            world.setBlock(x, y, z, Blocks.dirt);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World w, Block b, int x, int y, int z, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase, EntityLivingBase player) {
        return true;
    }
}