package io.github.jeffreyzh.mediacontroller.activity;

import io.github.jeffreyzh.mediacontroller.MediaControllerCustom;
import io.github.jeffreyzh.mediacontroller.R;
import io.github.jeffreyzh.mediacontroller.VideoViewCustom;
import io.github.jeffreyzh.mediacontroller.util.FilePathManager;
import io.github.jeffreyzh.mediacontroller.util.FileUtil;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class ExampleActivityA extends Activity {
	// Put in your Video URL here
	// private String VideoURL =
	// "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

	// private String VideoURL =
	// "http://player.ku6.com/refer/JFGsSjWej-3PG60pjNqxOw../v.swf";

	// private String url = "http://commonsware.com/misc/test2.3gp";

	String url = "http://www.miaosky.com/wp-content/uploads/2013/02/meilikaiguo.mp3";

	// String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

	// String url =
	// "http://carey-blog-image.googlecode.com/files/vid_20120510_090204.mp4";

	String mp3Path = FilePathManager.getInstance().getFileDirectory() + "mpppp3.mp3";
	String mp4Path = FilePathManager.getInstance().getFileDirectory() + "mpppp4.mp4";

	// Declare some variables
	private ProgressDialog pDialog;
	VideoViewCustom videoview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set the layout from video_main.xml
		setContentView(R.layout.video_main);
		// Find your VideoView in your video_main.xml layout
		videoview = (VideoViewCustom) findViewById(R.id.VideoView);

		try {
			String resourcePath = FilePathManager.getInstance().getFileDirectory();
			FileUtil.createMultilevelDirectory(resourcePath);
			if (!FileUtil.isFileExisted(mp3Path)) {
				// FileUtil.copy(getResources().getAssets().open("big_buck_bunny.mp4"),
				// mp3Path, true);
				FileUtil.copy(getResources().getAssets().open("KungFuFighting.mp3"), mp3Path, true);
			}
			if (!FileUtil.isFileExisted(mp4Path)) {
				FileUtil.copy(getResources().getAssets().open("big_buck_bunny.mp4"), mp4Path, true);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Execute StreamVideo AsyncTask
		new StreamVideo().execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	// StreamVideo AsyncTask
	private class StreamVideo extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressbar
			pDialog = new ProgressDialog(ExampleActivityA.this);
			// Set progressbar title
			pDialog.setTitle("Android Video Streaming Tutorial");
			// Set progressbar message
			pDialog.setMessage("Buffering...");
			pDialog.setIndeterminate(false);
			// Show progressbar
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {

			try {
				// Start the MediaController
				MediaControllerCustom mediacontroller = new MediaControllerCustom(ExampleActivityA.this);
				// mediacontroller.setAnchorView((FrameLayout)
				// videoview.getParent());
				// Get the URL from String VideoURL
				videoview.setMediaController(mediacontroller);

				videoview.setVideoPath(mp3Path);
				// videoview.setVideoPath(mp4Path);

				// Uri video = Uri.parse(url);
				// videoview.setVideoURI(video);

				videoview.requestFocus();
				videoview.setOnPreparedListener(new OnPreparedListener() {
					// Close the progress bar and play the video
					public void onPrepared(MediaPlayer mp) {
						pDialog.dismiss();
						videoview.start();
					}
				});
			} catch (Exception e) {
				pDialog.dismiss();
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

		}
	}

}
