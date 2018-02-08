package yadokaris_Status_HUD_plus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

	static float killCountSword, killCountBow, deathCount;
	static int xp, totalXp, rankPoint, nexusDamage;
	private final Set<String> jobs = new HashSet<String>(Arrays.asList("Acrobat", "Alchemist", "Archer", "Assassin", "Bard", "Berserker", "BloodMage", "Builder", "Civilian", "Dasher", "Defender", "Enchanter", "Engineer", "Farmer", "Handyman", "Healer", "Hunter", "IceMan", "Immobilizer", "Lumberjack", "Mercenary", "Miner", "Ninja", "Pyro", "RiftWalker", "RobinHood", "Scorpio", "Scout", "Sniper", "Spider", "Spy", "Succubus", "Swapper", "Thor", "Tinkerer", "Transporter", "Vampire", "Warrior", "Wizard"));
	final Map<String, Integer> teams = new HashMap<String, Integer>(){{
		put("Red", 0xFF0000);
		put("Green", 0x00FF00);
		put("Blue", 0x0000FF);
		put("Yellow", 0xFFFF00);
	}};
	static String currentJob = "Civilian", rank = "不明", team = "不明";
	private static boolean isJoin;
	private final List<String> ranks = new ArrayList<String>(Arrays.asList("Annihilator", "GrandMaster-III", "GrandMaster-II", "GrandMaster-I", "Master-III", "Master-II", "Master-I", "Gold-III", "Gold-II", "Gold-I", "Silver-III", "Silver-II", "Silver-I", "Novice-III", "Novice-II", "Novice-I"));

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKill(ClientChatReceivedEvent event) {

		String chat = event.getMessage().getUnformattedText();
		String playername = Rendering.player.getName();

		if (chat.contains(playername)) {

			//Rank
			if (chat.contains("You are currently the rank of")) {
				for (int i = 0; i < ranks.size(); i++) {
					if (chat.indexOf(ranks.get(i)) > 30) {
						this.rank = ranks.get(++i);
						break;
					}
				}

				chat = chat.replaceAll(Rendering.player.getName(), "");
				rankPoint = Integer.parseInt(chat.replaceAll("[^0-9]", ""));
			}

			//Kill
			else if (chat.matches(".*" + playername + ".*\\([A-Z]{3}\\).*\\([A-Z]{3}\\).*")) {
				if (chat.contains("killed")) {
					killCountSword += 1f;
					Status_HUD.totalKillCount += 1f;
					Rendering.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
					Rendering.rate = (ChatEvent.killCountSword + ChatEvent.killCountBow) / (ChatEvent.deathCount + 1f);
					Status_HUD.prop.setProperty("killCount", "" + Status_HUD.totalKillCount);
				}
				else if (chat.contains("shot")){
					killCountBow += 1f;
					Status_HUD.totalKillCount += 1f;
					Rendering.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
					Rendering.rate = (ChatEvent.killCountSword + ChatEvent.killCountBow) / (ChatEvent.deathCount + 1f);
					Status_HUD.prop.setProperty("killCount", "" + Status_HUD.totalKillCount);
				}

				OutputStream writer = null;

				try {
					writer = new FileOutputStream(Status_HUD.cfgFile);
					Status_HUD.prop.storeToXML(writer, "Comment");
					writer.flush();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
				finally {
					if (writer != null) {
						try {
							writer.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			return;
		}

		else if (!chat.contains(playername)) {

			//./killの時はカウントしない
			if (chat.equals("Ouch, that looks like it hurt!")) {
				ChatEvent.deathCount -= 1f;
				Status_HUD.totalDeathCount -= 1f;
				Rendering.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
				Rendering.rate = (ChatEvent.killCountSword + ChatEvent.killCountBow) / (ChatEvent.deathCount + 1f);
				Status_HUD.prop.setProperty("deathCount", "" + Status_HUD.totalDeathCount);

				OutputStream writer = null;

				try {
					writer = new FileOutputStream(Status_HUD.cfgFile);
					Status_HUD.prop.storeToXML(writer, "Comment");
					writer.flush();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
				finally {
					if (writer != null) {
						try {
							writer.close();
						}
						catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
				}
			}

			//Job
			else if (chat.contains("selected")) {
				for (String job : jobs) {
					if (chat.contains(job)) {
						if (chat.indexOf(job) < chat.indexOf("selected")) currentJob = job;
					}
				}
			}

			//XP
			else if (chat.matches(".*\\+[0-9] Shotbow Xp")) {
				int getxp = 0;
				getxp = Integer.parseInt(chat.replaceAll("[^0-9]",""));
				if (getxp != 0) {
					xp += getxp;
					totalXp += getxp;
					if (new NexusBreakEvent().isNexusBreak()) nexusDamage++;
				}
			}

			else if (chat.matches(".*You have [0-9]{1,}xp.*")) totalXp = Integer.parseInt(chat.replaceAll("[^0-9]",""));

			else if (chat.matches(".*You have joined the.*")) {
				isJoin = true;
				new Thread (new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(500);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						isJoin = false;
					}
				}).start();
			}

			else if (chat.matches(".*team.*") && isJoin) {
				for (String team : teams.keySet()) {
					if (chat.contains(team)) {
						this.team = team;
						if (Rendering.isChangeTeamColor) Status_HUD.color = teams.get(team);
						break;
					}
				}
				isJoin = false;
			}

			else if (chat.matches(".*You have been removed from your team") || chat.matches(".*Connecting you to hub")) {
				team = "不明";
				if (Rendering.isChangeTeamColor)Status_HUD.color = ColorSetting.colorcash;
			}

		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent e) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			Rendering.player = Minecraft.getMinecraft().thePlayer;
			Rendering.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
			Rendering.rate = (ChatEvent.killCountSword + ChatEvent.killCountBow) / (ChatEvent.deathCount + 1f);
		}
	}
}
