package com.fourgname.iia.mods.oc;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import li.cil.oc.integration.ManagedTileEntityEnvironment;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityLaunchController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Driver_GCLaunchController extends DriverSidedTileEntity {

    @Override
    public Class<?> getTileEntityClass() {
        return TileEntityLaunchController.class;
    }

    @Override
    public boolean worksWith(World world, int x, int y, int z, ForgeDirection side) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity instanceof TileEntityLaunchController;
    }

    public ManagedEnvironment createEnvironment(World world, int x, int y, int z, ForgeDirection side) {
        return new Driver_GCLaunchController.Environment((TileEntityLaunchController) world.getTileEntity(x, y, z));
    }

    public static final class Environment extends ManagedTileEntityEnvironment<TileEntityLaunchController> {

        public Environment(TileEntityLaunchController tileEntity) {
            super(tileEntity, "gc_launch_controller");
        }

        @Callback(doc = "function(frequency:number):boolean -- ")
        public Object[] setFrequency(Context context, Arguments args) {
            int freq = args.checkInteger(0);
            try {
                tileEntity.setFrequency(freq);
                boolean checkFreqValid = tileEntity.frequency > 0;
                return new Object[]{checkFreqValid};
            } catch (Throwable e) {
                return new Object[]{false, "invalid"};
            }
        }

        @Callback(doc = "function():number -- ")
        public Object[] getFrequency(Context context, Arguments args) {
            try {
                return new Object[]{tileEntity.frequency};
            } catch (Throwable e) {
                return new Object[]{-1};
            }
        }

        @Callback(doc = "function(frequency:number):boolean -- ")
        public Object[] setDestinationFrequency(Context context, Arguments args) {
            int freq = args.checkInteger(0);
            try {
                tileEntity.setDestinationFrequency(freq);
                boolean checkFreqValid = tileEntity.destFrequency > 0;
                return new Object[]{checkFreqValid};
            } catch (Throwable e) {
                return new Object[]{false, "invalid"};
            }
        }

        @Callback(doc = "function():number -- ")
        public Object[] getDestinationFrequency(Context context, Arguments args) {
            try {
                return new Object[]{tileEntity.destFrequency};
            } catch (Throwable e) {
                return new Object[]{-1};
            }
        }

        @Callback(doc = "function():boolean -- ")
        public Object[] setLaunchWhen(Context context, Arguments args) {
            try {
                tileEntity.launchSchedulingEnabled = args.checkBoolean(0);
                return new Object[]{};
            } catch (Throwable e) {
                return new Object[]{false, "invalid"};
            }
        }

        @Callback(doc = "function():boolean -- ")
        public Object[] getLaunchWhen(Context context, Arguments args) {
            try {
                return new Object[]{tileEntity.launchSchedulingEnabled};
            } catch (Throwable e) {
                return new Object[]{false, "invalid"};
            }
        }

        @Callback(doc = "function():boolean -- ")
        public Object[] setRemovePad(Context context, Arguments args) {
            try {
                tileEntity.launchPadRemovalDisabled = args.checkBoolean(0);
                return new Object[]{};
            } catch (Throwable e) {
                return new Object[]{false, "invalid"};
            }
        }

        @Callback(doc = "function():boolean -- ")
        public Object[] getRemovePad(Context context, Arguments args) {
            try {
                return new Object[]{tileEntity.launchPadRemovalDisabled};
            } catch (Throwable e) {
                return new Object[]{false, "invalid"};
            }
        }
    }
}