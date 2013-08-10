package slimevoid.paintingchooser.items;

import java.util.ArrayList;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import slimevoid.paintingchooser.api.IPCCommonProxy;
import slimevoid.paintingchooser.core.PaintingChooser;
import slimevoid.paintingchooser.entity.EntityPaintings;

public class ItemPaintings extends ItemHangingEntity {
	private Class paintingClass;

	public ItemPaintings(int par1, Class entityClass) {
		super(par1, entityClass);
		this.paintingClass = entityClass;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer,
			World world, int x, int y, int z, int side, float a, float b,
			float c) {
		if (side == 0) {
			return false;
		} else if (side == 1) {
			return false;
		} else {
			int direction = Direction.facingToDirection[side];
			EntityHanging painting = this.createHangingEntity(world,
					entityplayer, x, y, z, direction);

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
								((IPCCommonProxy) PaintingChooser.proxy)
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
