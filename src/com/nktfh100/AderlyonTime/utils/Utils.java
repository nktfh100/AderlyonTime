package com.nktfh100.AderlyonTime.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Utils {

	public static ItemStack setItemName(ItemStack item, String name, ArrayList<String> lore) {
		if (item != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			ArrayList<String> metaLore = new ArrayList<String>();

			for (String lore_ : lore) {
				metaLore.add(lore_);
			}
			meta.setLore(metaLore);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack setItemLore(ItemStack item, ArrayList<String> lore) {
		if (item != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> metaLore = new ArrayList<String>();
			for (String lore_ : lore) {
				metaLore.add(ChatColor.translateAlternateColorCodes('&', lore_));
			}
			meta.setLore(metaLore);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack enchantedItem(ItemStack item, Enchantment ench, int lvl) {
		if (item != null && item.getType() != Material.AIR && item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			meta.addEnchant(ench, lvl, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack createItem(Material mat, String name) {
		ItemStack item = new ItemStack(mat);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack createItem(Material mat, String name, int amount) {
		ItemStack item = createItem(mat, name);
		item.setAmount(amount);
		return (item);
	}

	public static ItemStack createItem(Material mat, String name, int amount, String... lore) {
		ItemStack item = createItem(mat, name, amount);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();

			ArrayList<String> metaLore = new ArrayList<String>();

			for (String lorecomments : lore) {
				metaLore.add(lorecomments);
			}
			meta.setLore(metaLore);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack createItem(Material mat, String name, int amount, ArrayList<String> lore) {
		ItemStack item = createItem(mat, name, amount);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();

			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack createEnchantedItem(Material mat, String name, Enchantment ench, int lvl, String... lore) {
		ItemStack item = createItem(mat, name, 1);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.addEnchant(ench, lvl, true);
			ArrayList<String> metaLore = new ArrayList<String>();

			for (String lorecomments : lore) {
				metaLore.add(lorecomments);
			}
			meta.setLore(metaLore);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack addItemFlag(ItemStack item, ItemFlag... flag) {
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.addItemFlags(flag);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack createSkull(String url, String name, int amount, ArrayList<String> lore) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
		if (url.isEmpty()) {
			return head;
		}
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", url));

		try {
			Field profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);

		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
			error.printStackTrace();
		}
		headMeta.setDisplayName(name);
		head.setAmount(amount);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String lorecomments : lore) {
			metaLore.add(lorecomments);
		}
		headMeta.setLore(metaLore);
		head.setItemMeta(headMeta);
		return head;
	}

	public static ItemStack createSkull(String url, String name, int amount, String... lore) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
		if (url.isEmpty()) {
			return head;
		}
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", url));

		try {
			Field profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);

		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
			error.printStackTrace();
		}
		headMeta.setDisplayName(name);
		head.setAmount(amount);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String lorecomments : lore) {
			metaLore.add(lorecomments);
		}
		headMeta.setLore(metaLore);
		head.setItemMeta(headMeta);
		return head;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getHead(String player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
		try {
			SkullMeta skull = (SkullMeta) item.getItemMeta();
			skull.setOwner(player);
			item.setItemMeta(skull);
			return item;
		} catch (Exception e) {
			Bukkit.getLogger().info("Couldnt get " + player + "'s head");
			e.printStackTrace();
			return item;
		}
	}

	public static void fillInv(Inventory inv) {
		fillInv(inv, Material.BLACK_STAINED_GLASS_PANE);
	}

	public static void fillInv(Inventory inv, Material mat) {
		ItemStack item = Utils.createItem(mat, " ");
		for (int slot = 0; slot < inv.getSize(); slot++) {
			inv.setItem(slot, item);
		}
	}

	public static void fillInv(Inventory inv, ItemStack item) {
		for (int slot = 0; slot < inv.getSize(); slot++) {
			inv.setItem(slot, item);
		}
	}

	final static char[] chars_ = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890".toCharArray();

	public static String getRandomString(int length) {
		String randomString = "";

		final Random random = new Random();
		for (int i = 0; i < length; i++) {
			randomString = randomString + chars_[random.nextInt(chars_.length)];
		}

		return randomString;
	}

	// Taken from:
	// https://github.com/EssentialsX/Essentials/blob/1e0d7fb0a3545d15c3b0cc1d180b47551f98cb22/Essentials/src/main/java/com/earth2me/essentials/utils/DescParseTickFormat.java

	private static final int ticksAtMidnight = 18000;
	private static final int ticksPerDay = 24000;
	private static final int ticksPerHour = 1000;
	private static final double ticksPerMinute = 1000d / 60d;
	private static final double ticksPerSecond = 1000d / 60d / 60d;

	public static Calendar ticksToDate(long ticks) {
		// Assume the server time starts at 0. It would start on a day.
		// But we will simulate that the server started with 0 at midnight.
		ticks = ticks - ticksAtMidnight + ticksPerDay;

		// How many ingame days have passed since the server start?
		final long days = ticks / ticksPerDay;
		ticks -= days * ticksPerDay;

		// How many hours on the last day?
		final long hours = ticks / ticksPerHour;
		ticks -= hours * ticksPerHour;

		// How many minutes on the last day?
		final long minutes = (long) Math.floor(ticks / ticksPerMinute);
		final double dticks = ticks - minutes * ticksPerMinute;

		// How many seconds on the last day?
		final long seconds = (long) Math.floor(dticks / ticksPerSecond);

		final Calendar cal = Calendar.getInstance();
		cal.setLenient(true);

		// And we set the time to 0! And append the time that passed!
		cal.set(0, Calendar.JANUARY, 1, 0, 0, 0);
		cal.add(Calendar.DAY_OF_YEAR, (int) days);
		cal.add(Calendar.HOUR_OF_DAY, (int) hours);
		cal.add(Calendar.MINUTE, (int) minutes);
		cal.add(Calendar.SECOND, (int) seconds + 1); // To solve rounding errors.

		return cal;
	}
}