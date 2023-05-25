package com.paneedah.weaponlib.vehicle.network;

import com.paneedah.weaponlib.ModContext;
import com.paneedah.weaponlib.compatibility.CompatibleMessageHandler;
import com.paneedah.weaponlib.vehicle.EntityVehicle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class VehicleInteractPHandler implements CompatibleMessageHandler<VehicleInteractPacket, IMessage> {
	
	public static ModContext context;

	public VehicleInteractPHandler() {
	}

	public VehicleInteractPHandler(ModContext context) {
		this.context = context;
	}

	@Override
	public <T extends net.minecraftforge.fml.common.network.simpleimpl.IMessage> T onCompatibleMessage(VehicleInteractPacket message, MessageContext messageContext) {
		if(messageContext.side == Side.SERVER) {
			messageContext.getServerHandler().player.getServer().addScheduledTask(() -> {
				EntityVehicle vehicle = (EntityVehicle) messageContext.getServerHandler().player.world.getEntityByID(message.vehicleID);
				EntityPlayer player = (EntityPlayer) messageContext.getServerHandler().player.world.getEntityByID(message.playerID);

				if(message.right) {
					if(vehicle.canFitPassenger(player)) {

						player.startRiding(vehicle);
					}
				} else {

					vehicle.setDead();


				}




			});
		}
		
		return null;
	}

}
