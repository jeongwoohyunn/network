package chat.client.win;

import java.util.Scanner;

public class ChatClientApp {

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();

			if (name.isEmpty() == false) {

				break;
			}

			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}

		scanner.close();
		
		//String request = null;
		
		//String[] tokens = request.split(":");
//		if("join:ok".equals(tokens[0])) {
//			
//		}
		// join
		// Response가 join ok면
		//
		ChatWindow cw = new ChatWindow(name);
		cw.show();
		//new ChatWindow cw
		new ChatWindowThread(cw).start();
	}
}
