package com.fourgname.iia.loader;

import com.fourgname.iia.mods.ic2.CropsLens;
import com.fourgname.iia.mods.ic2.CropsSpade;
import com.fourgname.iia.mods.oc.OpenComputers_LoaderDriver;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MainLoader {

    public final static Item itemSpade = new CropsSpade();
    public final static Item itemLens = new CropsLens();
    public final static ItemStack itemSpadeStack = new ItemStack(itemSpade);
    public final static ItemStack itemStackLens = new ItemStack(itemLens);

    public static void loadItems() {
        GameRegistry.registerItem(itemSpade, "itemSpade");
        GameRegistry.registerItem(itemLens, "itemLens");
    }

    public static void loadRecipes() {
        GameRegistry.addRecipe(itemSpadeStack, " P ", "PWP", " S ", 'P', iSget("plateDenseSteel"), 'W', Ic2Items.weedingTrowel.getItem(), 'S', Items.stick);
        GameRegistry.addRecipe(itemStackLens, "G  ", " S ", "  S", 'G', Blocks.glass, 'S', Items.stick);
    }

    public static void init(FMLInitializationEvent e) {
        loadItems();
        loadRecipes();
        if (Loader.isModLoaded("OpenComputers")) {
            OpenComputers_LoaderDriver.register(); //OC Driver Loader
        }
    }

    public static void preInit(FMLPreInitializationEvent e) {

    }

    public static void postInit(FMLPostInitializationEvent e) {

    }

    public static ItemStack iSget(String name) {
        return OreDictionary.getOres(name).size() != 0 ? OreDictionary.getOres(name).get(OreDictionary.getOres(name).size() - 1) : null;
    }
}