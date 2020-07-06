package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import net.minecraft.util.text.TextComponentTranslation;

public class SettingGui extends JFrame implements ActionListener {

	private final JPanel contentPane;
	private final JCheckBox checkBoxIsEnable = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doEnable").getUnformattedText());
	private final JCheckBox checkBoxDoShowText = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowText").getUnformattedText());
	private final JCheckBox checkBoxDoShowSword = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowSword").getUnformattedText());
	private final JCheckBox checkBoxDoShowBow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowBow").getUnformattedText());
	private final JCheckBox checkBoxDoShowAttacking = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowAttackingKill").getUnformattedText());
	private final JCheckBox checkBoxDoShowDefending = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowDefendingKill").getUnformattedText());
	private final JCheckBox checkBoxDoShowDeath = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowDeath").getUnformattedText());
	private final JCheckBox checkBoxDoShowRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowRate").getUnformattedText());
	private final JCheckBox checkBoxDoShowTotalRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowTotalRate").getUnformattedText());
	private final JCheckBox checkBoxDoShowNexusDamage = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowNexusDamage").getUnformattedText());
	private final JCheckBox checkBoxDoShowRepairPoint = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowRepairPoint").getUnformattedText());
	private final JCheckBox checkBoxDoShowGetxp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowGetxp").getUnformattedText());
	private final JCheckBox checkBoxDoShowHavexp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowHavexp").getUnformattedText());
	private final JCheckBox checkBoxDoShowRank = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowRank").getUnformattedText());
	private final JCheckBox checkBoxDoShowRankPoint = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowRankPoint").getUnformattedText());
	private final JCheckBox checkBoxDoShowCurrentJob = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowCurrentJob").getUnformattedText());
	private final JCheckBox checkBoxDoShowFPS = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowFPS").getUnformattedText());
	private final JCheckBox checkBoxDoShowCPS = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowCPS").getUnformattedText());
	private final JCheckBox checkBoxDoShowPing = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowPing").getUnformattedText());
	private final JCheckBox checkBoxDoShowTeam = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowTeam").getUnformattedText());
	private final JCheckBox checkBoxDoChangeTeamColor = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doChangeTeamColor").getUnformattedText());
	private final JSpinner spinner_x = new JSpinner();
	private final JSpinner spinner_y = new JSpinner();
	private final JSpinner spinner_size = new JSpinner();
	private final JTextField textField = new JTextField();
	private final JCheckBox checkBoxIsRainbow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isRainbow").getUnformattedText());

