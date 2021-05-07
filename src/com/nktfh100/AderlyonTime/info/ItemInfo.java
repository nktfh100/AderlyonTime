package com.nktfh100.AderlyonTime.info;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.nktfh100.AderlyonTime.main.AderlyonTime;
import com.nktfh100.AderlyonTime.utils.Utils;

public class ItemInfo {

	private Boolean isHead = false;
	private String texture;
	private Integer slot;
	private Material mat;
	private String title = "";
	private ArrayList<String> lore = new ArrayList<String>();

	public ItemInfo(Integer slot, Material mat, String title, ArrayList<String> lore) {
		this.slot = slot;
		this.mat = mat;
		this.title = title;
		this.lore = lore;
	}

	public void setHeadInfo(String texture) {
		this.isHead = true;
		this.texture = texture;
	}

	public ItemStack getItem(String... values) {
		if (this.isHead) {
			ItemStack line = Utils.createSkull(this.texture, this.getTitle(values), 1, this.getLore(values));
			Utils.addItemFlag(line, ItemFlag.HIDE_ATTRIBUTES);
			return line;
		} else {
			ItemStack line = Utils.createItem(this.getMat(), this.getTitle(values), 1, this.getLore(values));
			Utils.addItemFlag(line, ItemFlag.HIDE_ATTRIBUTES);
			return line;
		}
	}

	private String replaceValues(String line, String... values) {
		if (line == null || line.isEmpty()) {
			return "";
		}
		int i = 0;
		for (String val : values) {
			if (i == 0) {
				line = line.replace("%value%", val);
			} else {
				line = line.replace("%value" + i + "%", val);
			}
		}
		AderlyonTime plugin = AderlyonTime.getInstance();
		line = line.replaceAll("%minute%", plugin.getTimeManager().getCurrentMinute() + "");
		line = line.replaceAll("%hour%", plugin.getTimeManager().getCurrentHour() + "");
		line = line.replaceAll("%month_day%", plugin.getTimeManager().getCurrentDay() + "");
		line = line.replaceAll("%week_day%", plugin.getConfigManager().getWeekDayName(plugin.getTimeManager().getWeekDay()));
		line = line.replaceAll("%month%", plugin.getTimeManager().getCurrentMonth() + "");
		line = line.replaceAll("%month_name%", plugin.getTimeManager().getCurrentMonthInfo().getName());
		line = line.replaceAll("%year%", plugin.getTimeManager().getCurrentYear() + "");
		return line;
	}

	// ------- title -------

	public String getTitle(String... values) {
		if (this.title == null || this.title.isEmpty()) {
			return "";
		}
		return replaceValues(this.title, values);
	}

	// ------- lore -------

	public ArrayList<String> getLore(String... values) {
		if (this.lore == null || this.lore.size() == 0) {
			return new ArrayList<String>();
		}
		ArrayList<String> newLore = new ArrayList<String>();
		for (String line : this.lore) {
			newLore.add(this.replaceValues(line, values));
		}
		return newLore;
	}

	public Integer getSlot() {
		return slot;
	}

	public Material getMat() {
		return mat;
	}

	public Boolean getIsHead() {
		return isHead;
	}

	public String getTexture() {
		return texture;
	}
}
