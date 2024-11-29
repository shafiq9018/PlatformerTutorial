package gamestates;

import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import main.FlappyGame;
import ui.MenuButton;

public class State {

	protected FlappyGame flappyGame;

	public State(FlappyGame flappyGame) {
		this.flappyGame = flappyGame;
	}

	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}

	public FlappyGame getGame() {
		return flappyGame;
	}

	@SuppressWarnings("incomplete-switch")
	public void setGamestate(Gamestate state) {
		System.out.println("State: " + state.toString());
		switch (state) {
		case MENU -> flappyGame.getAudioPlayer().playSong(AudioPlayer.MENU_1);
		case PLAYING -> flappyGame.getAudioPlayer().setLevelSong(flappyGame.getPlaying().getLevelManager().getLevelIndex());
		}

		Gamestate.state = state;
	}

}