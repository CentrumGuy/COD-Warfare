package com.CentrumGuy.CodWarfare.Interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.OtherLoadout.Lethal;
import com.CentrumGuy.CodWarfare.OtherLoadout.Perk;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.OtherLoadout.Tactical;
import com.CentrumGuy.CodWarfare.Utilities.Items;

public class ItemsAndInventories {

	public static Inventory MainInventory = Bukkit.getServer().createInventory(null, 36, "Gun Menu");
	
	public static ItemStack shopItem = new ItemStack(Material.MAP);
	public static ItemStack selectionItem = new ItemStack(Material.CHEST);
	public static ItemStack backArrow = new ItemStack(Material.LAVA_BUCKET);
	public static ItemStack downArrow = new ItemStack(Material.WATER_BUCKET);
	public static ItemStack information = new ItemStack(Material.EMPTY_MAP);
	public static ItemStack exit = new ItemStack(Material.BED);
	public static ItemStack backShop = new ItemStack(Material.LAVA_BUCKET);
	public static ItemStack backAG = new ItemStack(Material.LAVA_BUCKET);
	
	public static ItemStack buyPGuns = new ItemStack(Material.STONE_HOE);
	public static ItemStack buySGuns = new ItemStack(Material.STONE_SPADE);
	
	public static HashMap<Player, Inventory> ClassSelection = new HashMap<Player, Inventory>();
	public static HashMap<Player, Inventory> ShopMainMenu = new HashMap<Player, Inventory>();
	public static HashMap<Player, Inventory> lethals = new HashMap<Player, Inventory>();
	public static HashMap<Player, Inventory> tacticals = new HashMap<Player, Inventory>();
	public static HashMap<Player, Inventory> perks = new HashMap<Player, Inventory>();
	
	public static HashMap<Player, ItemStack> PGun = new HashMap<Player, ItemStack>();
	public static HashMap<Player, ItemStack> SGun = new HashMap<Player, ItemStack>();
	
	public static ItemStack none = new ItemStack(Material.EMERALD);
	
	public static void setUp() {
		ItemMeta shopMeta = shopItem.getItemMeta();
		shopMeta.setDisplayName("§4§lBuy Guns");
		shopItem.setItemMeta(shopMeta);
		
		ItemMeta selectMeta = selectionItem.getItemMeta();
		selectMeta.setDisplayName("§4§lClass Selection");
		selectionItem.setItemMeta(selectMeta);
		
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName("§dClose Inventory");
		exit.setItemMeta(exitMeta);
		
		MainInventory.setItem(10, selectionItem);
		MainInventory.setItem(16, shopItem);
		MainInventory.setItem(27, exit);
		MainInventory.setItem(35, exit);
		
		//=====================================================
		
		ItemMeta buyPGunsMeta = buyPGuns.getItemMeta();
		buyPGunsMeta.setDisplayName("§4§lBuy Primary Guns");
		buyPGuns.setItemMeta(buyPGunsMeta);
		
		ItemMeta buySGunsMeta = buyPGuns.getItemMeta();
		buySGunsMeta.setDisplayName("§4§lBuy Secondary Guns");
		buySGuns.setItemMeta(buySGunsMeta);
		
		//=====================================================
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemMeta backMeta = backArrow.getItemMeta();
		backMeta.setDisplayName("§7§l« Back");
		lore.add("§7Back to Main Menu");
		backMeta.setLore(lore);
		backArrow.setItemMeta(backMeta);
		
		lore.clear();
		
		ItemMeta backShopMeta = backShop.getItemMeta();
		backShopMeta.setDisplayName("§7§l« Back");
		lore.add("§7Back to buy guns");
		backShopMeta.setLore(lore);
		backShop.setItemMeta(backShopMeta);
		
		lore.clear();
		
		ItemMeta AGmeta = backAG.getItemMeta();
		AGmeta.setDisplayName("§7§l« Back");
		lore.add("§7Back to class selection");
		AGmeta.setLore(lore);
		backAG.setItemMeta(AGmeta);
		
		//=====================================================
		
	}
	
