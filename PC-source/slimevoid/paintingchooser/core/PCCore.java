package slimevoid.paintingchooser.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import slimevoid.paintingchooser.entity.EntityPaintings;
import slimevoid.paintingchooser.items.ItemPaintings;
import cpw.mods.fml.common.registry.EntityRegistry;

public class PCCore {

	public static File configFile = new File(PCInit.minecraftDir,
			"config/PaintingChooser.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int entityPaintingsID, itemPaintingsID;
	public static Item itemPaintings;

	public static void initialize() {
		PCInit.initialize();
	}

	public static void addItems() {
		entityPaintingsID = configurationProperties();
		EntityRegistry.registerGlobalEntityID(EntityPaintings.class,
				"Choosable Painting", entityPaintingsID);
		itemPaintings = (new ItemPaintings(itemPaintingsID - 256,
				EntityPaintings.class)).setUnlocalizedName(
				"painting").func_111206_d("painting");
	}

	public static void addNames() {
		ModLoader.addName(itemPaintings, "Painting");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemPaintings, 1), new Object[] {
				"###", "#X#", "###", '#', Item.stick, 'X', Block.cloth });
	}

	public static int configurationProperties() {
		configuration.load();
		entityPaintingsID = configuration.get(
				Configuration.CATEGORY_GENERAL, "entityPaintingsID",
				ModLoader.getUniqueEntityId()).getInt();
		itemPaintingsID = configuration.get(
				Configuration.CATEGORY_ITEM, "itemPaintingsID",
				Item.painting.itemID).getInt();
		configuration.save();
		return entityPaintingsID;
	}
}
