package slimevoid.paintingchooser.core;

import net.minecraft.item.Item;
import slimevoid.paintingchooser.core.lib.CoreLib;
import slimevoidlib.core.SlimevoidCore;
import slimevoidlib.core.SlimevoidLib;
import slimevoidlib.util.ItemRemover;

public class PCInit {

	public static String minecraftDir = SlimevoidLib.proxy.getMinecraftDir();
	private static boolean initialized = false;

	public static void initialize() {
		if (!initialized) {
			initialized = true;
			load();
		}
	}

	private static void load() {
		//RecipeRemover.registerItemRecipeToRemove(Item.painting);
		ItemRemover.removeVanillaItem(Item.painting);
		PaintingChooser.proxy.preInit();
		PaintingChooser.proxy.registerRenderInformation();
		SlimevoidCore.console(CoreLib.MOD_ID, "Registering items...");
		PCCore.addItems();
		SlimevoidCore.console(CoreLib.MOD_ID, "Naming items...");
		PCCore.addNames();
		SlimevoidCore.console(CoreLib.MOD_ID, "Registering recipes...");
		PCCore.addRecipes();
	}
}
