package slimevoid.paintingchooser.network.packets;

import java.util.ArrayList;

import net.minecraft.util.EnumArt;

import eurysmods.network.packets.core.PacketIds;
import eurysmods.network.packets.core.PacketPayload;

import slimevoid.paintingchooser.EntityPaintings;

public class PacketPaintingGui extends PacketPainting {

	public PacketPaintingGui() {
		super(PacketIds.GUI);
	}

	public PacketPaintingGui(EntityPaintings entitypaintings, ArrayList artList) {
		this();
		this.payload = new PacketPayload(2, 0, artList.size(), 0);
		this.xPosition = entitypaintings.xPosition;
		this.yPosition = entitypaintings.yPosition;
		this.zPosition = entitypaintings.zPosition;
		this.setEntityId(entitypaintings.entityId);
		this.setKillCode(0);
		this.setArtList(artList);
		this.isChunkDataPacket = true;
	}

	public PacketPaintingGui(EntityPaintings entitypaintings, int i) {
		this();
		this.payload = new PacketPayload(2, 0, 0, 0);
		this.xPosition = entitypaintings.xPosition;
		this.yPosition = entitypaintings.yPosition;
		this.zPosition = entitypaintings.zPosition;
		this.setEntityId(entitypaintings.entityId);
		this.setKillCode(i);
		this.isChunkDataPacket = true;
	}

	public void setEntityId(int entityId) {
		this.payload.setIntPayload(0, entityId);
	}

	private void setKillCode(int i) {
		this.payload.setIntPayload(1, i);
	}
	
	public int getEntityId() {
		return this.payload.getIntPayload(0);
	}
	
	public int getKillCode() {
		return this.payload.getIntPayload(1);
	}
	
	public void setArtList(ArrayList artList) {
		for (int i = 0; i < artList.size(); i++) {
			EnumArt art = (EnumArt)artList.get(i);
			this.payload.setStringPayload(i, art.title);
		}
	}
	
	public ArrayList getArtList() {
		ArrayList artList = new ArrayList();
		EnumArt[] art = EnumArt.values();
		for (int i = 0; i < this.payload.getStringSize(); i++) {
			for (int j = 0; j < art.length; j++) {
				if (this.payload.getStringPayload(i).equals(art[j].title)) {
					artList.add(art[j]);
				}
			}
		}
		return artList;
	}
}