package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dataStore.DBUtil;
import user.User;

public class AddUserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField text_name;
	private JPasswordField passwordField;
	private String string_name;
	private String string_password;
	private String string_role;
	private AddUserDialog dialog;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
/*		try {
			AddUserDialog dialog = new AddUserDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public AddUserDialog(final DefaultTableModel tableModel) {
		dialog=this;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
		label.setBounds(50, 28, 72, 18);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(50, 74, 72, 18);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u7528\u6237\u89D2\u8272\uFF1A");
		label_2.setBounds(50, 127, 84, 18);
		contentPanel.add(label_2);
		
		text_name = new JTextField();
		text_name.setBounds(136, 25, 86, 24);
		contentPanel.add(text_name);
		text_name.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(136, 71, 86, 24);
		contentPanel.add(passwordField);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItem("Browser");
		comboBox.addItem("Operator");
		comboBox.addItem("Administrator");
		comboBox.setBounds(136, 124, 86, 24);
		contentPanel.add(comboBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						string_name=text_name.getText();
						string_password=String.valueOf(passwordField.getPassword());
						string_role=(String) comboBox.getSelectedItem();
						try {
							if(DBUtil.insertUser(string_name, string_password, string_role)){
								javax.swing.JOptionPane.showMessageDialog(dialog,"添加用户成功！");
								tableModel.setRowCount(0);//清空Table！
								Enumeration<User> users = DBUtil.getAllUser();
//								Enumeration<User> users = DataProcessing.getAllUser();
								User user;
								while(users.hasMoreElements()){
									user=users.nextElement();
									tableModel.addRow(new String[]{user.getName(), user.getPassword(),user.getRole()});
								}
							}else{
								javax.swing.JOptionPane.showMessageDialog(dialog,"添加用户失败！");
							}
							dialog.dispose();
						} catch (IllegalStateException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
