package slimevoid.paintingchooser;

import java.io.File;
import java.util.ArrayList;

import slimevoid.paintingchooser.client.RenderPaintings;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;

import eurysmods.api.ICommonProxy;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;

public class PCCore {

	public final static String version = "2.0.0.0";
	public static File configFile = new File(PCInit.minecraftDir,
			"config/PaintingChooser.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int entityPaintingsID, itemPaintingsID;
	public static Entity entityPaintings;
	public static Item itemPaintings;

	public static void initialize(ICommonProxy proxy) {
		PCInit.initialize(proxy);
	}
	
	public static void addItems() {
		entityPaintingsID = configurationProperties();
		//EntityRegistry.registerModEntity(EntityPaintings.class, "Choosable Painting", entityPaintingsID, PaintingChooser.instance, 250, 1, false);
		EntityRegistry.registerGlobalEntityID(EntityPaintings.class, "Choosable Painting", entityPaintingsID);
		RenderingRegistry.registerEntityRenderingHandler(EntityPaintings.class, new RenderPaintings());
		itemPaintings = (new ItemPaintings(itemPaintingsID - 256, EntityPaintings.class)).setIconCoord(10, 1).setItemName("painting");
	}

	public static void addNames() {
		ModLoader.addName(itemPaintings, "Painting");
	}

	public static void addRecipes() {
        ModLoader.addRecipe(new ItemStack(itemPaintings, 1), new Object[] {"###", "#X#", "###", '#', Item.stick, 'X', Block.cloth});
	}
	
	public static int configurationProperties() {
		configuration.load();
		entityPaintingsID = Integer.parseInt(configuration
				.get(Configuration.CATEGORY_GENERAL, "entityPaintingsID", ModLoader.getUniqueEntityId()).value);
		itemPaintingsID = Integer.parseInt(configuration
				.get(Configuration.CATEGORY_ITEM, "itemPaintingsID", Item.painting.shiftedIndex).value);
		configuration.save();
		return entityPaintingsID;
	}
}
