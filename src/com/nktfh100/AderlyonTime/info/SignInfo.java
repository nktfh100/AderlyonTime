package com.nktfh100.AderlyonTime.info;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import com.nktfh100.AderlyonTime.main.AderlyonTime;

public class SignInfo {

	private Block signBlock;
	private ArrayList<String> lines = new ArrayList<String>();

	public SignInfo(Block block, ArrayList<String> lines) {
		this.signBlock = block;
		this.lines = lines;
	}

	public SignInfo(Block block, String[] lines) {
		this.signBlock = block;
		ArrayList<String> lines_ = new ArrayList<String>();
		for (String line_ : lines) {
			lines_.add(line_);
		}
		this.lines = lines_;
	}

	public void update() {
		if (!this.signBlock.getType().toString().contains("SIGN")) {
			return;
		}
		Sign signState = (Sign) this.signBlock.getState();
		AderlyonTime plugin = AderlyonTime.getInstance();
		int i = 0;
		for (String line : this.lines) {
			line = line.replaceAll("%m%", plugin.getTimeManager().getCurrentMinute() + "");
			line = line.replaceAll("%h%", plugin.getTimeManager().getCurrentHour() + "");
			line = line.replaceAll("%md%", plugin.getTimeManager().getCurrentDay() + "");
			line = line.replaceAll("%wd%", plugin.getConfigManager().getWeekDayName(plugin.getTimeManager().getWeekDay()));
			line = line.replaceAll("%mo%", plugin.getTimeManager().getCurrentMonth() + "");
			line = line.replaceAll("%mn%", plugin.getTimeManager().getCurrentMonthInfo().getName());
			line = line.replaceAll("%y%", plugin.getTimeManager().getCurrentYear() + "");
			signState.setLine(i, line);
			i++;
		}
		signState.update();
	}

	public ArrayList<String> getLines() {
		return this.lines;
	}

	public Block getSignBlock() {
		return signBlock;
	}

}
