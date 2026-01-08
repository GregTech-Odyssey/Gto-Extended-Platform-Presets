# GTOEpp 平台模板自动注册使用说明书
## 适用人群
所有使用 GTOEpp 模组的玩家，无需编程基础即可通过配置文件添加/注册自定义平台建筑模板。

## 前置说明
1. 确保你安装的 GTOEpp 模组版本支持「配置文件自动注册模板」功能（模组版本 ≥ 1.0.0，具体以模组发布说明为准）；
2. 操作前建议关闭游戏，避免配置文件被游戏进程占用；
3. 所有配置文件编码格式为 `UTF-8`，建议用记事本/Notepad++/VS Code 等编辑器修改（不要用系统自带的写字板，避免格式错乱）；
4. JSON 文件中所有符号（`{}`/`,`/`:`/`"`）必须为**英文半角**，中文全角符号会导致加载失败。

## 核心操作流程（3步完成）
### 第一步：开启自动注册开关（修改YAML配置文件）
#### 1. 找到配置文件
打开你的 Minecraft 目录（以下简称 `.minecraft`），进入配置文件夹：
- Windows 系统：`此电脑 → 文档 → .minecraft → config → gto_extended_platform_presets → gtoepp.yml`
- Mac 系统：`访达 → 前往 → 前往文件夹 → 输入 ~/Library/Application Support/minecraft → config → gto_extended_platform_presets → gtoepp.yml`
- Linux 系统：`~/.minecraft/config/gto_extended_platform_presets/gtoepp.yml`

> 若首次启动模组，该文件会自动生成；若未找到，启动一次游戏再关闭即可。

#### 2. 修改配置内容
用编辑器打开 `gtoepp.yml`，将内容修改为以下格式（删除原有内容，复制粘贴）：
```yaml
dev:
  auto_registration: true
  filename:
    - sy_1_batch_construction_template  # 你要注册的JSON模板文件名（无.json后缀）
    # 如需添加多个模板，换行加“- 新文件名”即可，示例：
    # - "another_template"
    # - "test_template"
```
- 关键修改：`auto_registration` 必须设为 `true`（开启自动注册）；
- `filename` 里填写你要加载的 JSON 模板文件名（**不要加 .json 后缀**）。

### 第二步：放置/编写JSON模板文件
#### 1. 找到模板文件存放位置
在上述 `.minecraft/config/gto_extended_platform_presets/` 文件夹下，新建/放置你的 JSON 模板文件：
- 文件名必须和 YAML 里 `filename` 填写的一致，比如 `sy_1_batch_construction_template.json`；
- 示例路径：`.minecraft/config/gto_extended_platform_presets/sy_1_batch_construction_template.json`。

#### 2. JSON模板文件全字段解析（核心重点）
JSON 模板分为「模板组层级」和「单个结构层级」，以下是所有字段的**详细说明、是否必选、取值规则、示例**（对应模组底层解析逻辑，错写会导致加载失败）：

##### 一、模板组层级字段（控制整个模板组的基础信息）
| 字段名         | 是否必选 | 取值规则                                                                 | 通俗说明                                                                 | 示例值                                                                 |
|----------------|----------|--------------------------------------------------------------------------|--------------------------------------------------------------------------|------------------------------------------------------------------------|
| `name`         | 是       | 字符串，唯一标识（不能和其他模板组重复，只能用英文/数字/下划线）| 模板组的“内部唯一名字”，游戏内不显示，用于区分不同模板组                 | `"sy_1_batch_construction_template"`                                   |
| `displayName`  | 否       | 字符串，支持中英文（可用 `|` 分隔）| 游戏内显示的模板组名称（玩家能看到）                                     | `"SY-1批量建造模板 | SY-1 batch construction template"`                 |
| `description`  | 否       | 字符串，支持中英文（可用 `|` 分隔）| 游戏内显示的模板组描述（可选填，说明模板组用途）                         | `"涵盖大部分使用场景的建筑模板 | Building templates covering most usage scenarios"` |
| `source`       | 否       | 字符串，任意内容                                                         | 模板来源（比如作者名、模板出处）                                         | `"疏影"`                                                               |
| `structures`   | 是       | 数组（用 `[]` 包裹，内部是多个结构对象），且不能为空                     | 该模板组包含的所有建筑模板，至少要填1个结构                             | `[{"name":"rubiks_cube_factory", ...}, {...}]`（见下方结构字段）       |