	public SettingGui() {
		setTitle("Status HUD Plus Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 520);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button_color = new JButton(new TextComponentTranslation("yadokaris_shp.setting.colorButton").getUnformattedText());
		button_color.setBounds(315, 309, 144, 27);
		button_color.addActionListener(new ColorSetting());
		contentPane.add(button_color);

		checkBoxIsEnable.setBackground(Color.WHITE);
		checkBoxIsEnable.setSelected(Status_HUD.conf.getBoolean("doRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。"));
		checkBoxIsEnable.setBounds(22, 6, 254, 21);
		contentPane.add(checkBoxIsEnable);

		checkBoxDoShowText.setBackground(Color.WHITE);
		checkBoxDoShowText.setSelected(Status_HUD.doShow[Status.Text.ordinal()]);
		checkBoxDoShowText.setBounds(22, 29, 151, 21);
		contentPane.add(checkBoxDoShowText);

		checkBoxDoShowSword.setBackground(Color.WHITE);
		checkBoxDoShowSword.setSelected(Status_HUD.doShow[Status.KillCountSword.ordinal()]);
		checkBoxDoShowSword.setBounds(22, 52, 151, 21);
		contentPane.add(checkBoxDoShowSword);

		checkBoxDoShowBow.setBackground(Color.WHITE);
		checkBoxDoShowBow.setSelected(Status_HUD.doShow[Status.KillCountBow.ordinal()]);
		checkBoxDoShowBow.setBounds(22, 75, 151, 21);
		contentPane.add(checkBoxDoShowBow);

		checkBoxDoShowAttacking.setSelected(Status_HUD.doShow[Status.AttackingKillCount.ordinal()]);
		checkBoxDoShowAttacking.setBackground(Color.WHITE);
		checkBoxDoShowAttacking.setBounds(22, 98, 151, 21);
		contentPane.add(checkBoxDoShowAttacking);

		checkBoxDoShowDefending.setSelected(Status_HUD.doShow[Status.DefendingKillCount.ordinal()]);
		checkBoxDoShowDefending.setBackground(Color.WHITE);
		checkBoxDoShowDefending.setBounds(22, 121, 151, 21);
		contentPane.add(checkBoxDoShowDefending);

		checkBoxDoShowDeath.setBackground(Color.WHITE);
		checkBoxDoShowDeath.setSelected(Status_HUD.doShow[Status.DeathCount.ordinal()]);
		checkBoxDoShowDeath.setBounds(22, 144, 151, 21);
		contentPane.add(checkBoxDoShowDeath);

		checkBoxDoShowRate.setBackground(Color.WHITE);
		checkBoxDoShowRate.setSelected(Status_HUD.doShow[Status.Rate.ordinal()]);
		checkBoxDoShowRate.setBounds(22, 167, 151, 21);
		contentPane.add(checkBoxDoShowRate);

		checkBoxDoShowTotalRate.setBackground(Color.WHITE);
		checkBoxDoShowTotalRate.setSelected(Status_HUD.doShow[Status.TotalRate.ordinal()]);
		checkBoxDoShowTotalRate.setBounds(22, 190, 151, 21);
		contentPane.add(checkBoxDoShowTotalRate);

		checkBoxDoShowNexusDamage.setSelected(Status_HUD.doShow[Status.NexusDamage.ordinal()]);
		checkBoxDoShowNexusDamage.setBackground(Color.WHITE);
		checkBoxDoShowNexusDamage.setBounds(22, 213, 213, 21);
		contentPane.add(checkBoxDoShowNexusDamage);

		checkBoxDoShowRepairPoint.setSelected(Status_HUD.doShow[Status.RepairPoint.ordinal()]);
		checkBoxDoShowRepairPoint.setBackground(Color.WHITE);
		checkBoxDoShowRepairPoint.setBounds(22, 236, 254, 21);
		contentPane.add(checkBoxDoShowRepairPoint);

		checkBoxDoShowGetxp.setBackground(Color.WHITE);
		checkBoxDoShowGetxp.setSelected(Status_HUD.doShow[Status.XP.ordinal()]);
		checkBoxDoShowGetxp.setBounds(22, 259, 151, 21);
		contentPane.add(checkBoxDoShowGetxp);

		checkBoxDoShowHavexp.setBackground(Color.WHITE);
		checkBoxDoShowHavexp.setSelected(Status_HUD.doShow[Status.TotalXP.ordinal()]);
		checkBoxDoShowHavexp.setBounds(22, 282, 151, 21);
		contentPane.add(checkBoxDoShowHavexp);

		checkBoxDoShowRank.setSelected(Status_HUD.doShow[Status.Rank.ordinal()]);
		checkBoxDoShowRank.setBackground(Color.WHITE);
		checkBoxDoShowRank.setBounds(22, 305, 151, 21);
		contentPane.add(checkBoxDoShowRank);

		checkBoxDoShowRankPoint.setSelected(Status_HUD.doShow[Status.RankPoint.ordinal()]);
		checkBoxDoShowRankPoint.setBackground(Color.WHITE);
		checkBoxDoShowRankPoint.setBounds(22, 328, 213, 21);
		contentPane.add(checkBoxDoShowRankPoint);

		checkBoxDoShowCurrentJob.setBackground(Color.WHITE);
		checkBoxDoShowCurrentJob.setSelected(Status_HUD.doShow[Status.CurrentJob.ordinal()]);
		checkBoxDoShowCurrentJob.setBounds(22, 351, 151, 21);
		contentPane.add(checkBoxDoShowCurrentJob);

		checkBoxDoShowFPS.setSelected(Status_HUD.doShow[Status.FPS.ordinal()]);
		checkBoxDoShowFPS.setBackground(Color.WHITE);
		checkBoxDoShowFPS.setBounds(22, 374, 151, 21);
		contentPane.add(checkBoxDoShowFPS);

		checkBoxDoShowCPS.setSelected(Status_HUD.doShow[Status.CPS.ordinal()]);
		checkBoxDoShowCPS.setBackground(Color.WHITE);
		checkBoxDoShowCPS.setBounds(22, 397, 151, 21);
		contentPane.add(checkBoxDoShowCPS);

		checkBoxDoShowPing.setSelected(Status_HUD.doShow[Status.Ping.ordinal()]);
		checkBoxDoShowPing.setBackground(Color.WHITE);
		checkBoxDoShowPing.setBounds(22, 420, 151, 21);
		contentPane.add(checkBoxDoShowPing);

		checkBoxDoShowTeam.setSelected(Status_HUD.doShow[Status.Team.ordinal()]);
		checkBoxDoShowTeam.setBackground(Color.WHITE);
		checkBoxDoShowTeam.setBounds(22, 444, 151, 21);
		contentPane.add(checkBoxDoShowTeam);

		spinner_x.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_x.setValue(Status_HUD.x);
		spinner_x.setBounds(302, 138, 157, 27);
		contentPane.add(spinner_x);

		spinner_y.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_y.setValue(Status_HUD.y);
		spinner_y.setBounds(302, 179, 157, 27);
		contentPane.add(spinner_y);

		spinner_size.setModel(new SpinnerNumberModel(1, 0, 100, 0.01));
		spinner_size.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_size.setValue(Status_HUD.fontSize);
		spinner_size.setBounds(302, 254, 157, 27);
		contentPane.add(spinner_size);

		textField.setText(Status_HUD.text);
		textField.setBounds(205, 61, 254, 27);
		textField.setColumns(10);
		contentPane.add(textField);

		checkBoxIsRainbow.setBackground(Color.WHITE);
		checkBoxIsRainbow.setSelected(Status_HUD.isRainbow);
		checkBoxIsRainbow.setBounds(239, 351, 151, 21);
		contentPane.add(checkBoxIsRainbow);

		checkBoxDoChangeTeamColor.setSelected(Status_HUD.doChangeTeamColor);
		checkBoxDoChangeTeamColor.setBackground(Color.WHITE);
		checkBoxDoChangeTeamColor.setBounds(239, 374, 220, 21);
		contentPane.add(checkBoxDoChangeTeamColor);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TextComponentTranslation("yadokaris_shp.setting.settingText").getUnformattedText());
		textPane.setBounds(205, 37, 106, 21);
		contentPane.add(textPane);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TextComponentTranslation("yadokaris_shp.setting.settingPoint").getUnformattedText());
		textPane_1.setBounds(302, 110, 123, 21);
		contentPane.add(textPane_1);

