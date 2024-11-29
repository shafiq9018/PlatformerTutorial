package ui;

import static utils.Constants.UI.PauseButtons.SOUND_SIZE;
import static utils.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utils.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.FlappyGame;

public class AudioOptions {

	private VolumeButton volumeButton;
	private SoundButton musicButton, sfxButton;

	private FlappyGame flappyGame;

	public AudioOptions(FlappyGame flappyGame) {
		this.flappyGame = flappyGame;
		createSoundButtons();
		createVolumeButton();
	}

	private void createVolumeButton() {
		int vX = (int) (309 * FlappyGame.SCALE);
		int vY = (int) (278 * FlappyGame.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}

	private void createSoundButtons() {
		int soundX = (int) (450 * FlappyGame.SCALE);
		int musicY = (int) (140 * FlappyGame.SCALE);
		int sfxY = (int) (186 * FlappyGame.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	public void update() {
		musicButton.update();
		sfxButton.update();

		volumeButton.update();
	}

	public void draw(Graphics g) {
		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);

		// Volume Button
		volumeButton.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			float valueBefore = volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			float valueAfter = volumeButton.getFloatValue();
			if (valueBefore != valueAfter)
				flappyGame.getAudioPlayer().setVolume(valueAfter);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
				flappyGame.getAudioPlayer().toggleSongMute();
			}

		} else if (isIn(e, sfxButton)) {
			if (sfxButton.isMousePressed()) {
				sfxButton.setMuted(!sfxButton.isMuted());
				flappyGame.getAudioPlayer().toggleEffectMute();
			}
		}

		musicButton.resetBools();
		sfxButton.resetBools();

		volumeButton.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);

		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}
