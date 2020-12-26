package yadokaris_Status_HUD_plus;

import java.util.ArrayList;
import java.util.List;

public class StatusGroup {

	public String name;
	public List<String> statusIDs = new ArrayList<>();
	public float x, y;
	public boolean doShowName, isRainbow, doChangeTeamColor, doRender;
	public int color;

	public StatusGroup(String name, float x, float y, List<String> statusIDs, boolean doShowName, boolean isRainbow, boolean doChangeTeamColor, int color, boolean doRender) {
		this.name = name;
		this.x = x;
		this.y = y;
		for (String s : statusIDs) {
			this.statusIDs.add(s);
		}
		this.doShowName = doShowName;
		this.isRainbow = isRainbow;
		this.doChangeTeamColor = doChangeTeamColor;
		this.color = color;
		this.doRender = doRender;
	}
}
