package dataStore;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;

import dataStore.DataProcessing.Doc;
import user.Administrator;
import user.Browser;
import user.Operator;
import user.User;

public class DBUtil {
	private static Connection conn=null;
	private static String driver;
	private static String url;
	private static String userName;
	private static String password;
	private final static String CREATE_USER_TABLE="create table User(id int primary key  auto_increment,"
			+ "name varchar(20),password varchar(20),role varchar(20))";
	private final static String CREATE_DOC_TABLE="create table Doc(id int primary key  auto_increment,"
			+ "docID varchar(20),creator varchar(20),timestamp timestamp(6),"
			+ "description varchar(100),filename varchar(80))";
	private final static String INSERT_USER="insert into User (name,password,role) values (?,?,?)";
	private final static String INSERT_DOC="insert into Doc (docID,creator,timestamp,description,filename) values (?,?,?,?,?)";
	private final static String UPDATE_USER="update User set role=? , password=? where name=?";
	private final static String DELETE_USER="delete from User where name=?";
	private final static String SEARCH_A_USER="select role from User where name=? and password=?";
	private final static String SEARCH_A_DOC="select creator,timestamp,description,filename from DOC where docID=?";
	private final static String SEARCH_USERS="select name,password,role from User";
	private final static String SEARCH_DOCS="select docID,creator,timestamp,description,filename from Doc";

