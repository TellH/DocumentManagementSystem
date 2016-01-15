package user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

public abstract class User {

	private File file=new File("F:\\UserFolder");
	private String name;
	private String password;
	private String role;
	
	User(String name, String password, String role){
		this.name=name;
		this.password=password;
		this.role=role;
	}
	public boolean changeUserInfo(String password){
		return false;
	}
	public abstract void showMenu();
	public abstract void showWindow(JFrame mainFrame);
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public File getFile() {
		return file;
	}
	public static void copyFile(File sourceFile, File destinFile) throws IOException {
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(destinFile));
		BufferedInputStream in=new BufferedInputStream(new FileInputStream(sourceFile));
		int data;
		while((data=in.read())!=-1){
			out.write(data);
		}
		out.close();
		in.close();
	}
}
