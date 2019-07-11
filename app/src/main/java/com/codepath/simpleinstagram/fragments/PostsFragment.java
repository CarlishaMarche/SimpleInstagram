package com.codepath.simpleinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.simpleinstagram.Post;
import com.codepath.simpleinstagram.PostsAdapter;
import com.codepath.simpleinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    public final String TAG = "Post Fragment";

    RecyclerView timeline;
    private SwipeRefreshLayout swipeContainer;

    protected PostsAdapter adapter;
    protected List<Post> mPosts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timeline = view.findViewById(R.id.rvtimeline);
        super.onViewCreated(view, savedInstanceState);

        // create the data source
        mPosts = new ArrayList<>();
        // create the adapter
        adapter = new PostsAdapter(getContext(), mPosts);
        // set the adapter on the recycler view
        timeline.setAdapter(adapter);
        // set the layout manager on the recycler view
        timeline.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeContainer =  view.findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                queryPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        queryPosts();

    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e!=null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(objects);
                adapter.notifyDataSetChanged();

                for (int i = 0; i < objects.size(); i++) {
                    Post post = objects.get(i);
                    Log.d(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
            }

        });
    }
}
