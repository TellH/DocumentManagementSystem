package user;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.JFrame;

import test.BrowserPanel;
import test.MainFrame;
import dataStore.DataProcessing;
import dataStore.DataProcessing.Doc;

public class Browser extends User{
	public Browser(String name, String password, String role) {
		super(name, password, role);
		// TODO Auto-generated constructor stub
	}
	public void downloadFile(){
		Doc doc;
		String docID;
		Scanner scanner=new Scanner(System.in);
		System.out.println("�������ļ�ID Number");
		docID=scanner.nextLine();
		try {
			doc=DataProcessing.searchDoc(docID);
			if (doc==null){
				System.out.println("���ݿ����޴��ļ�ID");
				return;
			}else{
				copyFile(new File(DataProcessing.databaseFolder+"\\"+doc.getDocName()), new File(getFile()+"\\"+doc.getDocName()));
			}
		} catch (IOException|IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println("���سɹ�");
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
			e1.printStackTrace();
		}
	}
	public void showMenu(){
		int option;
		Scanner scanner=new Scanner(System.in);
		while (true){
			System.out.println("���ã�����Ա");
			System.out.println("����1:�����ļ�");
			System.out.println("����2:չʾ�ļ��б�");
			System.out.println("����3:�˳�");
			option=scanner.nextInt();
			scanner.nextLine();
			switch(option){
			case 1:
				downloadFile();
				break;
			case 2:
				showFileList();
				break;
			case 3:
				//scanner.close();
				return;
			default:
				break;
			}
		}
	}

	@Override
	public void showWindow(JFrame mainFrame) {
		// TODO Auto-generated method stub
		BrowserPanel panel=new BrowserPanel( mainFrame,getName());
		((MainFrame)mainFrame).contentPane.setVisible(false);
		mainFrame.setContentPane(panel);
	}
}
