package thread;

public class MultithreadEx03 {

	public static void main(String[] args) {
		Thread thread1 = new AlphabeticThread();
		Thread thread2 = new DigitThread();
		Thread thread3 = new Thread(new UppercaseAlphabeticRunnableimpl());
		thread1.start();
		thread2.start();
		//new Thread(runnalbe).start();
		thread3.start();
	}

}
