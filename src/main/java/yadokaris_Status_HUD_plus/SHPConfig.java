package yadokaris_Status_HUD_plus;

import net.minecraftforge.common.ForgeConfigSpec;

public class SHPConfig {
	public static ForgeConfigSpec.DoubleValue x, y;
	public static ForgeConfigSpec.BooleanValue[] doShow = new ForgeConfigSpec.BooleanValue[19];
	public static ForgeConfigSpec.BooleanValue doRender, isRainbow, doChangeTeamColor, doRenderWhenStart;
	public static ForgeConfigSpec.DoubleValue fontSize;
	public static ForgeConfigSpec.ConfigValue<String> colors, text;

	public static void init(ForgeConfigSpec.Builder client) {

		client.comment("yadokari's Status HUD Plus Config").push("general");

		colors = client.comment("文字の色を設定します。16進法で指定してください。").define("yadokaris_shp.color", "0xFFFFFF");

		doShow[Status.Text.ordinal()] = client.comment("ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowText", true);
		doShow[Status.KillCountSword.ordinal()] = client.comment("剣キルの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowSwordKill", true);
		doShow[Status.KillCountBow.ordinal()] = client.comment("弓キルの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowBowKill", true);
		doShow[Status.AttackingKillCount.ordinal()] = client.comment("ネクサスキルの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowAttackingKill", true);
		doShow[Status.DefendingKillCount.ordinal()] = client.comment("防衛キルの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowDefendingKill", true);
		doShow[Status.DeathCount.ordinal()] = client.comment("デス数の表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowDeath", true);
		doShow[Status.Rate.ordinal()] = client.comment("K/Dレートの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowRate", true);
		doShow[Status.TotalRate.ordinal()] = client.comment("総合K/Dレートの表示(true) / 非表示(false)を設定します。").define("yadkaris_shp.doShowTotalRate", true);
		doShow[Status.NexusDamage.ordinal()] = client.comment("ネクサスダメージの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowNexusDamage", true);
		doShow[Status.RepairPoint.ordinal()] = client.comment("回復したネクサスポイントの表示(true) / 非表示(false)を表示します。").define("yadokaris_shp.doShowRepairPoint", true);
		doShow[Status.XP.ordinal()] = client.comment("獲得xpの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowXP", true);
		doShow[Status.TotalXP.ordinal()] = client.comment("所持xpの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowTotalXP", true);
		doShow[Status.Rank.ordinal()] = client.comment("ランクの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowRank", true);
		doShow[Status.RankPoint.ordinal()] = client.comment("ランクポイントの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowRankPoint", true);
		doShow[Status.CurrentJob.ordinal()] = client.comment("現在の職業の表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowJob", true);
		doShow[Status.FPS.ordinal()] = client.comment("FPSの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowFPS", true);
		doShow[Status.CPS.ordinal()] = client.comment("CPSの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowCPS", true);
		doShow[Status.Ping.ordinal()] = client.comment("Pingの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowPing", true);
		doShow[Status.Team.ordinal()] = client.comment("所属チームの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doShowTeam", true);
		text = client.comment("ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。").define("yadokaris_shp.text", "%sのステータス");
		x = client.comment("ステータスの画面上のx座標を設定します。").defineInRange("yadokaris_shp.x", 2f, 0f, Integer.MAX_VALUE);
		y = client.comment("ステータスの画面上のy座標を設定します。").defineInRange("yadokaris_shp.y", 2f, 0f, Integer.MAX_VALUE);
		fontSize = client.comment("ステータスの文字サイズを設定します。").defineInRange("yadokaris_shp.fontSize", 1d, 0d, 100d);
		doRenderWhenStart = client.comment("起動時のステータスの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doRenderWhenStart", true);
		doRender = doRenderWhenStart;
		isRainbow = client.comment("ステータスの文字を虹色にする(true) / しない(false)を設定します。").define("yadokaris_shp.isRainbow", false);
		doChangeTeamColor = client.comment("テキストの色を所属チームに合わせて変える(true) / 変えない(false)を設定します。").define("yadokaris_shp.doChangeTeamColor", false);
		client.pop();
	}
}
