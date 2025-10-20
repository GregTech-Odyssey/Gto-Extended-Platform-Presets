package org.com.gtoepp;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import org.com.gtoepp.common.CommonProxy;

@Mod(GTOEpp.MODID)
public class GTOEpp {

    public static final String MODID = "gtoepp";

    public GTOEpp() {
        DistExecutor.unsafeRunForDist(() -> CommonProxy::new, () -> CommonProxy::new);
    }
}
