package org.com.gtoepp;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.gtoepp.common.CommonProxy;
import org.com.gtoepp.config.GTOEppConfigHolder;

@Mod(GTOEpp.MOD_ID)
public class GTOEpp {

    public static final String MOD_ID = "gtoepp";
    public static final String NAME = "Gto Extended Platform Presets";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public GTOEpp() {
        GTOEppConfigHolder.init();
        DistExecutor.unsafeRunForDist(() -> CommonProxy::new, () -> CommonProxy::new);
    }
}
