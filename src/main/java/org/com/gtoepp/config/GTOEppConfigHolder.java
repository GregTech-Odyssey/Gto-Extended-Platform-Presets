package org.com.gtoepp.config;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.ConfigHolder;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;
import org.com.gtoepp.GTOEpp;
import org.jetbrains.annotations.ApiStatus;

@Config(
        id = GTOEpp.MOD_ID,
        filename = "gto_extended_platform_presets/gtoepp")
public class GTOEppConfigHolder {

    public static GTOEppConfigHolder INSTANCE;
    private static final Object LOCK = new Object();

    @ApiStatus.Internal
    public static ConfigHolder<GTOEppConfigHolder> INTERNAL_INSTANCE;

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null || INTERNAL_INSTANCE == null) {
                INTERNAL_INSTANCE = Configuration.registerConfig(GTOEppConfigHolder.class, ConfigFormats.YAML);
                INSTANCE = INTERNAL_INSTANCE.getConfigInstance();

                // 自定义注册
                CustomRegistration.init();
            }
        }
    }

    @Configurable
    public DeveloperConfigs dev = new DeveloperConfigs();

    public static class DeveloperConfigs {

        @Configurable
        @Configurable.Comment({
                "是否启用配置文件自动注册新模板？ 默认值: false",
                "Do you want to enable automatic registration of new templates with configuration files? Default: false"
        })
        public boolean auto_registration = false;

        @Configurable
        @Configurable.Comment({
                "文件名",
                "filenames"
        })
        public String[] filename = new String[] {};
    }
}
