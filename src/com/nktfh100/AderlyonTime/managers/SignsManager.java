package com.nktfh100.AderlyonTime.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import com.nktfh100.AderlyonTime.info.SignInfo;
import com.nktfh100.AderlyonTime.main.AderlyonTime;
import com.nktfh100.AderlyonTime.utils.Utils;

public class SignsManager {

	private AderlyonTime plugin;

	public SignsManager(AderlyonTime instance) {
		this.plugin = instance;
	}

	private File signsConfigFile;
	private YamlConfiguration signsConfig;

	private ArrayList<SignInfo> signs = new ArrayList<SignInfo>();

	public void loadSigns() {
		this.signsConfigFile = new File(plugin.getDataFolder(), "signs.yml");
		if (!this.signsConfigFile.exists()) {
			try {
				plugin.saveResource("signs.yml", false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.signsConfig = YamlConfiguration.loadConfiguration(this.signsConfigFile);

		ArrayList<String> toRemove = new ArrayList<String>();
		ConfigurationSection signsSC = this.signsConfig.getConfigurationSection("signs");
		for (String key : signsSC.getKeys(false)) {
			ConfigurationSection locSC = signsSC.getConfigurationSection(key + ".loc");
			Location loc = new Location(Bukkit.getWorld(locSC.getString("world")), locSC.getInt("x"), locSC.getInt("y"), locSC.getInt("z"));
			if (loc.getBlock().getType().toString().contains("SIGN")) {
				this.signs.add(new SignInfo(loc.getBlock(), (ArrayList<String>) signsSC.getStringList(key + ".lines")));
			} else {
				toRemove.add(key);
			}
		}

		for (String toRemoveKey : toRemove) {
			this.signsConfig.set("signs." + toRemoveKey, null);
		}

		if (!toRemove.isEmpty()) {
			try {
				this.signsConfig.save(this.signsConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.updateSigns();
	}

	public void startUpdateTask() {
		new BukkitRunnable() {

			@Override
			public void run() {
				updateSigns();
			}
		}.runTaskTimer(plugin, 0L, 20L * plugin.getConfigManager().getSignsUpdateInterval());
	}

	public void updateSigns() {
		for (SignInfo signInfo : this.signs) {
			signInfo.update();
		}
	}

	public void addSign(SignInfo signInfo) {
		this.signs.add(signInfo);
		Location loc = signInfo.getSignBlock().getLocation();
		String key = "signs." + Utils.getRandomString(4) + this.signs.size();
		this.signsConfig.set(key + ".loc.world", loc.getWorld().getName());
		this.signsConfig.set(key + ".loc.x", loc.getBlockX());
		this.signsConfig.set(key + ".loc.y", loc.getBlockY());
		this.signsConfig.set(key + ".loc.z", loc.getBlockZ());
		this.signsConfig.set(key + ".lines", signInfo.getLines());
		try {
			this.signsConfig.save(this.signsConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
