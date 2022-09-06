package me.thatbenderguy.enhancedtooltips;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class EnhancedTooltipsTest {
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(EnhancedTooltipsPlugin.class);
        RuneLite.main(args);
    }
}
