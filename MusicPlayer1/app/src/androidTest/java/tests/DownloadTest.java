package tests;

import android.net.Uri;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;

import com.develop.awong.musicplayer2.Download;
import com.develop.awong.musicplayer2.MenuActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;

/**
 * Created by parkn on 3/8/2018.
 */

public class DownloadTest {
    Download download;
    long songID;

    @Rule
    public ActivityTestRule<MenuActivity> menuActivity = new ActivityTestRule<MenuActivity>(MenuActivity.class);

    @Before
    public void setup() {
        download = new Download(menuActivity.getActivity());
    }

    @Test
    public void testDownloadData() {
        Uri sampleSong = Uri.parse("http://androidtutorialpoint.comli.com/DownloadManager/AndroidDownloadManager.mp3");
        songID = download.DownloadData(sampleSong,menuActivity.getActivity(),"Sample.mp3");
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "Sample.mp3");
        assertFalse(file.exists());
    }

}
