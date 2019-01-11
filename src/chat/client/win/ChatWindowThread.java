package chat.client.win;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ChatWindowThread extends Thread {
	public ChatWindow cw;

	public ChatWindowThread(ChatWindow cw) {
		this.cw = cw;
	}

	@Override
	public void run() {
		while(true) {
			InputStreamReader isr;
			try {
				isr = new InputStreamReader(cw.getInputStream(),StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr);
				
				while(true) {
					String data = br.readLine();
					System.out.println(data);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	

}