	public static void setUpPlayer(Player p) {
		ClassSelection.put(p, Bukkit.getServer().createInventory(p, 54, "Class Selection"));
		
		Inventory inv = ClassSelection.get(p);
		
		ItemMeta downMeta = downArrow.getItemMeta();
		
		downMeta.setDisplayName("§7Primary");
		downArrow.setItemMeta(downMeta);
		inv.setItem(18, downArrow);
		
		downMeta.setDisplayName("§7Secondary");
		downArrow.setItemMeta(downMeta);
		inv.setItem(20, downArrow);
		
		downMeta.setDisplayName("§7Lethal");
		downArrow.setItemMeta(downMeta);
		inv.setItem(22, downArrow);
		
		downMeta.setDisplayName("§7Tactical");
		downArrow.setItemMeta(downMeta);
		inv.setItem(24, downArrow);
		
		downMeta.setDisplayName("§7Perk");
		downArrow.setItemMeta(downMeta);
		inv.setItem(26, downArrow);
		
		inv.setItem(0, backArrow);
		
		//===================================================
		
		ShopMainMenu.put(p, Bukkit.getServer().createInventory(null, 45, "Shop"));
		
		ShopMainMenu.get(p).setItem(19, buyPGuns);
		ShopMainMenu.get(p).setItem(25, buySGuns);
		ShopMainMenu.get(p).setItem(0, backArrow);
		
		//===================================================
		
		setWeaponInvs(p, WeaponType.BOTH);
		setPerkInv(p);
		
	}
	
	public enum WeaponType {
		TACTICAL, LETHAL, BOTH
	}
	
	public static void setPerkInv(Player p) {
		PerkAPI.loadOwnedPerks(p);
		PerkAPI.loadPerk(p);
		
		perks.put(p, Bukkit.getServer().createInventory(null, 45, "Perks"));
		Inventory inv = perks.get(p);
		
		inv.setItem(22, PerkAPI.noPerk);
		
		if (PerkAPI.ownsPerk(p, Perk.SPEED)) {
			inv.setItem(18, PerkAPI.ownsSpeed);
		}else{
			inv.setItem(18, PerkAPI.Speed);
		}
		
		if (PerkAPI.ownsPerk(p, Perk.MARATHON)) {
			inv.setItem(20, PerkAPI.ownsMarathon);
		}else{
			inv.setItem(20, PerkAPI.Marathon);
		}
		
		if (PerkAPI.ownsPerk(p, Perk.SCAVENGER)) {
			inv.setItem(24, PerkAPI.ownsScavenger);
		}else{
			inv.setItem(24, PerkAPI.Scavenger);
		}
		
		if (PerkAPI.ownsPerk(p, Perk.HARDLINE)) {
			inv.setItem(26, PerkAPI.ownsHardline);
		}else{
			inv.setItem(26, PerkAPI.Hardline);
		}
		
		if (PerkAPI.isUsingPerk(p)) {
			if (PerkAPI.getPerk(p) == Perk.SPEED) inv.setItem(9, PerkAPI.downArrow);
			if (PerkAPI.getPerk(p) == Perk.MARATHON) inv.setItem(11, PerkAPI.downArrow);
			if (PerkAPI.getPerk(p) == Perk.SCAVENGER) inv.setItem(15, PerkAPI.downArrow);
			if (PerkAPI.getPerk(p) == Perk.HARDLINE) inv.setItem(17, PerkAPI.downArrow);
		}else{
			inv.setItem(13, PerkAPI.downArrow);
		}
		
		inv.setItem(40, backAG);
		
		perks.put(p, inv);
	}
	
