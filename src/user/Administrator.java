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
			System.out.println("请输入要删除的用户名：");
			inputName=scanner.nextLine();
			try {
				if(DataProcessing.deleteUser(inputName)){
					System.out.println("删除成功！");
				}else {
					System.out.println("删除失败！");
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("输入1：继续");
			System.out.println("输入2：退出");
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
			System.out.println("请输入用户名：");
			inputName=scanner.nextLine();
			System.out.println("请输入密码：");
			inputPassword=scanner.nextLine();
			System.out.println("请输入角色类型：");
			inputRole=scanner.nextLine();
			try {
				if(DataProcessing.insertUser(inputName, inputPassword, inputRole)){
					System.out.println("添加用户成功！");
				}else{
					System.out.println("添加用户失败！");
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("输入1：继续");
			System.out.println("输入2：退出");
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
				System.out.println("用户名："+user.getName()+"  密码："+user.getPassword()+"  用户角色："
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
			System.out.println("您好，管理员");
			System.out.println("输入1:删除用户");
			System.out.println("输入2:增加用户");
			System.out.println("输入3:展示用户列表");
			System.out.println("输入4:退出");
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
