package test;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import socket.Client;
import user.User;
import dataStore.DBUtil;
import dataStore.DBUtil.Doc;

public class FileListDialog extends JDialog {
	final static int TOSHOW=0;
	final static int TODOWNLOAD=1;
	final static int TOUPLOAD=2;
	static DefaultTableModel tableModel;
	final FileListDialog dialog;
	private int selectedRowIndex;
	private JTable table;
	private Doc doc;
	class downloadFileMouseAdapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			selectedRowIndex=table.getSelectedRow();
			if(selectedRowIndex==-1){
				javax.swing.JOptionPane.showMessageDialog(dialog,"亲~ 你还没有选中要下载的一个文件呢");
				return;
			}
			String docID=(String) table.getValueAt(selectedRowIndex, 0);
			try {
				doc=DBUtil.searchDoc(docID);
//				doc=DataProcessing.searchDoc(docID);
				if (doc==null){
					javax.swing.JOptionPane.showMessageDialog(dialog,"数据库上无此文件ID");
					return;
				}else{
					FileDialog fileDialog=new FileDialog(dialog,"选择下载目录",FileDialog.SAVE);
					fileDialog.setFile(doc.getDocName());
					fileDialog.setVisible(true);
//					User.copyFile(new File(DataProcessing.databaseFolder+"\\"+doc.getDocName()), new File(fileDialog.getDirectory()+"\\"+fileDialog.getFile()));
//					User.copyFile(new File(DBUtil.databaseFolder+"\\"+doc.getDocName()), new File(fileDialog.getDirectory()+"\\"+fileDialog.getFile()));
					Client.downloadFromServer(doc.getDocName(), new File(fileDialog.getDirectory()+"\\"+fileDialog.getFile()));
					javax.swing.JOptionPane.showMessageDialog(dialog,"文件已经下载成功了亲~");
				}
			} catch (IllegalStateException | SQLException e_) {
				e_.printStackTrace();
			}
		}
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
/*		try {
			FileListDialog dialog = new FileListDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public static void updateTable(){
		try {
			Doc curdoc=null;
			Doc foredoc=null;
			Enumeration<Doc> docs = DBUtil.getAllDocs();
//			Enumeration<Doc> docs = DataProcessing.getAllDocs();
			tableModel.setRowCount(0);//清空Table！
			Doc doc;
			while(docs.hasMoreElements()){
				doc=docs.nextElement();
				tableModel.addRow(new String[]{doc.getDocID(), doc.getDocName(), doc.getOperatorName()
						,doc.getTimestamp().toString(),doc.getDiscribption()});
			}
		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public FileListDialog(Enumeration<Doc> docs,final String userName,int type) {
		dialog=this;
		final int selectedRowIndex;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 432, 202);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btn_download = new JButton("\u4E0B\u8F7D\u6587\u4EF6");
		if(type==TODOWNLOAD)
			btn_download.addMouseListener(new downloadFileMouseAdapter());
		else{
			btn_download.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					new UploadDialog(userName).setVisible(true);
					SwingUtilities.updateComponentTreeUI (dialog);
				}
			});
		}
		btn_download.setBounds(69, 215, 113, 27);
		getContentPane().add(btn_download);
		
		JButton btn_cancel = new JButton("\u53D6\u6D88");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		btn_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dialog.dispose();
			}
		});
		btn_cancel.setBounds(260, 215, 113, 27);
		getContentPane().add(btn_cancel);
		if(type==TOSHOW){
			btn_cancel.setVisible(false);
			btn_download.setVisible(false);
		}else if(type==TOUPLOAD){
			btn_download.setText("上传文件");
		}
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.addColumn("文件ID");
		tableModel.addColumn("文件名");
		tableModel.addColumn("操作者");
		tableModel.addColumn("创建时间");
		tableModel.addColumn("文件描述");
		while(docs.hasMoreElements()){
			doc=docs.nextElement();
			tableModel.addRow(new String[]{doc.getDocID(), doc.getDocName(), doc.getOperatorName()
					,doc.getTimestamp().toString(),doc.getDiscribption()});
		}
	}
}
