package thread;

public class MultithreadEx01 {

	public static void main(String[] args) {
		Thread digitThread = new DigitThread();
		
		digitThread.start();
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//메인에서 getId쓸때Thread.currentThread().getId()
		
	}

}
