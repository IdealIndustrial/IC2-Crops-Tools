package com.fourgname.iia.mods.ic2;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.crop.TileEntityCrop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.util.StatCollector.translateToLocal;

public class CropsLens extends Item {

    public CropsLens() {
        super();
        this.setMaxDamage(0);
        this.setUnlocalizedName("cppLens");
        this.setMaxStackSize(1);
        this.setTextureName("iia:itemLens");
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean b) {
        list.add(translateToLocal("lens.tooltip.0"));
        list.add(translateToLocal("lens.tooltip.1"));
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer aPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return false;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCrop) {
            TileEntityCrop crop = (TileEntityCrop) te;
            if (aPlayer != null && crop.getCrop() != null && crop.getCrop().name() != null) {
                aPlayer.addChatComponentMessage(new ChatComponentText("This is a " + crop.getCrop().name()));
            } else {
                return false;
            }
            if (crop.getScanLevel() < 1) {
                crop.setScanLevel((byte) 1);
                crop.markDirty();
            }
            return true;
        }
        return false;
    }
}