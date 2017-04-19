import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver {

	public static void main(String[] args) throws IOException{
		int port = 4445;
		
		try(
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
		){
			String input, output;
			
			while((input = in.readLine()) != null){
				TCPprotocol t = new TCPprotocol(input);
				output = t.getReturn();
				out.println(output);
				if(output.equals("quit")){
					break;
				}
			}
		} catch(IOException e){
			System.out.println("Exception caught when trying to listen on port "
	                + port + " or listening for a connection");
	            System.out.println(e.getMessage());
		}

	}

}
