package com.nktfh100.AderlyonTime.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.nktfh100.AderlyonTime.commands.CalendarCommand;
import com.nktfh100.AderlyonTime.events.InvClick;
import com.nktfh100.AderlyonTime.events.SignChange;
import com.nktfh100.AderlyonTime.managers.ConfigManager;
import com.nktfh100.AderlyonTime.managers.ItemsManager;
import com.nktfh100.AderlyonTime.managers.SignsManager;
import com.nktfh100.AderlyonTime.managers.TimeManager;

public class AderlyonTime extends JavaPlugin {

	private static AderlyonTime instance;

	public AderlyonTime() {
		instance = this;
	}

	private ConfigManager configManager;
	private ItemsManager itemsManager;
	private TimeManager timeManager;
	private SignsManager signsManager;

	@Override
	public void onEnable() {
		this.configManager = new ConfigManager(this);
		this.configManager.loadConfig();
		this.itemsManager = new ItemsManager(this);
		this.itemsManager.loadItems();
		this.timeManager = new TimeManager(this);
		this.timeManager.loadCurrentTime();
		this.timeManager.startTimeTask();
		this.signsManager = new SignsManager(this);
		this.signsManager.loadSigns();
		this.signsManager.startUpdateTask();

		this.getCommand("calendar").setExecutor(new CalendarCommand());

		this.getServer().getPluginManager().registerEvents(new InvClick(), this);
		this.getServer().getPluginManager().registerEvents(new SignChange(), this);

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new Placeholders(this).register();
		}
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public static AderlyonTime getInstance() {
		return instance;
	}

	public TimeManager getTimeManager() {
		return timeManager;
	}

	public ItemsManager getItemsManager() {
		return itemsManager;
	}

	public SignsManager getSignsManager() {
		return signsManager;
	}

}
