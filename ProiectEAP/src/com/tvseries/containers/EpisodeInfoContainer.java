package com.tvseries.containers;

import com.tvseries.utils.Triplet;

public class EpisodeInfoContainer
{
    private Integer episode_id;
    private String episode_title;

    private Integer episode_no;
    private Integer duration; //minutes

    private Triplet<Integer, String, String> season; //(season_id, season_title, season_poster)

    public EpisodeInfoContainer()
    {

    }

    public EpisodeInfoContainer(Integer episode_id, String episode_title, Integer episode_no, Integer duration, Triplet<Integer, String, String> season)
    {
        this.episode_id = episode_id;
        this.episode_title = episode_title;
        this.episode_no = episode_no;
        this.duration = duration;
        this.season = season;
    }

    public Integer getEpisode_id()
    {
        return episode_id;
    }

    public void setEpisode_id(Integer episode_id)
    {
        this.episode_id = episode_id;
    }

    public String getEpisode_title()
    {
        return episode_title;
    }

    public void setEpisode_title(String episode_title)
    {
        this.episode_title = episode_title;
    }

    public Integer getEpisode_no()
    {
        return episode_no;
    }

    public void setEpisode_no(Integer episode_no)
    {
        this.episode_no = episode_no;
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }

    public Triplet<Integer, String, String> getSeason()
    {
        return season;
    }

    public void setSeason(Triplet<Integer, String, String> season)
    {
        this.season = season;
    }
}
