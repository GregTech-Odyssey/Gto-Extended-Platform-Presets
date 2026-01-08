package org.com.gtoepp.config;

import com.gtocore.common.machine.noenergy.PlatformDeployment.PlatformBlockType;

import com.gtolib.utils.RLUtils;

import net.minecraft.util.GsonHelper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.com.gtoepp.GTOEpp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.com.gtoepp.config.JsonConfigUtil.loadSingleJsonObject;
import static org.com.gtoepp.platforms.PlatformPresets.extendedPresets;

public class CustomRegistration {

    private static List<JsonObject> CustomRegistrationObject;

    public static void init() {
        if (!GTOEppConfigHolder.INSTANCE.dev.auto_registration) return;
        String[] filenames = GTOEppConfigHolder.INSTANCE.dev.filename;
        CustomRegistrationObject = new ArrayList<>();
        for (String filename : filenames) {
            JsonObject jsonObject = loadSingleJsonObject(filename + ".json");
            if (!Objects.equals(jsonObject, new JsonObject())) CustomRegistrationObject.add(jsonObject);
        }
        GTOEpp.LOGGER.warn("Initialization of custom registration completed");
    }

    public static void register() {
        if (CustomRegistrationObject == null) {
            GTOEpp.LOGGER.warn("Custom registration configuration not initialized");
            return;
        }
        int configGroupsCount = 0;
        int successGroupsCount = 0;

        for (JsonObject groupJson : CustomRegistrationObject) {
            configGroupsCount++;

            try {
                String groupName = GsonHelper.getAsString(groupJson, "name", null);
                if (groupName == null) {
                    GTOEpp.LOGGER.error("Failed to parse {}th template group register config: Missing Name", configGroupsCount);
                    continue;
                }
                String groupDisplayName = GsonHelper.getAsString(groupJson, "displayName", null);
                String groupDescription = GsonHelper.getAsString(groupJson, "description", null);
                String groupSource = GsonHelper.getAsString(groupJson, "source", null);

                JsonArray structuresArray = GsonHelper.getAsJsonArray(groupJson, "structures", null);
                if (structuresArray == null) {
                    GTOEpp.LOGGER.error("Failed to parse {}th template group register config: Missing structures", configGroupsCount);
                    continue;
                }

                List<JsonObject> structures = new ArrayList<>();
                for (JsonElement element : structuresArray) {
                    if (element.isJsonObject()) structures.add(element.getAsJsonObject());
                    else GTOEpp.LOGGER.warn("There are non JsonObject elements in the structures, skip them");
                }
                if (structures.isEmpty()) {
                    GTOEpp.LOGGER.error("Failed to parse {}th template group register config: Structures are empty", configGroupsCount);
                    continue;
                }

                PlatformBlockType.PlatformPreset.PresetBuilder presetBuilder = PlatformBlockType.PlatformPreset.preset(groupName);
                if (groupDisplayName != null) presetBuilder.displayName(groupDisplayName);
                if (groupDescription != null) presetBuilder.description(groupDescription);
                if (groupSource != null) presetBuilder.source(groupSource);

                int configCount = 0;
                int successCount = 0;
                for (JsonObject structureJson : structures) {
                    configCount++;
                    try {
                        String name = GsonHelper.getAsString(structureJson, "name", null);
                        if (name == null) {
                            GTOEpp.LOGGER.error("Failed to parse {}th structure in {}th template group register config: Missing Name", configCount, configGroupsCount);
                            continue;
                        }
                        String type = GsonHelper.getAsString(structureJson, "type", null);
                        String displayName = GsonHelper.getAsString(structureJson, "displayName", null);
                        String description = GsonHelper.getAsString(structureJson, "description", null);
                        String source = GsonHelper.getAsString(structureJson, "source", null);

                        boolean preview = GsonHelper.getAsBoolean(structureJson, "preview", false);

                        String resource = GsonHelper.getAsString(structureJson, "resource", null);
                        if (resource == null) {
                            GTOEpp.LOGGER.error("Failed to parse {}th structure in {}th template group register config: Missing resource", configCount, configGroupsCount);
                            continue;
                        }
                        String symbolMap = GsonHelper.getAsString(structureJson, "symbolMap", null);
                        if (symbolMap == null) {
                            GTOEpp.LOGGER.error("Failed to parse {}th structure in {}th template group register config: Missing symbolMap", configCount, configGroupsCount);
                            continue;
                        }

                        int material_0 = GsonHelper.getAsInt(structureJson, "material_0", 0);
                        int material_1 = GsonHelper.getAsInt(structureJson, "material_1", 0);
                        int material_2 = GsonHelper.getAsInt(structureJson, "material_2", 0);

                        JsonObject extraMaterialsJson = GsonHelper.getAsJsonObject(structureJson, "extra_materials", null);
                        Object2IntOpenHashMap<String> extraMaterials = new Object2IntOpenHashMap<>();
                        if (extraMaterialsJson != null) {
                            for (String key : extraMaterialsJson.keySet()) {
                                int count = GsonHelper.getAsInt(extraMaterialsJson, key, 0);
                                if (count > 0) extraMaterials.put(key, count);
                            }
                        }

                        PlatformBlockType.PlatformBlockStructure.Builder structureBuilder = PlatformBlockType.PlatformBlockStructure.structure(name);
                        if (type != null) structureBuilder.type(type);
                        if (displayName != null) structureBuilder.displayName(displayName);
                        if (description != null) structureBuilder.description(description);
                        if (source != null) structureBuilder.source(source);

                        if (preview) structureBuilder.preview(true);

                        structureBuilder.resource(RLUtils.parse(resource));
                        structureBuilder.symbolMap(RLUtils.parse(symbolMap));

                        if (material_0 != 0) structureBuilder.materials(0, material_0);
                        if (material_1 != 0) structureBuilder.materials(1, material_1);
                        if (material_2 != 0) structureBuilder.materials(2, material_2);

                        if (!extraMaterials.isEmpty()) {
                            extraMaterials.forEach(structureBuilder::extraMaterials);
                        }

                        PlatformBlockType.PlatformBlockStructure structure = structureBuilder.build();
                        if (structure == null) {
                            GTOEpp.LOGGER.error("Failed to parse {}th structure in {}th template group register config: Build failed", configCount, configGroupsCount);
                            continue;
                        }
                        presetBuilder.addStructure(structure);

                        successCount++;
                    } catch (Exception e) {
                        GTOEpp.LOGGER.error("Failed to parse {}th structure in {}th template group register config: {}", configCount, configGroupsCount, e.getMessage(), e);
                    }
                }

                PlatformBlockType.PlatformPreset preset = presetBuilder.build();
                if (preset == null) {
                    GTOEpp.LOGGER.error("Failed to parse {}th template group register config: Build failed", configGroupsCount);
                    continue;
                }
                extendedPresets.add(preset);

                GTOEpp.LOGGER.info("Successfully registered the {}th template group, containing {} templates", configGroupsCount, successCount);
                successGroupsCount++;
            } catch (Exception e) {
                GTOEpp.LOGGER.error("Failed to parse {}th template group register config: {}", configGroupsCount, e.getMessage(), e);
            }
        }

        // 解析完成日志：统计成功/总数量
        GTOEpp.LOGGER.info("Parsed {} configs, successfully registered {} template groups.", configGroupsCount, successGroupsCount);
        CustomRegistrationObject.clear();
    }
}
