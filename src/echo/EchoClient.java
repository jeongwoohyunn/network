package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "218.39.221.68";
	private static final int SERVER_PORT = 6001;
	//ip와 port는 여기서 관리 밑에서는 바꿀수없다.
	public static void main(String[] args) {
		Socket socket = null;
		//Scanner scanner = new Scanner(System.in);
		//소켓과 스캐너 둘다 try문밖에
		Scanner scanner = null;
		try {

			// 0.스캐너 생성
			scanner = new Scanner(System.in);

			// 1. 서버소켓 생성
			socket = new Socket();

			// 2.서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			//호스트와 포트에 연결
			System.out.println("[client] connected");
			
			// 3. IOStream 받아오기
			// InputStream is = socket.getInputStream();//
			// OutputStream os = socket.getOutputStream();//

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			//input받아온걸 buffered로
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {
				// 키보드 입력 받기
				System.out.print(">>");
				String line = scanner.nextLine();
				if ("exit".contentEquals(line)) {
					break;
				}

				// 4. 쓰기
				pw.println(line);

				// 5. 읽기
				String data = br.readLine();
				if (data == null) {
					System.out.println("연결 끊김");
					break;
				}
				System.out.println("<<" + data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
