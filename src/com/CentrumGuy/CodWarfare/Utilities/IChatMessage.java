package com.CentrumGuy.CodWarfare.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class IChatMessage {
	
	/**
	 * Author: CentrumGuy
	 * For ChatColor use this symbol: §
	 */

	private String ichat;
	private ArrayList<String> loreList;
	
	public IChatMessage(String normal, String Message) {
		ichat = "{\"text\":\"" + normal + "\", \"extra\":[{\"text\":\"" + Message + "\"";
		//ichat = "{\"text\":\"" + Message + "\"";
		loreList = new ArrayList<String>();
	}
	
	public IChatMessage addLoreLine(String lore) {
		loreList.add(lore);
		return this;
	}
	
	public IChatMessage setLoreLines(ArrayList<String> lines) {
		if (lines == null) return this;
		if (!(lines.isEmpty())) {
			loreList = lines;
		}
		
		return this;
	}
	
	public IChatMessage setLoreLine(int index, String message) {
		if (loreList.size() > index) {
			loreList.set(index, message);
		}
		return this;
	}
	
	public IChatMessage addCommand(String command) {
		ichat = ichat + ", \"clickEvent\":{\"action\":\"run_command\", \"value\":\"" + command + "\"}";
		return this;
	}
	
	public IChatMessage addLink(String link) {
		ichat = ichat + ", \"clickEvent\":{\"action\":\"open_url\", \"value\":\"" + link + "\"}";
		return this;
	}
	
	public IChatMessage addSuggest(String suggestCommand) {
		ichat = ichat + ", \"clickEvent\":{\"action\":\"suggest_command\", \"value\":\"" + suggestCommand + "\"}";
		return this;
	}
	
	public IChatMessage removeLoreLine(String loreLine) {
		if (loreList.contains(loreLine)) loreList.remove(loreLine);
		return this;
	}
	
	public String getIchat() {
		if (!(ichat.endsWith("}]}"))) return ichat + "}]}";
		return ichat;
	}
	
	private String getLore() {
		if (loreList != null && !loreList.isEmpty()) {
			String l = "";
			for (int i = 0 ; i < loreList.size() ; i++) {
				String s = loreList.get(i);
				if (i != (loreList.size() - 1)) {
					s = s + "§r\n§r";
					l = l + s;
				}else{
					s = s + "§r";
					l = l + s;
				}
			}
			
			return ",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"" + l + "\"}";
		}
		
		return "";
	}
	
	private void finish() {
		if (!(ichat.endsWith(getLore() + "}]}"))) {
			ichat = ichat + getLore();
			ichat = ichat + "}]}";
		}
	}
	
	public IChatMessage send(ArrayList<Player> pls) {
		if (pls.isEmpty()) return this;
		finish();
		
		PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
	    chat.getChatComponents().write(0, WrappedChatComponent.fromJson(ichat));
		
		for (Player p : pls) {
		    try {
		        ProtocolLibrary.getProtocolManager().sendServerPacket(p, chat);
		    } catch (InvocationTargetException e) {
		        throw new IllegalStateException("Unable to send packet " + chat, e);
		    }
		}
		
		return this;
	}
	
	public IChatMessage send(Player p) {
		finish();
		PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
	    chat.getChatComponents().write(0, WrappedChatComponent.fromJson(ichat));
		
	    try {
	        ProtocolLibrary.getProtocolManager().sendServerPacket(p, chat);
	    } catch (InvocationTargetException e) {
	        throw new IllegalStateException("Unable to send packet " + chat, e);
	    }
		
	    return this;
	}
	
	public IChatMessage Broadcast() {
		finish();
		/*IChatBaseComponent comp = ChatSerializer.a(ichat);
		
		if (!(Bukkit.getOnlinePlayers().isEmpty())) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				PacketPlayOutChat packet = new PacketPlayOutChat(comp);
			    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			}
		}*/
		
		PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
	    chat.getChatComponents().write(0, WrappedChatComponent.fromJson(ichat));
	    
	    if (!(Bukkit.getOnlinePlayers().isEmpty())) {
			for (Player p : Bukkit.getOnlinePlayers()) {
			    try {
			        ProtocolLibrary.getProtocolManager().sendServerPacket(p, chat);
			    } catch (InvocationTargetException e) {
			        throw new IllegalStateException("Unable to send packet " + chat, e);
			    }
			}
	    }
		
		return this;
	}
}
