package firok.tiths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 从两份历史 OreSpawn 3 配置和回调参数重建 OreSpawn 4 的内置矿物配置。
 * 在项目根目录运行 {@code gradlew generateOreSpawnProvider}，或在 IDEA 中运行本类的
 * {@code main} 并传入 {@code --write}。省略参数时只校验当前文件。
 * 需要本机的 Git 和包含 {@code e2c5a05} 及其父提交的仓库历史。
 * 日常调整请直接编辑 {@code src/main/resources/assets/tiths/orespawn/provider.json}。
 * 使用 {@code --write} 会以此工具生成的规则覆盖该文件。
 */
public final class $Ignore_OreSpawnProviderGenerator
{
    private static final String LEGACY_CONFIG = "src/main/resources/assets/tiths/worldgens.json";
    private static final String BALANCE_COMMIT = "e2c5a055917d1d25c96e64a78aa976a3fd731350";
    private static final Path PROVIDER_FILE = Paths.get("src/main/resources/assets/tiths/orespawn/provider.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private $Ignore_OreSpawnProviderGenerator() { }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        boolean write = Arrays.equals(args, new String[] {"--write"});
        if (!write && args.length != 0)
        {
            throw new IllegalArgumentException("Usage: $Ignore_OreSpawnProviderGenerator [--write]");
        }

        JsonObject provider = generate(readLegacySpawns(BALANCE_COMMIT + "^"), readLegacySpawns(BALANCE_COMMIT));
        if (write)
        {
            Files.createDirectories(PROVIDER_FILE.getParent());
            Files.write(PROVIDER_FILE, (GSON.toJson(provider) + "\n").getBytes(StandardCharsets.UTF_8));
            System.out.println("Wrote " + PROVIDER_FILE);
        }
        else
        {
            String existing = new String(Files.readAllBytes(PROVIDER_FILE), StandardCharsets.UTF_8);
            if (!provider.equals(new JsonParser().parse(existing)))
            {
                throw new IllegalStateException("Generated rules differ from " + PROVIDER_FILE);
            }
            System.out.println("Verified " + PROVIDER_FILE);
        }
    }

    private static JsonObject readLegacySpawns(String revision) throws IOException, InterruptedException
    {
        String source = revision + ":" + LEGACY_CONFIG;
        Process git = new ProcessBuilder("git", "show", source)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start();
        String json;
        try (InputStream stdout = git.getInputStream())
        {
            json = IOUtils.toString(stdout, StandardCharsets.UTF_8);
        }
        if (git.waitFor() != 0)
        {
            throw new IOException("Cannot read " + source + ". Run from the project root with the required Git history.");
        }
        return new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("spawns");
    }

    private static JsonObject generate(JsonObject original, JsonObject lowered)
    {
        Map<String, OreAmount> rebalanced = rebalancedAmounts();
        JsonObject provider = new JsonObject();
        provider.addProperty("schema_version", 5);
        provider.addProperty("provider_modid", TinkersThings.MOD_ID);
        provider.addProperty("provider_revision", 2);
        JsonObject ores = new JsonObject();
        provider.add("ores", ores);

        for (Map.Entry<String, JsonElement> entry : lowered.entrySet())
        {
            String name = entry.getKey();
            // 高空云层由 TithsOreSpawnPlugin 单独注册。
            if ("test".equals(name)) continue;

            JsonObject spawn = entry.getValue().getAsJsonObject();
            JsonObject parameters = spawn.getAsJsonObject("parameters");
            JsonObject originalParameters = original.getAsJsonObject(name).getAsJsonObject("parameters");
            OreAmount amount = rebalanced.get(name);
            boolean changed = !parameters.equals(originalParameters);
            if (changed != (amount != null))
            {
                throw new IllegalStateException("Unexpected historical parameters for " + name);
            }

            LegacyAmount before = new LegacyAmount(originalParameters);
            LegacyAmount after = new LegacyAmount(parameters);
            OreAmount updated = amount != null ? amount : after.toPositiveRange();
            double beforeBudget = before.budget();
            double afterBudget = after.budget();
            validateBudget(name, beforeBudget, afterBudget, updated.budget(), changed);
            System.out.printf(Locale.ROOT, "%-22s %s -> %s -> %s%n",
                    name, beforeBudget, afterBudget, updated.budget());

            // 欧珀矿采用早期 Configs.WorldGens 中的沙漠群系白名单。
            JsonObject biomes = ("opal".equals(name) ? lowered.getAsJsonObject("altairium") : spawn)
                    .getAsJsonObject("biomes");
            // 保留迁移桥的规则 ID，避免给已有世界额外添加一套同种矿物的规则。
            ores.add(TinkersThings.MOD_ID + ":legacy/" + name, convertSpawn(spawn, parameters, updated, biomes));
        }
        return provider;
    }

