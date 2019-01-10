package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClientThread extends Thread {
	public Socket socket;
	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		while(true) {
			
			InputStreamReader isr;
			try {
				isr = new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr);
				
				while(true) {
					String data = br.readLine();
					System.out.println(">>"+data);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
}
