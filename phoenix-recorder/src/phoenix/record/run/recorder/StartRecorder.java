package phoenix.record.run.recorder;

import java.util.Scanner;

import phoenix.record.run.recorder.ScreenRecorderMain;

public class StartRecorder {

	public static void main(String[] args) {
		ScreenRecorderMain main = new ScreenRecorderMain();
		main.start("test");
		while(true){
			System.out.println("你要停止吗？请输入(stop)，程序会停止。");
			Scanner sc = new Scanner(System.in);
			if (sc.next().equalsIgnoreCase("stop")) {
				main.stop();
			}else{
				System.out.println("Input errors,Please re-enter!!");
			}
		}
	}
}
