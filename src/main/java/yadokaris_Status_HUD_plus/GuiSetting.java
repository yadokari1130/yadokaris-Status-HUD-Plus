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
import javax.swing.border.EmptyBorder;

import net.minecraft.util.text.TextComponentTranslation;

public class GuiSetting extends JFrame implements ActionListener{

	private final JPanel contentPane;
	private final JCheckBox checkBoxIsEnable = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isEnable").getUnformattedText());
	private final JCheckBox checkBoxIsShowText = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowText").getUnformattedText());
	private final JCheckBox checkBoxIsShowSword = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowSword").getUnformattedText());
	private final JCheckBox checkBoxIsShowBow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowBow").getUnformattedText());
	private final JCheckBox checkBoxIsShowDeath = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowDeath").getUnformattedText());
	private final JCheckBox checkBoxIsShowRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRate").getUnformattedText());
	private final JCheckBox checkBoxIsShowTotalRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowTotalRate").getUnformattedText());
	private final JCheckBox checkBoxIsShowNexusDamage = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowNexusDamage").getUnformattedText());
	private final JCheckBox checkBoxIsShowGetxp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowGetxp").getUnformattedText());
	private final JCheckBox checkBoxIsShowHavexp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowHavexp").getUnformattedText());
	private final JCheckBox checkBoxIsShowRank = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRank").getUnformattedText());
	private final JCheckBox checkBoxIsShowRankPoint = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRankPoint").getUnformattedText());
	private final JCheckBox checkBoxIsShowCurrentJob = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowCurrentJob").getUnformattedText());
	private final JCheckBox checkBoxIsShowFPS = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowFPS").getUnformattedText());
	private final JSpinner spinner_x = new JSpinner();
	private final JSpinner spinner_y = new JSpinner();
	private final JTextField textField = new JTextField();
	private final JCheckBox checkBoxIsRainbow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isRainbow").getUnformattedText());

	public GuiSetting() {
		setTitle("Status HUD Plus Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button_color = new JButton(new TextComponentTranslation("yadokaris_shp.setting.colorButton").getUnformattedText());
		button_color.setBounds(315, 255, 144, 27);
		button_color.addActionListener(new ColorSetting());
		contentPane.add(button_color);

		checkBoxIsEnable.setBackground(Color.WHITE);
		checkBoxIsEnable.setSelected(Status_HUD.cfg.getBoolean("isRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。"));
		checkBoxIsEnable.setBounds(22, 21, 254, 21);
		contentPane.add(checkBoxIsEnable);

		checkBoxIsShowText.setBackground(Color.WHITE);
		checkBoxIsShowText.setSelected(Status_HUD.isShow[0]);
		checkBoxIsShowText.setBounds(22, 44, 151, 21);
		contentPane.add(checkBoxIsShowText);

		checkBoxIsShowSword.setBackground(Color.WHITE);
		checkBoxIsShowSword.setSelected(Status_HUD.isShow[1]);
		checkBoxIsShowSword.setBounds(22, 67, 151, 21);
		contentPane.add(checkBoxIsShowSword);

		checkBoxIsShowBow.setBackground(Color.WHITE);
		checkBoxIsShowBow.setSelected(Status_HUD.isShow[2]);
		checkBoxIsShowBow.setBounds(22, 90, 151, 21);
		contentPane.add(checkBoxIsShowBow);

		checkBoxIsShowDeath.setBackground(Color.WHITE);
		checkBoxIsShowDeath.setSelected(Status_HUD.isShow[3]);
		checkBoxIsShowDeath.setBounds(22, 113, 151, 21);
		contentPane.add(checkBoxIsShowDeath);

		checkBoxIsShowRate.setBackground(Color.WHITE);
		checkBoxIsShowRate.setSelected(Status_HUD.isShow[4]);
		checkBoxIsShowRate.setBounds(22, 136, 151, 21);
		contentPane.add(checkBoxIsShowRate);

		checkBoxIsShowTotalRate.setBackground(Color.WHITE);
		checkBoxIsShowTotalRate.setSelected(Status_HUD.isShow[5]);
		checkBoxIsShowTotalRate.setBounds(22, 159, 151, 21);
		contentPane.add(checkBoxIsShowTotalRate);

		checkBoxIsShowNexusDamage.setSelected(Status_HUD.isShow[6]);
		checkBoxIsShowNexusDamage.setBackground(Color.WHITE);
		checkBoxIsShowNexusDamage.setBounds(22, 182, 151, 21);
		contentPane.add(checkBoxIsShowNexusDamage);

		checkBoxIsShowGetxp.setBackground(Color.WHITE);
		checkBoxIsShowGetxp.setSelected(Status_HUD.isShow[7]);
		checkBoxIsShowGetxp.setBounds(22, 205, 151, 21);
		contentPane.add(checkBoxIsShowGetxp);

		checkBoxIsShowHavexp.setBackground(Color.WHITE);
		checkBoxIsShowHavexp.setSelected(Status_HUD.isShow[8]);
		checkBoxIsShowHavexp.setBounds(22, 228, 151, 21);
		contentPane.add(checkBoxIsShowHavexp);

		checkBoxIsShowRank.setSelected(Status_HUD.isShow[9]);
		checkBoxIsShowRank.setBackground(Color.WHITE);
		checkBoxIsShowRank.setBounds(22, 251, 151, 21);
		contentPane.add(checkBoxIsShowRank);

		checkBoxIsShowRankPoint.setSelected(Status_HUD.isShow[10]);
		checkBoxIsShowRankPoint.setBackground(Color.WHITE);
		checkBoxIsShowRankPoint.setBounds(22, 274, 213, 21);
		contentPane.add(checkBoxIsShowRankPoint);

		checkBoxIsShowCurrentJob.setBackground(Color.WHITE);
		checkBoxIsShowCurrentJob.setSelected(Status_HUD.isShow[11]);
		checkBoxIsShowCurrentJob.setBounds(22, 297, 151, 21);
		contentPane.add(checkBoxIsShowCurrentJob);

		checkBoxIsShowFPS.setSelected(Status_HUD.isShow[12]);
		checkBoxIsShowFPS.setBackground(Color.WHITE);
		checkBoxIsShowFPS.setBounds(22, 320, 151, 21);
		contentPane.add(checkBoxIsShowFPS);

		spinner_x.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_x.setValue(Status_HUD.x);
		spinner_x.setBounds(302, 151, 157, 27);
		contentPane.add(spinner_x);

		spinner_y.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_y.setValue(Status_HUD.y);
		spinner_y.setBounds(302, 203, 157, 27);
		contentPane.add(spinner_y);

		textField.setText(Status_HUD.text);
		textField.setBounds(205, 76, 254, 27);
		textField.setColumns(10);
		contentPane.add(textField);

		checkBoxIsRainbow.setBackground(Color.WHITE);
		checkBoxIsRainbow.setSelected(Rendering.isRainbow);
		checkBoxIsRainbow.setBounds(315, 288, 151, 21);
		contentPane.add(checkBoxIsRainbow);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TextComponentTranslation("yadokaris_shp.setting.settingText").getUnformattedText());
		textPane.setBounds(205, 52, 106, 21);
		contentPane.add(textPane);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TextComponentTranslation("yadokaris_shp.setting.settingPoint").getUnformattedText());
		textPane_1.setBounds(302, 123, 123, 21);
		contentPane.add(textPane_1);

