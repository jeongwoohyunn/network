package thread;

public class DigitThread extends Thread {
	
	@Override
	public void run() {
		for (int i = 0; i <= 9; i++) {
			System.out.print(+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//sleep가 좋지는 않다.
		}
		//얘는 thread에 getId가있다.
		super.run();
	}
	
}
