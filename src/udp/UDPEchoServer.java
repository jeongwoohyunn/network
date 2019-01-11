package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;

public class UDPEchoServer {
	public static final int PORT = 6000;
	public static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 1. socket 생성
			socket = new DatagramSocket(PORT);
			
			while(true) {
			// 2. 데이터 수신
			// 버퍼는 내용 (버퍼, 버퍼사이즈) 포트,버퍼는 위에 따로 선언하는것이 좋다.
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			socket.receive(receivePacket);
			
			byte[] data = receivePacket.getData();
			int length = receivePacket.getLength();
			String message = new String(data, 0, length, "UTF-8");// 데이터를 스트링형식으로

			System.out.println("[server] received" + message);
			
			//3. 데이터 전송
			byte[] sendData = message.getBytes("UTF-8");
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, 
					receivePacket.getAddress(),
					receivePacket.getPort());//위와 다른 방법
			
			socket.send(sendPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}

}
