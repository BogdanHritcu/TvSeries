package com.tvseries.containers;

import com.tvseries.utils.Triplet;

import java.util.ArrayList;
import java.util.List;

public class SearchListContainer
{
    private List<Triplet<Integer, String, String>> series;
    private List<Triplet<Integer, String, String>> seasons;

    public SearchListContainer()
    {
        series = new ArrayList<>();
        seasons = new ArrayList<>();
    }

    public SearchListContainer(List<Triplet<Integer, String, String>> series, List<Triplet<Integer, String, String>> seasons)
    {
        this.series = series;
        this.seasons = seasons;
    }

    public List<Triplet<Integer, String, String>> getSeries()
    {
        return series;
    }

    public void setSeries(List<Triplet<Integer, String, String>> series)
    {
        this.series = series;
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
