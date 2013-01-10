package slimevoid.paintingchooser.items;

import java.util.ArrayList;

import slimevoid.paintingchooser.api.IPCCommonProxy;
import slimevoid.paintingchooser.core.PCInit;
import slimevoid.paintingchooser.entity.EntityPaintings;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemPaintings extends ItemHangingEntity {
	private Class paintingClass;

	public ItemPaintings(int par1, Class entityClass) {
		super(par1, entityClass);
		this.paintingClass = entityClass;
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer,
			World world, int x, int y, int z, int side, float a, float b,
			float c) {
		if (side == 0) {
			return false;
		} else if (side == 1) {
			return false;
		} else {
			int var11 = Direction.vineGrowth[side];
			EntityHanging painting = this.createHangingEntity(world,
					entityplayer, x, y, z, var11);

			if (!entityplayer.canPlayerEdit(x, y, z, side, itemstack)) {
				return false;
			} else {
				if (painting != null && painting.onValidSurface()) {
					if (!world.isRemote) {
						world.spawnEntityInWorld(painting);
						if (EntityPaintings.playerArt.containsKey(entityplayer)) {
							ArrayList art = EntityPaintings.playerArt
									.get(entityplayer);
							if (art.size() > 0) {
								((IPCCommonProxy) PCInit.PChooser.getProxy())
										.displayEntityGui(world, entityplayer,
												painting,
												EntityPaintings.playerArt
														.get(entityplayer));
							}
						}
					}

					--itemstack.stackSize;
				}

				return true;
			}
		}
	}

	private EntityHanging createHangingEntity(World world,
			EntityPlayer entityplayer, int x, int y, int z, int side) {
		return this.paintingClass == EntityPaintings.class ? new EntityPaintings(
				world, entityplayer, x, y, z, side) : null;
	}
}
