package slimevoid.paintingchooser;

import java.io.File;

import slimevoid.lib.ICommonProxy;
import slimevoid.paintingchooser.entity.EntityPaintings;
import slimevoid.paintingchooser.items.ItemPaintings;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


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
	public static Item itemPaintings;
	public static String setPaintingCommand = "SETPAINTING";
	public static String paintingUpdateCommand = "UPDATEPAINTING";
	public static String firstUpdateCommand = "FIRSTUPDATE";

	public static void initialize(ICommonProxy proxy) {
		PCInit.initialize(proxy);
	}

	public static void addItems() {
		entityPaintingsID = configurationProperties();
		EntityRegistry.registerGlobalEntityID(EntityPaintings.class,
				"Choosable Painting", entityPaintingsID);
		itemPaintings = (new ItemPaintings(itemPaintingsID - 256,
				EntityPaintings.class)).setIconCoord(10, 1).setItemName(
				"painting");
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
		entityPaintingsID = Integer.parseInt(configuration.get(
				Configuration.CATEGORY_GENERAL, "entityPaintingsID",
				ModLoader.getUniqueEntityId()).value);
		itemPaintingsID = Integer.parseInt(configuration.get(
				Configuration.CATEGORY_ITEM, "itemPaintingsID",
				Item.painting.itemID).value);
		configuration.save();
		return entityPaintingsID;
	}

	// Get the entity with the given entity ID
	@SideOnly(Side.CLIENT)
	public static Entity getEntityByID(int i) {
		if (i == ModLoader.getMinecraftInstance().thePlayer.entityId) {
			return ModLoader.getMinecraftInstance().thePlayer;
		} else {
			for (int j = 0; j < ModLoader.getMinecraftInstance().theWorld.loadedEntityList
					.size(); j++) {
				Entity entity = (Entity) ModLoader.getMinecraftInstance().theWorld.loadedEntityList
						.get(j);
				if (entity == null) {
					return null;
				}
				if (entity.entityId == i) {
					return entity;
				}
			}
		}
		return null;
	}
}
