package com.qingwenwei.eslpodcaster.listener;

import com.qingwenwei.eslpodcaster.entity.PodcastEpisode;

public interface PodcastEpisodeStatusChangeHandler {
    void setEpisodeFavoured(PodcastEpisode episode, boolean favor);
    void setEpisodeDownloaded(PodcastEpisode episode, boolean downloaded);
}