    private static JsonObject convertSpawn(JsonObject spawn, JsonObject parameters, OreAmount amount, JsonObject biomes)
    {
        JsonArray blocks = spawn.getAsJsonArray("blocks");
        if (blocks.size() != 1 || blocks.get(0).getAsJsonObject().get("chance").getAsInt() != 100
                || !"default".equals(spawn.get("feature").getAsString())
                || !"default".equals(spawn.get("replaces").getAsString()))
        {
            throw new IllegalStateException("Expected a default ore rule with one output block: " + spawn);
        }

        String dimensionField = "dimension_selectors";
        String dimension = "orespawn:all_except_nether_end";
        String host = "minecraft:stone";
        JsonElement legacyDimensions = spawn.get("dimensions");
        if (!legacyDimensions.isJsonArray() || legacyDimensions.getAsJsonArray().size() != 0)
        {
            JsonArray includes = legacyDimensions.getAsJsonObject().getAsJsonArray("includes");
            int dimensionId = includes.get(0).getAsInt();
            if (includes.size() != 1 || (dimensionId != -1 && dimensionId != 1))
            {
                throw new IllegalStateException("Unexpected legacy dimension filter: " + legacyDimensions);
            }
            dimensionField = "dimensions";
            dimension = dimensionId == -1 ? "minecraft:the_nether" : "minecraft:the_end";
            host = dimensionId == -1 ? "minecraft:netherrack" : "minecraft:end_stone";
        }

        boolean enabled = spawn.get("enabled").getAsBoolean();
        JsonObject placement = new JsonObject();
        placement.addProperty("enabled", enabled);
        placement.addProperty("min_y", parameters.get("minHeight").getAsInt());
        placement.addProperty("max_y", parameters.get("maxHeight").getAsInt() - 1);
        placement.addProperty("frequency", amount.frequency);
        placement.addProperty("pattern", "default");
        JsonArray hosts = new JsonArray();
        hosts.add(host);
        placement.add("host_blocks", hosts);
        if (amount.minimum == amount.maximum)
        {
            placement.addProperty("quantity", amount.minimum);
        }
        else
        {
            placement.addProperty("min_quantity", amount.minimum);
            placement.addProperty("max_quantity", amount.maximum);
        }
        JsonArray biomeIds = new JsonArray();
        Set<String> seen = new HashSet<>();
        if (biomes.has("includes"))
        {
            for (JsonElement include : biomes.getAsJsonArray("includes"))
            {
                String id = include.getAsString();
                if (seen.add(id)) biomeIds.add(id);
            }
        }
        if (biomeIds.size() > 0) placement.add("biome_ids", biomeIds);

        JsonObject dimensions = new JsonObject();
        dimensions.add(dimension, placement);
        JsonObject ore = new JsonObject();
        ore.addProperty("block", blocks.get(0).getAsJsonObject().get("name").getAsString());
        ore.addProperty("enabled", enabled);
        ore.addProperty("retrogen", spawn.get("retrogen").getAsBoolean());
        ore.add(dimensionField, dimensions);
        return ore;
    }

