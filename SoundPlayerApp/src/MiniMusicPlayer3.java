import javax.swing.*;
import javax.sound.midi.*;
import java.awt.*;
import java.io.*;

/*
 * This version uses a GUI to draw graphics in time with the music being played
 */

public class MiniMusicPlayer3 {

	static JFrame f = new JFrame("My First Music Video");
	static MyDrawPanel ml;
	
	public static void main(String[] args) {
		MiniMusicPlayer3 mini = new MiniMusicPlayer3();
		mini.go();
	}//main end
	
	public void setUpGui() {
		ml = new MyDrawPanel();
		f.setContentPane(ml);
		f.setBounds(30,30, 300, 300);
		f.setVisible(true);
	}//method end
	
	public void go() {
		setUpGui();
		
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addControllerEventListener(ml, new int[] {127});
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
				
			int r = 0;
			for (int i = 0; i<60; i+=4) {
				
				r = (int) ((Math.random()*50) + 1);
				track.add(makeEvent(144,1,r,100,i));
				track.add(makeEvent(176,1,127,0,i));
				track.add(makeEvent(128,1,r,100,i+2));
				
			}//end loop
			
			sequencer.setSequence(seq);
			sequencer.start();
			sequencer.setTempoInBPM(120);
			
		} catch (Exception e) {e.printStackTrace();}
	}// go end
	
	class MyDrawPanel extends JPanel implements ControllerEventListener {
		
		boolean msg = false;

		@Override
		public void controlChange(ShortMessage arg0) {
			msg = true;
			repaint();
			
		}
		
		public void paintComponent(Graphics g) {
			if(msg) {
				Graphics2D g2 = (Graphics2D) g;
				
				int r = (int) (Math.random()*250);
				int gr = (int) (Math.random()*250);
				int b = (int) (Math.random()*250);
				
				g.setColor(new Color(r,gr,b));
				
				int ht = (int) ((Math.random()*120) + 10);
				int width = (int) ((Math.random()*120) + 10);
				int x = (int) ((Math.random()*40) + 10);
				int y = (int) ((Math.random()*40) + 10);
				g.fillRect(x, y, width, ht);
				msg = false;
			}//if end
		}//paintComponent end
		

	}//inner class end
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
			
		} catch (Exception e) {e.printStackTrace();}// catch end
			
		
		return event;
		
	}// makeEvent end

}//class end
