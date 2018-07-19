package yadokaris_Status_HUD_plus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChatEvent {

	private final Set<String> JOBS = new HashSet<String>(Arrays.asList("Acrobat", "Alchemist", "Archer", "Assassin",
			"Bard", "Berserker", "BloodMage", "Builder", "Civilian", "Dasher", "Defender", "Enchanter", "Engineer",
			"Farmer", "Handyman", "Healer", "Hunter", "IceMan", "Immobilizer", "Lumberjack", "Mercenary", "Miner",
			"Ninja", "Pyro", "RiftWalker", "RobinHood", "Scorpio", "Scout", "Sniper", "Spider", "Spy", "Succubus",
			"Swapper", "Thor", "Tinkerer", "Transporter", "Vampire", "Warrior", "Wizard"));
	final Map<String, Integer> TEAMS = new HashMap<String, Integer>() {
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
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent event) {

		String chat = event.getMessage().getUnformattedText();

		if (chat.contains(Status_HUD.playerName)) {

			// Rank
			if (chat.contains("You are currently the rank of")) {
				for (int i = 0; i < RANKS.size(); i++) {
					if (chat.indexOf(RANKS.get(i)) > 30) {
						Status_HUD.rank = RANKS.get(++i);
						Rendering.updateText(11);
						break;
					}
				}

				Status_HUD.rankPoint = Integer.parseInt(chat.replace(Status_HUD.player.getName(), "").replaceAll("[^0-9]", ""));
				Rendering.updateText(12);
			}

			// Kill
			else if (chat.matches(".*" + Status_HUD.playerName + ".*\\([A-Z]{3}\\).*\\([A-Z]{3}\\).*")) {
				if (chat.contains("killed")) {
					if (chat.contains("attacking")) Status_HUD.attackingKillCount++;
					else if (chat.contains("defending")) Status_HUD.defendingKillCount++;
					Status_HUD.killCountSword++;
					Status_HUD.totalKillCount++;
					Status_HUD.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
					Status_HUD.rate = (Status_HUD.killCountSword + Status_HUD.killCountBow) / (Status_HUD.deathCount + 1f);
					Status_HUD.prop.setProperty("killCount", "" + Status_HUD.totalKillCount);
				}
				else if (chat.contains("shot")) {
					if (chat.contains("attacking")) Status_HUD.attackingKillCount++;
					else if (chat.contains("defending")) Status_HUD.defendingKillCount++;
					Status_HUD.killCountBow++;
					Status_HUD.totalKillCount++;
					Status_HUD.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
					Status_HUD.rate = (Status_HUD.killCountSword + Status_HUD.killCountBow) / (Status_HUD.deathCount + 1f);
					Status_HUD.prop.setProperty("killCount", "" + Status_HUD.totalKillCount);
				}
				Rendering.updateAllTexts();
				Status_HUD.writeProperty();
			}
		}

		
		else if (!chat.contains(Status_HUD.playerName)) {

			// ./killの時はカウントしない
			if (chat.equals("Ouch, that looks like it hurt!")) {
				Status_HUD.deathCount--;
				Status_HUD.totalDeathCount--;
				Status_HUD.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
				Status_HUD.rate = (Status_HUD.killCountSword + Status_HUD.killCountBow) / (Status_HUD.deathCount + 1f);
				Status_HUD.prop.setProperty("deathCount", "" + Status_HUD.totalDeathCount);
				Status_HUD.writeProperty();
				Rendering.updateTexts(5, 6, 7);
			}

			// Job
			else if (chat.contains("selected")) {
				for (String job : JOBS) {
					if (chat.contains(job)) {
						if (chat.indexOf(job) < chat.indexOf("selected")) {
							Status_HUD.currentJob = job;
							Rendering.updateText(13);
						}
					}
				}
			}

			// XP
			else if (chat.matches(".*\\+[0-9]* Shotbow Xp")) {
				int getxp = Integer.parseInt(chat.replaceAll("[^0-9]", ""));
				if (getxp != 0) {
					if (new NexusBreakEvent().isNexusBreak()) Status_HUD.nexusDamage++;
					Status_HUD.xp += getxp;
					Status_HUD.totalXp += getxp;
				}
				Rendering.updateTexts(9, 10);
			}

			else if (chat.matches(".*You have [0-9]*xp.*")) { 
				Status_HUD.totalXp = Integer.parseInt(chat.replaceAll("[^0-9]", ""));
				Rendering.updateText(10);
			}

			else if (chat.matches(".*You have joined the.*")) {
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
			
			else if (chat.matches(".*team.*") && isJoin) {
				for (String team : TEAMS.keySet()) {
					if (chat.contains(team)) {
						Status_HUD.team = team;
						Rendering.updateText(15);
						if (Status_HUD.doRender) Status_HUD.color = TEAMS.get(team);
						break;
					}
				}
				isJoin = false;
			}

			else if (chat.matches(".*You have been removed from your team") || chat.matches(".*Reset your password by visiting.*")) {
				Status_HUD.team = "UnKnown";
				Rendering.updateText(15);
				if (Status_HUD.doRender) Status_HUD.color = Status_HUD.colorCash;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {
		if (Status_HUD.player == null && Minecraft.getMinecraft().thePlayer != null) {
			Status_HUD.player = Minecraft.getMinecraft().thePlayer;
			Status_HUD.playerName = Status_HUD.player.getName();
			Rendering.updateText(0);
		}
	}
}