	@SuppressWarnings("deprecation")
	public static void setWeaponInvs(Player p, WeaponType wp) {
		if (wp == WeaponType.TACTICAL)  {
			Inventory tInv;
			if (tacticals.get(p) == null) {
				tInv = Bukkit.getServer().createInventory(null, 54, "Tacticals");
				tacticals.put(p, tInv);
			}else{
				tInv = tacticals.get(p);
			}
			
			tInv.clear();
			
			for (int i = 0 ; i < Tactical.tacticals.size() ; i++) {
				ItemStack template = Tactical.tacticals.get(i);
				ItemStack tactical = new ItemStack(template.getType(), template.getAmount(), template.getData().getData());
				
				ItemMeta tMeta = tactical.getItemMeta();
				tMeta.setDisplayName(template.getItemMeta().getDisplayName());
				ArrayList<String> lore = new ArrayList<String>();
				if (Tactical.getCost(template) != 0) {
					lore.add("§6This tactical costs §c" + Tactical.getCost(template));
					lore.add("§6credits");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when purchasing");
					lore.add("§7§m---------------------");
					lore.add("§bClick to buy this tactical");
					tMeta.setLore(lore);
				}else{
					lore.add("§6This tactical is §cfree");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when selecting");
					lore.add("§7§m---------------------");
					lore.add("§aClick to select");
					tMeta.setLore(lore);
				}
				
				tactical.setItemMeta(tMeta);
				
				tInv.addItem(tactical);
			}
			
			tInv.setItem(49, backAG);
		}else if (wp == WeaponType.LETHAL) {
			Inventory lInv;
			if (lethals.get(p) == null) {
				lInv = Bukkit.getServer().createInventory(null, 54, "Lethals");
				lethals.put(p, lInv);
			}else{
				lInv = lethals.get(p);
			}
			
			lInv.clear();
			
			for (int i = 0 ; i < Lethal.lethals.size() ; i++) {
				ItemStack template = Lethal.lethals.get(i);
				ItemStack lethal = new ItemStack(template.getType(), template.getAmount(), template.getData().getData());
				
				ItemMeta lMeta = lethal.getItemMeta();
				lMeta.setDisplayName(template.getItemMeta().getDisplayName());
				ArrayList<String> lore = new ArrayList<String>();
				if (Lethal.getCost(template) != 0) {
					lore.add("§6This lethal costs §c" + Lethal.getCost(template));
					lore.add("§6credits");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when purchasing");
					lore.add("§7§m-----------------------");
					lore.add("§bClick to buy this lethal");
					lMeta.setLore(lore);
				}else{
					lore.add("§6This lethal is §cfree");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when selecting");
					lore.add("§7§m-----------------------");
					lore.add("§aClick to select");
					lMeta.setLore(lore);
				}
				
				lethal.setItemMeta(lMeta);
				
				lInv.addItem(lethal);
			}
			
			lInv.setItem(49, backAG);
		}else if (wp == WeaponType.BOTH) {
			Inventory tInv;
			if (tacticals.get(p) == null) {
				tInv = Bukkit.getServer().createInventory(null, 54, "Tacticals");
				tacticals.put(p, tInv);
			}else{
				tInv = tacticals.get(p);
			}
			
			tInv.clear();
			
			for (int i = 0 ; i < Tactical.tacticals.size() ; i++) {
				ItemStack template = Tactical.tacticals.get(i);
				ItemStack tactical = new ItemStack(template.getType(), template.getAmount(), template.getData().getData());
				
				ItemMeta tMeta = tactical.getItemMeta();
				tMeta.setDisplayName(template.getItemMeta().getDisplayName());
				ArrayList<String> lore = new ArrayList<String>();
				if (Tactical.getCost(template) != 0) {
					lore.add("§6This tactical costs §c" + Tactical.getCost(template));
					lore.add("§6credits");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when purchasing");
					lore.add("§7§m---------------------");
					lore.add("§bClick to buy this tactical");
					tMeta.setLore(lore);
				}else{
					lore.add("§6This tactical is §cfree");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when selecting");
					lore.add("§7§m---------------------");
					lore.add("§aClick to select");
					tMeta.setLore(lore);
				}
				
				tactical.setItemMeta(tMeta);
				
				tInv.addItem(tactical);
			}
			
			tInv.setItem(49, backAG);
			
			//===================================================
			
			Inventory lInv;
			if (lethals.get(p) == null) {
				lInv = Bukkit.getServer().createInventory(null, 54, "Lethals");
				lethals.put(p, lInv);
			}else{
				lInv = lethals.get(p);
			}
			
			lInv.clear();
			
			for (int i = 0 ; i < Lethal.lethals.size() ; i++) {
				ItemStack template = Lethal.lethals.get(i);
				ItemStack lethal = new ItemStack(template.getType(), template.getAmount(), template.getData().getData());
				
				ItemMeta lMeta = lethal.getItemMeta();
				lMeta.setDisplayName(template.getItemMeta().getDisplayName());
				ArrayList<String> lore = new ArrayList<String>();
				if (Lethal.getCost(template) != 0) {
					lore.add("§6This lethal costs §c" + Lethal.getCost(template));
					lore.add("§6credits");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when purchasing");
					lore.add("§7§m-----------------------");
					lore.add("§bClick to buy this lethal");
					lMeta.setLore(lore);
				}else{
					lore.add("§6This lethal is §cfree");
					lore.add("");
					lore.add("§8- §6Get§e " + template.getAmount() + " §6when selecting");
					lore.add("§7§m-----------------------");
					lore.add("§aClick to select");
					lMeta.setLore(lore);
				}
				
				lethal.setItemMeta(lMeta);
				
				lInv.addItem(lethal);
			}
			
			lInv.setItem(49, backAG);
		}
	}
	
	public static void setInformation(String map, String gamemode) {
		ItemMeta iMeta = information.getItemMeta();
		iMeta.setDisplayName("§6Information:");
		
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add("§7Map:§e " + map);
		lore.add("§7Gamemode:§e " + gamemode);
		
		iMeta.setLore(lore);
		information.setItemMeta(iMeta);
		MainInventory.setItem(31, information);
	}
	
