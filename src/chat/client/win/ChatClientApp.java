package chat.client.win;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {
	private static final String SERVER_IP = "218.39.221.68";
	private static final int PORT = 9999;

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP,PORT));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),	"UTF-8"),true	);
		
		
		while (true) {

			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();

			if (name.isEmpty() == false) {
				pw.println("join:"+name);
				pw.flush();
				break;
			}

			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}
		
		scanner.close();
		
		ChatWindow cw = new ChatWindow(name,pw);
		

		new ChatClientThread(cw,socket).start();
		cw.show();
		}catch (IOException e) {		
			e.printStackTrace();
		}
	}
}
