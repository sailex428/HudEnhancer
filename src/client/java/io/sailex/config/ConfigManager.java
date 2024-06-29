package io.sailex.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public ConfigManager(PositionDisplayConfig config) {
        configFile = new File(FabricLoader.getInstance().getConfigDir().toString(), "position_display.json");
        this.config = config;
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
        ObjectNode json = readConfigFile();
        processPositionData(json);
    }

    private ObjectNode readConfigFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(configFile, ObjectNode.class);
        } catch (IOException e) {
            LOGGER.error("Error loading configs from position_display.json : ", e);
            return mapper.createObjectNode();
        }
    }

    private void processPositionData(ObjectNode json) {
        if (!json.has("data") && !(json.get("data") instanceof ArrayNode)) {
            return;
        }
        Map<String, HudElement> positionMap = new HashMap<>();
        for (JsonNode elementData : json.get("data")) {

            if (!isJsonValid(elementData)) {
                continue;
            }

            positionMap.put(elementData.get("name").asText(),
                    new HudElement(
                        elementData.get("x").asInt(),
                        elementData.get("y").asInt(),
                        elementData.get("width").asInt(),
                        elementData.get("height").asInt(),
                        elementData.get("color").asInt(),
                        elementData.get("backgroundColor").asInt(),
                        elementData.get("shadow").asBoolean()
                    )
            );
        }
        config.setPositionMap(positionMap);
    }

    public void saveConfig() {
        Map<String, HudElement> positions = config.getPositionMap();

        ObjectMapper mapper = new ObjectMapper();
        try {
            PrintWriter writer = new PrintWriter(configFile, StandardCharsets.UTF_8);
            writer.write(mapper.writeValueAsString(createJson(positions, mapper)));
            writer.close();
            LOGGER.info("Saved position_display.json successfully!");
        } catch (IOException e) {
            LOGGER.error("Error writing Position-Display configs to position_display.json : ", e);
        }
    }

    private JsonNode createJson(Map<String, HudElement> positions, ObjectMapper mapper) {
        ArrayNode positionsArrNode = mapper.createArrayNode();

        for (Map.Entry<String, HudElement> entry : positions.entrySet()) {
            ObjectNode elementNode = mapper.createObjectNode();
            elementNode.put("name", entry.getKey());
            elementNode.put("x", entry.getValue().x());
            elementNode.put("y", entry.getValue().y());
            elementNode.put("width", entry.getValue().width());
            elementNode.put("height", entry.getValue().height());
            elementNode.put("color", entry.getValue().color());
            elementNode.put("backgroundColor", entry.getValue().backgroundColor());
            elementNode.put("shadow", entry.getValue().shadow());

            positionsArrNode.add(elementNode);
        }
        return mapper.createObjectNode().set("data", positionsArrNode);
    }

    private boolean isJsonValid(JsonNode elementData) {
        return hasDimensions(elementData) || hasStyle(elementData);
    }

    private boolean hasDimensions(JsonNode elementData) {
        return elementData.has("x") &&
                elementData.has("y") &&
                elementData.has("width") &&
                elementData.has("height");
    }

    private boolean hasStyle(JsonNode elementData) {
        return elementData.has("color") &&
                elementData.has("backgroundColor") &&
                elementData.has("shadow");
    }

    private void saveConfigOnClientStop() {
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> saveConfig());
    }

}
