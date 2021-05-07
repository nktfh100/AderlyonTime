package com.nktfh100.AderlyonTime.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.nktfh100.AderlyonTime.info.Month;
import com.nktfh100.AderlyonTime.main.AderlyonTime;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {

	private AderlyonTime plugin;

	private HashMap<Integer, String> weekDays = new HashMap<Integer, String>();
	private HashMap<Integer, Month> months = new HashMap<Integer, Month>();

	private Integer signsUpdateInterval = 60;
	private String calendarTitle = "";
	private Boolean debug = false;
	private World mainWorld;

	public ConfigManager(AderlyonTime instance) {
		this.plugin = instance;
	}

	public void loadConfig() {
		this.plugin.saveDefaultConfig();
		this.loadConfigVars();
	}

	public void loadConfigVars() {
		this.months.clear();
		this.weekDays.clear();
		this.plugin.reloadConfig();
		FileConfiguration config = this.plugin.getConfig();

		this.mainWorld = Bukkit.getWorld(config.getString("main-world"));
		if (this.mainWorld == null) {
			this.plugin.getLogger().warning("World - " + config.getString("main-world") + " doesn't exists!");
			this.mainWorld = Bukkit.getWorlds().iterator().next();
		}
		this.debug = config.getBoolean("debug");
		this.signsUpdateInterval = config.getInt("signs-update-interval");
		this.calendarTitle = ChatColor.translateAlternateColorCodes('&', config.getString("calendar-title", ""));

		ConfigurationSection monthsSC = config.getConfigurationSection("months");
		for (String key : monthsSC.getKeys(false)) {
			Integer monthNum = Integer.parseInt(key);
			this.months.put(monthNum, new Month(monthsSC.getInt(key + ".days", 30), monthsSC.getString(key + ".name", key)));
		}

		ConfigurationSection weekDaysSC = config.getConfigurationSection("week-days");
		for (String key : weekDaysSC.getKeys(false)) {
			Integer dayNum = Integer.parseInt(key);
			this.weekDays.put(dayNum, weekDaysSC.getString(key, key));
		}
	}

	public Month getMonth(Integer num) {
		return this.months.get(num);
	}

	public String getWeekDayName(Integer num) {
		return this.weekDays.get(num);
	}

	public String getCalendarTitle() {
		String out = this.calendarTitle;
		out = out.replaceAll("%minute%", this.plugin.getTimeManager().getCurrentMinute() + "");
		out = out.replaceAll("%hour%", this.plugin.getTimeManager().getCurrentHour() + "");
		out = out.replaceAll("%month_day%", this.plugin.getTimeManager().getCurrentDay() + "");
		out = out.replaceAll("%week_day%", this.getWeekDayName(this.plugin.getTimeManager().getWeekDay()));
		out = out.replaceAll("%month%", this.plugin.getTimeManager().getCurrentMonth() + "");
		out = out.replaceAll("%month_name%", this.plugin.getTimeManager().getCurrentMonthInfo().getName());
		out = out.replaceAll("%year%", this.plugin.getTimeManager().getCurrentYear() + "");
		return out;
	}

	public Boolean getDebug() {
		return debug;
	}

	public World getMainWorld() {
		return mainWorld;
	}

	public Integer getSignsUpdateInterval() {
		return signsUpdateInterval;
	}
}
