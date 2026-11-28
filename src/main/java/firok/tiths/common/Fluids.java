package firok.tiths.common;

import firok.tiths.block.fluid.BlockDirtyWater;
import firok.tiths.block.fluid.BlockMoltenEnderTurbulence;
import firok.tiths.block.fluid.BlockMoltenHothium;
import firok.tiths.block.fluid.BlockShiningGel;
import firok.tiths.util.reg.FluidBlock;
import firok.tiths.util.reg.RegSmelteryFuel;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.fluid.FluidColored;
import slimeknights.tconstruct.library.fluid.FluidMolten;

import static firok.tiths.common.Keys.*;

/**
 * 本模组的流体定义，适用于 Minecraft 1.12.2、Forge 14.23.5.2860 和匠魂 2.13.0.183。
 *
 * <p>{@link RegistryHandler#registerFluids()} 注册流体、加入通用桶支持并构造绑定的流体方块。
 * Forge 的 {@code BlockFluidBase} 在构造时读取流体的温度、亮度、密度和黏度。
 * 修改 {@code Fluid} 的值不会自动同步到已构造的方块。机器和容器也可以独立读取流体属性。</p>
 *
 * <p><strong>Forge 参数的含义与取值</strong></p>
 * <ul>
 *     <li><strong>Temperature（温度）</strong>：{@code int}，默认 {@code 300}，以室温水约 {@code 300 K} 为参照。
 *         Setter 接受任意 {@code int}，按开尔文语义配值时从 {@code 0} 起。默认流体方块没有按温度结冰、沸腾或灼伤的机制。
 *         Forge 的默认下界蒸发判断使用绑定方块的 {@code Material.WATER} 材质。
 *         匠魂未显式指定温度的 {@code MeltingRecipe} 会用产出流体的温度和产出量计算配方温度。
 *         匠魂消耗已登记的流体燃料时，用流体温度减 {@code 300} 作为内部热度。
 *         <a href="https://github.com/SlimeKnights/TinkersConstruct/blob/1.12/src/main/java/slimeknights/tconstruct/library/smeltery/MeltingRecipe.java">熔炼配方</a>、
 *         <a href="https://github.com/SlimeKnights/TinkersConstruct/blob/1.12/src/main/java/slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructureFuelTank.java">燃料逻辑</a>。</li>
 *     <li><strong>Luminosity（发光等级）</strong>：{@code int}，默认 {@code 0}。Setter 直接赋值，方块光照等级应使用 {@code 0–15}。
 *         {@code BlockFluidBase} 将该值保存为 {@code maxScaledLight}。值非零时，实际发光等级为流体量比例乘以该值后取整。
 *         例如源块发光 {@code 10} 级，流体量为 {@code 4/8} 的格子发光 {@code 5} 级。
 *         值为 {@code 0} 时回退到父类方块光照，可由 {@code Block#setLightLevel(float)} 设置，输入比例为 {@code 0.0–1.0}。
 *         光照透明度、贴图发光效果和储罐显示由各自的实现控制。</li>
 *     <li><strong>Density（密度）</strong>：{@code int}，默认 {@code 1000}，以水约 {@code 1000 kg/m³} 为参照。
 *         {@code BlockFluidBase} 构造时以符号确定优先流向，正值向下，负值或零向上。
 *         方块自己的 {@code setDensity(0)} 会将零改为 {@code 1}，使其向下流，配值宜明确选用正值或负值。
 *         {@code Integer.MAX_VALUE} 是密度查询未识别到流体时使用的哨兵值。
 *         {@code BlockFluidClassic} 在通过方块替换表和材质检查后，允许密度更大的流体置换密度更小的流体。
 *         {@code BlockFluidFinite} 也用密度比较决定垂直相邻的流体是否交换位置。
 *         {@code Fluid#isLighterThanAir()} 以 {@code density <= 0} 判断，有 {@code BlockFluidBase} 时优先读方块当前密度。
 *         Forge 流体模型和启用 {@code flipGas} 的桶模型使用该方法决定渲染朝向。</li>
 *     <li><strong>Viscosity（黏度）</strong>：{@code int}，默认 {@code 1000}，以水为参照。
 *         Forge 约定使用非负值，Setter 直接赋值。{@code BlockFluidBase} 构造时按整数除法
 *         {@code tickRate = viscosity / 200} 计算流动更新的调度间隔。要得到至少 {@code 1} tick 的间隔，黏度应从 {@code 200} 起。
 *         {@code 1000}、{@code 1500}、{@code 3000}、{@code 6000}、{@code 10000} 分别得到 {@code 5}、{@code 7}、{@code 15}、
 *         {@code 30}、{@code 50} ticks。{@code 50} ticks 在 {@code 20 TPS} 下约为 {@code 2.5} 秒。
 *         水平流动距离由流体等级和扩散算法决定，管道输送速度由管道实现决定。
 *         原版水和熔岩使用 {@code BlockLiquid} 自己的逻辑，水为 {@code 5} ticks，熔岩通常为 {@code 30} ticks，在下界为 {@code 10} ticks。</li>
 *     <li><strong>Gaseous（气态标志）</strong>：{@code boolean}，默认 {@code false}。
 *         储罐、界面或机器可以通过 {@code isGaseous()} 实现气体专用的显示或接收规则。
 *         该标志与密度可独立设置。默认流体方块中，负密度液体也能上升，正密度气体仍向下流。
 *         世界放置、入桶、消散、加压等行为由对应的方块、容器或机器实现。</li>
 * </ul>
 *
 * <p><strong>原版及其他模组的注册值</strong></p>
 * <p>以下元组按 {@code (Temperature, Luminosity, Density, Viscosity, Gaseous)} 排列。
 * 整合包中的实际值取决于 {@code FluidRegistry} 最终选中的同名流体实现。</p>
 * <ul>
 *     <li>Forge 内置水 {@code (300, 0, 1000, 1000, false)} 和熔岩 {@code (1300, 15, 3000, 6000, false)}。
 *         原版牛奶桶对应的 {@code milk} 流体可以由匠魂等模组提供。
 *         <a href="https://github.com/MinecraftForge/MinecraftForge/blob/1.12.x/src/main/java/net/minecraftforge/fluids/FluidRegistry.java">Forge 流体注册表</a>。</li>
 *     <li>匠魂的 {@link FluidMolten} 默认值和熔融玛玉灵均为 {@code (1000, 10, 2000, 10000, false)}。
 *         熔融铁、金、钴、焦黑石共用此模板的其余四项，温度分别为 {@code 769}、{@code 532}、{@code 950}、{@code 800}。
 *         牛奶为 {@code (320, 0, 1000, 1000, false)}，血液为 {@code (336, 0, 1000, 1000, false)}，
 *         蓝色和紫色史莱姆液分别为 {@code (310, 0, 1500, 1500, false)} 和 {@code (370, 0, 1600, 1600, false)}。
 *         <a href="https://github.com/SlimeKnights/TinkersConstruct/blob/1.12/src/main/java/slimeknights/tconstruct/shared/TinkerFluids.java">匠魂流体定义</a>。</li>
 *     <li>Ender IO 的营养蒸馏液为 {@code (300, 0, 1500, 3000, false)}，火水为 {@code (2000, 0, 900, 1000, false)}，
 *         液态经验为 {@code (300, 10, 800, 1500, false)}，轻浮蒸气为 {@code (5, 0, -10, 100, true)}。
 *         液态阳光为 {@code (300, 0, 200, 400, false)}，绑定方块另外调用了 {@code setLightLevel(1)}。
 *         <a href="https://github.com/SleepyTrousers/EnderIO/blob/master/enderio-base/src/main/java/crazypants/enderio/base/fluid/Fluids.java">Ender IO 流体定义</a>。</li>
 *     <li>Mekanism 的重水为 {@code (300, 0, 1000, 1000, false)}，蒸汽为 {@code (300, 0, 1000, 1000, true)}。
 *         这里的 Forge 蒸汽没有绑定世界方块。Mekanism 另有独立的 {@code Gas} API。
 *         <a href="https://github.com/mekanism/Mekanism/blob/1.12/src/main/java/mekanism/common/MekanismFluids.java">Mekanism 流体定义</a>。</li>
 * </ul>
 *
 * <p><strong>本类的配值与注册行为</strong></p>
 * <ul>
 *     <li>默认熔融材料、{@link #dirtyWater} 和 {@link #shiningGel} 使用 {@code (1000, 10, 2000, 10000, false)}，
 *         默认流动调度间隔为 {@code 50} ticks。脏水和发光凝胶的自定义方块均使用 {@code Material.WATER}。
 *         {@link BlockShiningGel} 虽然设置了 {@code setLightLevel(1)}，但非零的 {@code maxScaledLight} 优先，满源发光 {@code 10} 级。</li>
 *     <li>工厂方法末尾的实参顺序是 {@code visc, dens}。{@link #greenSlime} 为 {@code (100, 0, 1200, 1500, false)}，
 *         默认流动调度间隔为 {@code 7} ticks。{@link #moltenAshenStone}、{@link #moltenCinderStone} 和 {@link #moltenLuminousStone}
 *         的温度均为 {@code 800}、密度为 {@code 2000}、黏度为 {@code 10000}，发光等级分别为 {@code 0}、{@code 0}、{@code 12}。</li>
 *     <li>未标注 {@link FluidBlock} 的流体统一绑定匠魂的 {@code BlockMolten}，使用 {@code Material.LAVA}，绿史莱姆液也走此分支。
 *         已标注的流体使用指定方块，例如 {@link BlockMoltenHothium} 使用 {@code Material.WATER} 并在碰撞回调中实现接触效果。</li>
 *     <li>{@link #moltenStellarium} 通过 {@link RegSmelteryFuel} 登记为匠魂燃料，每次消耗 {@code 10 mB}，
 *         时长参数为 {@code 400}。匠魂消耗它时使用内部热度 {@code 1000 - 300 = 700}。</li>
 * </ul>
 *
 * @see <a href="https://github.com/MinecraftForge/MinecraftForge/blob/1.12.x/src/main/java/net/minecraftforge/fluids/Fluid.java">Forge 流体属性定义</a>
 * @see <a href="https://github.com/MinecraftForge/MinecraftForge/blob/1.12.x/src/main/java/net/minecraftforge/fluids/BlockFluidBase.java">Forge 流体方块基础实现</a>
 * @see <a href="https://github.com/MinecraftForge/MinecraftForge/blob/1.12.x/src/main/java/net/minecraftforge/fluids/BlockFluidClassic.java">Forge 经典流体方块流动逻辑</a>
 */
