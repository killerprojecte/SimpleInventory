package dev.rgbmc.simpleinv;

import dev.rgbmc.simpleinv.handlers.ClickHandler;
import dev.rgbmc.simpleinv.handlers.ClickHandlers;
import dev.rgbmc.simpleinv.handlers.CloseHandler;
import dev.rgbmc.simpleinv.handlers.CloseSlotHandler;
import dev.rgbmc.simpleinv.holders.SimpleInventoryHolder;
import dev.rgbmc.simpleinv.item.ItemBuilder;
import dev.rgbmc.simpleinv.listeners.SimpleInventoryListener;
import dev.rgbmc.simpleinv.objects.*;
import dev.rgbmc.simpleinv.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SimpleInventory {
    private final Map<Integer, ClickHandlers> handlersMap = new HashMap<>();
    private final Map<Integer, CloseSlotHandler> closeSlotHandlers = new HashMap<>();
    private final Map<Integer, ItemStack> itemMap = new HashMap<>();
    private final List<Integer> allowsSlot = new ArrayList<>();
    private final List<CloseHandler> closeHandlers = new ArrayList<>();
    private final List<Integer> undefined_slots = new ArrayList<>();
    private InventoryType inventoryType;
    private String title;
    private InventoryMode mode = InventoryMode.NORMAL;
    private int size = 27;

    public SimpleInventory() {
        this(InventoryType.CHEST);
    }

    public SimpleInventory(InventoryType inventoryType) {
        this(inventoryType, "&7Created By SimpleInventory");
    }

    public SimpleInventory(String title) {
        this(InventoryType.CHEST, title);
    }

    public SimpleInventory(String title, int size) {
        this(InventoryType.CHEST, title, size);
    }

    public SimpleInventory(InventoryType inventoryType, String title) {
        this(inventoryType, title, 27);
    }

    public SimpleInventory(InventoryType inventoryType, String title, int size) {
        setInventoryType(inventoryType);
        setTitle(title);
        setSize(size);
    }

    public static void register(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new SimpleInventoryListener(), plugin);
    }

    public SimpleInventory bindSlot(int slot, ClickHandlers handlers) {
        if (handlersMap.containsKey(slot)) {
            handlersMap.get(slot).addHandlers(handlers);
        } else {
            handlersMap.put(slot, handlers);
        }
        return this;
    }

    public SimpleInventory bindSlot(int slot, ClickHandler handler) {
        if (handlersMap.containsKey(slot)) {
            handlersMap.get(slot).add(handler);
        } else {
            ClickHandlers clickHandlers = new ClickHandlers();
            clickHandlers.add(handler);
            handlersMap.put(slot, clickHandlers);
        }
        return this;
    }

    public SimpleInventory bindSlots(ClickHandler handler, int... slots) {
        for (int slot : slots) {
            bindSlot(slot, handler);
        }
        return this;
    }

    public SimpleInventory bindSlots(ClickHandlers handlers, int... slots) {
        for (int slot : slots) {
            bindSlot(slot, handlers);
        }
        return this;
    }

    public SimpleInventory closeSlot(int slot, CloseSlotHandler handler) {
        closeSlotHandlers.put(slot, handler);
        return this;
    }

    public SimpleInventory closeSlots(CloseSlotHandler handler, int... slots) {
        for (int slot : slots) {
            closeSlot(slot, handler);
        }
        return this;
    }

    public SimpleInventory clearCloseSlots() {
        closeSlotHandlers.clear();
        return this;
    }

    public SimpleInventory removeCloseSlot(int slot) {
        closeSlotHandlers.remove(slot);
        return this;
    }

    public void closedSlot(SlotState slotState) {
        if (closeSlotHandlers.containsKey(slotState.getSlot())) {
            closeSlotHandlers.get(slotState.getSlot()).call(slotState);
        }
    }

    public SimpleInventory addCloseHandler(CloseHandler closeHandler) {
        closeHandlers.add(closeHandler);
        return this;
    }

    public SimpleInventory removeCloseHandler(CloseHandler closeHandler) {
        closeHandlers.remove(closeHandler);
        return this;
    }

    public SimpleInventory clearCloseHandlers() {
        closeHandlers.clear();
        return this;
    }

    public SimpleInventory addItem(int slot, ItemStack item) {
        itemMap.put(slot, item);
        return this;
    }

    public SimpleInventory addItems(ItemStack item, int... slots) {
        for (int slot : slots) {
            addItem(slot, item);
        }
        return this;
    }

    public SimpleInventory removeItem(int slot) {
        itemMap.remove(slot);
        return this;
    }

    public SimpleInventory clearItems() {
        itemMap.clear();
        return this;
    }

    public SimpleInventory allowSlot(int slot) {
        allowsSlot.add(slot);
        return this;
    }

    public SimpleInventory denySlot(int slot) {
        allowsSlot.remove(slot);
        return this;
    }

    public SimpleInventory denyAll() {
        allowsSlot.clear();
        return this;
    }

    public boolean isAllow(int slot) {
        return allowsSlot.contains(slot);
    }

    public List<Integer> getInteractiveSlots() {
        return allowsSlot;
    }

    public void clickSlot(int slot, ClickState clickState) {
        if (handlersMap.containsKey(slot)) {
            handlersMap.get(slot).call(clickState);
        }
    }

    public void closeInventory(CloseState closeState) {
        for (CloseHandler closeHandler : closeHandlers) {
            closeHandler.call(closeState);
        }
    }

    public SimpleInventory clearBinds() {
        handlersMap.clear();
        return this;
    }

    public SimpleInventory removeBinds(int slot) {
        handlersMap.remove(slot);
        return this;
    }

    public Inventory build() {
        Inventory inventory = createInventory();
        for (Map.Entry<Integer, ItemStack> itemStackEntry : itemMap.entrySet()) {
            inventory.setItem(itemStackEntry.getKey(), itemStackEntry.getValue().clone());
        }
        return inventory;
    }

    private Inventory createInventory() {
        SimpleInventoryHolder holder = new SimpleInventoryHolder(this);
        if (inventoryType.equals(InventoryType.CHEST)) {
            return Bukkit.createInventory(holder, getSize(), getTitle());
        } else {
            return Bukkit.createInventory(holder, getInventoryType(), getTitle());
        }
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public SimpleInventory setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SimpleInventory setTitle(String title) {
        this.title = Color.color(title);
        return this;
    }

    public InventoryMode getMode() {
        return mode;
    }

    public SimpleInventory setMode(InventoryMode mode) {
        this.mode = mode;
        return this;
    }

    public int getSize() {
        return size;
    }

    public SimpleInventory setSize(int size) {
        this.size = size;
        return this;
    }

    public List<Integer> getUndefinedIcons() {
        return undefined_slots;
    }

    public SimpleInventory setUndefinedIcons(List<Integer> undefined_slots) {
        this.undefined_slots.clear();
        this.undefined_slots.addAll(undefined_slots);
        return this;
    }

    public static class ItemParser {
        public static ItemBuilder parseItem(ConfigurationSection section) {
            ItemBuilder itemBuilder = new ItemBuilder();
            itemBuilder.type(Material.getMaterial(section.getString("material").toUpperCase()));
            itemBuilder.displayName("");
            if (section.contains("amount")) {
                itemBuilder.amount(section.getInt("amount"));
            }
            if (section.contains("data")) {
                itemBuilder.damage((short) section.getInt("data"));
            }
            if (section.contains("name")) {
                itemBuilder.displayName(section.getString("name"));
            }
            if (section.contains("lore")) {
                itemBuilder.lore(section.getStringList("lore"));
            }
            if (section.contains("flags")) {
                for (String flag : section.getStringList("flags")) {
                    itemBuilder.flag(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }
            if (section.contains("unbreakable")) {
                if (section.getBoolean("unbreakable")) {
                    itemBuilder.unbreakable();
                } else {
                    itemBuilder.breakable();
                }
            }
            if (section.contains("enchantments")) {
                for (String s : section.getStringList("enchantments")) {
                    String[] split = s.split(":");
                    itemBuilder.enchantment(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
                }
            }
            if (section.contains("model")) {
                itemBuilder.customModelData(section.getInt("model"));
            }
            if (section.contains("texture")) {
                itemBuilder.skullTexture(section.getString("texture"));
            }
            return itemBuilder;
        }
    }

    public static class InventoryParser {
        public static InventoryBuilder deserialize(
                FileConfiguration configuration,
                Map<String, ClickHandlers> clickHandlers,
                CloseHandler closeHandler,
                Map<String, CloseSlotHandler> closeSlotHandlers) {
            return deserialize(
                    configuration, clickHandlers, closeHandler, closeSlotHandlers, variableInfo -> variableInfo.getOrigin());
        }

        public static InventoryBuilder deserialize(
                FileConfiguration configuration,
                Map<String, ClickHandlers> clickHandlers,
                CloseHandler closeHandler,
                Map<String, CloseSlotHandler> closeSlotHandlers,
                Function<VariableInfo, String> variableHandler) {
            List<String> layouts = configuration.getStringList("layout");
            Map<String, ItemBuilder> icons = new HashMap<>();
            Map<String, ClickHandlers> icon_actions = new HashMap<>();
            Map<String, CloseSlotHandler> icons_close = new HashMap<>();
            List<String> interactive_slots = new ArrayList<>();
            List<String> undefined_slots = new ArrayList<>();
            for (String key : configuration.getConfigurationSection("icons").getKeys(false)) {
                ConfigurationSection section = configuration.getConfigurationSection("icons." + key);
                ItemBuilder itemBuilder = ItemParser.parseItem(section);
                itemBuilder.slotName(key);
                if (section.contains("interactive") && section.getBoolean("interactive")) {
                    interactive_slots.add(key);
                }
                icons.put(key, itemBuilder);
                if (section.contains("action") && !section.getString("action").equalsIgnoreCase("none")) {
                    icon_actions.put(key, clickHandlers.get(section.getString("action")));
                }
                if (section.contains("close") && !section.getString("close").equalsIgnoreCase("none")) {
                    icons_close.put(key, closeSlotHandlers.get(section.getString("close")));
                }
                if (section.contains("undefined") && section.getBoolean("undefined")) {
                    undefined_slots.add(key);
                }
            }
            return new InventoryBuilder(icons, icon_actions, icons_close, interactive_slots, layouts, InventoryMode.valueOf(configuration.getString("mode").toUpperCase()), closeHandler, configuration.getString("title"), variableHandler, undefined_slots);
        }
    }

    public static class InventoryBuilder {
        private final Map<String, ItemBuilder> icons;
        private final List<String> layouts;
        private final InventoryMode mode;
        private final CloseHandler closeHandler;
        private final String title;
        private final Function<VariableInfo, String> variableHandler;
        private final Map<Integer, PackedLayout> mappedLayout = new HashMap<>();

        public InventoryBuilder(Map<String, ItemBuilder> icons, Map<String, ClickHandlers> icon_actions, Map<String, CloseSlotHandler> icons_close, List<String> interactive_slots, List<String> layouts, InventoryMode mode, CloseHandler closeHandler, String title, Function<VariableInfo, String> variableHandler, List<String> undefined_slots) {
            this.icons = icons;
            this.layouts = layouts;
            this.mode = mode;
            this.closeHandler = closeHandler;
            this.title = title;
            this.variableHandler = variableHandler;
            for (int r = 0; r < layouts.size(); r++) {
                String layout_str = layouts.get(r);
                char[] keys = layout_str.toCharArray();
                for (int i = 0; i < keys.length; i++) {
                    if (i >= 9) continue;
                    String key = String.valueOf(keys[i]);
                    if (icons.containsKey(key)) {
                        int slot = (r * 9) + i;
                        boolean undefined = false;
                        boolean interactive = false;
                        ClickHandlers clickHandlers = null;
                        CloseSlotHandler closeSlotHandler = null;
                        if (undefined_slots.contains(key)) {
                            undefined = true;
                        }
                        if (icon_actions.containsKey(key)) {
                            clickHandlers = icon_actions.get(key);
                        }
                        if (interactive_slots.contains(key)) {
                            interactive = true;
                        }
                        if (icons_close.containsKey(key)) {
                            closeSlotHandler = icons_close.get(key);
                        }
                        mappedLayout.put(slot, new PackedLayout(icons.get(key), clickHandlers, closeSlotHandler, interactive, undefined));
                    }
                }
            }
        }

        public SimpleInventory builder(Player player) {
            SimpleInventory inventory =
                    new SimpleInventory(variableHandler.apply(new VariableInfo(title, player)), layouts.size() * 9);
            inventory.setMode(mode);
            inventory.addCloseHandler(closeHandler);
            List<Integer> undefinedSlots = new ArrayList<>();
            for (Map.Entry<Integer, PackedLayout> layoutEntry : mappedLayout.entrySet()) {
                int slot = layoutEntry.getKey();
                PackedLayout layout = layoutEntry.getValue();
                inventory.addItem(slot, layout.getItemBuilder().build(player, variableHandler));
                if (layout.isUndefined()) {
                    undefinedSlots.add(slot);
                }
                if (layout.isInteractive()) {
                    inventory.allowSlot(slot);
                }
                if (layout.getClickHandlers() != null) {
                    inventory.bindSlot(slot, layout.getClickHandlers());
                }
                if (layout.getCloseSlotHandler() != null) {
                    inventory.closeSlot(slot, layout.getCloseSlotHandler());
                }
            }
            inventory.setUndefinedIcons(undefinedSlots);
            return inventory;
        }

        public List<String> getLayouts() {
            return layouts;
        }

        public Map<String, ItemBuilder> getIcons() {
            return icons;
        }

        public ItemBuilder getIcon(String key) {
            return icons.get(key);
        }
    }
}
