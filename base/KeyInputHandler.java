package base;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * A class to handle keyboard input from the user. The class
 * handles both dynamic input during game play, i.e. left/right 
 * and shoot, and more static type input (i.e. press any key to
 * continue)
 * 
 * This has been implemented as an inner class more through 
 * habbit then anything else. Its perfectly normal to implement
 * this as seperate class if slight less convienient.
 * 
 * @author Kevin Glass
 */
public class KeyInputHandler extends KeyAdapter {
	/** The number of key presses we've had while waiting for an "any key" press */
	private int pressCount = 1;
	private HashMap<Integer,Boolean> keys_state;
	private boolean waitingForKeyPress;
	private Observateur obs;
	
	public void add(Observateur o){
		obs=o;
	}
	public void remove(){
		obs=null;
	}
	
	public boolean isWaitingForKeyPress() {
		return waitingForKeyPress;
	}

	public void setWaitingForKeyPress(boolean waitingForKeyPress) {
		this.waitingForKeyPress = waitingForKeyPress;
	}

	public KeyInputHandler(){
		super();
		keys_state = new HashMap<>();
		waitingForKeyPress=false;
	}
	
	public boolean isKeyPressed(Integer e){
		return (keys_state.containsKey(e) 
				&& keys_state.get(e));
	}
	/**
	 * Notification from AWT that a key has been pressed. Note that
	 * a key being pressed is equal to being pushed down but *NOT*
	 * released. Thats where keyTyped() comes in.
	 *
	 * @param e The details of the key that was pressed 
	 */

	public void keyPressed(KeyEvent e) {
		keys_state.put(e.getKeyCode(), true);
	} 
	
	/**
	 * Notification from AWT that a key has been released.
	 *
	 * @param e The details of the key that was released 
	 */
	public void keyReleased(KeyEvent e) {
		keys_state.put(e.getKeyCode(), false);
	}

	/**
	 * Notification from AWT that a key has been typed. Note that
	 * typing a key means to both press and then release it.
	 *
	 * @param e The details of the key that was typed. 
	 */
	public void keyTyped(KeyEvent e) {
		// if we're waiting for a "any key" type then
		// check if we've recieved any recently. We may
		// have had a keyType() event from the user releasing
		// the shoot or move keys, hence the use of the "pressCount"
		// counter.
		if (waitingForKeyPress) {
			if (pressCount == 1) {
				// since we've now recieved our key typed
				// event we can mark it as such and start 
				// our new game
				waitingForKeyPress = false;
				pressCount = 0;
			} else {
				pressCount++;
			}
		}
		updateEvent();
		// if we hit escape, then quit the game
		if (e.getKeyChar() == 27) {
			System.exit(0);
		}
	}
	
	public void updateEvent(){
		if(obs!=null)
			obs.handleEvent(this);
	}
}