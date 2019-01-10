package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class ChatServerThread extends Thread{
	
	private String nickname;
	private Socket socket;
	List<Writer> listWriters;
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		
		int remotePort = inetRemoteSocketAddress.getPort();
		
		ChatServer.log(remoteHostAddress+":"+remotePort+"에서 접속을 하였습니다.");
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
			String request;
			while(true) {
				request = bufferedReader.readLine();
				if(request == null) {
					ChatServer.log(remoteHostAddress+":"+remotePort+"에서 접속을 끊었습니다.");
					break;
				}
				
				String[] tokens = request.split(":");
				
				if("join".equals(tokens[0])) {
					
					doJoin(tokens[1], printWriter);
					
				} else if("message".equals(tokens[0])){
					
					doMessage(tokens[2]);
					
				} else if("quit".equals(tokens[0])) {
					doQuit(printWriter);
				} else {
					ChatServer.log("Error : 알수없는 요청 ("+tokens[0]+")");
				}
			}
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		
		String data = nickname + " 님이 입장하였습니다.";
		broadcast(data);
		
		addWriter(writer);
		
		PrintWriter printWriter = (PrintWriter) writer;
		printWriter.println("join:ok");
		printWriter.flush();
		
	}
	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}
	
	private void doMessage(String message) {
		
		String data = nickname + ":"+message;
		broadcast(data);
		
		//PrintWriter printWriter = ;
		//printWriter.println("message:"+nickname+":"+message);
		//printWriter.flush();
	}
	
	private void doQuit(Writer writer) {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장 하였습니다.";
		broadcast(data);
		
		//PrintWriter printWriter = (PrintWriter) writer;
		//printWriter.println("quit");
		//printWriter.flush();
	}
	
	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}
	
	
	private void broadcast(String data) {
		synchronized (listWriters) {
			for(Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
	
	
	
}