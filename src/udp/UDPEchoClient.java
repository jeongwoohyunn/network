package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String SERVER_IP = "218.39.221.68";

	public static void main(String[] args) {
		Scanner scanner = null;
		DatagramSocket socket = null;

		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. 소켓 생성
			socket = new DatagramSocket();

			while (true) {
				// 3. 사용자 입력 받음
				System.out.print(">>");
				String message = scanner.nextLine();

				if ("quit".equals(message)) {
					break;
				}

				// 4. 메시지 전송
				byte[] data = message.getBytes("UTF-8");
				// 패킷안에 넣을바이트
				DatagramPacket sendPacket = new DatagramPacket(data, data.length,
						new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));

				socket.send(sendPacket);

				// 5. 메시지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE],
						UDPEchoServer.BUFFER_SIZE);
				// synchronized(socket) {
				socket.receive(receivePacket);
				message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

				if ("".equals(message)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
					message = format.format(new Date());
					System.out.println(message);
				} else
					System.out.println("<<" + message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 자원정리
			if (scanner != null) {
				scanner.close();
			}
			if (socket != null && socket.isClosed() == false) {
				scanner.close();
			}
		}
	}
}
