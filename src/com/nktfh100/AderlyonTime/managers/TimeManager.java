package com.nktfh100.AderlyonTime.managers;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.nktfh100.AderlyonTime.info.Month;
import com.nktfh100.AderlyonTime.main.AderlyonTime;
import com.nktfh100.AderlyonTime.utils.Utils;

public class TimeManager {

	private AderlyonTime plugin;

	private File dataFile;
	private YamlConfiguration dataConfig;

	private Integer currentMinute = 0;
	private Integer currentHour = 1;
	private Integer weekDay = 1;
	private Integer currentDay = 1;
	private Integer currentMonth = 1;
	private Integer currentYear = 1;

	private Integer firstWeekDay = 1;

	private long lastWorldTime = 0;

	private BukkitTask timeTask = null;

	public TimeManager(AderlyonTime instance) {
		this.plugin = instance;
		this.dataFile = new File(this.plugin.getDataFolder(), "time.yml");
		if (!this.dataFile.exists()) {
			try {
				this.plugin.saveResource("time.yml", false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.dataConfig = YamlConfiguration.loadConfiguration(this.dataFile);
	}

	public void startTimeTask() {
		TimeManager timeManager = this;
		if (this.timeTask != null) {
			this.timeTask.cancel();
		}
		this.lastWorldTime = plugin.getConfigManager().getMainWorld().getFullTime();
		this.timeTask = new BukkitRunnable() {
			@Override
			public void run() {
				timeManager.tick();
			}
		}.runTaskTimer(this.plugin, 0L, 20L);
	}

	public void loadCurrentTime() {
		try {
			this.dataConfig.load(this.dataFile);

			this.weekDay = this.dataConfig.getInt("current-time.week-day");
			this.currentDay = this.dataConfig.getInt("current-time.day");
			this.currentMonth = this.dataConfig.getInt("current-time.month");
			this.currentYear = this.dataConfig.getInt("current-time.year");
			this.firstWeekDay = this.dataConfig.getInt("current-time.first-week-day");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			plugin.getLogger().warning("Something is wrong with your time.yml file!");
		}
	}

	public void saveCurrentTime() {
		this.dataConfig.set("current-time.week-day", this.weekDay);
		this.dataConfig.set("current-time.day", this.currentDay);
		this.dataConfig.set("current-time.month", this.currentMonth);
		this.dataConfig.set("current-time.year", this.currentYear);
		this.dataConfig.set("current-time.first-week-day", this.firstWeekDay);

		try {
			this.dataConfig.save(this.dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		Boolean save = false;

		Integer lastHour = Utils.ticksToDate(this.lastWorldTime).get(Calendar.HOUR_OF_DAY);

		long worldTime = plugin.getConfigManager().getMainWorld().getFullTime();
		this.lastWorldTime = worldTime;

		Calendar cal = Utils.ticksToDate(worldTime);
		this.currentMinute = cal.get(Calendar.MINUTE);
		this.currentHour = cal.get(Calendar.HOUR_OF_DAY);

		if (lastHour > this.currentHour) {
			save = true;
			this.currentDay++;
			this.weekDay++;
			if (this.weekDay > 7) {
				this.weekDay = 1;
			}
			if (this.currentDay > this.getCurrentMonthInfo().getDays()) {
				this.currentDay = 1;
				this.currentMonth++;
				this.firstWeekDay = this.weekDay;
				if (this.currentMonth > 12) {
					this.currentMonth = 1;
					this.currentYear++;
				}
			}
		}
		if (save) {
			this.saveCurrentTime();
		}
		if (plugin.getConfigManager().getDebug()) {
			plugin.getLogger().info("Current Time:");
			plugin.getLogger().info(this.currentMinute + "m " + this.currentHour + "h " + this.currentDay + "d " + this.currentMonth + "mo ");
		}
	}

	public Month getCurrentMonthInfo() {
		return plugin.getConfigManager().getMonth(this.currentMonth);
	}

	public Integer getCurrentMinute() {
		return currentMinute;
	}

	public void setCurrentMinute(Integer currentMinute) {
		this.currentMinute = currentMinute;
	}

	public Integer getCurrentHour() {
		return currentHour;
	}

	public void setCurrentHour(Integer currentHour) {
		this.currentHour = currentHour;
	}

	public Integer getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Integer currentDay) {
		this.currentDay = currentDay;
	}

	public Integer getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(Integer currentMonth) {
		this.currentMonth = currentMonth;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer currentYear) {
		this.currentYear = currentYear;
	}

	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	public Integer getFirstWeekDay() {
		return this.firstWeekDay;
	}

	public void setFirstWeekDay(Integer firstWeekDay) {
		this.firstWeekDay = firstWeekDay;
	}
}
