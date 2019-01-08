package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private static final int PORT = 5002;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();
			// 2. Binding(Scoket에 SocketAddress(IPAddress + port) 바인딩한다.)
			InetAddress inetAddress = InetAddress.getLocalHost();
			String loaclhostAddress = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(loaclhostAddress, PORT));
			System.out.println("[server] binding " + loaclhostAddress + " : " + PORT);
			// 3. accept(클라이언트로부터 연결요청을 기다린다.)
			Socket socket = serverSocket.accept();// Blocking
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			System.out.println("[server] connected by client [" + remoteHostAddress + ":" + remotePort + "]");

			try {
				// 4. IOStream 받아오기

				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				while (true) {
					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // blocking
					if (readByteCount == -1) {
						// 정상종료 : remote socket close()
						// 메소드를 통해서 정상적으로 종료 소켓을 닫은 경우

						System.out.println("[server] closed by client");
						break;
					}
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					// 배열을 0부터 카운트만큼 디코딩해라
					System.out.println("[server] received" + data);
					// 6번 데이터 쓰기

					os.write(data.getBytes("UTF-8"));
				}
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
		} catch (IOException e) {
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
}
