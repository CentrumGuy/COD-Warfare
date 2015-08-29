package com.CentrumGuy.CodWarfare.Commands;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.Items;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;

@SuppressWarnings("deprecation")
public class CreateGunCommand {
	
	public static HashMap<Player, Boolean> gunBuilder = new HashMap<Player, Boolean>();
	public static HashMap<Player, Integer> gunBuilderStep = new HashMap<Player, Integer>();
	public static HashMap<Player, ItemStack> Gun = new HashMap<Player, ItemStack>();
	public static HashMap<Player, ItemStack> Ammo = new HashMap<Player, ItemStack>();
	public static HashMap<Player, String> GName = new HashMap<Player, String>();
	public static HashMap<Player, String> AName = new HashMap<Player, String>();
	public static HashMap<Player, Integer> LevelUnlock = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> gunCost = new HashMap<Player, Integer>();
	public static HashMap<Player, String> type = new HashMap<Player, String>();
	public static HashMap<Player, ItemStack[]> inventory = new HashMap<Player, ItemStack[]>();
	public static HashMap<Player, GameMode> gamemode = new HashMap<Player, GameMode>();
	
	public static ItemStack primary = Items.createItem(Material.WOOL, 1, 14, "§3» §bClick to set gun to primary §3«");
	public static ItemStack secondary = Items.createItem(Material.WOOL, 1, 5, "§e» §6Click to set gun to secondary §e«");
	public static ItemStack confirm = Items.createItem(Material.INK_SACK, 1, 10, "§2» §aClick to confirm gun creation §2«");
	public static ItemStack abort = Items.createItem(Material.INK_SACK, 1, 5, "§4» §cAbort gun creation §4«");

	public static void createGun(Player p, String[] args) {
		if (args.length == 11) {
			String gunID = args[1];
			String gunData = args[2];
			String gunName = args[3];
			String ammoID = args[4];
			String ammoData = args[5];
			String ammoAmount = args[6];
			String ammoName = args[7];
			String Level = args[8];
			String Cost = args[9];
			String Type = args[10];
			
			try{
				Integer.parseInt(gunID);
				Integer.parseInt(gunData);
				Integer.parseInt(ammoID);
				Integer.parseInt(ammoData);
				Integer.parseInt(ammoAmount);
				Integer.parseInt(Level);
				Integer.parseInt(Cost);
			} catch (NumberFormatException exception) {
				p.sendMessage(Main.codSignature + "§cGun Item ID, Gun Data Value, Ammo Item ID, Ammo Data Value, Ammo Amount, Level, and Cost all have to be valid numbers");
				return;
			}
			
			Guns.loadGuns();
			
			final String displayName = "§e" + gunName;
			
			for (int i = 0 ; i < Guns.guns.size() ; i++) {
				if (Guns.guns.get(i).getItemMeta().getDisplayName().equals(displayName)) {
					p.sendMessage(Main.codSignature + "§cThe gun§4 " + gunName + " §calready exists");
					return;
				}
			}
			
			if (!(Type.equalsIgnoreCase("Primary")) && !(Type.equalsIgnoreCase("Secondary"))) p.sendMessage(Main.codSignature + "§cInvalid type. Try §ePrimary §cor §eSecondary");
			
			if (Type.equalsIgnoreCase("Primary")) Type = "Primary";
			if (Type.equalsIgnoreCase("Secondary")) Type = "Secondary";
				
			ItemStack gun = new ItemStack(Integer.parseInt(gunID));
			ItemMeta gunMeta = gun.getItemMeta();
			gunMeta.setDisplayName(gunName);
			gun.setItemMeta(gunMeta);
		
			ItemStack ammo = new ItemStack(Integer.parseInt(ammoID), Integer.parseInt(ammoAmount));
			ItemMeta ammoMeta = ammo.getItemMeta();
			ammoMeta.setDisplayName(ammoName);
			ammo.setItemMeta(ammoMeta);
		
			Guns.saveGun(gun, Integer.parseInt(gunData), ammo, Integer.parseInt(ammoData), Integer.parseInt(Cost), Integer.parseInt(Level), Type);
		
			p.sendMessage(Main.codSignature + "§bGun Created!");
		}else{
			p.sendMessage(Main.codSignature + "§ePlease type §b/cod creategun §6[Gun Item ID] [Gun Data Value] [Gun Name] [Ammo Item ID] [Ammo Data Value] [Ammo Amount] [Ammo Name] [Level Unlock] [Gun Cost] [§3Primary§6|§3Secondary§6]");
		}
	}
	
