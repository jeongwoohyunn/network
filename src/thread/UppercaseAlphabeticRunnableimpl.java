package thread;

public class UppercaseAlphabeticRunnableimpl extends UppercaseAlpbetic implements Runnable {
//얘는 스레드 클래스가 아니라서 start안됨
	@Override
	public void run() {
		print();
	}

}
