package chat.client.win;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import chat.ChatServer;

public class ChatClientThread extends Thread {
	private BufferedReader br;
	private Socket socket;
	private ChatWindow cw;

	public ChatClientThread(ChatWindow cw, Socket socket) {
		this.socket = socket;
		this.cw = cw;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while (true) {
				String data = br.readLine();
				cw.getTextArea().append(data);
				cw.getTextArea().append("\n");
				// 이코드가 의미하는 바
			}
		} catch (IOException e) {// io익셉션
			e.printStackTrace();
		}
	}
}
