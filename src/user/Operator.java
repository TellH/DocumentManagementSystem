package user;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.JFrame;

import test.MainFrame;
import test.OperatorPanel;
import dataStore.DataProcessing;
import dataStore.DataProcessing.Doc;

public class Operator extends User{
	public Operator(String name, String password, String role) {
		super(name, password, role);
		// TODO Auto-generated constructor stub
	}
	public void uploadFile(){
		String docID;
		String operatorName;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		String discribption;
		String docName;
		operatorName=getName();
		Scanner scanner=new Scanner(System.in);
		System.out.println("请输入文件ID Number");
		docID=scanner.nextLine();
		System.out.println("请输入文件描述");
		discribption=scanner.nextLine();
		System.out.println("请输入文件名");
		docName=scanner.nextLine();
		try {
			DataProcessing.insertDoc(docID, operatorName, timestamp, discribption, docName);
		} catch (IllegalStateException | SQLException e) {
			System.out.println("上传文件失败");
			e.printStackTrace();
		}
		try {
			copyFile(new File(getFile().getAbsolutePath()+"//"+docName), 
					new File(DataProcessing.databaseFolder.getAbsolutePath()+"//"+docName));
		} catch (IOException e) {
			System.out.println("上传文件失败,你没有该文件");
			e.printStackTrace();
		}
		System.out.println("上传成功");
	}
	public void downloadFile(){
		Doc doc;
		String docID;
		Scanner scanner=new Scanner(System.in);
		System.out.println("请输入文件ID Number");
		docID=scanner.nextLine();
		try {
			doc=DataProcessing.searchDoc(docID);
			if (doc==null){
				System.out.println("数据库上无此文件ID");
				return;
			}else{
				copyFile(new File(DataProcessing.databaseFolder+"\\"+doc.getDocName()), new File(getFile()+"\\"+doc.getDocName()));
			}
		} catch (IOException|IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println("下载成功");
	}
	public void showFileList(){
		Enumeration<Doc> docs;
		try {
			Doc doc;
			docs=DataProcessing.getAllDocs();
			while(docs.hasMoreElements()){
				doc=docs.nextElement();
				System.out.println(doc.getDocID()+" "+doc.getDocName()+" "+doc.getOperatorName()
						+" "+doc.getDocName());
			}
		} catch (IllegalStateException | SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("加载文件列表失败");
			e1.printStackTrace();
		}
		System.out.println("加载文件列表成功");
	}
	public void showMenu(){
		int option;
		Scanner scanner=new Scanner(System.in);
		while (true){
			System.out.println("您好，操作员");
			System.out.println("输入1:上传文件");
			System.out.println("输入2:下载文件");
			System.out.println("输入3:展示文件列表");
			System.out.println("输入4:退出");
			option=scanner.nextInt();
			scanner.nextLine();
			switch(option){
			case 1:
				uploadFile();
				break;
			case 2:
				downloadFile();
				break;
			case 3:
				showFileList();
				break;
			case 4:
				//scanner.close();
				return;
			default:
				break;
			}
		}
	}
	@Override
	public void showWindow(JFrame mainFrame) {
		OperatorPanel panel=new OperatorPanel( mainFrame,getName());
		((MainFrame)mainFrame).contentPane.setVisible(false);
		mainFrame.setContentPane(panel);
	}
}
