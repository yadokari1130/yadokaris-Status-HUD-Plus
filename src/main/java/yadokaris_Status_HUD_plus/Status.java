package yadokaris_Status_HUD_plus;

import net.minecraft.util.text.TextComponentTranslation;

public enum Status {

	Text("Text", "Unknown", "Unknown"),
	KillCountSword("KillCountSword", 0f),
	KillCountBow("KillCountBow", 0f),
	AttackingKillCount("AttackingKillCount", 0f),
	DefendingKillCount("DefendingKillCount", 0f),
	DeathCount("DeathCount", 0f),
	Rate("Rate", 0f),
	TotalRate("TotalRate", 0f),
	NexusDamage("NexusDamage", 0f),
	RepairPoint("RepairPoint", 0f),
	XP("XP", 0f),
	TotalXP("TotalXP", 0f),
	Rank("Rank", "Unknown"),
	RankPoint("RankPoint", 0f),
	CurrentJob("CurrentJob", "Civilian"),
	FPS("FPS", "0"),
	CPS("CPS", 0f),
	Ping("Ping", "0ms"),
	Team("Team", "Unknown"),
	Coordinate("Coordinate", "0, 0, 0"),
	Angle("Angle", 0f),
	Enchant("Enchant", "Enchantment", false);

	private final String id;
	public Object value;
	private final boolean hasLang;
	private final Object defaultValue;
	public String text;
	public boolean doAddStatusGroup;

	private Status(String id, Object value) {
		this.id = id;
		this.value = value;
		this.defaultValue = value;
		this.hasLang = true;
		this.doAddStatusGroup = true;
	}

	private Status(String id, Object value, String text) {
		this.id = id;
		this.value = value;
		this.defaultValue = value;
		this.text = text;
		this.hasLang = false;
	}

	private Status(String id, Object value, boolean doAddStatusGroup) {
		this.id = id;
		this.value = value;
		this.defaultValue = value;
		this.hasLang = true;
		this.doAddStatusGroup = doAddStatusGroup;
	}

	public static Status getStatus(String id) {
		Status result = null;
		for (Status s : values()) {
			if (s.getId().equals(id)) result = s;
		}

		return result;
	}

	public String getId() {
		return id;
	}

	public String getString() {
		if (!hasLang) return String.format(text, value);
		else return String.format(new TextComponentTranslation("yadokaris_shp.render." + id).getUnformattedText().replace("$", "%"), value);
	}

	public String getDefaultString() {
		if (!hasLang) return String.format(text, defaultValue);
		else return String.format(new TextComponentTranslation("yadokaris_shp.render." + id).getUnformattedComponentText().replace("$", "%"), defaultValue);
	}

	public void increment() {
		float f = (float)value;
		value = ++f;
	}

	public void decrement() {
		float f = (float)value;
		value = --f;
	}

	public void add(float f) {
		float vf = (float)value;
		value = vf + f;
	}

	public void subtract(float f) {
		float vf = (float)value;
		value = vf - f;
	}
}
