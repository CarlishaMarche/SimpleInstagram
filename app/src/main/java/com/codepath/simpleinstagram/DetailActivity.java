package com.codepath.simpleinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

public class DetailActivity extends AppCompatActivity {

    TextView handle;
    TextView timeCreated;
    TextView caption;
    ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        handle = findViewById(R.id.detailHandleView);
        timeCreated = findViewById(R.id.timeCreatedDetailView);
        caption = findViewById(R.id.detailDescriptionView);
        postImage = findViewById(R.id.detailPostImageView);

        String postId = getIntent().getStringExtra("postId");

      ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        try {
            Post currPost = postQuery.get(postId);
            timeCreated.setText(currPost.getCreatedAt().toString());
            caption.setText(currPost.getDescription());
            handle.setText(currPost.getUser().fetchIfNeeded().getUsername());
            ParseFile image = currPost.getImage();
            if (image != null ){
                Glide.with(this).load(image.getUrl()).into(postImage);
            }
        } catch (ParseException e) {
            Log.e("Detail Activity", "Unable to retrieve post!");
            e.printStackTrace();
        }


    }
}
