package net.mcft.copy.betterstorage.addon;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.mcft.copy.betterstorage.addon.thaumcraft.ThaumcraftAddon;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;

public abstract class Addon {
	
	private static final List<Addon> addons = new ArrayList<Addon>();
	private static final Addon thaumcraft = new ThaumcraftAddon();
	
	public static void initAll() {
		ListIterator<Addon> iter = addons.listIterator();
		while (iter.hasNext()) {
			Addon addon = iter.next();
			if (!Loader.isModLoaded(addon.modName))
				iter.remove();
		}
	}
	
	public static void loadAllConfigs(Configuration config) {
		for (Addon addon : addons) addon.loadConfig(config);
	}
	public static void initializeAllItems() {
		for (Addon addon : addons) addon.initializeItems();
	}
	public static void registerAllItems() {
		for (Addon addon : addons) addon.registerItems();
	}
	public static void addAllRecipes() {
		for (Addon addon : addons) addon.addRecipes();
	}
	public static void addAllLocalizations(LanguageRegistry lang) {
		for (Addon addon : addons) addon.addLocalizations(lang);
	}
	public static void registerAllTileEntites() {
		for (Addon addon : addons) addon.registerTileEntites();
	}
	public static void postInitAll() {
		for (Addon addon : addons) addon.postInit();
	}
	
	
	public final String modName;
	
	public Addon(String modName) {
		this.modName = modName;
		addons.add(this);
	}
	
	public abstract void loadConfig(Configuration config);
	
	public abstract void initializeItems();
	
	public abstract void registerItems();
	
	public abstract void addRecipes();
	
	public abstract void addLocalizations(LanguageRegistry lang);
	
	public abstract void registerTileEntites();
	
	public abstract void postInit();
	
}