		JTextPane txtpnX = new JTextPane();
		txtpnX.setText("X");
		txtpnX.setBounds(277, 144, 13, 21);
		contentPane.add(txtpnX);

		JTextPane txtpnY = new JTextPane();
		txtpnY.setText("Y");
		txtpnY.setBounds(277, 185, 13, 21);
		contentPane.add(txtpnY);

		JButton button_set = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingSave").getUnformattedText());
		button_set.setBounds(354, 444, 106, 27);
		button_set.addActionListener(this);
		contentPane.add(button_set);

		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText(new TextComponentTranslation("yadokaris_shp.setting.settingFontSize").getUnformattedText());
		textPane_2.setBounds(302, 223, 123, 21);
		contentPane.add(textPane_2);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Status_HUD.doShow[Status.Text.ordinal()] = checkBoxDoShowText.isSelected();
		Status_HUD.doShow[Status.KillCountSword.ordinal()] = checkBoxDoShowSword.isSelected();
		Status_HUD.doShow[Status.KillCountBow.ordinal()] = checkBoxDoShowBow.isSelected();
		Status_HUD.doShow[Status.AttackingKillCount.ordinal()] = checkBoxDoShowAttacking.isSelected();
		Status_HUD.doShow[Status.DefendingKillCount.ordinal()] = checkBoxDoShowDefending.isSelected();
		Status_HUD.doShow[Status.DeathCount.ordinal()] = checkBoxDoShowDeath.isSelected();
		Status_HUD.doShow[Status.Rate.ordinal()] = checkBoxDoShowRate.isSelected();
		Status_HUD.doShow[Status.TotalRate.ordinal()] = checkBoxDoShowTotalRate.isSelected();
		Status_HUD.doShow[Status.NexusDamage.ordinal()] = checkBoxDoShowNexusDamage.isSelected();
		Status_HUD.doShow[Status.RepairPoint.ordinal()] = checkBoxDoShowRepairPoint.isSelected();
		Status_HUD.doShow[Status.XP.ordinal()] = checkBoxDoShowGetxp.isSelected();
		Status_HUD.doShow[Status.TotalXP.ordinal()] = checkBoxDoShowHavexp.isSelected();
		Status_HUD.doShow[Status.Rank.ordinal()] = checkBoxDoShowRank.isSelected();
		Status_HUD.doShow[Status.RankPoint.ordinal()] = checkBoxDoShowRankPoint.isSelected();
		Status_HUD.doShow[Status.CurrentJob.ordinal()] = checkBoxDoShowCurrentJob.isSelected();
		Status_HUD.doShow[Status.FPS.ordinal()] = checkBoxDoShowFPS.isSelected();
		Status_HUD.doShow[Status.CPS.ordinal()] = checkBoxDoShowCPS.isSelected();
		Status_HUD.doShow[Status.Ping.ordinal()] = checkBoxDoShowPing.isSelected();
		Status_HUD.doShow[Status.Team.ordinal()] = checkBoxDoShowTeam.isSelected();
		Status_HUD.text = textField.getText();
		Status_HUD.x = (int)spinner_x.getValue();
		Status_HUD.y = (int)spinner_y.getValue();
		Status_HUD.fontSize = (double) spinner_size.getValue();
		Status_HUD.isRainbow = checkBoxIsRainbow.isSelected();
		Status_HUD.doChangeTeamColor = checkBoxDoChangeTeamColor.isSelected();
		if (Status_HUD.doChangeTeamColor && ChatEvent.TEAMS.containsKey(Status_HUD.team)) Status_HUD.color = ChatEvent.TEAMS.get(Status_HUD.team);
		else Status_HUD.color = Status_HUD.colorCash;

