package test;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataStore.DBUtil;
import dataStore.DBUtil.Doc;

public class OperatorPanel extends JPanel {
	private OperatorPanel panel;
	final JFrame mainFrame_;
	/**
	 * Create the panel.
	 */
	public OperatorPanel(JFrame mainFrame,final String userName) {
		mainFrame_=mainFrame;
		panel=this;
		setLayout(null);
		
		JLabel label = new JLabel("\u4F60\u597D\uFF01\u64CD\u4F5C\u5458");
		label.setFont(new Font("ËÎÌו", Font.PLAIN, 21));
		label.setBounds(143, 32, 133, 43);
		add(label);
		
		JButton btn_upload = new JButton("\u4E0A\u4F20\u6587\u4EF6");
		btn_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Enumeration<Doc> docs;
				try {
					docs=DBUtil.getAllDocs();
//					docs=DataProcessing.getAllDocs();
					FileListDialog fileListDialog=new FileListDialog(docs,userName,FileListDialog.TOUPLOAD);
//					FileListDialog fileListDialog=new FileListDialog(docs,userName,FileListDialog.TOUPLOAD);
					fileListDialog.setVisible(true);
				} catch (IllegalStateException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_upload.setBounds(153, 105, 113, 27);
		add(btn_upload);
		
		JButton btn_download = new JButton("\u4E0B\u8F7D\u6587\u4EF6");
		btn_download.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Enumeration<Doc> docs;
				try {
					docs=DBUtil.getAllDocs();
//					docs=DataProcessing.getAllDocs();
					FileListDialog fileListDialog=new FileListDialog(docs,userName,FileListDialog.TODOWNLOAD);
					fileListDialog.setVisible(true);
					//SwingUtilities.updateComponentTreeUI (fileListDialog);
				} catch (IllegalStateException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_download.setBounds(153, 145, 113, 27);
		add(btn_download);
		
		JButton btn_fileList = new JButton("\u6587\u4EF6\u5217\u8868");
		btn_fileList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Enumeration<Doc> docs;
				try {
					docs=DBUtil.getAllDocs();
//					docs=DataProcessing.getAllDocs();
					FileListDialog fileListDialog=new FileListDialog(docs,userName,FileListDialog.TOSHOW);
					fileListDialog.setVisible(true);
					//SwingUtilities.updateComponentTreeUI (fileListDialog);
				} catch (IllegalStateException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_fileList.setBounds(153, 185, 113, 27);
		add(btn_fileList);
		
		JButton btn_exit = new JButton("\u9171\u6CB9\u6253\u5B8C\u4E86");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				((MainFrame)mainFrame_).setContentPane(((MainFrame)mainFrame_).contentPane);
				((MainFrame)mainFrame_).contentPane.setVisible(true);
			}
		});
		btn_exit.setBounds(153, 225, 113, 27);
		add(btn_exit);
		
	}

}