	static{
		driver="com.mysql.jdbc.Driver";
		url="jdbc:mysql://localhost:3306/DocManagementDB";
		userName="root";
		password="a123456";
		/*
		 * 系统默认添加的三个User在程序第一次启动前第一次已经被添加到数据库
		 * 系统默认添加的Doc文件在程序第一次启动前已被添加到数据库*/
	}
	public static File databaseFolder=new File("F:\\DatabaseFolder");
	public static class Doc {
		public String docID;
		public String operatorName;
		public Timestamp timestamp;
		public String discribption;
		public String docName;
		public  Doc(String docID , String operatorName, Timestamp timestamp,
				String discribption, String docName) {
			this.docID=docID;
			this.operatorName=operatorName;
			this.timestamp=timestamp;
			this.discribption=discribption;
			this.docName=docName;
		}
		public String getDocID() {
			return docID;
		}
		public String getOperatorName() {
			return operatorName;
		}
		public Timestamp getTimestamp() {
			return timestamp;
		}
		public String getDiscribption() {
			return discribption;
		}
		public String getDocName() {
			return docName;
		}
	}
	public static void connect(){
		if(conn==null){
			try {
				Class.forName(driver);
				conn= DriverManager.getConnection(url,userName,password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private static void close(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void createUserTable(){
		if(conn!=null){
			try {
				Statement stmt=conn.createStatement();
				stmt.execute(CREATE_USER_TABLE);
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void createDocTable(){
		if(conn!=null){
			try {
				Statement stmt=conn.createStatement();
				stmt.execute(CREATE_DOC_TABLE);
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	public static Doc searchDoc(String ID) throws SQLException,IllegalStateException {
		Doc doc=null;
//		if(conn==null)
//			connect();
		if(conn!=null){
			PreparedStatement pstm=conn.prepareStatement(SEARCH_A_DOC);
			pstm.setString(1, ID);
			ResultSet rs=pstm.executeQuery();
			if(rs.next()){
				Timestamp timestamp=rs.getTimestamp("timestamp");
				String creator=rs.getString("creator");
				String description=rs.getString("description");
				String filename=rs.getString("filename");
				doc=new Doc(ID, creator, timestamp, description, filename);
			}
			pstm.close();
		}
		return doc;
	}
	public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException,IllegalStateException{
		if(conn!=null){
			PreparedStatement pstm = conn.prepareStatement(INSERT_DOC);
			pstm.setString(1, ID);
			pstm.setString(2, creator);
			pstm.setTimestamp(3, timestamp);
			pstm.setString(4, description);
			pstm.setString(5, filename);
			pstm.executeUpdate();
			pstm.close();
			return true;
		}
		return false;
	}
	public static User searchUser(String name, String password) throws SQLException,IllegalStateException {
		User user=null;
		if(conn!=null){
			PreparedStatement pstm=conn.prepareStatement(SEARCH_A_USER);
			pstm.setString(1, name);
			pstm.setString(2, password);
			ResultSet rs=pstm.executeQuery();
			if(rs.next()){
				String role=rs.getString("role");
				if(role.equals("administrator")||role.equals("Administrator")){
					user=new Administrator(name, password, role);
				}else if(role.equals("browser")||role.equals("Browser")){
					user=new Browser(name, password, role);
				}else if(role.equals("operator")||role.equals("Operator")){
					user=new Operator(name, password, role);
				}
			}
			pstm.close();
		}
		return user;
	}
	public static Enumeration<User> getAllUser() throws SQLException,IllegalStateException{
		Hashtable<String, User> users=null;
		if(conn!=null){
			User user=null;
			String role;
			String name;
			String password;
			users = new Hashtable<String, User>();
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery(SEARCH_USERS);
			while (rs.next()){
				role=rs.getString("role");
				name=rs.getString("name");
				password=rs.getString("password");
				System.out.println(name+" "+role);
				if(role.equals("administrator")||role.equals("Administrator")){
					user=new Administrator(name, password, role);
				}else if(role.equals("browser")||role.equals("Browser")){
					user=new Browser(name, password, role);
				}else if(role.equals("operator")||role.equals("Operator")){
					user=new Operator(name, password, role);
				}
				users.put(name, user);
			}
			rs.close();
		}
		return users.elements();
	}
	public static Enumeration<Doc> getAllDocs() throws SQLException,IllegalStateException{		
		Hashtable<String, Doc> docs=null;
		if(conn!=null){
			docs = new Hashtable<String,Doc>();
			Doc doc=null;
			String docID;
			String creator;
			String description;
			String filename;
			Timestamp timestamp;
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery(SEARCH_DOCS);
			while(rs.next()){
				docID=rs.getString("docID");
				creator=rs.getString("creator");
				description=rs.getString("description");
				filename=rs.getString("filename");
				timestamp=rs.getTimestamp("timestamp");
				doc=new Doc(docID, creator, timestamp, description, filename);
				docs.put(docID, doc);
			}
			rs.close();
		}
		return docs.elements();
	}
	public static boolean updateUser(String name, String password, String role) throws SQLException,IllegalStateException{
		if(conn!=null){
			PreparedStatement pstm=conn.prepareStatement(UPDATE_USER);
			pstm.setString(1, password);
			pstm.setString(2, role);
			pstm.setString(3, name);
			pstm.executeUpdate();
			pstm.close();
			return true;
		}
		return false;
	}
	public static boolean insertUser(String name, String password, String role) throws SQLException,IllegalStateException{
		if(conn!=null){
			PreparedStatement pstm = conn.prepareStatement(INSERT_USER);
			pstm.setString(1, name);
			pstm.setString(2, password);
			pstm.setString(3, role);
			pstm.executeUpdate();
			pstm.close();
			return true;
		}
		return false;
	}
	public static boolean deleteUser(String name) throws SQLException,IllegalStateException{
		if(conn!=null){
			PreparedStatement pstm=conn.prepareStatement(DELETE_USER);
			pstm.setString(1, name);
			pstm.executeUpdate();
			pstm.close();
			return true;
		}
		return false;
	}
	public static void disconnectFromDB() {
		close();
	}
	public static void main(String[] args) {
		try {
			connect();
			Enumeration<User> users = DBUtil.getAllUser();
			User user=DBUtil.searchUser("rose", "123");
			System.out.println(user.getName());
			while(users.hasMoreElements()){
				user=users.nextElement();
				System.out.println(user.getName());
			}
			close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
