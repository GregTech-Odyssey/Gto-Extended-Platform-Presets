package org.com.gtoepp.common;

import com.gtolib.api.lang.CNEN;
import com.gtolib.api.lang.SimplifiedChineseLanguageProvider;
import com.gtolib.api.lang.TraditionalChineseLanguageProvider;
import com.gtolib.utils.ChineseConverter;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.utils.collection.O2OOpenCacheHashMap;

import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.tterrag.registrate.providers.ProviderType;
import org.com.gtoepp.platforms.PlatformPresets;

import java.util.Map;

import static org.com.gtoepp.common.GTOEPPRegistration.GTOEPP;

public class CommonProxy {

    public CommonProxy() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(CommonProxy::modConstruct);
    }

    private static void modConstruct(FMLConstructModEvent event) {
        if (GTCEu.isDataGen()) {
            GTOEPP.addDataGenerator(ProviderType.LANG, CommonProxy::enInitialize);
            GTOEPP.addDataGenerator(SimplifiedChineseLanguageProvider.LANG, CommonProxy::cnInitialize);
            GTOEPP.addDataGenerator(TraditionalChineseLanguageProvider.LANG, CommonProxy::twInitialize);
        }
    }

    private static final Map<String, CNEN> LANGS = new O2OOpenCacheHashMap<>();

    private static void addCNEN(String key, CNEN CNEN) {
        if (LANGS.containsKey(key)) throw new IllegalArgumentException("Duplicate key: " + key);
        LANGS.put(key, CNEN);
    }

    public static void enInitialize(LanguageProvider provider) {
        PlatformPresets.LANG.forEach((k, v) -> addCNEN("gtoepp.platform." + k, v));
        LANGS.forEach((k, v) -> {
            if (v.en() == null) return;
            provider.add(k, v.en());
        });
    }

    public static void cnInitialize(SimplifiedChineseLanguageProvider provider) {
        LANGS.forEach((k, v) -> {
            if (v.cn() == null) return;
            provider.add(k, v.cn());
        });
    }

    public static void twInitialize(TraditionalChineseLanguageProvider provider) {
        LANGS.forEach((k, v) -> {
            if (v.cn() == null) return;
            provider.add(k, ChineseConverter.convert(v.cn()));
        });
    }
}
