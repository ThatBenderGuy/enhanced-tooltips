package me.thatbenderguy.enhancedtooltips;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.MenuEntry;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.QuantityFormatter;

import java.awt.*;

public class EnhancedTooltipsOverlay extends Overlay {

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    TooltipManager tooltipManager;

    @Inject
    EnhancedTooltipsConfig config;

    @Override
    public Dimension render(Graphics2D graphics) {

        renderPriceTooltips();

        return null;
    }


    private int getGEForItem(int itemID){
        final int gePrice = itemManager.getItemPrice(itemID);
        if(gePrice > 0) return gePrice;
        else return -1;
    }
    private int getHAForItem(int itemID, ItemComposition itemComposition){
        final int hAlchPrice = itemComposition.getHaPrice();
        if(hAlchPrice > 0) return hAlchPrice;
        else return -1;
    }
    private int getLAForItem(int itemID, ItemComposition itemComposition){
        final int hAlchPrice = itemComposition.getHaPrice();
        if(hAlchPrice > 0) {
            int lAlchPrice = (int) Math.ceil(hAlchPrice * 0.6666d);
            return lAlchPrice;
        } else {
            return -1;
        }
    }

    private void renderPriceTooltips()
    {
        if(client.isMenuOpen()) return;                                 // If menu is not opened then return

        final MenuEntry[] menu = client.getMenuEntries();
        final int menuSize = menu.length;
        if(menuSize <= 0) return;                                      // return if there is no menu to show

        final MenuEntry entry = menu[menuSize - 1];
        final Widget widget = entry.getWidget();
        if(widget == null) return;                                    // return if there is no widget

        final int group = WidgetInfo.TO_GROUP(widget.getId());
        int itemId = -1;
        int itemQuantity = 0;
        boolean moreThanOne = false;

        if(widget.getId() == WidgetInfo.INVENTORY.getId()             // Only populate item info if the inventory or equipment widget is shown
                || group == WidgetInfo.EQUIPMENT_INVENTORY_ITEMS_CONTAINER.getGroupId()
                || widget.getId() == WidgetInfo.EQUIPMENT_INVENTORY_ITEMS_CONTAINER.getId())
        {
            itemId = widget.getItemId();
            itemQuantity = widget.getItemQuantity();
        }

        if(itemId == -1) return;                                                            // If no item or empty slot return
        final ItemComposition itemComposition = itemManager.getItemComposition(itemId);     // Get item composition for HA and LA prices
        if(itemQuantity > 1) moreThanOne = true;                                            // if there is more than one item set to true in order to list the (Xea) price

        int gep = getGEForItem(itemId);                     // GE Price
        int hap = getHAForItem(itemId, itemComposition);    // HA Price
        int lap = getLAForItem(itemId, itemComposition);    // LA Price

        StringBuilder sb = new StringBuilder();
        if(config.showGE() && gep != -1 && gep != 0){
            sb.append(generateTooltip("GE: ", gep, itemQuantity, moreThanOne, config.geColor(), true));
        }
        if(config.showHA() && hap != -1 && hap != 0){
            sb.append(generateTooltip("HA: ", hap, itemQuantity, moreThanOne, config.haColor(), true));
        }
        if(config.showLA() && lap != -1 && lap != 0){
            sb.append(generateTooltip("LA: ~", lap, itemQuantity, moreThanOne, config.laColor(), false));
        }
        final String tooltip = sb.toString();
        tooltipManager.add(new Tooltip(tooltip));
    }

    private String generateTooltip(String prefix, int price, int quantity, boolean multiple, Color color, boolean breakAfter){
        StringBuilder builder = new StringBuilder();
        builder.append(ColorUtil.prependColorTag("", color));
        builder.append(prefix);
        String total = QuantityFormatter.formatNumber(price * quantity);
        String each = QuantityFormatter.formatNumber(price);
        builder.append(total);
        if(multiple)  builder.append(" (" + each + "ea)");
        builder.append(ColorUtil.CLOSING_COLOR_TAG);
        if(breakAfter) builder.append("</br>");

        return builder.toString();
    }
}
