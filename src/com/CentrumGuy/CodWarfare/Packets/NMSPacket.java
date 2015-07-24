package com.CentrumGuy.CodWarfare.Packets;
 
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
 
public class NMSPacket {
 
    private static String packageName = Bukkit.getServer().getClass().getPackage().getName();
    private static String version = packageName.substring(packageName.lastIndexOf(".") + 1);
 
    private Object packet;
    private Class<?> nmsPacket;
 
    public NMSPacket(String packetName){
        try {
            nmsPacket = Class.forName("net.minecraft.server." + version + "." + packetName);
            packet = nmsPacket.newInstance();
        } catch (Exception e){
            e.printStackTrace();
            packet = null;
            nmsPacket = null;
        }
    }
    
    public void setField(String fieldName, Object value){
        try {
            Field f = packet.getClass().getField(fieldName);
            f.setAccessible(true);
            f.set(packet, value);
            f.setAccessible(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
 
    public void setDeclaredField(String fieldName, Object value){
        try {
            Field f = packet.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(packet, value);
            f.setAccessible(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
 
    public Object getField(String fieldName){
        try {
            Field f = packet.getClass().getField(fieldName);
            f.setAccessible(true);
            Object s = f.get(packet);
            f.setAccessible(false);
            return s;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
 
    public Object getDeclaredField(String fieldName){
        try {
            Field f = packet.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            Object s = f.get(packet);
            f.setAccessible(false);
            return s;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
 
    public Class<?> getPacketClass(){
        return nmsPacket;
    }
 
    public Object getPacket(){
        return packet;
    }
 
    public boolean isUsable(){
        return packet != null;
    }
    
    public static void sendPacket(Player plyr, Object o){
        try {
            Class<?> packet = Class.forName("net.minecraft.server." + version + ".Packet");
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
 
            if(!packet.isAssignableFrom(o.getClass())){
                throw new IllegalArgumentException("Object o wasn't a packet!");
            }
 
            Object cp = craftPlayer.cast(plyr);
            Object handle = craftPlayer.getMethod("getHandle").invoke(cp);
            Object con = handle.getClass().getField("playerConnection").get(handle);
            con.getClass().getMethod("sendPacket", packet).invoke(con, o);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
 
    public static void sendPackets(Object o){
        try {
            Class<?> packet = Class.forName("net.minecraft.server." + version + ".Packet");
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            for (Player plyr : Bukkit.getOnlinePlayers()){
                if(!packet.isAssignableFrom(o.getClass())){
                    throw new IllegalArgumentException("Object o wasn't a packet!");
                }
 
                Object cp = craftPlayer.cast(plyr);
                Object handle = craftPlayer.getMethod("getHandle").invoke(cp);
                Object con = handle.getClass().getField("playerConnection").get(handle);
                con.getClass().getMethod("sendPacket", packet).invoke(con, o);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
 
}