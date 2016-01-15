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
import javax.swing.border.EmptyBorder;

import dataStore.DBUtil;
import dataStore.DBUtil.Doc;

public class BrowserPanel extends JPanel {
	private BrowserPanel panel;
	/**
	 * Create the panel.
	 */
	public BrowserPanel(JFrame mainFrame,String userName) {
		final String userName_=userName;
		final JFrame mainFrame_=mainFrame;
		panel=this;
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel label = new JLabel("\u4F60\u597D\uFF01\u6D4F\u89C8\u8005");
		label.setFont(new Font("ËÎÌו", Font.PLAIN, 25));
		label.setBounds(124, 26, 179, 62);
		add(label);
		
		JButton btn_download = new JButton("\u4E0B\u8F7D\u6587\u4EF6");
		btn_download.setBounds(159, 112, 113, 27);
		add(btn_download);
		btn_download.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Enumeration<Doc> docs;
				try {
					docs=DBUtil.getAllDocs();
//					docs=DataProcessing.getAllDocs();
					FileListDialog fileListDialog=new FileListDialog(docs, userName_, FileListDialog.TODOWNLOAD);
					fileListDialog.setVisible(true);
				} catch (IllegalStateException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btn_filelist = new JButton("\u6587\u4EF6\u5217\u8868");
		btn_filelist.setBounds(159, 152, 113, 27);
		add(btn_filelist);
		btn_filelist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Enumeration<Doc> docs;
				try {
					docs=DBUtil.getAllDocs();
//					docs=DataProcessing.getAllDocs();
					FileListDialog fileListDialog=new FileListDialog(docs,userName_,FileListDialog.TOSHOW);
					fileListDialog.setVisible(true);
					//SwingUtilities.updateComponentTreeUI (fileListDialog);
				} catch (IllegalStateException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btn_exit = new JButton("\u6253\u5B8C\u9171\u6CB9\u8BE5\u8D70\u4E86");
		btn_exit.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
		btn_exit.setBounds(146, 192, 144, 27);
		add(btn_exit);
		btn_exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//panel.removeAll();
				panel.setVisible(false);
				((MainFrame)mainFrame_).setContentPane(((MainFrame)mainFrame_).contentPane);
				((MainFrame)mainFrame_).contentPane.setVisible(true);
			}
		});

	}

}
