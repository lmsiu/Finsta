package com.example.finsta.fragments;

import android.util.Log;

import com.example.finsta.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends postsFragment {
    @Override
    protected void queryPosts() {
        //super.queryPosts();
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //only include posts by the user
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        //only show 20 posts at a time
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue getting posts", e);
                }

                for(Post post: posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", user: " + post.getUser().getUsername());
                }

                allPosts.clear();
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);

            }
        });
    }
}
