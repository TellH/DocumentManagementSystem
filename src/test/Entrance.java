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
			System.out.println("��ӭ�����ĵ�����ϵͳ");
			System.out.println("����1����½");
			System.out.println("����2���˳�");
			//option=scanner.nextInt();
			try{
				String str=reader.readLine();
				option=Integer.parseInt(str);
				//reader.readLine();
			}catch(Exception e){
				System.out.println("��ȡ�û��������");
			}
			if(option==2){
				//scanner.close();
				try{
					reader.close();
				}catch(Exception e){
					System.out.println("�ַ��������رմ���");
				}

				return ;
			}else if(option==1){
				System.out.println("�������û���");
//				scanner.nextLine();
//				inputName=scanner.nextLine();
				try{
					inputName=reader.readLine();
				}catch(Exception e){
					System.out.println("��ȡ�û��������");
				}
				System.out.println("�������û�����");
				//inputPassword=scanner.nextLine();
				try{
					inputPassword=reader.readLine();
				}catch(Exception e){
					System.out.println("��ȡ�û��������");
				}
				//scanner.nextLine();
				User user;
				try {
					user = DataProcessing.searchUser(inputName, inputPassword);
					if(user==null){
						System.out.println("��½ʧ��");
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
