package com.example.android.sensilence;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {
    /**
     * Resource ID for the background color for this list of songs
     */
    private int mColorResourceId;

    SongAdapter(Activity context, ArrayList<Song> songs, int colorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // The second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, songs);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Song} object located at this position in the list
        Song currentSong = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView bandTextView = listItemView.findViewById(R.id.sensilence_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        assert currentSong != null;
        bandTextView.setText(currentSong.getNameOfTheBand());
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current Song object and
        // set this text on the number TextView
        defaultTextView.setText(currentSong.getDefaultSong());
        //Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = listItemView.findViewById(R.id.image);
        //Set the ImageView to the image resource specified in the current song.
        imageView.setImageResource(currentSong.getImageResourceId());
        if (currentSong.hasImage()) {
            //Set the ImageView to the image resource specified in the current Song.
            imageView.setImageResource(currentSong.getImageResourceId());
        } else {
            imageView.setVisibility(View.GONE);
        }
        //Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        //Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        //Set the background color of the text container View
        textContainer.setBackgroundColor(color);
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
