package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import dataStore.DBUtil;

public class Server {
	private final static String ADDRESS="127.0.1";
	private static final int PORT =2016;
	public static final int DOWNLOAD=0;
	public static final int UPLOAD=1;	
	//private ServerSocket serSocket;
	private DataInputStream dis;
	private void downLoadFile(DataInputStream dis,Socket client) throws IOException, IllegalStateException, SQLException{
		Writer writer;
		BufferedOutputStream bos;
		writer=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		String docName=dis.readUTF();
		File file=new File(DBUtil.databaseFolder+"\\"+docName);
		FileInputStream fis=new FileInputStream(file);
		BufferedInputStream in=new BufferedInputStream(fis);
		int data;
		while((data=in.read())!=-1){
			writer.write(data);
			writer.flush();
		}
		if(in!=null)
			in.close();
	}
	private void uploadFile(DataInputStream dis,Socket client) throws IOException{
		/*    	String docID=dis.readUTF();
    	String docName = dis.readUTF();
    	String description=dis.readUTF();
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis()); */
		String docName=dis.readUTF();
		//long fileLength = dis.readLong();
		Writer writer;
		BufferedOutputStream bos;
		bos =new BufferedOutputStream(new FileOutputStream(new File(DBUtil.databaseFolder.getAbsolutePath()+"//"+docName)));
		int data;
		while((data=dis.read())!=-1){
			bos.write(data);
			bos.flush();
		}
	}
	private  void startServer(){
		for(;;){
			try {
				final ServerSocket serSocket=new ServerSocket(PORT);
				final Socket client=serSocket.accept();
				new Thread(new Runnable() {
					DataInputStream dis;
					@Override
					public void run() {
						try {
							dis=new DataInputStream(new BufferedInputStream(client.getInputStream()));
							int callFromClient=dis.readInt();
							switch(callFromClient){
							case DOWNLOAD:
								downLoadFile(dis,client);
								break;
							case UPLOAD:
								uploadFile(dis,client);
								break;
							default:
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
							try {
								if(dis !=null)
									dis.close();
								serSocket.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
				if(client!=null){
					client.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Server server=new Server();
		server.startServer();
	}

}
