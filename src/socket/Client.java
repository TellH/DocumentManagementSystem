package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP ="127.0.0.1";
    private static final int SERVER_PORT =2016;
    private static Socket client;
    private static BufferedInputStream bis;
    private static DataOutputStream dos;
    private static Writer writer;
    public static void downloadFromServer(String docName,File file){
    	try {
			client =new Socket(SERVER_IP, SERVER_PORT);
			bis=new BufferedInputStream(client.getInputStream());
			dos =new DataOutputStream(client.getOutputStream());
			dos.writeInt(Server.DOWNLOAD);
			dos.flush();
			dos.writeUTF(docName);
			dos.flush();
			writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			int data;
			while((data=bis.read())!=-1){
				writer.write(data);
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
            try {
            	if(dos!=null)
            		dos.close();
    			if(bis !=null)
                    bis.close();
                if(dos !=null)
                    dos.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    public static void uploadFromServer(File file){
    	try {
			client =new Socket(SERVER_IP, SERVER_PORT);
			bis =new BufferedInputStream(new FileInputStream(file));
            dos =new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
            dos.writeInt(Server.UPLOAD);
            dos.flush();
            dos.writeUTF(file.getName());
            dos.flush();
//            dos.writeLong(file.length());
//            dos.flush();
            int data;
    		while((data=bis.read())!=-1){
    			dos.write(data);
    			dos.flush();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bis !=null)
					bis.close();
				if(dos !=null)
					dos.close();
//				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
	public static void main(String[] args) {

	}

}
