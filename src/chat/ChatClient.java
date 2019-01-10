package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "218.39.221.68";
	private static final int PORT = 9991;
	//private static final String SERVER_IP = "218.39.221.66";
	//private static final int PORT = 5001;
	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;

		try {
			scanner = new Scanner(System.in);
			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, PORT));

			//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);

			System.err.print("닉네임>>");
			String nickname = scanner.nextLine();
			printWriter.println("join:"+nickname);
			printWriter.flush();
			
			while(true) {
				new ChatClientThread(socket).start();	
				
				String input = scanner.nextLine();

				if("quit".equals(input)==true) {
					printWriter.println("quit");
					printWriter.flush();
					break;
				} else {
					printWriter.println("message:"+nickname+":"+input);
					printWriter.flush();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed()==false) {
					socket.close();
				}
				if(scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

}