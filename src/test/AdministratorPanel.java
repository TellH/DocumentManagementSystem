package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdministratorPanel extends JPanel {
	private AdministratorPanel panel;
	final JFrame mainFrame_;
	/**
	 * Create the panel.
	 */
	public AdministratorPanel(JFrame mainFrame,final String userName) {
		mainFrame_=mainFrame;
		panel=this;
		setLayout(null);
		
		JLabel label = new JLabel("\u4F60\u597D\uFF01\u7BA1\u7406\u5458");
		label.setBounds(166, 24, 103, 33);
		add(label);
		
		JButton btn_add = new JButton("\u589E\u5220\u7528\u6237");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UsersListDialog(userName, UsersListDialog.TOADD).setVisible(true);;
			}
		});
		btn_add.setBounds(156, 103, 113, 27);
		add(btn_add);
		
		JButton btn_usersList = new JButton("\u7528\u6237\u5217\u8868");
		btn_usersList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UsersListDialog(userName, UsersListDialog.TOSHOW).setVisible(true);
			}
		});
		btn_usersList.setBounds(156, 143, 113, 27);
		add(btn_usersList);
		
		JButton btn_exit = new JButton("\u9171\u6CB9\u6253\u5B8C\u4E86");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				((MainFrame)mainFrame_).setContentPane(((MainFrame)mainFrame_).contentPane);
				((MainFrame)mainFrame_).contentPane.setVisible(true);
			}
		});
		btn_exit.setBounds(156, 183, 113, 27);
		add(btn_exit);
	}

}