@SuppressWarnings("unused")
public final class Fluids
{
    public static final FluidMolten moltenRoyalAlloy = $(nameRoyalAlloy, colorRoyalAlloy);
    /**
     * TODO 试将温度从 {@code 1000} 提到 {@code 1300–1400}，发光等级从 {@code 10} 提到 {@code 15}，
     * 呼应辉耀特性的点燃效果以及恒星金属块的满级光照。
     * 温度提高后，作为燃料的内部热度会从 {@code 700} 提到 {@code 1000–1100}。
     * 同时核对普通熔岩能否熔化恒星矿和金属块，以及浇铸冷却时间是否符合预期。
     */
    @RegSmelteryFuel(amount = 10, duration = 400)
    public static final FluidMolten moltenStellarium = $(nameStellarium, colorStellarium);

    /**
     * TODO 试将温度从 {@code 1000} 降到 {@code 250–270}，呼应材料的极寒特性和流体方块的接触减速效果。
     * 先为霍斯的熔炼配方保留显式的熔炼门槛，并为锭、粒、块和工具部件确认正值浇铸时间。
     * 匠魂会把低于 {@code 300} 的流体温度用于推导配方，默认熔炼门槛会降到底，较大体积的默认浇铸时间可能降到零以下。
     * {@link BlockMoltenHothium} 负责接触减速。
     */
    @FluidBlock(BlockMoltenHothium.class)
    public static final FluidMolten moltenHothium = $(nameHothium, colorHothium);
    public static final FluidMolten moltenCinnabar = $(nameCinnabar, colorCinnabar);
    public static final FluidMolten moltenImmersedSilver = $(nameImmersedSilver, colorImmersedSilver);
    public static final FluidMolten moltenSolita = $(nameSolita, colorSolita);
    public static final FluidMolten moltenMagiga = $(nameMagiga, colorMagiga);
    public static final FluidMolten moltenInertWitherium = $(nameInertWitherium, colorInertWitherium);
    public static final FluidMolten moltenWitherium = $(nameWitherium, colorWitherium);
    public static final FluidMolten moltenPolarium = $(namePolarium, colorPolarium);
    public static final FluidMolten moltenHalleium = $(nameHalleium, colorHalleium);
    public static final FluidMolten moltenAltairium = $(nameAltairium, colorAltairium);

