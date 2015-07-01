package phoenix.record.run.recorder;

import java.awt.AWTException;
import java.io.IOException;

import phoenix.record.constant.Constant;


@SuppressWarnings("serial")
public class ScreenRecorderMain  extends javax.swing.JFrame{
    
    private ScreenRecorder screenRecorder;

	public ScreenRecorderMain() {

    }
    
    public void start(String mediaName) {
            try {
				screenRecorder = new ScreenRecorder(mediaName,getGraphicsConfiguration(), Constant.BIT_DEPTH, Constant.CURSOR_STYLE, Constant.SCREEN_RATE,Constant.MOUSE_RATE);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            screenRecorder.start();
    }

    public void stop() {
/*        if (screenRecorder != null) {
            final ScreenRecorder r = screenRecorder;           
            new Worker<Object>() {
                @Override
                protected Object construct() throws Exception {                	
                    r.stop(); 
                    screenRecorder = null;
                    return null;
                }
                @Override
                protected void finished() {
 
                }
            }.start();
        }*/
    	try {
    		
			screenRecorder.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }
}
