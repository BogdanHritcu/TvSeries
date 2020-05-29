package com.tvseries.dao;

import com.tvseries.containers.EpisodeInfoContainer;
import com.tvseries.containers.SearchListContainer;
import com.tvseries.utils.C3P0DataSource;
import com.tvseries.utils.Triplet;
import org.apache.commons.validator.GenericValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchListDAO
{
    public static SearchListContainer getSearchList(String search_key, Integer limit_series, Integer limit_seasons) throws Exception
    {
        if(GenericValidator.isBlankOrNull(search_key))
        {
            return new SearchListContainer();
        }

        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        SearchListContainer search_list = new SearchListContainer();

        //build query
        query = "select series_id, series_title, series_poster " +
                "from t_series " +
                "where lower(series_title) like lower(?) limit ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, "%" + search_key + "%");
        st.setObject(2, limit_series);
        //execute
        rs = st.executeQuery();

        //process results
        List<Triplet<Integer, String, String>> series_list = new ArrayList<>();

        while (rs.next())
        {
            Triplet<Integer, String, String> series = new Triplet<>(rs.getObject("series_id", Integer.class),
                                                                    rs.getObject("series_title", String.class),
                                                                    rs.getObject("series_poster", String.class));
            series_list.add(series);
        }
        search_list.setSeries(series_list);

        //clean
        st.close();
        rs.close();

        //build query
        query = "select season_id, season_title, season_poster " +
                "from t_season " +
                "where lower(season_title) like lower(?) limit ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, "%" + search_key + "%");
        st.setObject(2, limit_seasons);
        //execute
        rs = st.executeQuery();

        //process results
        List<Triplet<Integer, String, String>> season_list = new ArrayList<>();
        while (rs.next())
        {
            Triplet<Integer, String, String> season = new Triplet<>(rs.getObject("season_id", Integer.class),
                                                                    rs.getObject("season_title", String.class),
                                                                    rs.getObject("season_poster", String.class));
            season_list.add(season);
        }
        search_list.setSeasons(season_list);

        //clean
        st.close();
        rs.close();
        con.close();

        return search_list;
    }
}
