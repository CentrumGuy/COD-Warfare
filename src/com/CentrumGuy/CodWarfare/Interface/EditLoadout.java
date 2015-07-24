package com.CentrumGuy.CodWarfare.Interface;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.OtherLoadout.Lethal;
import com.CentrumGuy.CodWarfare.OtherLoadout.Tactical;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;

public class EditLoadout {

	@SuppressWarnings("deprecation")
	public static void buyGun(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().equals(Material.AIR)) return;
		if (!(e.getCurrentItem().hasItemMeta())) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if ((!(e.getInventory().equals(ShopInventoryPrimary.getPrimaryShop(p)))) && (!(e.getInventory().equals(ShopInventorySecondary.getSecondaryShop(p))))) return;
		
		e.setCancelled(true);
		
		if (e.getCurrentItem().equals(ItemsAndInventories.backShop)) {
			p.openInventory(ItemsAndInventories.ShopMainMenu.get(p));
			return;
		}else{
			ItemStack ClickedItem = e.getCurrentItem();
			
			if (!(ClickedItem.getItemMeta().hasDisplayName())) return;
			
			ItemStack gun = new ItemStack(ClickedItem.getType(), 1, ClickedItem.getData().getData());
			ItemMeta gunMeta = gun.getItemMeta();
			gunMeta.setDisplayName("§e" + GetNormalName.get(ClickedItem.getItemMeta().getDisplayName()));
			gun.setItemMeta(gunMeta);
			
			int Price = Guns.getCost(gun);
			
			Scores.saveScores(p);
			Scores.loadScores(p);
			
			if (Main.LobbyCreditsScore.get(p.getName()).getScore() >= Price) {
				p.closeInventory();
				Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() - Price);
				Main.GameCreditsScore.get(p.getName()).setScore(Main.GameCreditsScore.get(p.getName()).getScore() - Price);
				
				Scores.saveScores(p);
				Scores.loadScores(p);
				
				if (Guns.getType(gun).equalsIgnoreCase("Primary")) {
					for (int i = 0 ; i < ShopInventoryPrimary.getPrimaryShop(p).getSize() ; i++) {
						ItemStack item = ShopInventoryPrimary.getPrimaryShop(p).getItem(i);
						if (item == null) continue;
						if (item.equals(ClickedItem)) ShopInventoryPrimary.getPrimaryShop(p).setItem(i, null);
					}
					
					ShopInventoryPrimary.savePrimaryShop(p);
					ShopInventoryPrimary.loadPrimaryShop(p);
					
					AGPInventory.getAGP(p).addItem(gun);
					AGPInventory.saveAGP(p);
					AGPInventory.loadAGP(p);
					
					ItemsAndInventories.updateShop(p);
					
					p.sendMessage(Main.codSignature + "§6Succesfully purchased:§e " + gun.getItemMeta().getDisplayName());
					
					return;
				}else if (Guns.getType(gun).equalsIgnoreCase("Secondary")) {
					for (int i = 0 ; i < ShopInventorySecondary.getSecondaryShop(p).getSize() ; i++) {
						ItemStack item = ShopInventorySecondary.getSecondaryShop(p).getItem(i);
						if (item == null) continue;
						if (item.equals(ClickedItem)) ShopInventorySecondary.getSecondaryShop(p).setItem(i, null);
					}
					
					ShopInventorySecondary.saveSecondaryShop(p);
					ShopInventorySecondary.loadSecondaryShop(p);
					
					AGSInventory.getAGS(p).addItem(gun);
					AGSInventory.saveAGS(p);
					AGSInventory.loadAGS(p);
					
					ItemsAndInventories.updateShop(p);
					
					p.sendMessage(Main.codSignature + "§6Succesfully purchased:§e " + gun.getItemMeta().getDisplayName());
					
					return;
				}else{
					return;
				}
			}else{
				p.closeInventory();
				p.sendMessage(Main.codSignature + "§cNot enough credits");
			}
		}
	}
	
	public static void selectGun(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().equals(Material.AIR)) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if ((!(e.getInventory().equals(AGPInventory.getAGP(p)))) && (!(e.getInventory().equals(AGSInventory.getAGS(p))))) return;
		
		e.setCancelled(true);
		
		if (e.getCurrentItem().equals(ItemsAndInventories.backAG)) {
			p.openInventory(ItemsAndInventories.ClassSelection.get(p));
			return;
		}else{
			ItemStack gun = e.getCurrentItem();
			
			if (!(Guns.guns.contains(gun))) return;
			
			if (Guns.type.get(gun).equalsIgnoreCase("Primary")) {
				p.closeInventory();
				
				KitInventory.loadKit(p);
				
				KitInventory.getKit(p).setItem(1, gun);
				KitInventory.getKit(p).setItem(2, Guns.getAmmo(gun));
				KitInventory.saveKit(p);
				KitInventory.loadKit(p);
				
				ItemsAndInventories.setAvailableGuns(p);
				
				p.sendMessage(Main.codSignature + "§6Succesfully set primary gun to:§e " + gun.getItemMeta().getDisplayName());

			}else if (Guns.type.get(gun).equalsIgnoreCase("Secondary")) {
				p.closeInventory();
				
				KitInventory.loadKit(p);
				
				KitInventory.getKit(p).setItem(3, gun);
				KitInventory.getKit(p).setItem(4, Guns.getAmmo(gun));
				KitInventory.saveKit(p);
				KitInventory.loadKit(p);
				
				ItemsAndInventories.setAvailableGuns(p);
				
				p.sendMessage(Main.codSignature + "§6Succesfully set secondary gun to:§e " + gun.getItemMeta().getDisplayName());
				
			}else{
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void buyWeapon(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().equals(Material.AIR)) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if (e.getCurrentItem().equals(ItemsAndInventories.backAG)) {
			p.openInventory(ItemsAndInventories.ClassSelection.get(p));
			return;
		}
		
		ItemStack item = new ItemStack(e.getCurrentItem().getType(), e.getCurrentItem().getAmount(), e.getCurrentItem().getData().getData());
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
		im.setLore(new ArrayList<String>());
		item.setItemMeta(im);
	
		e.setCancelled(true);
		
		if (!item.hasItemMeta()) return;
		if (!item.getItemMeta().hasDisplayName()) return;
		
		if (Tactical.tacticals.contains(item)) {
			if (Main.LobbyCreditsScore.get(p.getName()).getScore() >= Tactical.getCost(item)) {
				Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() - Tactical.getCost(item));
				
				ItemStack tChanged = new ItemStack(item.getType(), 1, item.getData().getData());
				ItemMeta tMeta = tChanged.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				tMeta.setDisplayName("§eTactical");
				lore.add("§6" + GetNormalName.get(item.getItemMeta().getDisplayName()));
				lore.add("");
				lore.add("§cYou will be able to use this");
				lore.add("§ctactical weapon in the next round");
				tMeta.setLore(lore);
				tChanged.setItemMeta(tMeta);
				
				ItemsAndInventories.ClassSelection.get(p).setItem(33, tChanged);
				
				if (!(Main.CrackShot)) {
					KitInventory.getKit(p).setItem(6, item);
				}else{
					KitInventory.getKit(p).setItem(6, Main.CrackShotAPI.generateWeapon(GetNormalName.get(item.getItemMeta().getDisplayName())));
				}
				
				if (Tactical.getCost(item) != 0) {
					p.sendMessage(Main.codSignature + "§aSuccessfully purchased tactical weapon:§e " + item.getItemMeta().getDisplayName());
				}else{
					p.sendMessage(Main.codSignature + "§aSuccessfully set tactical weapon to:§e " + item.getItemMeta().getDisplayName());
				}
				
				p.closeInventory();
			}else{
				p.closeInventory();
				p.sendMessage(Main.codSignature + "§cYou do not have enough credits to buy this weapon");
			}
		}else if (Lethal.lethals.contains(item)) {
			if (Main.LobbyCreditsScore.get(p.getName()).getScore() >= Lethal.getCost(item)) {
				Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() - Lethal.getCost(item));
				
				ItemStack lChanged = new ItemStack(item.getType(), 1, item.getData().getData());
				ItemMeta lMeta = lChanged.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				lMeta.setDisplayName("§eLethal");
				lore.add("§6" + GetNormalName.get(item.getItemMeta().getDisplayName()));
				lore.add("");
				lore.add("§cYou will be able to use this");
				lore.add("§clethal weapon in the next round");
				lMeta.setLore(lore);
				lChanged.setItemMeta(lMeta);
				
				ItemsAndInventories.ClassSelection.get(p).setItem(31, lChanged);
				
				if (!(Main.CrackShot)) {
					KitInventory.getKit(p).setItem(5, item);
				}else{
					KitInventory.getKit(p).setItem(5, Main.CrackShotAPI.generateWeapon(GetNormalName.get(item.getItemMeta().getDisplayName())));
				}
				
				if (Lethal.getCost(item) != 0) {
					p.sendMessage(Main.codSignature + "§aSuccessfully purchased lethal weapon:§e " + item.getItemMeta().getDisplayName());
				}else{
					p.sendMessage(Main.codSignature + "§aSuccessfully set lethal weapon to:§e " + item.getItemMeta().getDisplayName());
				}
			
				p.closeInventory();
			}else{
				p.closeInventory();
				p.sendMessage(Main.codSignature + "§cYou do not have enough credits to buy this weapon");
			}
		}
	}
}
