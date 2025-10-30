package org.com.gtoepp.platforms;

import com.gtocore.common.machine.noenergy.PlatformDeployment.PlatformBlockType;

import com.gtolib.api.lang.CNEN;
import com.gtolib.utils.RLUtils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.utils.collection.O2OOpenCacheHashMap;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gtocore.common.machine.noenergy.PlatformDeployment.PlatformBlockType.PlatformBlockStructure.structure;
import static com.gtocore.common.machine.noenergy.PlatformDeployment.PlatformBlockType.PlatformPreset.preset;

public class PlatformPresets {

    public static final Map<String, CNEN> LANG = GTCEu.isDataGen() ? new O2OOpenCacheHashMap<>() : null;

    public static final List<PlatformBlockType.PlatformPreset> extendedPresets = new ArrayList<>();

    private static final String platform = add("平台", "platform");
    private static final String platform_3_3 = add("平台(3*3)", "platform(3*3)");
    private static final String platform_large = add("平台(大)", "platform(large)");
    private static final String road = add("道路", "road");
    private static final String factory = add("工厂", "factory");

    private static String add(String key, String cn, String en) {
        if (LANG != null) LANG.put(key, new CNEN(cn, en));
        return "gtoepp.platform." + key;
    }

    private static String add(String cn, String en) {
        String key = en.replace(' ', '_').toLowerCase();
        return add(key, cn, en);
    }

    private static String in(String path) {
        return "platforms/" + path;
    }

    private static ResourceLocation GTOEpp(String name) {
        return RLUtils.fromNamespaceAndPath("gtoepp", name);
    }