	public static void createAutoGun(final Player p) {
		if (CreateArenaCommand.creatingArena.get(p) == true) {
			p.sendMessage(Main.codSignature + "§cPlease finish creating your arena first");
			return;
		}
		
		gunBuilder.put(p, true);
		p.sendMessage(Main.codSignature + "§7You entered gun creator mode. Type §f/cod guncreator§7 to abort creation");
		p.sendMessage("§21. §aPut the gun item in your hand and right-click");
		
		gunBuilderStep.put(p, 1);
		
		gamemode.put(p, p.getGameMode());
		p.setGameMode(GameMode.CREATIVE);
		
		BukkitRunnable br = new BukkitRunnable() {
			public void run() {
				if (gunBuilder.get(p)) {
					SendCoolMessages.sendOverActionBar(p, "§7You are in gun creator mode. Type §f/cod guncreator§7 to leave");
				}
			}
		};
		
		br.runTaskTimer(ThisPlugin.getPlugin(), 0L, 30L);
	}
	
	public static void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (gunBuilder.get(p) == true) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getItem() == null || e.getItem().getType() == Material.AIR) return;
				if (gunBuilderStep.get(p) == 1) {
					e.setCancelled(true);
					ItemStack gun = new ItemStack(p.getItemInHand().getType(), 1, p.getItemInHand().getData().getData());
					Gun.put(p, gun);
					p.sendMessage(" §f» §8Set gun item to§7 " + gun.getType());
					p.sendMessage("§22. §aPut the ammo in your hand and right-click");
					gunBuilderStep.put(p, 2);
				}else if (gunBuilderStep.get(p) == 2) {
					e.setCancelled(true);
					ItemStack ammo = new ItemStack(p.getItemInHand().getType(), p.getItemInHand().getAmount(), p.getItemInHand().getData().getData());
					Ammo.put(p, ammo);
					//if (gamemode.get(p) != null) p.setGameMode(gamemode.get(p));
					p.sendMessage(" §f» §8Set ammo to§7 " + ammo.getType());
					p.sendMessage("§23. §aNow type the name of the gun in the chat");
					gunBuilderStep.put(p, 3);
				}else if (gunBuilderStep.get(p) == 7) {
					e.setCancelled(true);
					if ((e.getItem().getType().equals(Material.WOOL)) && (e.getItem().getData().getData() == (byte) 14)) {
						type.put(p, "primary");
						p.getInventory().clear();
						p.getInventory().setItem(1, confirm);
						p.getInventory().setItem(7, abort);
						p.getInventory().setHeldItemSlot(4);
						
						p.sendMessage(" §f» §8Set gun type to §7primary");
						p.sendMessage("§28. §aNow right-click the green dye to confirm gun creation or right-click the purple dye to cancel gun creation");
						
						p.updateInventory();
						gunBuilderStep.put(p, 8);
					}else if ((e.getItem().getType().equals(Material.WOOL)) && (e.getItem().getData().getData() == (byte) 5)) {
						type.put(p, "secondary");
						p.getInventory().clear();
						p.getInventory().setItem(1, confirm);
						p.getInventory().setItem(7, abort);
						p.getInventory().setHeldItemSlot(4);
						
						p.sendMessage(" §f» §8Set gun type to §7secondary");
						p.sendMessage("§28. §aNow right-click the green dye to confirm gun creation or right-click the purple dye to cancel gun creation");
						
						p.updateInventory();
						gunBuilderStep.put(p, 8);
					}
				}else if (gunBuilderStep.get(p) == 8) {
					e.setCancelled(true);
					if (e.getItem().getType().equals(Material.INK_SACK) && (e.getItem().getData().getData() == (byte) 10)) {
						p.sendMessage(" §e» §6Finalizing gun creation...");
						if (!(makeAutoGun(p))) {
							p.sendMessage("§cGun creation failed");
						}else{
							p.sendMessage(" §b» §3Successfully created gun!");
						}
						endGunCreation(p, false);
					}else if (e.getItem().getType().equals(Material.INK_SACK) && (e.getItem().getData().getData() == (byte) 5)) {
						endGunCreation(p, true);
					}
				}
			}
		}else{
			return;
		}
	}
	
	public static boolean makeAutoGun(Player p) {
		if (gunBuilder.get(p) == true) {			
			Guns.loadGuns();
			
			final String displayName = "§e" + GName.get(p);
			
			for (int i = 0 ; i < Guns.guns.size() ; i++) {
				if (Guns.guns.get(i).getItemMeta().getDisplayName().equals(displayName)) {
					p.sendMessage("§cThe gun§4 " + GName.get(p) + " §calready exists");
					return false;
				}
			}
			
			String Type = type.get(p);
			int Cost = gunCost.get(p);
			int Level = LevelUnlock.get(p);
			
			if (!(Type.equalsIgnoreCase("Primary")) && !(Type.equalsIgnoreCase("Secondary"))) p.sendMessage(Main.codSignature + "§cInvalid type. Try §ePrimary §cor §eSecondary");
			
			if (Type.equalsIgnoreCase("Primary")) Type = "Primary";
			if (Type.equalsIgnoreCase("Secondary")) Type = "Secondary";
				
			ItemStack gun = Gun.get(p);
			ItemMeta gunMeta = gun.getItemMeta();
			gunMeta.setDisplayName(GName.get(p));
			gun.setItemMeta(gunMeta);
		
			ItemStack ammo = Ammo.get(p);
			ItemMeta ammoMeta = ammo.getItemMeta();
			ammoMeta.setDisplayName(AName.get(p));
			ammo.setItemMeta(ammoMeta);
		
			Guns.saveGun(gun, (int) Gun.get(p).getData().getData(), ammo, (int) Ammo.get(p).getData().getData(), Cost, Level, Type);
		
			return true;
		}
		
		return false;
	}
	
	public static void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
 		Player p = (Player) e.getWhoClicked();
		if (gunBuilder.get(p) == true) {
			if (gunBuilderStep.get(p) >= 7) {
				e.setCancelled(true);
			}
		}
	}
	
	public static void endGunCreation(Player p, Boolean abortMessage) {
		if (gunBuilder.get(p) == true) {
			p.getInventory().clear();
			gunBuilder.put(p, false);
			gunBuilderStep.put(p, 0);
			if (inventory.get(p) != null) p.getInventory().setContents(inventory.get(p));
			if (gamemode.get(p) != null) p.setGameMode(gamemode.get(p));
			gamemode.put(p, null);
			inventory.put(p, null);
			p.updateInventory();
			if (abortMessage) p.sendMessage("§cGun creation canceled");
			p.sendMessage(Main.codSignature + "§7You left gun creator");
		}
	}
	
	public static void onChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		if (gunBuilder.get(p) == true) {
			if (gunBuilderStep.get(p) == 3) {
				e.setCancelled(true);
				//String Name = StringUtils.remove(e.getMessage(), ' ');
				GName.put(p, e.getMessage());
				p.sendMessage(" §f» §8Set gun name to§7 " + e.getMessage());
				p.sendMessage("§24. §aNow type the name of the ammo in the chat");
				gunBuilderStep.put(p, 4);
			}else if (gunBuilderStep.get(p) == 4) {
				e.setCancelled(true);
				//String Name = StringUtils.remove(e.getMessage(), ' ');
				AName.put(p, e.getMessage());
				p.sendMessage(" §f» §8Set ammo name to§7 " + e.getMessage());
				p.sendMessage("§25. §aNow type the cost of the gun in the chat");
				gunBuilderStep.put(p, 5);
			}else if (gunBuilderStep.get(p) == 5) {
				e.setCancelled(true);
				Integer cost = 0;
				try {
					String message = StringUtils.remove(e.getMessage(), ' ');
					cost = Integer.parseInt(message);
				} catch (NumberFormatException exeption) {
					p.sendMessage("§cGun cost must be a valid number. Try again");
					return;
				}
				
				gunCost.put(p, cost);
				p.sendMessage(" §f» §8Set gun cost to§7 " + cost);
				p.sendMessage("§26. §aNow type the level at which the gun will be unlocked in the chat");
				gunBuilderStep.put(p, 6);
			}else if (gunBuilderStep.get(p) == 6) {
				e.setCancelled(true);
				Integer level = 0;
				try {
					String message = StringUtils.remove(e.getMessage(), ' ');
					level = Integer.parseInt(message);
				} catch (NumberFormatException exception) {
					p.sendMessage("§cUnlock level must be a valid number. Try again");
					return;
				}
				
				LevelUnlock.put(p, level);
				p.sendMessage(" §f» §8Set gun level unlock to §7 " + level);
				p.sendMessage("§27. §aNow right-click the red wool in your inventory to make the gun primary, or right-click the green wool in you inventory to make the gun secondary");
				gunBuilderStep.put(p, 7);
				
				inventory.put(p, p.getInventory().getContents());
				p.getInventory().clear();
				p.getInventory().setItem(1, primary);
				p.getInventory().setItem(7, secondary);
				p.getInventory().setHeldItemSlot(4);
			}
		}
	}
}