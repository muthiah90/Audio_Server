package edu.uic.cs478.Muthiah.Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import edu.uic.cs478.Muthiah.Service.MusicKey;

public class AudioServer extends Service {

	// Initializing the Media Player
	MediaPlayer mediaPlayer;
	private boolean Pause = false;
	private int playedLength;

	// Resource Locator for the song
	private Uri songURI;

	// Variables for the players state manipulation
	private int currentTrackSelection = 0;
	private int previousTrack = 0;
	private String playerState = "Idle";

	// Initializing the Database Helper
	private DatabaseHelper dbHelper;

	/**
	 * On Create method of the service which gets called when the service is
	 * initialized for the very first time
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// Getting a reference to the DatabaseHelper class
		dbHelper = new DatabaseHelper(getApplicationContext());

		// Clearing all the contents of the database before starting to insert
		// data in it
		clearAll();
	}

	// Implement the Stub for the MusicKey
	private final MusicKey.Stub mBinder = new MusicKey.Stub() {

		/**
		 * This method starts the Music Player with the selected track
		 * Overridden method thats defined in the aidl file
		 */
		@Override
		public void startMusic(int clipID) throws RemoteException {

			// Initializing the media player
			if (mediaPlayer == null) {
				mediaPlayer = new MediaPlayer();
			} else {
				mediaPlayer.reset();
			}

			// Setting on complete listener for the Media PLayer to keeo track
			// of the completion of the selected song
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {

					// Broadcasting an intent to the client application soon
					// after the track completes
					Intent intent = new Intent();
					intent.setAction("android.intent.action.MUSIC_COMPLETED");
					sendBroadcast(intent);

				}
			});

			// Switch statement to load the selected track into the URI
			switch (clipID) {
			case 1:
				songURI = new Uri.Builder()
						.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
						.authority(getApplicationContext().getPackageName())
						.appendPath(String.valueOf(R.raw.endcredits)).build();
				break;

			case 2:
				songURI = new Uri.Builder()
						.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
						.authority(getApplicationContext().getPackageName())
						.appendPath(String.valueOf(R.raw.veena)).build();
				break;

			case 3:
				songURI = new Uri.Builder()
						.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
						.authority(getApplicationContext().getPackageName())
						.appendPath(String.valueOf(R.raw.dombia)).build();
				break;

			default:
				songURI = new Uri.Builder()
						.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
						.authority(getApplicationContext().getPackageName())
						.appendPath(String.valueOf(R.raw.veena)).build();
				break;
			}

			if (mediaPlayer != null) {
				try {

					// Setting the data source to the media player
					mediaPlayer.setDataSource(getApplicationContext(), songURI);

					// Preparing ans starting the media player
					mediaPlayer.prepare();
					mediaPlayer.start();

					// Pushing information for status tracking
					if (previousTrack == 0) {
						previousTrack = clipID;
						currentTrackSelection = clipID;
					} else {
						previousTrack = currentTrackSelection;
						currentTrackSelection = clipID;
					}

					// Inserting transaction record
					insertTransactionRec(musicPlayerState(), "Play");
					playerState = "Play";
				} catch (Exception e) {

				}
			}

		}

		/**
		 * This method stops the music player irrepsective of the track thats
		 * being player currently Overridden method thats defined in the aidl
		 * file
		 */
		@Override
		public void stopMusic() throws RemoteException {

			if (mediaPlayer.isPlaying()) {

				// Stropping the music player
				mediaPlayer.stop();

				// Inserting transaction record
				insertTransactionRec(musicPlayerState(), "Stop");

				playerState = "Stop";
			}
		}

		/**
		 * This method pauses the music player irrespective of the thats being
		 * played currently Overridden method thats defined in the aidl file
		 */
		@Override
		public void pauseMusic() throws RemoteException {
			if (mediaPlayer.isPlaying()) {

				// Pausing the media player
				mediaPlayer.pause();

				// Getting the track progress
				playedLength = mediaPlayer.getCurrentPosition();
				Pause = true;

				// Inserting transaction record
				insertTransactionRec(musicPlayerState(), "Pause");

				playerState = "Pause";
			}

		}

		/**
		 * This method resumes the music player to play the track in which the
		 * music was stopped Overridden method thats defined in the aidl file
		 */
		@Override
		public void resumePlay() throws RemoteException {
			if (Pause) {

				// The media is seeked to the position in which the music was
				// stopped. The length for seeking is obtained from the media
				// player when the music was about to be paused
				mediaPlayer.seekTo(playedLength);

				// Starting the music player
				mediaPlayer.start();
				Pause = false;

				// inserting transaction record
				insertTransactionRec(musicPlayerState(), "Resume");

				playerState = "Resume";
			}

		}

		/**
		 * This method retrieves all the transaction records from the SQLite
		 * database Overridden method thats defined in the aidl file
		 * 
		 * This method returns a list of records
		 */
		@Override
		public List<String> readTransactionData() throws RemoteException {
			String outputString = null;
			List<String> returnList = new ArrayList<String>();

			// Column names that are to be retrieved from the database
			String[] projection = { DatabaseHelper._ID,
					DatabaseHelper.REQUEST_TYPE, DatabaseHelper.CURRENT_STATE,
					DatabaseHelper.CLIP_NUM, DatabaseHelper.DATE,
					DatabaseHelper.TIME };

			// Setting up the database cursor with the columns to be retrieved
			Cursor dbCursor = dbHelper.getReadableDatabase().query(
					DatabaseHelper.TABLE_NAME, projection, null, null, null,
					null, null);

			// Check if there is data in the cursor
			if (dbCursor.moveToFirst()) {

				// Looping through the records in the cursor and pushing them
				// into a string
				while (!dbCursor.isAfterLast()) {

					// Retrieving data from the cursor and putting them into a
					// string
					outputString = String.valueOf(dbCursor.getInt(dbCursor
							.getColumnIndex(DatabaseHelper._ID)))
							+ " , "
							+ dbCursor
									.getString(dbCursor
											.getColumnIndex(DatabaseHelper.REQUEST_TYPE))
							+ " , "
							+ dbCursor
									.getString(dbCursor
											.getColumnIndex(DatabaseHelper.CURRENT_STATE))
							+ " , "
							+ dbCursor.getString(dbCursor
									.getColumnIndex(DatabaseHelper.CLIP_NUM))
							+ " , "
							+ dbCursor.getString(dbCursor
									.getColumnIndex(DatabaseHelper.DATE))
							+ " , "
							+ dbCursor.getString(dbCursor
									.getColumnIndex(DatabaseHelper.TIME));

					// Pushing all the strings into a list
					returnList.add(outputString);
					dbCursor.moveToNext();
				}
			}

			// Returning the list to the caller method
			return returnList;
		}

	};

	// Return the Stub defined above
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void clearAll() {

		dbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE_NAME, null,
				null);

	}

	/**
	 * This method inserts the trabsaction record into the database
	 * 
	 * @param runningState
	 * @param requestType
	 */
	private void insertTransactionRec(String runningState, String requestType) {

		// Initializing the content Values and calendar
		ContentValues recordValues = new ContentValues();
		Calendar calendar = Calendar.getInstance();

		// Getting the date and time in String format so that it can be stored
		// in the database
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy h:mm:ss a");
		String date = format.format(calendar.getTime());

		// Pushing all the data into Content Vales corresponding to the columns
		// in the database
		recordValues.put(DatabaseHelper.CLIP_NUM,
				String.valueOf(currentTrackSelection));
		recordValues.put(DatabaseHelper.CURRENT_STATE, runningState);
		recordValues.put(DatabaseHelper.DATE, date);
		recordValues.put(DatabaseHelper.REQUEST_TYPE, requestType);
		recordValues.put(DatabaseHelper.TIME, date);

		// Getting a writable database to push all the content values into the
		// database
		dbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_NAME, null,
				recordValues);

		// Clearing the content values
		recordValues.clear();
	}

	/**
	 * This method creates the players current state based on previous and
	 * current player state
	 * 
	 * @return
	 */
	private String musicPlayerState() {
		String currentState = null;

		// Check if the player is idle
		if (playerState == "Idle") {
			currentState = "Idle";
		} else {
			// If the player is starting to play, pause or resume
			if (playerState == "Play") {
				currentState = "Playing clp# "
						+ String.valueOf(previousTrack);
			} else if (playerState == "Pause") {
				currentState = "Paused in clp# "
						+ String.valueOf(previousTrack);
			} else if (playerState == "Resume") {
				currentState = "Resmd in clp# "
						+ String.valueOf(previousTrack);
			} else {
				currentState = "Stpd on clp# "
						+ String.valueOf(previousTrack);
			}
		}

		return currentState;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		mediaPlayer.stop();
		return super.onUnbind(intent);
	}
}
