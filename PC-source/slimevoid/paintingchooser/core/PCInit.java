package slimevoid.paintingchooser.core;

import net.minecraft.item.Item;
import slimevoid.lib.ICommonProxy;
import slimevoid.lib.ICore;
import slimevoid.lib.core.Core;
import slimevoid.lib.core.ItemRemover;
import slimevoid.lib.core.SlimevoidCore;
import slimevoid.lib.core.SlimevoidLib;

public class PCInit {

	public static String minecraftDir = SlimevoidLib.proxy.getMinecraftDir();
	public static ICore PChooser;
	private static boolean initialized = false;

	public static void initialize(ICommonProxy proxy) {
		if (!initialized) {
			initialized = true;
			PChooser = new Core(proxy);
			PChooser.setModName("PaintingChooser");
			PChooser.setModChannel("PChooser");
			load();
		}
	}

	private static void load() {
		//RecipeRemover.registerItemRecipeToRemove(Item.painting);
		ItemRemover.removeVanillaItem(Item.painting);
		PChooser.getProxy().preInit();
		PChooser.getProxy().registerRenderInformation();
		SlimevoidCore.console(PChooser.getModName(), "Registering items...");
		PCCore.addItems();
		SlimevoidCore.console(PChooser.getModName(), "Naming items...");
		PCCore.addNames();
		SlimevoidCore.console(PChooser.getModName(), "Registering recipes...");
		PCCore.addRecipes();
	}
}
