package me.thatbenderguy.enhancedtooltips;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.*;

@ConfigGroup("enhancedtooltips")
public interface EnhancedTooltipsConfig extends Config{

    @ConfigSection(
            name = "Colors",
            description = "Custom Colors for tooltips",
            position = 0
    )
    String colorsSection = "colorsSection";

    @ConfigItem(
            keyName = "showGE",
            name = "Show Grand Exchange Price",
            description = "Whether or not to show price from GE",
            position = 1
    )
    default boolean showGE(){
        return true;
    }

    @ConfigItem(
            keyName = "geColor",
            name = "Grand Exchange Color",
            description = "Color of the grand exchange tooltip",
            section = colorsSection
    )
    default Color geColor(){
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "showLA",
            name = "Show Low Alch Price",
            description = "Whether or not to show price for LA. NOTE: LA price is a rough estimate based off of wiki's description of Low Alchemy",
            position = 5
    )
    default boolean showLA(){
        return false;
    }

    @ConfigItem(
            keyName = "laColor",
            name = "Low Alchemy Color",
            description = "Color of the low alchemy tooltip",
            section = colorsSection
    )
    default Color laColor(){
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "showHA",
            name = "Show High Alch Price",
            description = "Whether or not to show price for HA",
            position = 3
    )
    default boolean showHA(){
        return true;
    }

    @ConfigItem(
            keyName = "haColor",
            name = "High Alchemy Color",
            description = "Color of the high alchemy tooltip",
            section = colorsSection
    )
    default Color haColor(){
        return new Color(0xFFFFFF);
    }
}
