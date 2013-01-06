package slimevoid.paintingchooser;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

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
		RecipeRemover.registerItemRecipeToRemove(Item.painting);
		ItemRemover.removeVanillaItem(Item.painting);
		EurysCore.console(PChooser.getModName(), "Registering items...");
		PCCore.addItems();
		EurysCore.console(PChooser.getModName(), "Naming items...");
		PCCore.addNames();
		EurysCore.console(PChooser.getModName(), "Registering recipes...");
		PCCore.addRecipes();
	}

	public static Entity getEntityByID(World world, int entityId) {
		for (int i = 0; i < world.loadedEntityList
				.size(); ++i) {
			Entity entity = (Entity) world.loadedEntityList
					.get(i);

			if (entity == null) {
				return null;
			}

			if (entity.entityId == entityId) {
				return entity;
			}
		}
		return null;
	}
}
