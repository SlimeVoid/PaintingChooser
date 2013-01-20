package slimevoid.paintingchooser.network.packets;

import net.minecraft.entity.item.EntityPainting;
import slimevoid.lib.network.PacketIds;
import slimevoid.lib.network.PacketPayload;

public class PacketUpdatePainting extends PacketPainting {

	public PacketUpdatePainting() {
		super(PacketIds.UPDATE);
	}

	public PacketUpdatePainting(EntityPainting entitypaintings) {
		this();

		this.payload = new PacketPayload(3, 0, 1, 0);
		this.xPosition = entitypaintings.xPosition;
		this.yPosition = entitypaintings.yPosition;
		this.zPosition = entitypaintings.zPosition;
		this.setEntityId(entitypaintings.entityId);
		this.setDirection(entitypaintings.hangingDirection);
		this.isChunkDataPacket = true;
	}

	public PacketUpdatePainting(EntityPainting entitypaintings, String command) {
		this(entitypaintings);
		this.setCommand(command);
	}

	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
	}

	public void setEntityId(int entityId) {
		this.payload.setIntPayload(0, entityId);
	}

	public void setArtConstant(int enumValue) {
		this.payload.setIntPayload(1, enumValue);
	}

	public void setDirection(int direction) {
		this.payload.setIntPayload(2, direction);
	}

	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	public int getEntityId() {
		return this.payload.getIntPayload(0);
	}

	public int getArtConstant() {
		return this.payload.getIntPayload(1);
	}

	public int getDirection() {
		return this.payload.getIntPayload(2);
	}
}
