package com.tvseries.dao;

import com.tvseries.containers.EpisodeInfoContainer;
import com.tvseries.containers.SeasonInfoContainer;
import com.tvseries.utils.C3P0DataSource;
import com.tvseries.utils.Triplet;
import javafx.util.Pair;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class EpisodeInfoDAO
{
    private static String white_list = "episode_id, episode_title, episode_no, duration, season_id";

    public static EpisodeInfoContainer getEpisodeInfo(Integer episode_id) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        EpisodeInfoContainer episode_info = new EpisodeInfoContainer();

        //build query
        query = "select episode_id, episode_title, episode_no, duration, " +
                "season_id, season_title, season_poster " +
                "from t_episode join t_season using(season_id) " +
                "where episode_id <=> ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, episode_id);
        //execute
        rs = st.executeQuery();

        //process results
        if (rs.next())
        {
            Triplet<Integer, String, String> season = new Triplet<>(rs.getObject("season_id", Integer.class), rs.getObject("season_title", String.class), rs.getObject("season_poster", String.class));
            episode_info.setEpisode_id(rs.getObject("episode_id", Integer.class));
            episode_info.setEpisode_title(rs.getObject("episode_title", String.class));
            episode_info.setEpisode_no(rs.getObject("episode_no", Integer.class));
            episode_info.setDuration(rs.getObject("duration", Integer.class));
            episode_info.setSeason(season);
        }

        //clean
        st.close();
        rs.close();
        con.close();

        return episode_info;
    }

    public static boolean existsEpisode(Integer episode_id) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;

        //build query
        query = "select episode_id from t_episode where episode_id <=> ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, episode_id);
        //execute
        rs = st.executeQuery();

        //process results
        boolean exists = rs.next();

        //clean
        st.close();
        rs.close();
        con.close();

        return exists;
    }

    public static boolean setEpisodeAttribute(Integer episode_id, String attribute_name, Object value) throws Exception
    {
        if(!white_list.contains(attribute_name))
        {
            return false;
        }

        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "update t_episode set %s = ? where episode_id <=> ?;";
        query = String.format(query, attribute_name);
        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, value);
        st.setObject(2, episode_id);
        //execute
        int rows = st.executeUpdate();

        //process results
        boolean updated = rows > 0;

        //clean
        st.close();
        con.close();

        return updated;
    }

    public static boolean addEpisode(List<Pair<String, Object>> attributes) throws Exception
    {
        int attributes_no = attributes.size();

        if(attributes_no < 1)
        {
            return false;
        }

        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        String columns = "%s";
        String column_name = attributes.get(0).getKey();
        if(!white_list.contains(column_name))
        {
            System.out.println("Not in whitelist:" + column_name);
            return false;
        }

        columns = String.format(columns, column_name);
        String values = "?";
        for(int i = 1; i < attributes_no; i++)
        {
            columns += ", %s";
            column_name = attributes.get(i).getKey();
            columns = String.format(columns, column_name);
            if(!white_list.contains(column_name))
            {
                System.out.println("Not in whitelist:" + column_name);
                return false;
            }

            values += ", ?";
        }
        query = "insert into t_episode(" + columns + ") values(" + values + ");";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        for(int i = 0; i < attributes_no; i++)
        {
            st.setObject(i + 1, attributes.get(i).getValue()); //set the attribute value
        }

        //execute
        boolean inserted = st.execute();

        //clean
        st.close();
        con.close();

        return inserted;
    }
}
