package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.EventQueue;
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

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.loading.FMLPaths;

public class SettingGUI extends HasColorFrame implements ActionListener {

	private static final long serialVersionUID = -4919436647821859260L;
	private final JPanel contentPane;
	private final JCheckBox checkBoxIsEnable = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doEnable").getString());
	private final JCheckBox checkBoxDoChangeTeamColor = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doChangeTeamColor").getString());
	private final JCheckBox checkBoxDoRenderEnchantment = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doRenderEnchantment").getString());
	private final JSpinner spinnerSize = new JSpinner();
	private final JTextField textField = new JTextField();
	private final JCheckBox checkBoxIsRainbow = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.isRainbow").getString());

	public SettingGUI() {
		setTitle("Status HUD Plus Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton buttonColor = new JButton(new TranslationTextComponent("yadokaris_shp.setting.colorButton").getString());
		buttonColor.setBounds(328, 264, 144, 27);
		buttonColor.addActionListener(new ColorSetting(this));
		contentPane.add(buttonColor);

		checkBoxIsEnable.setBackground(Color.WHITE);
		checkBoxIsEnable.setSelected(SHPConfig.doRenderWhenStart.get());
		checkBoxIsEnable.setBounds(22, 18, 254, 21);
		contentPane.add(checkBoxIsEnable);

		spinnerSize.setModel(new SpinnerNumberModel(1, 0, 100, 0.01));
		spinnerSize.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinnerSize.setValue(SHPConfig.fontSize.get());
		spinnerSize.setBounds(22, 175, 157, 27);
		contentPane.add(spinnerSize);

		textField.setText(SHPConfig.text.get());
		textField.setBounds(22, 83, 254, 27);
		textField.setColumns(10);
		contentPane.add(textField);

		checkBoxIsRainbow.setBackground(Color.WHITE);
		checkBoxIsRainbow.setSelected(SHPConfig.isRainbow.get());
		checkBoxIsRainbow.setBounds(22, 244, 279, 21);
		contentPane.add(checkBoxIsRainbow);

		checkBoxDoChangeTeamColor.setSelected(SHPConfig.doChangeTeamColor.get());
		checkBoxDoChangeTeamColor.setBackground(Color.WHITE);
		checkBoxDoChangeTeamColor.setBounds(22, 267, 279, 21);
		contentPane.add(checkBoxDoChangeTeamColor);

		checkBoxDoRenderEnchantment.setSelected(SHPConfig.doRenderEnchantment.get());
		checkBoxDoRenderEnchantment.setBackground(Color.WHITE);
		checkBoxDoRenderEnchantment.setBounds(22, 290, 279, 21);
		contentPane.add(checkBoxDoRenderEnchantment);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TranslationTextComponent("yadokaris_shp.setting.settingText").getString());
		textPane.setBounds(22, 52, 450, 21);
		contentPane.add(textPane);

		JButton buttonSave = new JButton(new TranslationTextComponent("yadokaris_shp.setting.settingSave").getString());
		buttonSave.setBounds(366, 424, 106, 27);
		buttonSave.addActionListener(this);
		contentPane.add(buttonSave);

		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText(new TranslationTextComponent("yadokaris_shp.setting.settingFontSize").getString());
		textPane_2.setBounds(22, 144, 123, 21);
		contentPane.add(textPane_2);

		JButton buttonEdit = new JButton(new TranslationTextComponent("yadokaris_shp.setting.settingEdit").getString());
		buttonEdit.setBounds(22, 371, 106, 27);
		buttonEdit.addActionListener(l -> {
			EventQueue.invokeLater(() -> {
				try {
					EditGroupGUI frame = new EditGroupGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		contentPane.add(buttonEdit);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TranslationTextComponent("yadokaris_shp.setting.settingGroup").getString());
		textPane_1.setBounds(22, 340, 450, 21);
		contentPane.add(textPane_1);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		SHPConfig.text.set(textField.getText());
		Status.Text.text = textField.getText();
		SHPConfig.fontSize.set((double) spinnerSize.getValue());
		SHPConfig.isRainbow.set(checkBoxIsRainbow.isSelected());
		SHPConfig.doChangeTeamColor.set(checkBoxDoChangeTeamColor.isSelected());
		SHPConfig.doRenderEnchantment.set(checkBoxDoRenderEnchantment.isSelected());
		SHPConfig.doRenderWhenStart.set(checkBoxIsEnable.isSelected());
		if (color != -1) Status_HUD.color = color;
		SHPConfig.colors.set("0x" + Integer.toHexString(Status_HUD.color));

		Config.saveConfig(FMLPaths.CONFIGDIR.get().resolve("yadokaris_shp.toml").toString());
		if (Status_HUD.player != null) Status_HUD.player.sendChatMessage("/multiplier");

		JOptionPane.showMessageDialog(new SettingGUI(), new TranslationTextComponent("yadokaris_shp.setting.save").getString());
	}
}
