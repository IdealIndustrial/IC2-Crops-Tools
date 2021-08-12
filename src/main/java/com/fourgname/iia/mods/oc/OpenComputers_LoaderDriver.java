package com.fourgname.iia.mods.oc;

import li.cil.oc.api.Driver;

public class OpenComputers_LoaderDriver {
    public static void register() {
        Driver.add(new Driver_GCLaunchController());
        //Driver.add(new ConverterDataStick());
    }
}