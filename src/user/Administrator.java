package user;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.JFrame;

import test.AdministratorPanel;
import test.MainFrame;
import dataStore.DataProcessing;

public class Administrator extends User{
	
	public Administrator(String name, String password, String role) {
		super(name, password, role);
	}
	public boolean changeUserInfo(String password){
		return true;
	}
	public void delUser(){
		Scanner scanner=new Scanner(System.in);
		String inputName;
		int option;
		while(true){
			System.out.println("������Ҫɾ�����û�����");
			inputName=scanner.nextLine();
			try {
				if(DataProcessing.deleteUser(inputName)){
					System.out.println("ɾ���ɹ���");
				}else {
					System.out.println("ɾ��ʧ�ܣ�");
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("����1������");
			System.out.println("����2���˳�");
			option=scanner.nextInt();
			if(option==2)
				return;
		}
	}
	public void addUser(){
		Scanner scanner=new Scanner(System.in);
		String inputName;
		String inputPassword;
		String inputRole;
		int option;
		while(true){
			System.out.println("�������û�����");
			inputName=scanner.nextLine();
			System.out.println("���������룺");
			inputPassword=scanner.nextLine();
			System.out.println("�������ɫ���ͣ�");
			inputRole=scanner.nextLine();
			try {
				if(DataProcessing.insertUser(inputName, inputPassword, inputRole)){
					System.out.println("����û��ɹ���");
				}else{
					System.out.println("����û�ʧ�ܣ�");
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("����1������");
			System.out.println("����2���˳�");
			option=scanner.nextInt();
			scanner.nextLine();
			if(option==2)
				return;
		}
	}
	public void listUser(){
		Enumeration<User> users;
		try {
			users = DataProcessing.getAllUser();
			User user;
			while(users.hasMoreElements()){
				user=users.nextElement();
				System.out.println("�û�����"+user.getName()+"  ���룺"+user.getPassword()+"  �û���ɫ��"
						+user.getRole());
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataProcessing.disconnectFromDB();
		}
	}
	public void showMenu(){
		int option;
		Scanner scanner=new Scanner(System.in);
		while (true){
			System.out.println("���ã�����Ա");
			System.out.println("����1:ɾ���û�");
			System.out.println("����2:�����û�");
			System.out.println("����3:չʾ�û��б�");
			System.out.println("����4:�˳�");
			option=scanner.nextInt();
			scanner.nextLine();
			switch(option){
			case 1:
				delUser();
				break;
			case 2:
				addUser();
				break;
			case 3:
				listUser();
				break;
			case 4:
				//scanner.close();
				return;
			default:
				break;
			}
			continue;
		}
	}
	@Override
	public void showWindow(JFrame mainFrame) {
		AdministratorPanel panel=new AdministratorPanel( mainFrame,getName());
		((MainFrame)mainFrame).contentPane.setVisible(false);
		mainFrame.setContentPane(panel);
	}
}
