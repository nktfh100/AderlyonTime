package com.nktfh100.AderlyonTime.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.nktfh100.AderlyonTime.info.SignInfo;
import com.nktfh100.AderlyonTime.main.AderlyonTime;

public class SignChange implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSignChange(SignChangeEvent ev) {
		Boolean containsPlaceholders = false;
		for (String line : ev.getLines()) {
			if (line.contains("%s%") || line.contains("%m%") || line.contains("%h%") || line.contains("%md%") || line.contains("%wd%") || line.contains("%mo%") || line.contains("%mn%")
					|| line.contains("%y%")) {
				containsPlaceholders = true;
				break;
			}
		}
		if (containsPlaceholders) {
			AderlyonTime plugin = AderlyonTime.getInstance();
			SignInfo signInfo = new SignInfo(ev.getBlock(), ev.getLines());
			plugin.getSignsManager().addSign(signInfo);
			new BukkitRunnable() {
				@Override
				public void run() {
					plugin.getSignsManager().updateSigns();
				}
			}.runTaskLater(plugin, 10L);
		}
	}
}
