package com.CentrumGuy.CodWarfare.Inventories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class SaveAndLoad {
	
	public static ArrayList<String> toString(Inventory inv) {
		if (isEmpty(inv) == false) {
			ArrayList<String> list = new ArrayList<String>();
			
			for (ItemStack i : inv.getContents()) {
				if (i == null) continue;
				String s = deserializeItem(i);
				if (!(list.contains(s))) list.add(deserializeItem(i));
			}
			
			return list;
		}else{
			return null;
		}
	}
	
	public static Inventory fromString(Inventory inv, List<?> list) {
		inv.clear();
		
		if (!list.isEmpty()) {
			for (int i = 0 ; i < list.size() ; i++) {
				String s = (String) list.get(i);
				ItemStack item = serializeItem(s);
				if (!(inv.contains(item))) inv.addItem(serializeItem(s));
			}
			
			return inv;
		}else{
			return inv;
		}
	}
	
	public static boolean isEmpty(Inventory inv) {
		for(int i = 0 ; i < inv.getSize() ; i++) {
			ItemStack item = inv.getItem(i);
			
		    if(item != null)
		      return false;
		}
		
		return true;
	}
	
	private static String getEnchants(ItemStack i){
        List<String> e = new ArrayList<String>();
        Map<Enchantment, Integer> en = i.getEnchantments();
        for(Enchantment t : en.keySet()) {
                e.add(t.getName() + ":" +en.get(t));
        }
        
        return StringUtils.join(e, ",");
	}
	
	@SuppressWarnings("deprecation")
	public static String deserializeItem (ItemStack i){
		String[] parts = new String[6];
		parts[0] = i.getType().name();
		parts [1] = Integer.toString(i.getAmount());
		parts [2] = String.valueOf(i.getDurability());
		parts [3] = i.getItemMeta().getDisplayName();
		parts [4] = String.valueOf (i.getData().getData());
		parts [5] = getEnchants(i);
		return StringUtils.join(parts, ";");

	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack serializeItem (String p) {
		String[] a = p.split(";");
		ItemStack i = new ItemStack(Material.getMaterial(a[0]), Integer.parseInt (a [1]));
		i.setDurability((short) Integer.parseInt(a [2]));
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(a[3]);
		i.setItemMeta(meta);
		MaterialData data = i.getData();
		data.setData ((byte) Integer.parseInt (a[4]));
		i.setData(data);
			if (a.length > 5) {
				String[] parts = a[5].split(",");
				for(String s : parts) {
					String label = s.split(":")[0];
					String amplifier = s.split(":")[1];
					Enchantment type = Enchantment.getByName(label);
                if(type == null) {
                        continue;
                }
                
                int f;
                
                try {
                	f = Integer.parseInt(amplifier);
                }catch(Exception ex) {
                        continue;
                }
                
                i.addEnchantment(type, f);
			}
		}
	return i;
	}
}