package com.qingwenwei.eslpodcaster.fragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qingwenwei.eslpodcaster.R;
import com.qingwenwei.eslpodcaster.adapter.ArchiveEpisodeRecyclerViewAdapter;
import com.qingwenwei.eslpodcaster.db.EpisodeDAO;
import com.qingwenwei.eslpodcaster.entity.PodcastEpisode;

import java.util.ArrayList;

public class ArchiveFragment extends Fragment
    implements View.OnLongClickListener, View.OnClickListener{

    private static final String TAG = "ArchiveFragment";

    private RecyclerView recyclerView;
    private ArchiveEpisodeRecyclerViewAdapter adapter;

//    public ArchiveFragment() {
//        // Required empty public constructor
//        this.adapter = new ArchiveEpisodeRecyclerViewAdapter();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        adapter = new ArchiveEpisodeRecyclerViewAdapter();
        adapter.setOnCardViewLongClickListener(this);
        adapter.setOnCardViewClickListener(this);

        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    public void refresh(){
        new RefreshArchivedEpisodeListAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public boolean onLongClick(View v) {
        //show dialog
        switch (v.getId()){
            case R.id.favoriteCardView:
                showPopupMenu((PodcastEpisode) v.getTag());
                break;
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        //load playing episode
        switch (v.getId()){
            case R.id.favoriteCardView:

                break;
        }
    }

    private class RefreshArchivedEpisodeListAsyncTask extends AsyncTask<Void, Void, ArrayList<PodcastEpisode>>{
        private static final String TAG = "RefreshArchivedEpisodeListAsyncTask";

        @Override
        protected ArrayList<PodcastEpisode> doInBackground(Void... params) {
            EpisodeDAO dao = new EpisodeDAO(getContext());
            ArrayList archives = (ArrayList) dao.getAllArchivedEpisodes();
            return archives;
        }

        @Override
        protected void onPostExecute(ArrayList<PodcastEpisode> podcastEpisodes) {
            adapter.updateEpisodes(podcastEpisodes);
            Log.i(TAG,"Refreshed archive list size: " + podcastEpisodes.size());
        }
    }

    private void showPopupMenu(final PodcastEpisode episode){
        CharSequence items[] = new CharSequence[] {
            "Download this episode",
            "Unarchive this episode"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,episode.getTitle() + " which:" + which);
                switch (which){
                    //download
                    case 0:{
//                        onEpisodeStatusChangeHandler.setEpisodeDownloaded(episode,true);

                        break;
                    }

                    //unarchive
                    case 1:{
//                        onEpisodeStatusChangeHandler.setEpisodeFavoured(episode,false);

                        break;
                    }
                }
            }
        });
        builder.show();
    }

}
