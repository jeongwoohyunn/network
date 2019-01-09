package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket serverSocket;
	private static final int PORT = 6002;
	public EchoServerReceiveThread(Socket socket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		
		InetAddress inetAddress = InetAddress.getLocalHost();
		String loaclhostAddress = inetAddress.getHostAddress();

		serverSocket.bind(new InetSocketAddress(loaclhostAddress, PORT));
		System.out.println("[server] binding " + loaclhostAddress + " : " + PORT);
		// while(true){ thread

		while(true) {
		// 3. accept(클라이언트로부터 연결요청을 기다린다.)
		//Socket socket = serverSocket.accept();
		Thread therad = new EchoServerReceiveThread();
		thread.start();
		}
		//연결 후에 통신을 위한 객체들을 얻는다.
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		System.out.println("connected by client[" + inetRemoteSocketAddress.getAddress().getHostAddress() + ":"
				+ inetRemoteSocketAddress.getPort() + "]");

		try {
			// 4. IOStream 받아오기

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {
				// 5. 데이터 읽기(수신)
				String data = br.readLine();
				if (data == null) {
					System.out.println("closed by client");
					break;
				}
				System.out.println("received:" + data);

				// 6. 데이터 쓰기(전송)
				pw.println(data);
			} // 6번 데이터 쓰기

			// os.write(data.getBytes("UTF-8"));
		} catch (SocketException e) {
			System.out.println("[server] abnormal by client");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7. 자원정리
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} catch (

	IOException e) {
		e.printStackTrace();
	} finally {
		try {
			if (serverSocket != null && serverSocket.isClosed() == false) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

}
