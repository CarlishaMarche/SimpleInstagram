package com.codepath.simpleinstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    ImageView profilePic;
    TextView handle;
    Button setBtn;
    Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = findViewById(R.id.profilePictureView);
        handle = findViewById(R.id.handleProfileView);
        setBtn = findViewById(R.id.setProfilePictureBtn);
        logOutBtn = findViewById(R.id.logOutPBtn);

        if (ParseUser.getCurrentUser().getParseFile("profilePicture")!= null ){
            Glide.with(this).load(ParseUser.getCurrentUser().getParseFile("profilePicture").getUrl()).into(profilePic);
        }

        handle.setText(ParseUser.getCurrentUser().getUsername());

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent i = new Intent(ProfileActivity.this, LogInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void saveToUser(File photoFile) {
        ParseUser currUser = ParseUser.getCurrentUser();
        currUser.put("profilePicture", new ParseFile(photoFile));
        try {
            currUser.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    /* Returns the File for a photo stored on disk given the fileName */
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                // RESIZE BITMAP, see section below
                // Load a bitmap from the drawable folder
//                Bitmap bMap = BitmapFactory.decodeResource(getResources(), getView().getId());
                // Resize the bitmap to 150x100 (width x height)
                Bitmap bMapScaled = Bitmap.createScaledBitmap(takenImage, 150, 100, true);
                // Loads the resized Bitmap into an ImageView

                // Load the taken image into a preview
                ImageView proPic =  findViewById(R.id.profilePictureView);
                proPic.setImageBitmap(bMapScaled);
                saveToUser(photoFile);
            } else { // Result was a failure
                Toast.makeText(this , "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
