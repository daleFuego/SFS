package com.view.frames;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.defines.DefineUtils;

public class WelcomeScreen extends JFrame {

	private static final long serialVersionUID = -8824293977897628397L;

	public WelcomeScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setLayout(null);
		setSize(580, 615);
		setTitle(DefineUtils.APP_TILTE);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(8, 6, 570, 570);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblLogo);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setMinimum(20);
		progressBar.setBounds(8, 582, 570, 25);
		progressBar.setValue(100);
		getContentPane().add(progressBar);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/icon.png")));
			lblLogo.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/logo.jpg"))));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
						Thread.sleep(2000);
					dispose();
					new SupplyChainMgmt();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
	}
}
