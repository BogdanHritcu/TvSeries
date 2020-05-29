package com.tvseries.containers;

import com.tvseries.utils.Triplet;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.List;

public class SeasonInfoContainer
{
    private Integer season_id;
    private String season_title;

    private LocalDate release_date;
    private LocalDate end_date;
    private Integer season_no;
    private String season_poster;

    private String watch_status;
    private Integer score;

    private Triplet<Integer, String, String> prequel; //(prequel_id, prequel_title, season_poster)

    private Triplet<Integer, String, String> sequel; //(sequel_id, sequel_title, season_poster)

    private Triplet<Integer, String, String> series; //(series_id, series_title, series_poster)

    private List<Triplet<Integer, String, Boolean>> episodes; //list of (episode_id, episode_title, //watched)
    //the Boolean is set by verifying a list of watched episodes

    public SeasonInfoContainer()
    {

    }

    public SeasonInfoContainer(Integer season_id, String season_title, LocalDate release_date, LocalDate end_date, Integer season_no, String season_poster, String watch_status, Integer score, Triplet<Integer, String, String> prequel, Triplet<Integer, String, String> sequel, Triplet<Integer, String, String> series, List<Triplet<Integer, String, Boolean>> episodes)
    {
        this.season_id = season_id;
        this.season_title = season_title;
        this.release_date = release_date;
        this.end_date = end_date;
        this.season_no = season_no;
        this.season_poster = season_poster;
        this.watch_status = watch_status;
        this.score = score;
        this.prequel = prequel;
        this.sequel = sequel;
        this.series = series;
        this.episodes = episodes;
    }

    public Integer getSeason_id()
    {
        return season_id;
    }

    public void setSeason_id(Integer season_id)
    {
        this.season_id = season_id;
    }

    public String getSeason_title()
    {
        return season_title;
    }

    public void setSeason_title(String season_title)
    {
        this.season_title = season_title;
    }

    public LocalDate getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date)
    {
        this.release_date = release_date;
    }

    public LocalDate getEnd_date()
    {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date)
    {
        this.end_date = end_date;
    }

    public Integer getSeason_no()
    {
        return season_no;
    }

    public void setSeason_no(Integer season_no)
    {
        this.season_no = season_no;
    }

    public String getSeason_poster()
    {
        return season_poster;
    }

    public void setSeason_poster(String season_poster)
    {
        this.season_poster = season_poster;
    }

    public String getWatch_status()
    {
        return watch_status;
    }

    public void setWatch_status(String watch_status)
    {
        this.watch_status = watch_status;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public Triplet<Integer, String, String> getPrequel()
    {
        return prequel;
    }

    public void setPrequel(Triplet<Integer, String, String> prequel)
    {
        this.prequel = prequel;
    }

    public Triplet<Integer, String, String> getSequel()
    {
        return sequel;
    }

    public void setSequel(Triplet<Integer, String, String> sequel)
    {
        this.sequel = sequel;
    }

    public Triplet<Integer, String, String> getSeries()
    {
        return series;
    }

    public void setSeries(Triplet<Integer, String, String> series)
    {
        this.series = series;
    }

    public List<Triplet<Integer, String, Boolean>> getEpisodes()
    {
        return episodes;
    }

    public void setEpisodes(List<Triplet<Integer, String, Boolean>> episodes)
    {
        this.episodes = episodes;
    }
}
