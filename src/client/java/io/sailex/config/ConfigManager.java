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

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> saveConfig());
    }

    public void initialize() {
        if (configFile.exists()) {
            loadConfig();
        } else {
            saveConfig();
        }
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            return;
        }
        ObjectNode json;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.readValue(configFile, ObjectNode.class);
        } catch (IOException e) {
            LOGGER.error("Error loading configs from position_display.json : ", e);
            return;
        }
        processPositionData(json);
    }

    private void processPositionData(ObjectNode json) {
        Map<String, Position> positionMap = new HashMap<>();
        for (JsonNode elementData : json.get("data")) {

            if (!elementData.has("x") || !elementData.has("y")) {
                continue;
            }
            if (!elementData.has("width") || !elementData.has("height")) {
                continue;
            }
            positionMap.put(elementData.get("name").asText(), new Position(elementData.get("x").asInt(), elementData.get("y").asInt(), elementData.get("width").asInt(), elementData.get("height").asInt()));
        }
        config.setPositionMap(positionMap);
    }

    public void saveConfig() {
        Map<String, Position> positions = config.getPositionMap();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode positionsArrNode = mapper.createArrayNode();

        for (Map.Entry<String, Position> entry : positions.entrySet()) {
            ObjectNode valueNode = mapper.createObjectNode();
            valueNode.put("x", entry.getValue().x());
            valueNode.put("y", entry.getValue().y());
            valueNode.put("width", entry.getValue().width());
            valueNode.put("height", entry.getValue().height());

            ObjectNode positionNode = mapper.createObjectNode();
            positionNode.set(entry.getKey(), valueNode);
            positionsArrNode.add(positionNode);
        }

        try {
            PrintWriter writer = new PrintWriter(configFile, StandardCharsets.UTF_8);
            writer.write(mapper.writeValueAsString(mapper.createObjectNode().set("data", positionsArrNode)));
            writer.close();
            LOGGER.info("Saved position_display.json successfully!");
        } catch (IOException e) {
            LOGGER.error("Error writing Position-Display configs to position_display.json : ", e);
        }
    }

}
