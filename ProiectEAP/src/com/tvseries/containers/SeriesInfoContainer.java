package com.tvseries.containers;

import com.tvseries.utils.Triplet;

import java.util.List;

public class SeriesInfoContainer
{
    private Integer series_id;
    private String series_title;

    private String rating;
    private String series_poster;

    private List<String> genres;

    private List<Triplet<Integer, String, String>> seasons; //list of (season_id, season_title, season_poster)

    public SeriesInfoContainer()
    {

    }

    public SeriesInfoContainer(Integer series_id, String series_title, String rating, String series_poster, List<String> genres, List<Triplet<Integer, String, String>> seasons)
    {
        this.series_id = series_id;
        this.series_title = series_title;
        this.rating = rating;
        this.series_poster = series_poster;
        this.genres = genres;
        this.seasons = seasons;
    }

    public Integer getSeries_id()
    {
        return series_id;
    }

    public void setSeries_id(Integer series_id)
    {
        this.series_id = series_id;
    }

    public String getSeries_title()
    {
        return series_title;
    }

    public void setSeries_title(String series_title)
    {
        this.series_title = series_title;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getSeries_poster()
    {
        return series_poster;
    }

    public void setSeries_poster(String series_poster)
    {
        this.series_poster = series_poster;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public List<Triplet<Integer, String, String>> getSeasons()
    {
        return seasons;
    }

    public void setSeasons(List<Triplet<Integer, String, String>> seasons)
    {
        this.seasons = seasons;
    }
}
