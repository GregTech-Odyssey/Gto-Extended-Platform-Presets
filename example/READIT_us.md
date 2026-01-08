# GTOEpp Platform Template Auto-Registration User Manual
## Target Audience
All players using the GTOEpp mod. No programming knowledge required to add/register custom platform building templates via configuration files.

## Prerequisites
1. Ensure your installed GTOEpp mod version supports the **configuration file auto-registration** feature (mod version ≥ 1.0.0, refer to the mod release notes for details);
2. It is recommended to close the game before making changes to avoid configuration file locking;
3. All configuration files must be encoded in **UTF-8**. Use editors like Notepad, Notepad++ or VS Code (avoid using the system's default WordPad to prevent formatting errors);
4. All symbols in JSON files (`{}`/`,`/`:`/`"`) **must be English half-width characters**. Full-width Chinese symbols will cause loading failures.

## Core Operation Process (3 Steps)
### Step 1: Enable Auto-Registration (Modify YAML Configuration File)
#### 1. Locate the Configuration File
Open your Minecraft directory (hereinafter referred to as `.minecraft`) and navigate to the config folder:
- **Windows**: `This PC → Documents → .minecraft → config → gto_extended_platform_presets → gtoepp.yml`
- **Mac**: `Finder → Go → Go to Folder → Enter ~/Library/Application Support/minecraft → config → gto_extended_platform_presets → gtoepp.yml`
- **Linux**: `~/.minecraft/config/gto_extended_platform_presets/gtoepp.yml`

> The file will be generated automatically when you launch the mod for the first time. If it cannot be found, launch the game once and then close it.

#### 2. Modify Configuration Content
Open `gtoepp.yml` with an editor and replace the content with the following format (delete the original content and paste):
```yaml
dev:
  auto_registration: true
  filename:
    - sy_1_batch_construction_template  # Name of your JSON template file (without .json suffix)
    # To add multiple templates, add a new line starting with "- filename", e.g.:
    # - "another_template"
    # - "test_template"
```
- **Critical Modification**: Set `auto_registration` to `true` to enable auto-registration;
- **Filename Rules**: Enter the name of your JSON template file in `filename` (**do not add the .json suffix**).

### Step 2: Place/Write the JSON Template File
#### 1. Locate the Template File Directory
Create/place your JSON template file in the following folder:
```
.minecraft/config/gto_extended_platform_presets/
```
- The filename **must match** the name specified in the YAML `filename` field (e.g., `sy_1_batch_construction_template.json`);
- Example Path: `.minecraft/config/gto_extended_platform_presets/sy_1_batch_construction_template.json`

#### 2. Full Field Explanation of JSON Template (Core Focus)
The JSON template is divided into **Template Group Level** and **Individual Structure Level**. Below are the **detailed explanations, mandatory/optional status, value rules and examples** for all fields (corresponding to the mod's underlying parsing logic; incorrect entries will cause loading failures):

##### I. Template Group Level Fields (Control Basic Information of the Entire Template Group)
| Field Name    | Mandatory | Value Rules                                                                                              | Plain English Explanation                                                                    | Example Value                                                                               |
|---------------|-----------|----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|
| `name`        | Yes       | String, unique identifier (cannot duplicate other template groups; only use letters/numbers/underscores) | Internal unique name of the template group (not displayed in-game, used for differentiation) | `"sy_1_batch_construction_template"`                                                        |
| `displayName` | No        | String, supports Chinese and English (separated by `                                                     | `)                                                                                           | In-game display name of the template group (visible to players)                             | `"SY-1批量建造模板 | SY-1 batch construction template"`                    |
| `description` | No        | String, supports Chinese and English (separated by `                                                     | `)                                                                                           | In-game description of the template group (optional, explains the template group's purpose) | `"涵盖大部分使用场景的建筑模板 | Building templates covering most usage scenarios"` |
| `source`      | No        | String, any content                                                                                      | Source of the template (e.g., author name, template origin)                                  | `"疏影"`                                                                                      |
| `structures`  | Yes       | Array (wrapped in `[]`, containing multiple structure objects), **cannot be empty**                      | All building templates included in this group; at least one structure must be added          | `[{"name":"rubiks_cube_factory", ...}, {...}]` (see structure fields below)                 |

##### II. Individual Structure Level Fields (Each object in the `structures` array, controlling a single building template)
| Field Name        | Mandatory | Value Rules                                                                                                           | Plain English Explanation                                                                                                       | Example Value                                                                              |
|-------------------|-----------|-----------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| `name`            | Yes       | String, unique identifier (cannot duplicate other structures in the same group; only use letters/numbers/underscores) | Internal unique name of the individual building template (not displayed in-game)                                                | `"rubiks_cube_factory"`                                                                    |
| `type`            | No        | String, usually `factory` (custom types are also allowed)                                                             | Type classification of the building template (does not affect functionality, only for backend differentiation)                  | `"factory"`                                                                                |
| `displayName`     | No        | String, supports Chinese and English (separated by `                                                                  | `)                                                                                                                              | In-game display name of the individual building (visible to players)                       | `"魔方厂房 | Rubik's Cube Factory"`                                       |
| `description`     | No        | String, supports Chinese and English (separated by `                                                                  | `)                                                                                                                              | In-game description of the individual building (optional, explains the building's purpose) | `"拥有地下49层 | It has 49 underground floors"`                           |
| `source`          | No        | String, any content                                                                                                   | Source of the individual building template (optional)                                                                           | `"疏影"`                                                                                     |
| `preview`         | No        | Boolean (`true`/`false`), default value is `false`                                                                    | Enable preview function for the building template (usually set to `false`)                                                      | `false`                                                                                    |
| `resource`        | Yes       | String, format: `modID:path` (must match the building resource path in the mod)                                       | Core resource path of the building template (incorrect paths will cause loading failures)                                       | `"gtoepp:sy_1/rubiks_cube_factory"`                                                        |
| `symbolMap`       | Yes       | String, format: `modID:path.json` (must match the block mapping file path in the mod)                                 | Block mapping file of the building template (defines which blocks the building uses; incorrect paths will cause display errors) | `"gtoepp:sy_1/rubiks_cube_factory.json"`                                                   |
| `material_0`      | No        | Integer (≥0), default value is `0`                                                                                    | Quantity of "Basic Material 0" required to build the structure (specific material type defined by the mod)                      | `800`                                                                                      |
| `material_1`      | No        | Integer (≥0), default value is `0`                                                                                    | Quantity of "Basic Material 1" required to build the structure                                                                  | `800`                                                                                      |
| `material_2`      | No        | Integer (≥0), default value is `0`                                                                                    | Quantity of "Basic Material 2" required to build the structure                                                                  | `0`                                                                                        |
| `extra_materials` | No        | Object (wrapped in `{}`), format: `"itemID":quantity` (quantity ≥1)                                                   | Extra materials required to build the structure (e.g., iron ingots, diamonds; use `{}` for empty)                               | `{"minecraft:iron_ingot": 100, "gtoepp:special_alloy": 50}`                                |

#### 3. Complete JSON Template Example (Ready to Copy and Use)
```json
{
  "name": "sy_1_batch_construction_template",
  "displayName": "SY-1批量建造模板 | SY-1 batch construction template",
  "description": "涵盖大部分使用场景的建筑模板 | Building templates covering most usage scenarios",
  "source": "疏影",
  "structures": [
    {
      "name": "rubiks_cube_factory",
      "type": "factory",
      "displayName": "魔方厂房 | Rubik's Cube Factory",
      "preview": false,
      "resource": "gtoepp:sy_1/rubiks_cube_factory",
      "symbolMap": "gtoepp:sy_1/rubiks_cube_factory.json",
      "material_0": 800,
      "material_1": 800,
      "material_2": 0,
      "extra_materials": {}
    },
    {
      "name": "trans_space_assembly_plant",
      "type": "factory",
      "displayName": "超时空装配厂 | Trans-Space Assembly Plant",
      "description": "拥有地下49层 | It has 49 underground floors",
      "preview": false,
      "resource": "gtoepp:sy_1/trans_space_assembly_plant",
      "symbolMap": "gtoepp:sy_1/trans_space_assembly_plant.json",
      "material_0": 800,
      "material_1": 800,
      "extra_materials": {
        "minecraft:diamond": 50,
        "gtoepp:high_grade_steel": 200
      }
    }
  ]
}
```

#### 4. Beginner Modification Guide (No Programming Knowledge Required)
- **Modify Only Display Names/Descriptions/Material Quantities**: Directly edit the values of `displayName`, `description`, `material_0` and `material_1`;
- **Add Extra Materials**: Add entries in `extra_materials` using the `"itemID":quantity` format (e.g., add 100 iron ingots with `"minecraft:iron_ingot": 100`);
- **Add New Building Templates**: Copy an existing structure object in the `structures` array and modify fields such as `name`, `displayName`, `resource` and `symbolMap` (ensure `resource` and `symbolMap` match existing paths in the mod);
- **Prohibited Modifications**: Do not modify `name` (internal identifier), `resource` or `symbolMap` (incorrect paths will cause building loading failures).

### Step 3: Verify Template Activation
#### 1. Launch the Game and Check Logs (Recommended for Beginners)
After launching the game, open the Minecraft log file (`.minecraft/logs/latest.log`) and search for the following keywords:
- **Success Messages** (template activated if found):
  ```
  Successfully registered the 1th template group, containing 2 templates
  Parsed 1 configs, successfully registered 1 template groups.
  ```
- **Error Messages** (fix according to prompts, corresponding to field errors):

| Log Error Message                                  | Cause and Solution                                                                                                       |
|----------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| `Failed to parse 1th template group: Missing Name` | `name` field at the template group level is missing or empty; add the field                                              |
| `Missing structures`                               | `structures` field at the template group level is missing; add `structures: []` and populate with at least one structure |
| `Structures are empty`                             | `structures` array is empty; add at least one structure object                                                           |
| `Missing resource`                                 | `resource` field of an individual structure is missing; add the field using the `modID:path` format                      |
| `Missing symbolMap`                                | `symbolMap` field of an individual structure is missing; add the field using the `modID:path.json` format                |
| `Build failed`                                     | `resource`/`symbolMap` path is incorrect, or building dimensions do not meet mod requirements (restore default paths)    |

#### 2. In-Game Verification (Intuitive)
After entering the game, open the GTOEpp mod's platform template interface. If you can see the `displayName` you set (e.g., **SY-1批量建造模板**) and its subordinate building names (e.g., **魔方厂房**), the registration was successful.

## Common Troubleshooting
| Issue Symptom                          | Cause and Solution                                                                                                                                                        |
|----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Game log shows "File not found"        | 1. JSON filename does not match the `filename` in the YAML file; 2. JSON file is placed in the wrong folder (must be in `gto_extended_platform_presets`)                  |
| Templates load but no buildings appear | `structures` array is empty, or `resource`/`symbolMap` path is incorrect (restore default paths)                                                                          |
| Material quantities do not take effect | `material_0`/`material_1`/`material_2` fields are misspelled (e.g., using `material-0` instead of `material_0`; use underscores)                                          |
| Game crashes on launch                 | JSON file has formatting errors (e.g., missing commas, mismatched brackets, using Chinese symbols); validate the format using an online JSON validator (https://json.cn/) |
| Some buildings fail to load            | `name` of the failed building is duplicated, or `resource`/`symbolMap` path is incorrect (check the "Nth structure" prompt in the log)                                    |

## Notes
1. Restart the game after modifying any configuration files for changes to take effect;
2. Do not arbitrarily delete mandatory fields in the JSON file (e.g., `name`, `structures`, `resource`), as this will cause the entire template group to fail to load;
3. To delete a template, simply remove the corresponding line in the YAML `filename` field or delete the relevant JSON file;
4. Item IDs in `extra_materials` must correspond to existing items in the mod (e.g., `minecraft:iron_ingot` is a vanilla iron ingot, `gtoepp:xxx` is a mod-custom item);
5. If a JSON file is empty or has formatting errors, the mod will automatically skip it without affecting the loading of other templates.