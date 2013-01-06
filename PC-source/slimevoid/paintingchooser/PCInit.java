package slimevoid.paintingchooser;

import net.minecraft.item.Item;
import eurysmods.api.ICommonProxy;
import eurysmods.api.ICore;
import eurysmods.core.Core;
import eurysmods.core.EurysCore;
import eurysmods.core.EurysMods;
import eurysmods.core.ItemRemover;
import eurysmods.core.RecipeRemover;

public class PCInit {

	public static String minecraftDir = EurysMods.proxy.getMinecraftDir();
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
		PChooser.getProxy().preInit();
		RecipeRemover.registerItemRecipeToRemove(Item.painting);
		ItemRemover.removeVanillaItem(Item.painting);
		PChooser.getProxy().registerRenderInformation();
		EurysCore.console(PChooser.getModName(), "Registering items...");
		PCCore.addItems();
		EurysCore.console(PChooser.getModName(), "Naming items...");
		PCCore.addNames();
		EurysCore.console(PChooser.getModName(), "Registering recipes...");
		PCCore.addRecipes();
	}
}
