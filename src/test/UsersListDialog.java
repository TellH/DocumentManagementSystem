package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dataStore.DBUtil;
import user.User;

public class UsersListDialog extends JDialog {
	public final static int TOSHOW=0;
	public final static int TOADD=1;	
	public final static int TODELETE=2;		
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private UsersListDialog dialog;
	private Enumeration<User> users;
	private DefaultTableModel tableModel;
	private int selectedRowIndex;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
/*		try {
			UsersListDialog dialog = new UsersListDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public UsersListDialog(String userName,int type) {
		dialog=this;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 432, 216);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.addColumn("用户名：");
		tableModel.addColumn("密码：");
		tableModel.addColumn("用户角色：");
		try {
			users = DBUtil.getAllUser();
//			users = DataProcessing.getAllUser();
			User user;
			while(users.hasMoreElements()){
				user=users.nextElement();
				tableModel.addRow(new String[]{user.getName(), user.getPassword(),user.getRole()});
			}
		} catch (IllegalStateException | SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(table);
		if(type!=TOSHOW)
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btn_add = new JButton("\u589E\u52A0\u7528\u6237");
				btn_add.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new AddUserDialog(tableModel).setVisible(true);
					}				
				});
				buttonPane.add(btn_add);
			}
			{
				JButton btn_delete = new JButton("\u5220\u9664\u7528\u6237");
				btn_delete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedRowIndex=table.getSelectedRow();
						if(selectedRowIndex==-1){
							javax.swing.JOptionPane.showMessageDialog(dialog,"亲~ 你还没有选中一个要删除的用户呢");
							return;
						}
						try {
							if(DBUtil.deleteUser((String) table.getValueAt(selectedRowIndex, 0))){
								javax.swing.JOptionPane.showMessageDialog(dialog,"删除成功");
								tableModel.setRowCount(0);//清空Table！
								users = DBUtil.getAllUser();
//								users = DataProcessing.getAllUser();
								User user;
								while(users.hasMoreElements()){
									user=users.nextElement();
									tableModel.addRow(new String[]{user.getName(), user.getPassword(),user.getRole()});
								}
							}else {
								javax.swing.JOptionPane.showMessageDialog(dialog,"删除失败");
								return;
							}
						} catch (HeadlessException | IllegalStateException
								| SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
				buttonPane.add(btn_delete);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
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