		JTextPane txtpnX = new JTextPane();
		txtpnX.setText("X");
		txtpnX.setBounds(277, 157, 13, 21);
		contentPane.add(txtpnX);

		JTextPane txtpnY = new JTextPane();
		txtpnY.setText("Y");
		txtpnY.setBounds(277, 209, 13, 21);
		contentPane.add(txtpnY);

		JButton button_set = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingSave").getUnformattedText());
		button_set.setBounds(365, 325, 106, 27);
		button_set.addActionListener(this);
		contentPane.add(button_set);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Status_HUD.isShow[0] = checkBoxIsShowText.isSelected();
		Status_HUD.isShow[1] = checkBoxIsShowSword.isSelected();
		Status_HUD.isShow[2] = checkBoxIsShowBow.isSelected();
		Status_HUD.isShow[3] = checkBoxIsShowDeath.isSelected();
		Status_HUD.isShow[4] = checkBoxIsShowRate.isSelected();
		Status_HUD.isShow[5] = checkBoxIsShowTotalRate.isSelected();
		Status_HUD.isShow[6] = checkBoxIsShowNexusDamage.isSelected();
		Status_HUD.isShow[7] = checkBoxIsShowGetxp.isSelected();
		Status_HUD.isShow[8] = checkBoxIsShowHavexp.isSelected();
		Status_HUD.isShow[9] = checkBoxIsShowRank.isSelected();
		Status_HUD.isShow[10] = checkBoxIsShowRankPoint.isSelected();
		Status_HUD.isShow[11] = checkBoxIsShowCurrentJob.isSelected();
		Status_HUD.isShow[12] = checkBoxIsShowFPS.isSelected();
		Status_HUD.text = textField.getText();
		Status_HUD.x = (int) spinner_x.getValue();
		Status_HUD.y = (int) spinner_y.getValue();
		Rendering.isRainbow = checkBoxIsRainbow.isSelected();

		Status_HUD.cfg.get("render", "isShowText", true, "ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[0]);
		Status_HUD.cfg.get("render", "isShowSwordKill", true, "剣キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[1]) ;
		Status_HUD.cfg.get("render", "isShowBowKill", true, "弓キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[2]);
		Status_HUD.cfg.get("render", "isShowDeath", true, "デス数の表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[3]);
		Status_HUD.cfg.get("render", "isShowRate", true, "K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[4]);
		Status_HUD.cfg.get("render", "isShowTotalRate", true, "総合K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[5]);
		Status_HUD.cfg.get("render", "isShowNexusDamage", true, "ネクサスダメージの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[6]);
		Status_HUD.cfg.get("render", "isShowXP", true, "獲得xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[7]);
		Status_HUD.cfg.get("render", "isShowTotalXP", true, "所持xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[8]);
		Status_HUD.cfg.get("render", "isShowRank", true, "ランクの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[9]);
		Status_HUD.cfg.get("render", "isShowRankPoint", true, "ランクポイントの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[10]);
		Status_HUD.cfg.get("render", "isShowJob", true, "現在の職業の表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[11]);
		Status_HUD.cfg.get("render", "isShowFPS", true, "FPSの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[12]);
		Status_HUD.cfg.get("render", "text", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。").set(Status_HUD.text);
		Status_HUD.cfg.get("render", "x", 2, "ステータスの画面上のx座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.x);
		Status_HUD.cfg.get("render", "y", 2, "ステータスの画面上のy座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.y);
		Status_HUD.cfg.get("render", "isRainbow", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。").set(Rendering.isRainbow);
		Status_HUD.cfg.get("render", "isRenderWhenStart", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。").set(checkBoxIsEnable.isSelected());
		Status_HUD.cfg.get("render", "color", 0xFF0000, "文字の色を設定します。16進数で指定してください。").set("0x" + Integer.toHexString(ColorSetting.colorcash));

		Status_HUD.color = ColorSetting.colorcash;

		Status_HUD.cfg.save();

		JOptionPane.showMessageDialog(new GuiSetting(), new TextComponentTranslation("yadokaris_shp.setting.save").getUnformattedText());
	}
}