##### 二、单个结构层级字段（`structures` 数组内的每个对象，控制单个建筑模板）
| 字段名           | 是否必选 | 取值规则                                                                 | 通俗说明                                                                 | 示例值                                                                 |
|------------------|----------|--------------------------------------------------------------------------|--------------------------------------------------------------------------|------------------------------------------------------------------------|
| `name`           | 是       | 字符串，唯一标识（不能和同组内其他结构重复，只能用英文/数字/下划线）| 单个建筑模板的“内部唯一名字”，游戏内不显示                               | `"rubiks_cube_factory"`                                                |
| `type`           | 否       | 字符串，通常填 `factory`（也可填自定义类型）| 建筑模板的类型分类（不影响功能，仅用于后台区分）| `"factory"`                                                            |
| `displayName`    | 否       | 字符串，支持中英文（可用 `|` 分隔）| 游戏内显示的单个建筑名称（玩家能看到）                                   | `"魔方厂房 | Rubik's Cube Factory"`                                    |
| `description`    | 否       | 字符串，支持中英文（可用 `|` 分隔）| 游戏内显示的单个建筑描述（可选填，说明建筑用途）                         | `"拥有地下49层 | It has 49 underground floors"`                        |
| `source`         | 否       | 字符串，任意内容                                                         | 单个建筑模板的来源（可选填）                                             | `"疏影"`                                                               |
| `preview`        | 否       | 布尔值（`true`/`false`），默认值 `false`                                 | 是否开启建筑模板的预览功能（一般填 `false` 即可）| `false`                                                                |
| `resource`       | 是       | 字符串，格式为 `模组ID:路径`（必须和模组内的建筑资源路径匹配）| 建筑模板的核心资源路径（错写会导致建筑加载不出来）| `"gtoepp:sy_1/rubiks_cube_factory"`                                    |
| `symbolMap`      | 是       | 字符串，格式为 `模组ID:路径.json`（必须和模组内的方块映射文件路径匹配）| 建筑模板的方块映射文件（定义建筑用了哪些方块，错写会导致建筑显示异常）| `"gtoepp:sy_1/rubiks_cube_factory.json"`                               |
| `material_0`     | 否       | 整数（≥0），默认值 `0`                                                   | 建造该建筑需要的“基础材料0”的数量（具体对应哪种材料由模组定义）| `800`                                                                  |
| `material_1`     | 否       | 整数（≥0），默认值 `0`                                                   | 建造该建筑需要的“基础材料1”的数量                                       | `800`                                                                  |
| `material_2`     | 否       | 整数（≥0），默认值 `0`                                                   | 建造该建筑需要的“基础材料2”的数量                                       | `0`                                                                    |
| `extra_materials`| 否       | 对象（用 `{}` 包裹），格式为 `"物品ID":数量`（数量≥1）| 建造该建筑需要的额外材料（比如铁锭、钻石等，空则填 `{}`）| `{"minecraft:iron_ingot": 100, "gtoepp:special_alloy": 50}`            |

#### 3. 完整JSON模板示例（可直接复制使用）
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

#### 4. 新手修改指南（无需懂代码）
- 仅修改「显示名称/描述/材料数量」：直接改 `displayName`/`description`/`material_0`/`material_1` 的值即可；
- 添加额外材料：在 `extra_materials` 里按 `"物品ID":数量` 格式添加，比如要加100个铁锭就写 `"minecraft:iron_ingot": 100`；
- 新增建筑模板：复制 `structures` 里的一个结构对象，修改 `name`/`displayName`/`resource`/`symbolMap` 等字段（`resource` 和 `symbolMap` 需和模组内已有的资源匹配）；
- **禁止修改**：`name`（内部标识）、`resource`、`symbolMap`（路径错写会导致建筑加载失败）。

### 第三步：验证模板是否生效
#### 1. 启动游戏，查看日志（新手推荐）
启动游戏后，打开 Minecraft 日志文件（`.minecraft/logs/latest.log`），搜索以下关键词：
- 成功提示（找到即生效）：
  ```
  Successfully registered the 1th template group, containing 2 templates
  Parsed 1 configs, successfully registered 1 template groups.
  ```
- 失败提示（根据提示修复，对应字段错误）：

| 日志错误提示                                  | 原因及解决方法                                                                 |
|-----------------------------------------------|------------------------------------------------------------------------------|
| `Failed to parse 1th template group: Missing Name` | 模板组层级的 `name` 字段缺失/为空，补充该字段即可                             |
| `Missing structures`                          | 模板组层级的 `structures` 字段缺失，补充 `structures: []` 并填入至少1个结构   |
| `Structures are empty`                        | `structures` 数组为空，至少添加1个结构对象                                   |
| `Missing resource`                            | 单个结构的 `resource` 字段缺失，补充该字段（格式：模组ID:路径）|
| `Missing symbolMap`                           | 单个结构的 `symbolMap` 字段缺失，补充该字段（格式：模组ID:路径.json）|
| `Build failed`                                | `resource`/`symbolMap` 路径错误，或建筑尺寸不符合模组要求（恢复默认路径即可）|

#### 2. 游戏内验证（直观）
进入游戏后，打开 GTOEpp 模组的平台模板界面，若能看到你设置的 `displayName`（比如「SY-1批量建造模板」）及下属的建筑名称（比如「魔方厂房」），说明注册成功。

## 常见问题排查
| 问题现象                  | 原因及解决方法                                                                 |
|---------------------------|------------------------------------------------------------------------------|
| 游戏日志提示“文件找不到”| 1. JSON 文件名和 YAML 里的 `filename` 不一致；2. JSON 文件放错文件夹，需放到 `gto_extended_platform_presets` 下； |
| 模板加载后无建筑          | `structures` 数组为空，或 `resource`/`symbolMap` 路径错误（恢复默认路径即可）；|
| 材料数量不生效            | `material_0`/`material_1`/`material_2` 字段拼写错误（比如写成 `material-0`），需改为下划线格式； |
| 游戏启动崩溃              | JSON 文件格式错误（比如少写逗号、括号不匹配、用了中文符号），用在线 JSON 校验工具（https://json.cn/）检查格式； |
| 部分建筑加载失败          | 失败建筑的 `name` 重复，或 `resource`/`symbolMap` 路径错误（查看日志里的“第N个结构”提示）； |

## 注意事项
1. 每次修改配置文件后，需重启游戏才能生效；
2. JSON 文件中不要随意删除必选字段（比如 `name`/`structures`/`resource`），否则会导致整个模板组加载失败；
3. 若想删除某个模板，只需在 YAML 的 `filename` 里删除对应行，或删除对应的 JSON 文件即可；
4. `extra_materials` 里的物品ID必须是模组内已存在的物品（比如 `minecraft:iron_ingot` 是原版铁锭，`gtoepp:xxx` 是模组自定义物品）；
5. 若 JSON 文件内容为空或格式错误，模组会自动跳过该文件，不会影响其他模板的加载。