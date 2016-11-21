import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
 
public class Server {
    
    public static void main(String[] args) throws Exception{
        ServerSocket soc = new ServerSocket(9030);  //Æ÷Æ® 9030
        
        System.out.println("Server Start");
        System.out.println("Waiting for Client");
        
        download_from_client(soc);
    }
    
    public static void download_from_client(ServerSocket soc) throws Exception{
    	try{
            Socket client = new Socket();
            client = soc.accept();                       //accept client

	        System.out.println("client accept!");
	        InputStream in = null;                       
	        FileOutputStream out = null;
	        in = client.getInputStream();                
	        DataInputStream din = new DataInputStream(in);  
	        
	        while(true){
	            int data = din.readInt();           
		        String filename = din.readUTF();            
		        File file = new File(filename);             
		        out = new FileOutputStream(file);          
		 
		        int datas = data;                            
		        byte[] buffer = new byte[1024];       
		        int len;                               
		          
		        for(;data>0;data--){                   
		            len = in.read(buffer);
		            out.write(buffer,0,len);
		        }
		        
		        System.out.println("Filename:" + filename + ", " +datas+" kbytes of file is received.");
		        out.flush();
		        out.close();
	        }
	        
        }
        catch(SocketException e){ // when socket connection is disconnected.
        	System.out.println("Socket is disconnected");
        	System.out.println("Exception: " + e);
        	
        	download_from_client(soc);
        }
    }
 
}
 