    /**
     * TODO 试将温度从 {@code 1000} 降到 {@code 340–400}、发光等级从 {@code 10} 降到 {@code 0}、
     * 黏度从 {@code 10000} 降到 {@code 3000–5000}。可可具有美味和热熔材料特性，棕色染料也可熔出少量可可。
     * 调整后核对染料和可可块的熔炼时间、工具部件的浇铸时间，以及默认熔岩材质在世界中的接触表现。
     */
    public static final FluidMolten moltenCocoa = $(nameCocoa, colorCocoa);

    public static final FluidMolten moltenTitanium = $(nameTitanium, colorTitanium);
    public static final FluidMolten moltenTonium = $(nameTonium, colorTonium);
    public static final FluidMolten moltenIrisia = $(nameIrisia, colorIrisia);
    public static final FluidMolten moltenOraclium = $(nameOraclium, colorOraclium);

    /**
     * TODO 试将密度从 {@code 2000} 降到 {@code 1200–1500}，呼应气钢装备的浮力和蒸汽主题。
     * 保留正密度和液态标志，让熔融金属仍向下流。核对它与水及其他熔融金属接触时的置换表现。
     */
    public static final FluidMolten moltenSteamium = $(nameSteamium, colorSteamium);

    public static final FluidMolten moltenChloroplast = $(nameChloroplast, colorChloroplast);
    public static final FluidMolten moltenTanatonium = $(nameTanatonium, colorTanatonium);
    public static final FluidMolten moltenDecurrium = $(nameDecurrium, colorDecurrium);

