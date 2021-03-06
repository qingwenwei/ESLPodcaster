package com.qingwenwei.eslpodcaster.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qingwenwei.eslpodcaster.R;
import com.qingwenwei.eslpodcaster.entity.PodcastEpisode;

import java.util.ArrayList;
import java.util.List;

public class ArchiveListAdapter extends RecyclerView.Adapter {
    private static final String TAG = "ArchiveListAdapter";

    private List<PodcastEpisode> episodes;

    private View.OnLongClickListener onCardViewLongClickListener;
    private View.OnClickListener onCardViewClickListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_EMPTY = 0;

    //adaptor constructor
    public ArchiveListAdapter() {
        this.episodes = new ArrayList<>();
    }

    public static class ArchiveViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;
        public final CardView cardView;
        public final TextView titleTextView;
        public final TextView subtitleTextView;
        public final TextView archivedDateTextView;

        public ArchiveViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.archiveCardView);
            titleTextView = (TextView) view.findViewById(R.id.archiveTitleTextView);
            subtitleTextView = (TextView) view.findViewById(R.id.archiveSubtitleTextView);
            archivedDateTextView = (TextView) view.findViewById(R.id.archiveDateTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleTextView.getText();
        }
    }

    public static class NoDataViewHolder extends RecyclerView.ViewHolder {
        public final TextView noDataHintTextView;

        public NoDataViewHolder(View view) {
            super(view);
            noDataHintTextView = (TextView) view.findViewById(R.id.noDataHintTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + noDataHintTextView.getText();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return episodes.get(position) != null ? VIEW_ITEM : VIEW_EMPTY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder()");

        RecyclerView.ViewHolder viewHolder;
        if(viewType == VIEW_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_layout_archives_list, parent, false);
            viewHolder = new ArchiveViewHolder(view);
        }else{
            // no data available
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_no_date_item, parent, false);
            viewHolder = new NoDataViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ArchiveViewHolder){
            PodcastEpisode episode = episodes.get(position);
            ((ArchiveViewHolder)holder).mBoundString = episode.getTitle();
            ((ArchiveViewHolder)holder).titleTextView.setText(episode.getTitle());
            ((ArchiveViewHolder)holder).subtitleTextView.setText(episode.getSubtitle());
            ((ArchiveViewHolder)holder).archivedDateTextView.setText(episode.getArchivedDate());
            ((ArchiveViewHolder)holder).cardView.setOnLongClickListener(onCardViewLongClickListener);
            ((ArchiveViewHolder)holder).cardView.setOnClickListener(onCardViewClickListener);
            ((ArchiveViewHolder)holder).cardView.setTag(episode);
        }
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public void updateEpisodes(List<PodcastEpisode> newEpisodes){
        this.episodes.clear();
        this.episodes.addAll(newEpisodes);
        this.notifyDataSetChanged();
    }

    public void setOnCardViewLongClickListener(View.OnLongClickListener listener) {
        this.onCardViewLongClickListener = listener;
    }

    public void setOnCardViewClickListener(View.OnClickListener listener) {
        this.onCardViewClickListener = listener;
    }

}
