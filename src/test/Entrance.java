package test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;

import user.User;
import dataStore.DataProcessing;
public class Entrance {
	public static void main(String[] args) {
		int option=0;
		String inputName=null;
		String inputPassword=null;
		//Scanner scanner=new Scanner(System.in);
		BufferedReader reader=new BufferedReader(new InputStreamReader((System.in)));
		while(true){
			System.out.println("欢迎来到文档管理系统");
			System.out.println("输入1：登陆");
			System.out.println("输入2：退出");
			//option=scanner.nextInt();
			try{
				String str=reader.readLine();
				option=Integer.parseInt(str);
				//reader.readLine();
			}catch(Exception e){
				System.out.println("读取用户输入错误");
			}
			if(option==2){
				//scanner.close();
				try{
					reader.close();
				}catch(Exception e){
					System.out.println("字符输入流关闭错误");
				}

				return ;
			}else if(option==1){
				System.out.println("请输入用户名");
//				scanner.nextLine();
//				inputName=scanner.nextLine();
				try{
					inputName=reader.readLine();
				}catch(Exception e){
					System.out.println("读取用户输入错误");
				}
				System.out.println("请输入用户密码");
				//inputPassword=scanner.nextLine();
				try{
					inputPassword=reader.readLine();
				}catch(Exception e){
					System.out.println("读取用户输入错误");
				}
				//scanner.nextLine();
				User user;
				try {
					user = DataProcessing.searchUser(inputName, inputPassword);
					if(user==null){
						System.out.println("登陆失败");
						continue;
					}else {
						user.showMenu();		
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					DataProcessing.disconnectFromDB();
				}
			}
		}
	}
}
