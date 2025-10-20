package org.com.gtoepp.common;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import org.com.gtoepp.GTOEpp;

public final class GTOEPPRegistration extends GTRegistrate {

    public static final GTOEPPRegistration GTOEPP = new GTOEPPRegistration(false);
    public static final GTOEPPRegistration GTM = new GTOEPPRegistration(true);

    static {
        GTOEPP.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
        GTM.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
        GTOEPP.registerRegistrate();
    }

    public final boolean gtm;

    private GTOEPPRegistration(boolean gtm) {
        super(gtm ? GTCEu.MOD_ID : GTOEpp.MODID);
        this.gtm = gtm;
    }
}
