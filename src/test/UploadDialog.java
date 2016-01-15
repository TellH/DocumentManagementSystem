package test;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import socket.Client;
import user.User;
import dataStore.DBUtil;

public class UploadDialog extends JDialog {
	Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
	private final JPanel contentPanel = new JPanel();
	private JTextField text_docID;
	private JTextField text_docName;
	private JTextField text_docDescription;
	String string_docID;
	String string_docName;
	String string_docDescription;
	private UploadDialog dialog;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
/*		try {
			UploadDialog dialog = new UploadDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public UploadDialog(final String userName) {
		dialog=this;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblid = new JLabel("\u6587\u4EF6ID:");
			lblid.setBounds(31, 37, 72, 18);
			contentPanel.add(lblid);
		}
		{
			JLabel lblNewLabel = new JLabel("\u6587\u4EF6\u540D\uFF1A");
			lblNewLabel.setBounds(31, 84, 72, 18);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel label = new JLabel("\u6587\u4EF6\u63CF\u8FF0\uFF1A");
			label.setBounds(14, 145, 88, 18);
			contentPanel.add(label);
		}
		{
			text_docID = new JTextField();
			text_docID.setBounds(96, 34, 86, 24);
			contentPanel.add(text_docID);
			text_docID.setColumns(10);
		}
		{
			text_docName = new JTextField();
			text_docName.setEnabled(false);
			text_docName.setBounds(96, 81, 86, 24);
			contentPanel.add(text_docName);
			text_docName.setColumns(10);
		}
		{
			text_docDescription = new JTextField();
			text_docDescription.setBounds(96, 118, 235, 73);
			contentPanel.add(text_docDescription);
			text_docDescription.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						string_docID=text_docID.getText();
						try {
							if(DBUtil.searchDoc(string_docID) != null){
								javax.swing.JOptionPane.showMessageDialog(dialog,"你所输入的文件ID与数据库的有重复，请重新输入~");
								return;
							}
						} catch (IllegalStateException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						string_docDescription=text_docDescription.getText();
						FileDialog fileDialog=new FileDialog(dialog,"选择上传的文件",FileDialog.LOAD);
						fileDialog.setVisible(true);
						string_docName=fileDialog.getFile();
						if(string_docName==null){
							javax.swing.JOptionPane.showMessageDialog(dialog,"上传失败！我们也有出错的时候~");
							return;
						}
							/*try {
								User.copyFile(new File(fileDialog.getDirectory()+"\\"+string_docName), 
										new File(DBUtil.databaseFolder.getAbsolutePath()+"//"+string_docName));
							} catch (IOException e1) {
								e1.printStackTrace();
							}*/
						Client.uploadFromServer(new File(fileDialog.getDirectory()+"\\"+string_docName));
						javax.swing.JOptionPane.showMessageDialog(dialog,"上传成功！");
						try {
							System.out.println(userName);
							DBUtil.insertDoc(string_docID, userName, timestamp, string_docDescription, string_docName);
//								DataProcessing.insertDoc(string_docID, userName, timestamp, string_docDescription, string_docName);
							FileListDialog.updateTable();
						} catch (IllegalStateException | SQLException e_) {
							System.out.println("上传文件失败");
							e_.printStackTrace();
						}
						dialog.dispose();
					}
				});
				buttonPane.add(okButton);
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
