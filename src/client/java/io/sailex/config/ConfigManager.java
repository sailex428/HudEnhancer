package io.sailex.config;

import com.google.gson.*;
import io.sailex.gui.hud.HudElementsManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the configuration for the HUD elements.
 *
 * @author sailex
 */
public class ConfigManager {

    private final Logger LOGGER = LogManager.getLogger("ConfigManager");
    private HudElementsManager hudElementsManager;

    private final File configFile;
    private final DefaultConfig config;
    private final Gson gson;

    /**
     * Constructs a ConfigManager with the given default configuration.
     *
     * @param config the default configuration
     */
    public ConfigManager(DefaultConfig config) {
        configFile = new File(FabricLoader.getInstance().getConfigDir().toString(), "hud-enhancer.json");
        this.config = config;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Loads/saves the config on mod initialize.
     */
    public void initialize() {
        if (configFile.exists()) {
            loadConfig();
        } else {
            saveConfig();
        }
        saveConfigOnClientStop();
    }

    /**
     * Loads the configuration from the configuration file.
     */
    private void loadConfig() {
        processPositionData(readConfigFile());
    }

    /**
     * Reads the configuration file and returns its content as a JsonObject.
     *
     * @return the content of the configuration file as a JsonObject
     */
    private JsonObject readConfigFile() {
        try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            LOGGER.error("Error loading configs from hud-enhancer.json: ", e);
            return new JsonObject();
        }
    }

    /**
     * Processes the position data from the given JsonObject.
     *
     * @param json the JsonObject containing the position data
     */
    private void processPositionData(JsonObject json) {
        if (!json.has("data") || !json.get("data").isJsonArray()) {
            return;
        }
        Map<String, ConfigElement> positionMap = new HashMap<>();
        JsonArray dataArray = json.getAsJsonArray("data");
        for (JsonElement element : dataArray) {
            JsonObject elementData = element.getAsJsonObject();

            if (!isJsonValid(elementData)) {
                continue;
            }

            positionMap.put(elementData.get("name").getAsString(),
                    new ConfigElement(
                            elementData.get("x").getAsInt(),
                            elementData.get("y").getAsInt(),
                            elementData.get("width").getAsInt(),
                            elementData.get("height").getAsInt(),
                            elementData.get("color").getAsInt(),
                            elementData.get("hue").getAsInt(),
                            elementData.get("isRainbow").getAsBoolean(),
                            elementData.get("background").getAsBoolean(),
                            elementData.get("shadow").getAsBoolean(),
                            elementData.get("isActive").getAsBoolean()
                    )
            );
        }
        config.setPositionMap(positionMap);
    }

    /**
     * Saves the current configuration to the configuration file.
     */
    private void saveConfig() {
        Map<String, ConfigElement> configOfElements = config.getConfigElementMap();

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
            gson.toJson(createJson(configOfElements), writer);
            LOGGER.info("Saved {} successfully!", configFile.getName());
        } catch (IOException e) {
            LOGGER.error("Error writing HudEnhancer configs to {}: ", configFile.getName(), e);
        }
    }

    /**
     * Creates a JsonObject from the given positions map.
     *
     * @param positions the map of positions
     * @return the JsonObject representing the positions
     */
    private JsonObject createJson(Map<String, ConfigElement> positions) {
        JsonArray positionsArrNode = new JsonArray();

        for (Map.Entry<String, ConfigElement> entry : positions.entrySet()) {
            JsonObject elementNode = new JsonObject();
            elementNode.addProperty("name", entry.getKey());
            elementNode.addProperty("x", entry.getValue().x());
            elementNode.addProperty("y", entry.getValue().y());
            elementNode.addProperty("width", entry.getValue().width());
            elementNode.addProperty("height", entry.getValue().height());
            elementNode.addProperty("color", entry.getValue().color());
            elementNode.addProperty("hue", entry.getValue().hue());
            elementNode.addProperty("isRainbow", entry.getValue().isRainbow());
            elementNode.addProperty("background", entry.getValue().background());
            elementNode.addProperty("shadow", entry.getValue().shadow());
            elementNode.addProperty("isActive", entry.getValue().isActive());

            positionsArrNode.add(elementNode);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", positionsArrNode);
        return jsonObject;
    }

    /**
     * Checks if the given JsonObject is valid.
     *
     * @param elementData the JsonObject to check
     * @return true if the JsonObject is valid, false otherwise
     */
    private boolean isJsonValid(JsonObject elementData) {
        return hasDimensions(elementData) || hasStyle(elementData);
    }

    /**
     * Checks if the given JsonObject has dimension properties.
     *
     * @param elementData the JsonObject to check
     * @return true if the JsonObject has dimension properties, false otherwise
     */
    private boolean hasDimensions(JsonObject elementData) {
        return elementData.has("x") &&
                elementData.has("y") &&
                elementData.has("width") &&
                elementData.has("height");
    }

    /**
     * Checks if the given JsonObject has style properties.
     *
     * @param elementData the JsonObject to check
     * @return true if the JsonObject has style properties, false otherwise
     */
    private boolean hasStyle(JsonObject elementData) {
        return elementData.has("color") &&
                elementData.has("hue") &&
                elementData.has("isRainbow") &&
                elementData.has("background") &&
                elementData.has("shadow") &&
                elementData.has("isActive");
    }

    /**
     * Registers a callback to save the configuration when the client stops.
     */
    private void saveConfigOnClientStop() {
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            hudElementsManager.updateConfigElements();
            saveConfig();
        });
    }

    /**
     * Sets the HUD elements manager.
     *
     * @param hudElementsManager the HUD elements manager
     */
    public void setHudElementsManager(HudElementsManager hudElementsManager) {
        this.hudElementsManager = hudElementsManager;
    }

}
