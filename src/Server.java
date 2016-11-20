import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
 
public class Server {
    
    static Socket client = new Socket();
    
    public static void main(String[] args) throws Exception{
        ServerSocket soc = new ServerSocket(9027);  //Æ÷Æ® 9027 
        System.out.println("Server Start");
        client = soc.accept();                       //accept client
        
        try{
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
        	System.out.println("Socket connection is disconnected");
        	System.out.println("Exception: " + e);
        	
        	soc.close();
        	System.out.println("Please restart Server and Client.");
        }
    }
 
}
 