	@SuppressWarnings("deprecation")
	public static void setAvailableGuns(Player p) {
		if ((KitInventory.getKit(p).getItem(1) == null) || (KitInventory.getKit(p).getItem(3) == null)) {
			return;
		}
		
		none = new ItemStack(Material.EMERALD);
		
		ItemMeta noneMeta = none.getItemMeta();
		noneMeta.setDisplayName("§4None");
		none.setItemMeta(noneMeta);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		final ItemStack Primary = new ItemStack(KitInventory.getKit(p).getItem(1).getType(), 1, KitInventory.getKit(p).getItem(1).getData().getData());
		
		ItemMeta pMeta = Primary.getItemMeta();
		pMeta.setDisplayName(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName());
		
		final ItemStack Secondary = new ItemStack(KitInventory.getKit(p).getItem(3).getType(), 1, KitInventory.getKit(p).getItem(3).getData().getData());
		
		ItemMeta sMeta= Secondary.getItemMeta();
		sMeta.setDisplayName(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName());
		
		String PrimaryName = pMeta.getDisplayName();
		String SecondaryName = sMeta.getDisplayName();
		
		ItemMeta PMeta = Primary.getItemMeta();
		PMeta.setDisplayName("§7Current Primary:§e " + PrimaryName);
		lore.add("§8Click this item to change");
		lore.add("§8your primary weapon");
		lore.add("");
		PMeta.setLore(lore);
		Primary.setItemMeta(PMeta);
		
		lore.clear();
		
		ItemMeta SMeta = Secondary.getItemMeta();
		SMeta.setDisplayName("§7Current Secondary:§e " + SecondaryName);
		lore.add("§8Click this item to change");
		lore.add("§8your secondary weapon");
		SMeta.setLore(lore);
		Secondary.setItemMeta(SMeta);
		
		PGun.put(p, Primary);
		SGun.put(p, Secondary);
		
		ClassSelection.get(p).setItem(27, Primary);
		ClassSelection.get(p).setItem(29, Secondary);
		ClassSelection.get(p).setItem(31, none);
		ClassSelection.get(p).setItem(33, none);
	
		Perk name = PerkAPI.getPerk(p);
		
		if (name == Perk.NO_PERK) {
			ItemsAndInventories.ClassSelection.get(p).setItem(35, none);
		}else if (name == Perk.SPEED) {
			ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(PerkAPI.Speed.getType(), 1, 0, "§a§lSpeed", new ArrayList<String>(Arrays.asList("§2Click to change"))));
		}else if (name == Perk.MARATHON) {
			ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(PerkAPI.Marathon.getType(), 1, 0, "§a§lMarathon", new ArrayList<String>(Arrays.asList("§2Click to change"))));
		}else if (name == Perk.SCAVENGER) {
			ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(PerkAPI.Scavenger.getType(), 1, 0, "§a§lScavenger", new ArrayList<String>(Arrays.asList("§2Click to change"))));
		}else if (name == Perk.HARDLINE) {
			ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(PerkAPI.Hardline.getType(), 1, 0, "§a§lHardline", new ArrayList<String>(Arrays.asList("§2Click to change"))));
		}
	}
	
	public static void updateShop(Player p) {
		ItemStack alert = new ItemStack(Material.REDSTONE);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		if (!isEmpty(ShopInventoryPrimary.getPrimaryShop(p))) {
			ItemMeta PAlert = alert.getItemMeta();
			PAlert.setDisplayName("§6§lGuns Available");
			lore.add("§7You can buy a new");
			lore.add("§7primary gun");
			PAlert.setLore(lore);
			alert.setItemMeta(PAlert);
			
			ShopMainMenu.get(p).setItem(28, alert);
		}else{
			ShopMainMenu.get(p).setItem(28, null);
		}
		
		lore.clear();
		
		if (!isEmpty(ShopInventorySecondary.getSecondaryShop(p))) {
			ItemMeta SAlert = alert.getItemMeta();
			SAlert.setDisplayName("§6§lGuns Available");
			lore.add("§7You can buy a new");
			lore.add("§7secondary gun");
			SAlert.setLore(lore);
			alert.setItemMeta(SAlert);
			
			ShopMainMenu.get(p).setItem(34, alert);
		}else{
			ShopMainMenu.get(p).setItem(34, null);
		}
	}
	
	private static boolean isEmpty(Inventory inv) {
		for(int i = 0 ; i < inv.getSize() ; i++) {
			ItemStack item = inv.getItem(i);
			if (item == null) continue;
			if (item.equals(backShop)) continue;

		      return false;
		}
		
		return true;
	}
}
