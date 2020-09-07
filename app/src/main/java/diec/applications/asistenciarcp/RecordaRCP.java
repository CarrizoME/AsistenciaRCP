package diec.applications.asistenciarcp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.aplications.asistenciarcp.R;


public class RecordaRCP extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorda_rcp);
        VideoView videoView= findViewById(R.id.video_view);
        String videoDir = "android.resource://" + getPackageName() + "/" + R.raw.tecnicarcp;
        Uri uri=Uri.parse(videoDir);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();

    }

}
