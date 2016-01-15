package test;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import user.User;
import dataStore.DBUtil;

public class MainFrame extends JFrame {
	private MainFrame mainFrame;
	public JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		mainFrame=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("\u6B22\u8FCE\u4F7F\u7528\u6587\u6863\u7BA1\u7406\u7CFB\u7EDF");
		lblNewLabel.setFont(new Font("ËÎÌå", Font.PLAIN, 23));
		lblNewLabel.setBounds(173, 32, 253, 71);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
		label.setBounds(196, 116, 72, 18);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(196, 173, 72, 18);
		contentPane.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(282, 170, 100, 24);
		passwordField.setEchoChar('*');
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(282, 113, 100, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btn_login = new JButton("\u767B\u5F55");
		btn_login.setBounds(136, 252, 113, 27);
		contentPane.add(btn_login);
		btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBUtil.connect();
				String inputName=textField.getText();
				textField.setText("");
				String inputPassword=String.valueOf(passwordField.getPassword());
				passwordField.setText("");
				User user;
				try {
					//user = DataProcessing.searchUser(inputName, inputPassword);
					user=DBUtil.searchUser(inputName, inputPassword);
					if(user==null){
						javax.swing.JOptionPane.showMessageDialog(mainFrame,"µÇÂ¼Ê§°Ü£¬¿É²»ÒªÆøÄÙÅ¶Ç×~");
					}else {
						user.showWindow(mainFrame);
					}
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btn_quiz = new JButton("\u9000\u51FA");
		btn_quiz.setBounds(352, 252, 113, 27);
		contentPane.add(btn_quiz);
		btn_quiz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DBUtil.disconnectFromDB();
				System.exit(1);
			}
		});
	}
}
