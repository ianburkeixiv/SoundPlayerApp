import javax.sound.midi.*;

/*
 * This class plays piano notes from 5 to note 61. This version introduces a static utility method that makes a message
 * and returns a MidiEvent. (Shorter way of doing it compared to MiniMusicCmdLine) 
 */
public class MiniMusicPlayer1 {
	public static void main(String[] args) {
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
			
			for (int i=5; i<61; i+=4) {
				track.add(makeEvent(144, 1, i, 100, i));
				track.add(makeEvent(128, 1, i, 100, i + 2));
			}//for loop end
			
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
			
		} catch (Exception e) {e.printStackTrace();	}//catch end
		
	}// main end
	
	public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
			
		} catch (Exception e) {e.printStackTrace();}// catch end
			
		
		return event;
		
	}// makeEvent end
	
}//class end
