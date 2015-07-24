package com.CentrumGuy.CodWarfare.Utilities;

/*import net.minecraft.server.v1_8_R1.PacketPlayOutResourcePackSend;

//import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;*/


public class AutoResourcePack {

	/*public void onPacketSending(PacketEvent event) {
		String channel = event.getPacket().getStrings().read(0);
                System.out.println(channel);
        }

	public void onPacketReceiving(PacketEvent event) {
		String channel = event.getPacket().getStrings().read(0);
                System.out.println(channel);
        }


	public static void sendPack(final Player p, final String url) {
		
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutResourcePackSend(url, "3ecafccdb061c34e3e0719583e3e388801acbc1f"));
            
            /*Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
            	public void run() {
            		PacketContainer container = new PacketContainer(PacketType.Play.Client.CUSTOM_PAYLOAD);
            		container.getStrings().write(0, "3ecafccdb061c34e3e0719583e3e388801acbc1f");
            		container.getIntegers().write(0, 3);
            		
            		try {
						ProtocolLibrary.getProtocolManager().sendServerPacket(p, container);
					} catch (InvocationTargetException e) {
						throw new IllegalStateException("Unable to send packet " + container);
					}
            	}
            }, 10L);*/
            
            
            //((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|RPack", url.getBytes()));
    		/*Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
			public void run() {
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|RPack", url.getBytes(Charsets.UTF_8)));
			}
		} , 10L);*/
}