    static {

        // SY-1批量建造模板
        {
            extendedPresets.add(
                    preset("sy_1_batch_construction_template")
                            .displayName(add("SY-1批量建造模板", "SY-1 batch construction template"))
                            .description(add("涵盖大部分使用场景的建筑模板", "Building templates covering most usage scenarios"))
                            .addStructure(structure("rubiks_cube_factory")
                                    .type(factory)
                                    .displayName(add("魔方厂房", "Rubik's Cube Factory"))
                                    .resource(GTOEpp(in("sy_1/rubiks_cube_factory")))
                                    .symbolMap(GTOEpp(in("sy_1/rubiks_cube_factory.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("starry_sky_theme_suite_earth_style")
                                    .type(factory)
                                    .displayName(add("星空主题套房 - 地球款", "Starry Sky Theme Suite - Earth Style"))
                                    .resource(GTOEpp(in("sy_1/starry_sky_theme_suite_earth_style")))
                                    .symbolMap(GTOEpp(in("sy_1/starry_sky_theme_suite_earth_style.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("starry_sky_theme_suite_solar_system_style")
                                    .type(factory)
                                    .displayName(add("星空主题套房 - 太阳系款", "Starry Sky Theme Suite - Solar System Style"))
                                    .resource(GTOEpp(in("sy_1/starry_sky_theme_suite_solar_system_style")))
                                    .symbolMap(GTOEpp(in("sy_1/starry_sky_theme_suite_solar_system_style.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("starry_sky_theme_suite_barnard_style")
                                    .type(factory)
                                    .displayName(add("星空主题套房 - 巴纳德款", "Starry Sky Theme Suite - Barnard Style"))
                                    .resource(GTOEpp(in("sy_1/starry_sky_theme_suite_barnard_style")))
                                    .symbolMap(GTOEpp(in("sy_1/starry_sky_theme_suite_barnard_style.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("starry_sky_theme_suite_proxima_style")
                                    .type(factory)
                                    .displayName(add("星空主题套房  - 比邻星款", "Starry Sky Theme Suite - Proxima Style"))
                                    .resource(GTOEpp(in("sy_1/starry_sky_theme_suite_proxima_style")))
                                    .symbolMap(GTOEpp(in("sy_1/starry_sky_theme_suite_proxima_style.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("starry_sky_theme_suite_ross_128b_style")
                                    .type(factory)
                                    .displayName(add("星空主题套房 - 罗斯128b款", "Starry Sky Theme Suite - Ross 128b Style"))
                                    .resource(GTOEpp(in("sy_1/starry_sky_theme_suite_ross_128b_style")))
                                    .symbolMap(GTOEpp(in("sy_1/starry_sky_theme_suite_ross_128b_style.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("extra_large_factory")
                                    .type(factory)
                                    .displayName(add("特大厂房", "Extra large factory"))
                                    .resource(GTOEpp(in("sy_1/extra_large_factory")))
                                    .symbolMap(GTOEpp(in("sy_1/extra_large_factory.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("assembly_plant")
                                    .type(factory)
                                    .displayName(add("装配工厂", "Assembly Plant"))
                                    .resource(GTOEpp(in("sy_1/assembly_plant")))
                                    .symbolMap(GTOEpp(in("sy_1/assembly_plant.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("trans_space_assembly_plant")
                                    .type(factory)
                                    .displayName(add("超时空装配厂", "Trans-Space Assembly Plant"))
                                    .description(add("拥有地下49层", "It has 49 underground floors"))
                                    .resource(GTOEpp(in("sy_1/trans_space_assembly_plant")))
                                    .symbolMap(GTOEpp(in("sy_1/trans_space_assembly_plant.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("cell_culture_center")
                                    .type(factory)
                                    .displayName(add("细胞培育中心", "Cell Culture Center"))
                                    .resource(GTOEpp(in("sy_1/cell_culture_center")))
                                    .symbolMap(GTOEpp(in("sy_1/cell_culture_center.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("bacteria_factory")
                                    .type(factory)
                                    .displayName(add("细菌工厂", "Bacteria Factory"))
                                    .resource(GTOEpp(in("sy_1/bacteria_factory")))
                                    .symbolMap(GTOEpp(in("sy_1/bacteria_factory.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("fluid_refinery")
                                    .type(factory)
                                    .displayName(add("流体精炼厂", "Fluid Refinery"))
                                    .resource(GTOEpp(in("sy_1/fluid_refinery")))
                                    .symbolMap(GTOEpp(in("sy_1/fluid_refinery.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("silica_rock_power_plant")
                                    .type(factory)
                                    .displayName(add("硅岩发电厂", "Silica rock power plant"))
                                    .resource(GTOEpp(in("sy_1/silica_rock_power_plant")))
                                    .symbolMap(GTOEpp(in("sy_1/silica_rock_power_plant.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("nuclear_power_plant")
                                    .type(factory)
                                    .displayName(add("核电站", "Nuclear power plant"))
                                    .resource(GTOEpp(in("sy_1/nuclear_power_plant")))
                                    .symbolMap(GTOEpp(in("sy_1/nuclear_power_plant.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("mineral_processing_center")
                                    .type(factory)
                                    .displayName(add("矿物处理中心", "Mineral Processing Center"))
                                    .resource(GTOEpp(in("sy_1/mineral_processing_center")))
                                    .symbolMap(GTOEpp(in("sy_1/mineral_processing_center.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("deep_compositing_center")
                                    .type(factory)
                                    .displayName(add("深度合成中心", "Deep Compositing Center"))
                                    .resource(GTOEpp(in("sy_1/deep_compositing_center")))
                                    .symbolMap(GTOEpp(in("sy_1/deep_compositing_center.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("institute_of_microphysics")
                                    .type(factory)
                                    .displayName(add("微观物理研究所", "Institute of Microphysics"))
                                    .resource(GTOEpp(in("sy_1/institute_of_microphysics")))
                                    .symbolMap(GTOEpp(in("sy_1/institute_of_microphysics.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("supercomputing_center_tai_chi_computer_room")
                                    .type(factory)
                                    .displayName(add("超算中心 - 太极机房", "Supercomputing Center - Tai Chi Computer Room"))
                                    .resource(GTOEpp(in("sy_1/supercomputing_center_tai_chi_computer_room")))
                                    .symbolMap(GTOEpp(in("sy_1/supercomputing_center_tai_chi_computer_room.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("supercomputing_center_simple_computer_room")
                                    .type(factory)
                                    .displayName(add("超算中心 - 简易机房", "Supercomputing Center - Simple Computer Room"))
                                    .resource(GTOEpp(in("sy_1/supercomputing_center_simple_computer_room")))
                                    .symbolMap(GTOEpp(in("sy_1/supercomputing_center_simple_computer_room.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .addStructure(structure("space_elevator")
                                    .type(factory)
                                    .displayName(add("太空电梯", "Space Elevator"))
                                    .resource(GTOEpp(in("sy_1/space_elevator")))
                                    .symbolMap(GTOEpp(in("sy_1/space_elevator.json")))
                                    .materials(0, 800)
                                    .materials(1, 800)
                                    .build())
                            .source("疏影")
                            .build());
        }
    }
}