		Status_HUD.conf.get("render", "doShowText", true, "ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.Text.ordinal()]);
		Status_HUD.conf.get("render", "doShowSwordKill", true, "剣キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.KillCountSword.ordinal()]);
		Status_HUD.conf.get("render", "doShowBowKill", true, "弓キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.KillCountBow.ordinal()]);
		Status_HUD.conf.get("render", "doShowAttackingKill", true, "ネクサスキルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.AttackingKillCount.ordinal()]);;
		Status_HUD.conf.get("render", "doShowDefendingKill", true, "防衛キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.DefendingKillCount.ordinal()]);;
		Status_HUD.conf.get("render", "doShowDeath", true, "デス数の表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.DeathCount.ordinal()]);
		Status_HUD.conf.get("render", "doShowRate", true, "K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.Rate.ordinal()]);
		Status_HUD.conf.get("render", "doShowTotalRate", true, "総合K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.TotalRate.ordinal()]);
		Status_HUD.conf.get("render", "doShowNexusDamage", true, "ネクサスダメージの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.NexusDamage.ordinal()]);
		Status_HUD.conf.get("render", "doShowRepairPoint", true, "回復したネクサスポイントの表示(true) / 非表示(false)を表示します。").set(Status_HUD.doShow[Status.RepairPoint.ordinal()]);;
		Status_HUD.conf.get("render", "doShowXP", true, "獲得xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.XP.ordinal()]);
		Status_HUD.conf.get("render", "doShowTotalXP", true, "所持xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.TotalXP.ordinal()]);
		Status_HUD.conf.get("render", "doShowRank", true, "ランクの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.Rank.ordinal()]);
		Status_HUD.conf.get("render", "doShowRankPoint", true, "ランクポイントの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.RankPoint.ordinal()]);
		Status_HUD.conf.get("render", "doShowJob", true, "現在の職業の表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.CurrentJob.ordinal()]);
		Status_HUD.conf.get("render", "doShowFPS", true, "FPSの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.FPS.ordinal()]);
		Status_HUD.conf.get("render", "doShowCPS", true, "CPSの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.CPS.ordinal()]);
		Status_HUD.conf.get("render", "doShowPing", true, "Pingの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.Ping.ordinal()]);
		Status_HUD.conf.get("render", "doShowTeam", true, "所属チームの表示(true) / 非表示(false)を設定します。").set(Status_HUD.doShow[Status.Team.ordinal()]);
		Status_HUD.conf.get("render", "text", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。").set(Status_HUD.text);
		Status_HUD.conf.get("render", "x", 2, "ステータスの画面上のx座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.x);
		Status_HUD.conf.get("render", "y", 2, "ステータスの画面上のy座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.y);
		Status_HUD.conf.get("render", "fontSize", 1, "ステータスの文字サイズを設定します。", 0, 100).set(Status_HUD.fontSize);
		Status_HUD.conf.get("render", "isRainbow", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。").set(Status_HUD.isRainbow);
		Status_HUD.conf.get("render", "doChangeTeamColor", false, "テキストの色を所属チームに合わせて変える(true) / 変えない(false)を設定します。").set(Status_HUD.doChangeTeamColor);
		Status_HUD.conf.get("render", "doRenderWhenStart", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。").set(checkBoxIsEnable.isSelected());
		Status_HUD.conf.get("render", "color", 0xFF0000, "文字の色を設定します。16進数で指定してください。").set("0x" + Integer.toHexString(Status_HUD.colorCash));

		Status_HUD.conf.save();
		Rendering.updateAllTexts();

		JOptionPane.showMessageDialog(new SettingGui(), new TextComponentTranslation("yadokaris_shp.setting.save").getUnformattedText());
	}
}
