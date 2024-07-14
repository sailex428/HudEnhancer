package io.sailex.config;

import com.google.gson.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {

    private final Logger LOGGER = LogManager.getLogger("ConfigManager");
    private final File configFile;
    private final PositionDisplayConfig config;
    private final Gson gson;

    public ConfigManager(PositionDisplayConfig config) {
        configFile = new File(FabricLoader.getInstance().getConfigDir().toString(), "position_display.json");
        this.config = config;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void initialize() {
        if (configFile.exists()) {
            loadConfig();
        } else {
            saveConfig();
        }
        saveConfigOnClientStop();
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            return;
        }
        JsonObject json = readConfigFile();
        processPositionData(json);
    }

    private JsonObject readConfigFile() {
        try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            LOGGER.error("Error loading configs from position_display.json : ", e);
            return new JsonObject();
        }
    }

    private void processPositionData(JsonObject json) {
        if (!json.has("data") || !json.get("data").isJsonArray()) {
            return;
        }
        Map<String, HudElement> positionMap = new HashMap<>();
        JsonArray dataArray = json.getAsJsonArray("data");
        for (JsonElement element : dataArray) {
            JsonObject elementData = element.getAsJsonObject();

            if (!isJsonValid(elementData)) {
                continue;
            }

            positionMap.put(elementData.get("name").getAsString(),
                    new HudElement(
                            elementData.get("x").getAsInt(),
                            elementData.get("y").getAsInt(),
                            elementData.get("width").getAsInt(),
                            elementData.get("height").getAsInt(),
                            elementData.get("color").getAsInt(),
                            elementData.get("hue").getAsInt(),
                            elementData.get("background").getAsBoolean(),
                            elementData.get("shadow").getAsBoolean()
                    )
            );
        }
        config.setPositionMap(positionMap);
    }

    public void saveConfig() {
        Map<String, HudElement> positions = config.getPositionMap();

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
            gson.toJson(createJson(positions), writer);
            LOGGER.info("Saved position_display.json successfully!");
        } catch (IOException e) {
            LOGGER.error("Error writing Position-Display configs to position_display.json : ", e);
        }
    }

    private JsonObject createJson(Map<String, HudElement> positions) {
        JsonArray positionsArrNode = new JsonArray();

        for (Map.Entry<String, HudElement> entry : positions.entrySet()) {
            JsonObject elementNode = new JsonObject();
            elementNode.addProperty("name", entry.getKey());
            elementNode.addProperty("x", entry.getValue().x());
            elementNode.addProperty("y", entry.getValue().y());
            elementNode.addProperty("width", entry.getValue().width());
            elementNode.addProperty("height", entry.getValue().height());
            elementNode.addProperty("color", entry.getValue().color());
            elementNode.addProperty("hue", entry.getValue().hue());
            elementNode.addProperty("background", entry.getValue().background());
            elementNode.addProperty("shadow", entry.getValue().shadow());

            positionsArrNode.add(elementNode);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", positionsArrNode);
        return jsonObject;
    }

    private boolean isJsonValid(JsonObject elementData) {
        return hasDimensions(elementData) || hasStyle(elementData);
    }

    private boolean hasDimensions(JsonObject elementData) {
        return elementData.has("x") &&
                elementData.has("y") &&
                elementData.has("width") &&
                elementData.has("height");
    }

    private boolean hasStyle(JsonObject elementData) {
        return elementData.has("color") &&
                elementData.has("hue") &&
                elementData.has("background") &&
                elementData.has("shadow");
    }

    private void saveConfigOnClientStop() {
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> saveConfig());
    }

}
