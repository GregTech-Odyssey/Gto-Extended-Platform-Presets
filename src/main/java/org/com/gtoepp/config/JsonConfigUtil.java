package org.com.gtoepp.config;

import net.minecraftforge.fml.loading.FMLPaths;

import com.google.gson.*;
import org.com.gtoepp.GTOEpp;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonConfigUtil {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public static Path getConfigPath(String fileName) {
        Path configDir = FMLPaths.CONFIGDIR.get().resolve("gto_extended_platform_presets");
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            GTOEpp.LOGGER.error("创建配置文件夹失败: {}", configDir, e);
        }
        return configDir.resolve(fileName);
    }

    private static JsonElement loadJsonElement(String fileName, JsonElement defaultElement) {
        Path configPath = getConfigPath(fileName);
        File configFile = configPath.toFile();

        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(defaultElement, writer);
                GTOEpp.LOGGER.info("配置文件不存在，已创建默认文件: {}", configPath);
                return defaultElement;
            } catch (IOException e) {
                GTOEpp.LOGGER.error("创建默认配置文件失败: {}", configPath, e);
                return defaultElement;
            }
        }

        try (FileReader reader = new FileReader(configFile)) {
            return GSON.fromJson(reader, JsonElement.class);
        } catch (IOException e) {
            GTOEpp.LOGGER.error("读取配置文件IO失败: {}", configPath, e);
            return defaultElement;
        } catch (JsonParseException e) {
            GTOEpp.LOGGER.error("配置文件JSON格式错误，无法解析: {}", configPath, e);
            return defaultElement;
        }
    }

    /**
     * 读取指定配置文件并解析为JsonObject
     */
    public static JsonObject loadSingleJsonObject(String fileName) {
        JsonElement defaultElement = new JsonObject();
        JsonElement element = loadJsonElement(fileName, defaultElement);

        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        } else {
            GTOEpp.LOGGER.warn("配置文件 {} 不是合法的JsonObject，返回空对象", fileName);
            return new JsonObject();
        }
    }
}
