package com.music.android.sensilence;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZigmundAfraidFragment extends Fragment {
    View rootView;
    MusicAlbum musicAlbum;
    ListView listView;
    protected MediaPlayer mMediaPlayer;

    /*Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    musicAlbum.onFocusChange(focusChange,mMediaPlayer);
                }
            };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
           musicAlbum.releaseMediaPlayer( );
        }
    };
    // Create an array of songs
    final ArrayList<Song> songs = new ArrayList<>();

    public ZigmundAfraidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.song_list, container, false);
        musicAlbum = new MusicAlbum();
        //Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.AUDIO_SERVICE);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.zigmund_afraid_cover);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bmp);
        bitmapDrawable.setAlpha(100);
        listView = rootView.findViewById(R.id.list);
        listView.setBackground(bitmapDrawable);

        // Create a list of songs
        Song song = new Song("Zigmund Afraid", "Abroad", R.drawable.ic_za,
                "https://rildi.sunproxy.net/file/c3Z3TXhVSndLUzFEcXV0VkxZeFhlUWtQZ0FNbEVua3hURVNzbFFtOE85TnV3dmVmWENHKzVQZjZwOWo1b2xzdlJNbmZwOWJuQVI1bzlSVmdvd2p3dVhpb3R6Ky9vY2VGaTJLUkZXQXdmWFk9/Zigmund_Afraid_-_Abroad_(hydro.fm).mp3");
        songs.add(song);
        songs.add(new Song("Zigmund Afraid", "Abroad (Retroflex Encoded)",
                R.drawable.vt_dnb120, "https://rildi.sunproxy.net/file/c3Z3TXhVSndLUzFEcXV0VkxZeFhlUWtQZ0FNbEVua3hURVNzbFFtOE85TnV3dmVmWENHKzVQZjZwOWo1b2xzdk5qcW5ubm9id3hlMVduc3F4K2ZkK1lTTHM0UDVsZEgvaGx5cnY1VnIrTHM9/Zigmund_Afraid_-_Abroad_Retroflex_Encoded_(hydro.fm).mp3"));
        songs.add(new Song("Zigmund Afraid", "Pleasure was mine (∞)",
                R.drawable.pwm, R.raw.zigmund_afraid_pleasure_was_mine));
        // Create an {@link SongAdapter}, whose data source is a list of {@link Song}s. The
        // adapter knows how to create list items for each item in the list.
        SongAdapter adapter = new SongAdapter(getActivity(), songs, R.color.category_zigmund_afraid);
        listView.setAdapter(adapter);
        //Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(firstClickListener);
        return rootView;
    }

    AdapterView.OnItemClickListener firstClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
            musicAlbum.onFirstClick( view, position, mOnAudioFocusChangeListener, mCompletionListener,
                    secondClickListener, listView, songs, mAudioManager,getActivity());
        }
    };

    AdapterView.OnItemClickListener secondClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            musicAlbum.onSecondClick(firstClickListener,listView);
        }
    };
}
