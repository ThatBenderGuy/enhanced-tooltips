package me.thatbenderguy.enhancedtooltips;

import com.google.inject.Binder;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Enhanced Inventory Tooltips",
        description = "Extra tooltips with features like custom colors and more types of tooltips!",
        tags = {"alch", "inventory", "overlay", "price"}
)
public class EnhancedTooltipsPlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private EnhancedTooltipsOverlay overlay;

    @Inject
    private Client client;

    @Inject
    private EnhancedTooltipsConfig config;

    @Provides
    EnhancedTooltipsConfig getConfig(ConfigManager configManager){
        return configManager.getConfig(EnhancedTooltipsConfig.class);
    }

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        log.info("Example stopped!");
    }
}
