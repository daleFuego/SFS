package com.view.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.dao.DBData;
import com.defines.DefineUtils;
import com.defines.ProductStage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Process extends JPanel {

	private static final long serialVersionUID = 2522013535122155862L;
	public ArrayList<JLabel> midLabels = new ArrayList<>();
	public JButton btnStartProcess;

	public Process() {
		setBorder(null);
		setLayout(null);
		setSize(530, 408);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Delivery and distribution process description", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel.setBounds(10, 11, 510, 386);
		add(panel);
		panel.setLayout(null);

		JPanel panelPicBorder = new JPanel();
		panelPicBorder.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPicBorder.setBounds(186, 27, 302, 302);
		panel.add(panelPicBorder);
		panelPicBorder.setLayout(null);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Image scaledImage = ImageIO.read(getClass().getResourceAsStream("/images/chart.png")).getScaledInstance(300,
					300, 0);

			JLabel lblPic = new JLabel("");
			lblPic.setBounds(1, 1, 300, 300);
			panelPicBorder.add(lblPic);
			lblPic.setHorizontalAlignment(SwingConstants.CENTER);
			lblPic.setIcon(new ImageIcon(scaledImage));

			JPanel panelMidProcesses = new JPanel();
			panelMidProcesses.setBounds(21, 27, 144, 302);
			panel.add(panelMidProcesses);
			panelMidProcesses.setLayout(null);

			JLabel lblReceivingCoffeeBeans = new JLabel(ProductStage.IMPORT.toString());
			lblReceivingCoffeeBeans.setHorizontalAlignment(SwingConstants.CENTER);
			lblReceivingCoffeeBeans.setBounds(9, 10, 125, 14);
			panelMidProcesses.add(lblReceivingCoffeeBeans);

			JLabel lblStorage = new JLabel(ProductStage.STORAGE.toString());
			lblStorage.setHorizontalAlignment(SwingConstants.CENTER);
			lblStorage.setBounds(9, 34, 125, 14);
			panelMidProcesses.add(lblStorage);

			JLabel lblSifter = new JLabel(ProductStage.SIFTER.toString());
			lblSifter.setHorizontalAlignment(SwingConstants.CENTER);
			lblSifter.setBounds(9, 58, 125, 14);
			panelMidProcesses.add(lblSifter);

			JLabel lblAssortment = new JLabel(ProductStage.ASSORTMENT.toString());
			lblAssortment.setHorizontalAlignment(SwingConstants.CENTER);
			lblAssortment.setBounds(9, 82, 125, 14);
			panelMidProcesses.add(lblAssortment);

			JLabel lblRoasting = new JLabel(ProductStage.ROASTING.toString());
			lblRoasting.setHorizontalAlignment(SwingConstants.CENTER);
			lblRoasting.setBounds(9, 106, 125, 14);
			panelMidProcesses.add(lblRoasting);

			JLabel lblQa = new JLabel(ProductStage.QUALITY_TESTING.toString());
			lblQa.setHorizontalAlignment(SwingConstants.CENTER);
			lblQa.setBounds(9, 130, 125, 14);
			panelMidProcesses.add(lblQa);

			JLabel lblCooling = new JLabel(ProductStage.COOLING.toString());
			lblCooling.setHorizontalAlignment(SwingConstants.CENTER);
			lblCooling.setBounds(9, 154, 125, 14);
			panelMidProcesses.add(lblCooling);

			JLabel lblBlending = new JLabel(ProductStage.BLENDING.toString());
			lblBlending.setHorizontalAlignment(SwingConstants.CENTER);
			lblBlending.setBounds(9, 178, 125, 14);
			panelMidProcesses.add(lblBlending);

			JLabel lblQa_1 = new JLabel(ProductStage.FINAL_TESTING.toString());
			lblQa_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblQa_1.setBounds(9, 202, 125, 14);
			panelMidProcesses.add(lblQa_1);

			JLabel lblPackaging = new JLabel(ProductStage.PACKAGING.toString());
			lblPackaging.setHorizontalAlignment(SwingConstants.CENTER);
			lblPackaging.setBounds(9, 226, 125, 14);
			panelMidProcesses.add(lblPackaging);

			JLabel lblPaletizing = new JLabel(ProductStage.PALLETIZING.toString());
			lblPaletizing.setHorizontalAlignment(SwingConstants.CENTER);
			lblPaletizing.setBounds(9, 250, 125, 14);
			panelMidProcesses.add(lblPaletizing);

			JLabel lblFinalProduct = new JLabel(ProductStage.READY.toString());
			lblFinalProduct.setHorizontalAlignment(SwingConstants.CENTER);
			lblFinalProduct.setBounds(9, 274, 125, 14);
			panelMidProcesses.add(lblFinalProduct);

			btnStartProcess = new JButton("Start process");
			btnStartProcess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int val = DBData.checkCoffeeResources() - DefineUtils.LOAD_VALUE;
					String query = "UPDATE \"RESOURCES\" SET \"QUANTITY_COFFEE\"='" + val + "';";

					if (DBData.sendProcessConfirmation()) {
						DBData.updateDBTable(query);
						for (JLabel lbl : midLabels) {
							try {
								Thread.sleep(100);
								Font font = lbl.getFont();
								Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
								lbl.setFont(boldFont);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Process could not be started", "",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnStartProcess.setBounds(21, 340, 467, 35);
			panel.add(btnStartProcess);

			midLabels.add(lblReceivingCoffeeBeans);
			midLabels.add(lblStorage);
			midLabels.add(lblSifter);
			midLabels.add(lblRoasting);
			midLabels.add(lblAssortment);
			midLabels.add(lblQa);
			midLabels.add(lblCooling);
			midLabels.add(lblBlending);
			midLabels.add(lblQa_1);
			midLabels.add(lblPackaging);
			midLabels.add(lblPaletizing);
			midLabels.add(lblFinalProduct);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
