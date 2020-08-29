package yadokaris_Status_HUD_plus;

import java.util.ArrayList;
import java.util.List;

public class StatusGroup {

	public String name;
	public List<String> statusIDs = new ArrayList<>();
	public float x, y;
	public boolean doShowName;

	public StatusGroup(String name, float x, float y, List<String> statusIDs, boolean doShowName) {
		this.name = name;
		this.x = x;
		this.y = y;
		for (String s : statusIDs) {
			this.statusIDs.add(s);
		}
		this.doShowName = doShowName;
	}
}