    private static void validateBudget(String name, double before, double after, double updated, boolean changed)
    {
        boolean valid;
        if (before > after)
        {
            valid = after < updated && updated < before;
        }
        else if (changed)
        {
            valid = updated < before;
        }
        else
        {
            valid = updated == before;
        }
        if (!valid)
        {
            throw new IllegalStateException("Unexpected ore budget for " + name + ": " + before + " -> " + after + " -> " + updated);
        }
    }

    private static Map<String, OreAmount> rebalancedAmounts()
    {
        Map<String, OreAmount> amounts = new HashMap<>();
        amounts.put("chloroplast", new OreAmount(1.9, 1, 1));
        amounts.put("cinnabar", new OreAmount(3.5, 7, 10));
        amounts.put("cordierite", new OreAmount(5.5, 4, 9));
        amounts.put("corundum", new OreAmount(3.25, 2, 6));
        amounts.put("halleium", new OreAmount(1.4, 1, 3));
        amounts.put("hothium", new OreAmount(2.4, 1, 1));
        amounts.put("iceland_spar", new OreAmount(3.75, 1, 1));
        amounts.put("icelit", new OreAmount(5.5, 2, 5));
        amounts.put("immersed_silver", new OreAmount(7.25, 1, 4));
        amounts.put("inert_witherium", new OreAmount(0.9, 1, 1));
        amounts.put("lava_crystal", new OreAmount(8.5, 1, 4));
        amounts.put("lizanite", new OreAmount(3.6, 1, 2));
        amounts.put("moon_stone", new OreAmount(2.75, 2, 5));
        amounts.put("nitre", new OreAmount(3.5, 6, 10));
        amounts.put("opal", new OreAmount(1, 3, 5));
        amounts.put("polarium", new OreAmount(5.5, 3, 6));
        amounts.put("prehnite", new OreAmount(3.75, 1, 1));
        amounts.put("proustite", new OreAmount(4.5, 6, 11));
        amounts.put("pyrophyllite", new OreAmount(3.5, 4, 7));
        amounts.put("redins", new OreAmount(2.75, 2, 5));
        amounts.put("spinel", new OreAmount(4.5, 1, 2));
        amounts.put("steamium", new OreAmount(6, 4, 7));
        amounts.put("sun_stone", new OreAmount(1.75, 1, 2));
        amounts.put("talcum", new OreAmount(9, 3, 8));
        amounts.put("tanatonium", new OreAmount(5.5, 1, 4));
        amounts.put("titanium", new OreAmount(2.75, 2, 5));
        amounts.put("tonium", new OreAmount(1.8, 1, 4));
        amounts.put("topaz", new OreAmount(4.5, 1, 4));
        amounts.put("tourmaline", new OreAmount(1.8, 2, 5));
        amounts.put("vibrating_crystal", new OreAmount(6.25, 1, 2));
        return amounts;
    }

    private static final class OreAmount
    {
        private final Number frequency;
        private final int minimum;
        private final int maximum;

        private OreAmount(Number frequency, int minimum, int maximum)
        {
            this.frequency = frequency;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        private double budget()
        {
            return frequency.doubleValue() * (minimum + maximum) / 2.0;
        }
    }

    private static final class LegacyAmount
    {
        private final double frequency;
        private final int minimum;
        private final int maximum;

        private LegacyAmount(JsonObject parameters)
        {
            int size = parameters.get("size").getAsInt();
            int variation = parameters.get("variation").getAsInt();
            frequency = parameters.get("frequency").getAsDouble();
            minimum = size - variation;
            maximum = variation == 0 ? size : size + variation - 1;
        }

        // OS3 的非正数量等于空尝试。OS4 只接受正数数量，通过降低频率保留其期望值。
        private OreAmount toPositiveRange()
        {
            int positiveMinimum = Math.max(1, minimum);
            double positiveFrequency = frequency * (maximum - positiveMinimum + 1) / (maximum - minimum + 1);
            return new OreAmount(positiveFrequency, positiveMinimum, maximum);
        }

        private double budget()
        {
            return toPositiveRange().budget();
        }
    }
}
