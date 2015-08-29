package com.CentrumGuy.CodWarfare.MySQL;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class MySQL {
	public static boolean mySQL = false;
	
	public static boolean mySQLenabled() {
		return mySQL;
	}
	
    public static Connection getConnection() {
    	if (!(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled"))) return null;
    	
    	String ip = ThisPlugin.getPlugin().getConfig().getString("MySQL.Ip");
    	String userName = ThisPlugin.getPlugin().getConfig().getString("MySQL.Username");
    	String password = ThisPlugin.getPlugin().getConfig().getString("MySQL.Password");
    	String db = ThisPlugin.getPlugin().getConfig().getString("MySQL.Database");
    	
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static ArrayList<String> stringToList(String s) {
    	ArrayList<String> list = new ArrayList<String>();
    	if (s.equals("")) return new ArrayList<String>();
    	list.addAll(Arrays.asList(s.split("±")));
    	return list;
    }
    
    public static String listToString(ArrayList<String> list) {
    	if (list == null || list.isEmpty()) return "";
    	String seperate = "";
    	StringBuilder sb = new StringBuilder();
    	
		for (String s : list) {
			sb.append(seperate);
			sb.append(s);
			seperate = "±";
		}
    	
    	return sb.toString();
    }
    
    public static Clob stringToClob(String s, Connection c) throws Exception {
    	Clob clob = c.createClob();
    	clob.setString(1, s);
    	return clob;
    }
    
    public static String clobToString(Clob c) throws Exception {
    	if (c == null) return "";
    	String s = c.getSubString(1, (int) c.length());
    	if (s == null) return "";
    	return s;
    }
    
    public static void createSecondaryShopTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODSecondaryShop(uuid varchar(36) NOT NULL, list longtext, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createPrimaryShopTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODPrimaryShop(uuid varchar(36) NOT NULL, list longtext, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createAPGTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODAPG(uuid varchar(36) NOT NULL, list longtext, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createASGTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODASG(uuid varchar(36) NOT NULL, list longtext, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createKitsTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODKits(uuid varchar(36) NOT NULL, list longtext, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createPerksTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODPerks(uuid varchar(36) NOT NULL, currentperk varchar(100), list longtext, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createAchievementsTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODAchievements(uuid varchar(36) NOT NULL, list longtext, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createScoresTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODScores(uuid varchar(36) NOT NULL, Credits int, Level int, Kills int, Deaths int, HighestKillStreak int, Exp int, NeededExp int, NeededExpBefore int, UNIQUE(uuid), PRIMARY KEY(uuid))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createClanTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODClans(clan varchar(255) NOT NULL, owner varchar(36), players longtext, admins longtext, UNIQUE(clan), PRIMARY KEY(clan))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
    
    public static void createJoinedTable() throws Exception {
    	Connection c = getConnection();
    	PreparedStatement create = c.prepareStatement("CREATE TABLE IF NOT EXISTS CODJoined(players varchar(36) NOT NULL, UNIQUE(players), PRIMARY KEY(players))");
    	create.executeUpdate();
    	create.close();
    	c.close();
    }
}