package io.github.jeffreyzh.mediacontroller.activity;

import io.github.jeffreyzh.mediacontroller.MediaControllerCustom;
import io.github.jeffreyzh.mediacontroller.R;

import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;

public class ExampleActivityC extends Activity implements MediaPlayer.OnPreparedListener,
		MediaControllerCustom.MediaPlayerControl {

	SurfaceView videoSurface;
	MediaPlayer player;
	MediaControllerCustom controller;
	// String url =
	// "http://carey-blog-image.googlecode.com/files/vid_20120510_090204.mp4";

	String url = "http://www.miaosky.com/wp-content/uploads/2013/02/meilikaiguo.mp3";

	// private String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);

		player = new MediaPlayer();
		controller = new MediaControllerCustom(this);

		try {
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setDataSource(this, Uri.parse(url));
			player.setOnPreparedListener(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		controller.show();
		return false;
	}

	// End SurfaceHolder.Callback

	// Implement MediaPlayer.OnPreparedListener
	@Override
	public void onPrepared(MediaPlayer mp) {
		controller.setMediaPlayer(this);
		controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
		player.start();
	}

	// End MediaPlayer.OnPreparedListener

	// Implement VideoMediaController.MediaPlayerControl
	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return player.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return player.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public void pause() {
		player.pause();
	}

	@Override
	public void seekTo(int i) {
		player.seekTo(i);
	}

	@Override
	public void start() {
		player.start();
	}

	@Override
	public boolean isFullScreen() {
		return false;
	}

	@Override
	public void toggleFullScreen() {

	}
	// End VideoMediaController.MediaPlayerControl

}
