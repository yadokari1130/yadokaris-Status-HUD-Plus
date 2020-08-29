package yadokaris_Status_HUD_plus;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChatEvent {

	private final Set<String> JOBS = new HashSet<String>(Arrays.asList("Acrobat", "Alchemist", "Archer", "Assassin",
			"Bard", "Berserker", "BloodMage", "Builder", "Civilian", "Dasher", "Defender", "Enchanter", "Engineer",
			"Farmer", "Handyman", "Healer", "Hunter", "IceMan", "Immobilizer", "Lumberjack", "Mercenary", "Miner",
			"Ninja", "Pyro", "RiftWalker", "RobinHood", "Scorpio", "Scout", "Sniper", "Spider", "Spy", "Succubus",
			"Swapper", "Thor", "Tinkerer", "Transporter", "Vampire", "Warrior", "Wizard"));
	static final Map<String, Integer> TEAMS = new HashMap<String, Integer>() {
		{
			put("Red", 0xFF0000);
			put("Green", 0x00FF00);
			put("Blue", 0x0000FF);
			put("Yellow", 0xFFFF00);
		}
	};
	private final List<String> RANKS = new ArrayList<String>(Arrays.asList("Annihilator", "GrandMaster-III",
			"GrandMaster-II", "GrandMaster-I", "Master-III", "Master-II", "Master-I", "Gold-III", "Gold-II", "Gold-I",
			"Silver-III", "Silver-II", "Silver-I", "Novice-III", "Novice-II", "Novice-I"));
	private static boolean isJoin;

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent event) {

		String chat = event.getMessage().getString();

		if (chat.contains(Status_HUD.playerName)) {

			// Rank
			if (chat.contains("You are currently the rank of")) {
				for (int i = 0; i < RANKS.size(); i++) {
					if (chat.indexOf(RANKS.get(i)) > 30) {
						Status.Rank.value = RANKS.get(++i);
						break;
					}
				}

				Status.RankPoint.value = Float.parseFloat(chat.replace(Status_HUD.player.getName().getString(), "").replaceAll("[^0-9]", ""));
			}

			// Kill
			else if (chat.matches(".*" + Status_HUD.playerName + ".*\\([A-Z]{3}\\).*\\([A-Z]{3}\\).*")) {
				if (chat.contains("killed")) {
					if (chat.contains("attacking")) Status.AttackingKillCount.increment();
					else if (chat.contains("defending")) Status.DefendingKillCount.increment();
					Status.KillCountSword.increment();
					Status_HUD.totalKillCount++;
					Status.TotalRate.value = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
					Status.Rate.value = ((float)(Status.KillCountSword.value) + (float)Status.KillCountBow.value) / ((float)Status.DeathCount.value + 1f);
					Status_HUD.prop.setProperty("killCount", "" + Status_HUD.totalKillCount);
				}
				else if (chat.contains("shot")) {
					if (chat.contains("attacking")) Status.AttackingKillCount.increment();
					else if (chat.contains("defending")) Status.DefendingKillCount.increment();
					Status.KillCountBow.increment();
					Status_HUD.totalKillCount++;
					Status.TotalRate.value = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
					Status.Rate.value = ((float)(Status.KillCountSword.value) + (float)Status.KillCountBow.value) / ((float)Status.DeathCount.value + 1f);
					Status_HUD.prop.setProperty("killCount", "" + Status_HUD.totalKillCount);
				}
				Status_HUD.writeProperty();
			}

			//Repair Nexus
			else if (chat.contains("repaired your nexus with the Handyman class!")) {
				Status.RepairPoint.increment();
			}
		}


		else {

			// ./killの時はカウントしない
			if (chat.equals("Ouch! Seems like that hurt.")) {
				Status.DeathCount.decrement();
				Status_HUD.totalDeathCount--;
				Status.TotalRate.value = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
				Status.Rate.value = ((float)(Status.KillCountSword.value) + (float)Status.KillCountBow.value) / ((float)Status.DeathCount.value + 1f);
				Status_HUD.prop.setProperty("deathCount", "" + Status_HUD.totalDeathCount);
				Status_HUD.writeProperty();
			}

			// Job
			else if (chat.contains("Selected")) {
				for (String job : JOBS) {
					if (chat.contains(job)) {
						if (chat.indexOf(job) < chat.indexOf("Selected")) {
							Status.CurrentJob.value = job;
						}
					}
				}
			}

			// XP
			else if (chat.matches(".*\\+[0-9]* Shotbow Xp")) {
				float getXP = Float.parseFloat(chat.replaceAll("[^0-9]", ""));
				if (getXP != 0) {
					try {
						if (Status_HUD.getActionbar().contains(Status_HUD.playerName)) Status.NexusDamage.increment();
					}
					catch (NullPointerException e) {
						e.printStackTrace();
					}
					Status.XP.add(getXP);
					Status.TotalXP.add(getXP);
					Status.RankPoint.subtract((float)getXP / Status_HUD.serverMultiple / Status_HUD.multiple);
					if ((float)Status.RankPoint.value <= 0) (Status_HUD.player).sendChatMessage("/rank");
				}
			}

			else if (chat.matches(".*You have [0-9]*xp.*")) {
				Status.TotalXP.value = Float.parseFloat(chat.replaceAll("[^0-9]", ""));
			}

			else if (chat.contains("You have joined the")) {
				isJoin = true;
				new Thread(() -> {
					try {
						Thread.sleep(500);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					isJoin = false;
				}).start();
			}

			//Rank
			else if (chat.contains("Current rank:")) {
				for (int i = 0; i < RANKS.size(); i++) {
					if (chat.contains(RANKS.get(i))) {
						Status.Rank.value = RANKS.get(i);
						break;
					}
				}
			}

			else if (chat.contains("Points required:")) {
				Status.RankPoint.value = Float.parseFloat(chat.replaceAll("[^0-9]", ""));
			}

			else if (chat.contains("team") && isJoin) {
				for (String team : TEAMS.keySet()) {
					if (chat.contains(team)) {
						Status.Team.value = team;
						if (SHPConfig.doRender.get()) Status_HUD.color = TEAMS.get(team);
						break;
					}
				}
				isJoin = false;

				Status_HUD.doCheck = true;
				Status_HUD.player.sendChatMessage("/multiplier");
			}

			else if (chat.contains("You have been removed from your team") || chat.contains("Reset your password by visiting")) {
				Status.Team.value = "UnKnown";
				Status.CurrentJob.value = "Civilian";
				Status_HUD.doCheck = false;
				if (SHPConfig.doRender.get()) Status_HUD.color = Status_HUD.colorCash;
			}

			else if (chat.contains("Current Multiplier:")) {
				Status_HUD.multiple = Float.parseFloat(chat.replaceAll("[^0-9].", ""));
			}

			if (chat.contains("T") && chat.contains("R") && chat.contains("G") && chat.contains("B") && chat.contains("Y")) {
				String total = chat.substring(0, chat.indexOf("|"));
				String repair = chat.substring(chat.lastIndexOf("|"));

				Status.NexusDamage.value = Float.parseFloat(total.replaceAll("[^0-9]", ""));
				Status.RepairPoint.value = Float.parseFloat(repair.replaceAll("[^0-9]", ""));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {
		if (Status_HUD.player == null && Minecraft.getInstance().player != null) {
			ClientPlayerEntity player = Minecraft.getInstance().player;
			Status_HUD.player = player;
			Status_HUD.playerName = player.getName().getString();
			Status.Text.value = player.getName().getString();

			new Thread(() -> {
				String update = null;
				try {
					update = IOUtils.toString(new URL("https://raw.githubusercontent.com/yadokari1130/yadokaris-Status-HUD-Plus/master/update.json"), "UTF-8");
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				Gson gson = new Gson();
				Map map = gson.fromJson(update, Map.class);

				String latest = ((Map<String, String>) map.get("promos")).get("1.16.1-latest");

				if (!latest.equals(Status_HUD.version)) {
					new Thread(() -> {
						try {
							Thread.sleep(5000);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						ClickEvent linkClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, (String)map.get("homepage"));
						Style clickableStyle = Style.field_240709_b_.func_240715_a_(linkClickEvent).func_240712_a_(TextFormatting.AQUA);
						Style color = Style.field_240709_b_.func_240712_a_(TextFormatting.GREEN);
						player.sendMessage(new TranslationTextComponent("yadokaris_shp.update.message1").func_230530_a_(color), null);
						player.sendMessage(new TranslationTextComponent("yadokaris_shp.update.message2").func_230530_a_(clickableStyle), null);
						player.sendMessage(new TranslationTextComponent("yadokaris_shp.update.infomation"), null);
						player.sendMessage(new StringTextComponent("----------------------------------------------------------------------"), null);
						player.sendMessage(new StringTextComponent(((Map<String, String>) map.get("1.16.1")).get(latest)), null);
						player.sendMessage(new StringTextComponent("----------------------------------------------------------------------"), null);
					}).start();
				}

				Status_HUD.serverMultiple = Float.parseFloat((String) map.get("serverMultiple"));
			}).start();
		}
	}
}
