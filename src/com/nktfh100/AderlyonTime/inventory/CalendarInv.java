package com.nktfh100.AderlyonTime.inventory;

import com.nktfh100.AderlyonTime.info.ItemInfo;
import com.nktfh100.AderlyonTime.info.ItemInfoContainer;
import com.nktfh100.AderlyonTime.main.AderlyonTime;
import com.nktfh100.AderlyonTime.utils.Utils;

public class CalendarInv extends CustomHolder {

	public CalendarInv() {
		super(54, AderlyonTime.getInstance().getConfigManager().getCalendarTitle());

		this.update();
	}

	@Override
	public void update() {
		AderlyonTime plugin = AderlyonTime.getInstance();
		this.clearInv();

		Utils.fillInv(this.inv, plugin.getItemsManager().getItem("calendar_background").getItem().getItem());

		ItemInfo topDayInfo = plugin.getItemsManager().getItem("calendar_top-day").getItem();
		for (int i = 1; i < 8; i++) {
			this.inv.setItem(i, topDayInfo.getItem(plugin.getConfigManager().getWeekDayName(i)));
		}

		int currentDay = plugin.getTimeManager().getCurrentDay();

		ItemInfoContainer squareInfoContainer = plugin.getItemsManager().getItem("calendar_square");
		int toAdd = plugin.getTimeManager().getFirstWeekDay();
		int slot = 10;
		if (toAdd > 1) {
			int lastDayNum = 30;
			if (plugin.getTimeManager().getCurrentMonth() >= 12) {
				lastDayNum = plugin.getConfigManager().getMonth(1).getDays();
			} else if (plugin.getTimeManager().getCurrentMonth() == 1) {
				lastDayNum = plugin.getConfigManager().getMonth(12).getDays();
			} else {
				lastDayNum = plugin.getConfigManager().getMonth(plugin.getTimeManager().getCurrentMonth() - 1).getDays();
			}
			for (int i = toAdd + 10; i > 9; i--) {
				this.inv.setItem(i, squareInfoContainer.getItem3().getItem(lastDayNum + ""));
				lastDayNum--;
			}
			slot += toAdd;
		}
		for (int i = 1; i <= plugin.getTimeManager().getCurrentMonthInfo().getDays(); i++) {
			if (slot == 17 || slot == 26 || slot == 35 || slot == 44) {
				slot += 2;
			}

			if (i == currentDay) {
				this.inv.setItem(slot, squareInfoContainer.getItem2().getItem(i + ""));
			} else {
				this.inv.setItem(slot, squareInfoContainer.getItem().getItem(i + ""));
			}
			this.inv.getItem(slot).setAmount(i);

			slot++;
		}

		int weekSlot = 9;
		ItemInfo weekNumInfo = plugin.getItemsManager().getItem("calendar_week-numbers").getItem();
		for (int i = 1; i < 6; i++) {
			this.inv.setItem(weekSlot, weekNumInfo.getItem(i + ""));
			this.inv.getItem(weekSlot).setAmount(i);

			weekSlot += 9;
		}
	}
}