    /**
     * TODO 试将温度从 {@code 1000} 降到 {@code 300–400}。
     * {@link BlockMoltenEnderTurbulence} 使用水材质，接触效果是末影传送和摔落伤害。
     * 调整时核对末影裂隙碎片的熔炼门槛和末影乱流糖果的浇铸时间。
     */
    @FluidBlock(BlockMoltenEnderTurbulence.class)
    public static final FluidMolten moltenEnderTurbulence = $(nameEnderTurbulence, colorEnderTurbulence);

    /**
     * TODO 按混有泥土的水配值，试用温度 {@code 300–330}、发光等级 {@code 0}、
     * 密度 {@code 1100–1300}、黏度 {@code 1400–2000}。现有配值来自熔融金属模板。
     * 较低的黏度会让污水更快扩散，且它仍可能置换原版水。试放时检查虚弱效果覆盖的范围。
     */
    @FluidBlock(BlockDirtyWater.class)
    public static final FluidColored dirtyWater = $$(nameDirtyWater, colorDirtyWater);

    /**
     * TODO 试将温度从 {@code 1000} 降到 {@code 300–330}，与荧光凝胶物品直接转换水方块的玩法一致。
     * 若以 {@link BlockShiningGel} 中的 {@code setLightLevel(1)} 为设计目标，将流体发光等级从 {@code 10} 提到 {@code 15}，
     * 让满源达到最高光照，流动格继续按流体量衰减。
     */
    @FluidBlock(BlockShiningGel.class)
    public static final FluidColored shiningGel = $$(nameShiningGel, colorShiningGel);

    /**
     * TODO 试将温度从 {@code 100} 提到 {@code 300–310}，以匠魂蓝色史莱姆液的 {@code 310} 为参照。
     * 当前活化史莱姆球用 {@code 250 mB} 浇铸，按匠魂的 {@code CastingRecipe#calcCooldownTime()} 算出的冷却时间为 {@code -7} ticks。
     * 调整后与蓝、紫史莱姆球对照熔炼和浇铸时间，并另行核对绿史莱姆液的世界方块材质。
     */
    public static final FluidColored greenSlime = $$("tiths_green_slime", 0x77bb45, 100, 0, 1500, 1200);
    public static final FluidMolten moltenAshenStone = $("tiths_ashen_stone", 0xb6b5b0, 800, 0, 10000, 2000);
    public static final FluidMolten moltenCinderStone = $("tiths_cinder_stone", 0x57545a, 800, 0, 10000, 2000);
    public static final FluidMolten moltenLuminousStone = $("tiths_luminous_stone", 0xd5c852, 800, 12, 10000, 2000);
    private Fluids() {}

    static FluidMolten $(String name, int color)
    {
        return $(name, color, 1000, 10, 10000, 2000);
    }

    static FluidMolten $(String name, int color, int temp, int lumen, int visc, int dens)
    {
        FluidMolten fluid = new FluidMolten(
                name, color,
                new ResourceLocation("tconstruct:blocks/fluids/molten_metal"),
                new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow")
        );
        fluid.setTemperature(temp); // 温度
        fluid.setLuminosity(lumen); // 亮度
        fluid.setViscosity(visc); // 黏度
        fluid.setDensity(dens); // 密度
        return fluid;
    }

    static FluidColored $$(String name, int color)
    {
        return $$(name, color, 1000, 10, 10000, 2000);
    }

    static FluidColored $$(String name, int color, int temp, int lumen, int visc, int dens)
    {
        FluidColored fluid = new FluidColored(
                name, color,
                new ResourceLocation("tconstruct:blocks/fluids/liquid"),
                new ResourceLocation("tconstruct:blocks/fluids/liquid_flow")
        );
        fluid.setTemperature(temp); // 温度
        fluid.setLuminosity(lumen); // 亮度
        fluid.setViscosity(visc); // 黏度
        fluid.setDensity(dens); // 密度
        return fluid;
    }
}
