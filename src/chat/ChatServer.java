package chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private static final int PORT = 9991;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<Writer> listWriters = new ArrayList<Writer>();
		
		try {
			serverSocket = new ServerSocket();
			//1-1
			//종료후 빨리 바인딩을 하기 위해서
			
			serverSocket.setReuseAddress(true);
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipAddress = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(ipAddress, PORT));
			log("binding" + ipAddress + ":" + PORT);

			while (true) {
				Socket socket = serverSocket.accept();
				Thread thread = new ChatServerThread(socket,listWriters);
				thread.start();
			}

		} catch (IOException e) {
			log("error : " + e);

		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				log("error : " + e);
			}
		}

	}

	public static void log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() + "]" + log);
	}
}