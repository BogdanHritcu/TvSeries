package com.tvseries.dao;

import com.tvseries.containers.BingeWatchingContainer;
import com.tvseries.containers.FriendsInfoContainer;
import com.tvseries.containers.SeasonsListContainer;
import com.tvseries.containers.UserInfoContainer;
import com.tvseries.utils.C3P0DataSource;
import com.tvseries.utils.Triplet;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDAO
{
    private static String white_list = "username,display_name,email,password,first_name,last_name,birth_date,picture_path,settings_id";

    public static UserInfoContainer getUserInfo(String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        UserInfoContainer user_info = new UserInfoContainer();

        //build query
        query = "select username, display_name, first_name, last_name, birth_date, picture_path " +
                "from t_user " +
                "where username <=> ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, username);
        //execute
        rs = st.executeQuery();

        //process results
        if(rs.next())
        {
            user_info.setUsername(rs.getObject("username", String.class));
            user_info.setDisplay_name(rs.getObject("display_name", String.class));
            user_info.setFirst_name(rs.getObject("first_name", String.class));
            user_info.setLast_name(rs.getObject("last_name", String.class));
            user_info.setBirth_date(rs.getObject("birth_date", LocalDate.class));
            user_info.setPicture_path(rs.getObject("picture_path", String.class));
        }

        //clean
        st.close();
        rs.close();

        query = "select username1 as friend_username, display_name, picture_path " +
                "from t_relationship join t_user on username1 = username " +
                "where relation_status <=> 'friends' and username2 <=> ? " +
                "union " +
                "select username2 as friend_username, display_name, picture_path " +
                "from t_relationship join t_user on username2 = username " +
                "where relation_status <=> 'friends' and username1 <=> ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, username);
        st.setObject(2, username);
        //execute
        rs = st.executeQuery();

        //process results
        List<FriendsInfoContainer> friends = new ArrayList<>();

        while(rs.next())
        {
            friends.add(new FriendsInfoContainer(rs.getObject("friend_username", String.class), rs.getObject("display_name", String.class), rs.getObject("picture_path", String.class)));
        }
        user_info.setFriends(friends);

        //clean
        st.close();
        rs.close();
        con.close();

        return user_info;
    }

    public static List<SeasonsListContainer> getUserList(String username, String watch_status) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        List<SeasonsListContainer> season_list = new ArrayList<>();

        //build query
        query = "select season_id, season_title, my_start, my_end, score, season_poster " +
                "from t_seasons_list join t_season using(season_id) " +
                "where username <=> ? and watch_status <=> ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, username);
        st.setObject(2, watch_status);
        //execute
        rs = st.executeQuery();

        while(rs.next())
        {
            SeasonsListContainer season = new SeasonsListContainer();
            season.setSeason_id(rs.getObject("season_id", Integer.class));
            season.setSeason_title(rs.getObject("season_title", String.class));
            season.setSeason_poster(rs.getObject("season_poster", String.class));
            season.setScore(rs.getObject("score", Integer.class));
            season.setMy_start(rs.getObject("my_start", LocalDate.class));
            season.setMy_end(rs.getObject("my_end", LocalDate.class));

            season_list.add(season);
        }

        //clean
        st.close();
        rs.close();
        con.close();

        return season_list;
    }

    public static Object getUserAttribute(String username, String attribute_name) throws Exception
    {
        if(!white_list.contains(attribute_name))
        {
            return null;
        }

        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        Object attribute = null;

        //build query
        query = "select %s " +
                "from t_user " +
                "where username <=> ?;";
        query = String.format(query, attribute_name);

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, username);
        //execute
        rs = st.executeQuery();

        if(rs.next())
        {
            attribute = rs.getObject(attribute_name);
        }

        //clean
        st.close();
        rs.close();
        con.close();

        return attribute;
    }

    public static boolean existsUser(String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;

        //build query
        query = "select 1 from t_user where username <=> ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, username);
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

    public static boolean setUserAttribute(String username, String attribute_name, Object value) throws Exception
    {
        if(!white_list.contains(attribute_name))
        {
            return false;
        }

        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "update t_user set %s = ? where username <=> ?;";
        query = String.format(query, attribute_name);

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, value);
        st.setObject(2, username);
        //execute
        int rows = st.executeUpdate();

        //process results
        boolean updated = rows > 0;

        //clean
        st.close();
        con.close();

        return updated;
    }

    public static boolean addUser(List<Pair<String, Object>> attributes) throws Exception
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
        query = "insert into t_user(" + columns + ") values(" + values + ");";

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

    public static boolean alreadyInList(String username, Integer season_id) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;

        //build query
        query = "select season_id from t_seasons_list where username <=> ? and season_id <=> ?;";

        //prepare
        st = con.prepareStatement(query);
        //set parameters
        st.setObject(1, username);
        st.setObject(2, season_id);
        //execute
        rs = st.executeQuery();

        //process results
        boolean in_list = rs.next();

        //clean
        st.close();
        rs.close();
        con.close();

        return in_list;
    }

    public static boolean updateSeasonsList(Integer season_id, String username, LocalDate my_start, LocalDate my_end, Integer score, String watch_status) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "insert into t_seasons_list(season_id, username, my_start, my_end, score, watch_status) " +
                "values(?, ?, ?, ?, ?, ?) " +
                "on duplicate key update my_start = ?, my_end = ?, score = ?, watch_status = ?;";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        st.setObject(1, season_id);
        st.setObject(2, username);
        st.setObject(3, my_start);
        st.setObject(4, my_end);
        st.setObject(5, score);
        st.setObject(6, watch_status);
        st.setObject(7, my_start);
        st.setObject(8, my_end);
        st.setObject(9, score);
        st.setObject(10, watch_status);

        //execute
        boolean updated = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return updated;
    }

    public static boolean removeSeason(Integer season_id, String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "delete from t_seasons_list where season_id <=> ? and username <=> ?";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        st.setObject(1, season_id);
        st.setObject(2, username);

        //execute
        boolean removed = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return removed;
    }

    public static boolean updateEpisodesList(List<Integer> episode_ids, String username, Integer season_id) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //remove every episode of the season from the list
        query = "delete from t_episodes_list where username <=> ? and season_id <=> ?;";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        st.setObject(1, username);
        st.setObject(2, season_id);

        //execute
        boolean deleted = st.executeUpdate() >= 0;

        //clean
        st.close();

        //update the watched episodes
        if(episode_ids.isEmpty())
        {
            return deleted;
        }

        //build query
        String values = "(?,?,?,?)" + ",(?,?,?,?)".repeat(episode_ids.size() - 1) + ";";
        query = "insert into t_episodes_list(episode_id, season_id, username, watch_date) " +
                "values " + values;

        //prepare
        st = con.prepareStatement(query);

        LocalDate watch_date = LocalDate.now(ZoneId.of("Europe/Paris"));
        //set parameters
        int i = 1;
        for(Integer episode_id : episode_ids)
        {
            st.setObject(i++, episode_id);
            st.setObject(i++, season_id);
            st.setObject(i++, username);
            st.setObject(i++, watch_date);
        }
        //execute
        boolean updated = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return updated;
    }

    public static boolean setCompleted(String username, Integer season_id) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "insert into t_episodes_list(episode_id, season_id, username, watch_date) " +
        "(select episode_id, ?, ?, ? from t_episode where season_id <=> ?) " +
        "on duplicate key update t_episodes_list.episode_id = t_episodes_list.episode_id;";

        //prepare
        st = con.prepareStatement(query);

        LocalDate watch_date = LocalDate.now(ZoneId.of("Europe/Paris"));
        //set parameters
        st.setObject(1, season_id);
        st.setObject(2, username);
        st.setObject(3, watch_date);
        st.setObject(4, season_id);

        //execute
        boolean updated = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return updated;
    }

    public static boolean addFriend(String username, String friend_username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "insert into t_relationship(username1, username2, relation_status) " +
                "values(?, ?, ?);";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        st.setObject(1, username);
        st.setObject(2, friend_username);
        st.setObject(3, "pending");

        //execute
        boolean updated = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return updated;
    }

    public static boolean acceptFriend(String username, String friend_username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "update t_relationship set relation_status = 'friends' " +
                "where username1 <=> ? and username2 <=> ?;";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        st.setObject(1, friend_username);
        st.setObject(2, username);

        //execute
        boolean updated = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return updated;
    }

    public static boolean removeFriend(String username, String friend_username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //build query
        query = "delete from t_relationship where (username1 <=> ? and username2 <=> ?) or (username2 <=> ? and username1 <=> ?);";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        st.setObject(1, username);
        st.setObject(2, friend_username);
        st.setObject(3, username);
        st.setObject(4, friend_username);

        //execute
        boolean deleted = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return deleted;
    }

    public static List<FriendsInfoContainer> getFriendsList(String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        List<FriendsInfoContainer> friends_list = new ArrayList<>();

        query = "select username1 as friend_username, display_name, picture_path " +
                "from t_relationship join t_user on username1 = username " +
                "where relation_status <=> 'friends' and username2 <=> ? " +
                "union " +
                "select username2 as friend_username, display_name, picture_path " +
                "from t_relationship join t_user on username2 = username " +
                "where relation_status <=> 'friends' and username1 <=> ?;";

        st = con.prepareStatement(query);

        st.setObject(1, username);
        st.setObject(2, username);

        rs = st.executeQuery();

        while(rs.next())
        {
            FriendsInfoContainer friend = new FriendsInfoContainer();
            friend.setFriend_username(rs.getObject("friend_username", String.class));
            friend.setFriend_display_name(rs.getObject("display_name", String.class));
            friend.setFriend_picture(rs.getObject("picture_path", String.class));
            friends_list.add(friend);
        }

        st.close();
        rs.close();
        con.close();

        return friends_list;
    }

    public static List<FriendsInfoContainer> getPendingList(String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        List<FriendsInfoContainer> friends_list = new ArrayList<>();

        query = "select username2 as friend_username, display_name, picture_path " +
                "from t_relationship join t_user on username2 = username " +
                "where relation_status <=> 'pending' and username1 <=> ?;";

        st = con.prepareStatement(query);

        st.setObject(1, username);

        rs = st.executeQuery();

        while(rs.next())
        {
            FriendsInfoContainer friend = new FriendsInfoContainer();
            friend.setFriend_username(rs.getObject("friend_username", String.class));
            friend.setFriend_picture(rs.getObject("picture_path", String.class));
            friend.setFriend_display_name(rs.getObject("display_name", String.class));
            friends_list.add(friend);
        }

        st.close();
        rs.close();
        con.close();

        return friends_list;
    }

    public static List<FriendsInfoContainer> getRequestsList(String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;
        List<FriendsInfoContainer> friends_list = new ArrayList<>();

        query = "select username1 as friend_username, display_name, picture_path " +
                "from t_relationship join t_user on username1 = username " +
                "where relation_status <=> 'pending' and username2 <=> ?;";

        st = con.prepareStatement(query);

        st.setObject(1, username);

        rs = st.executeQuery();

        while(rs.next())
        {
            FriendsInfoContainer friend = new FriendsInfoContainer();
            friend.setFriend_username(rs.getObject("friend_username", String.class));
            friend.setFriend_display_name(rs.getObject("display_name", String.class));
            friend.setFriend_picture(rs.getObject("picture_path", String.class));
            friends_list.add(friend);
        }

        st.close();
        rs.close();
        con.close();

        return friends_list;
    }

    public static boolean alreadyFriends(String username, String friend_username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;

        //build query
        query = "select 1 from t_relationship where (username1 <=> ? and username2 <=> ?) or (username2 <=> ? and username1 <=> ?);";

        //prepare
        st = con.prepareStatement(query);

        st.setObject(1, username);
        st.setObject(2, friend_username);
        st.setObject(3, username);
        st.setObject(4, friend_username);

        rs = st.executeQuery();

        boolean friends = rs.next();

        st.close();
        rs.close();
        con.close();

        return friends;
    }

    public static boolean addToBingeWatching(String username, List<String> friends, Integer season_id) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;

        //update the watched episodes
        if(friends.isEmpty())
        {
            return true;
        }

        //build query
        String values = "(?,?,?)" + ",(?,?,?)".repeat(friends.size() - 1);
        query = "insert into t_binge_watching(username1, username2, season_id) " +
                "values " + values +
        " on duplicate key update t_binge_watching.season_id = t_binge_watching.season_id;";

        //prepare
        st = con.prepareStatement(query);

        //set parameters
        int i = 1;
        for(String friend : friends)
        {
            st.setObject(i++, username);
            st.setObject(i++, friend);
            st.setObject(i++, season_id);
        }
        //execute
        boolean added = st.executeUpdate() > 0;

        //clean
        st.close();
        con.close();

        return added;
    }

    public static List<BingeWatchingContainer> getBingeWatchingList(String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;

        query = "select username1 as friend_username, display_name, picture_path, " +
                "season_id, season_title, season_poster " +
                "from t_binge_watching " +
                "join t_user on (username1 = username) " +
                "join t_season using(season_id) " +
                "where username2 <=> ?;";

        st = con.prepareStatement(query);

        st.setObject(1, username);

        rs = st.executeQuery();

        List<BingeWatchingContainer> binge_watching_list = new ArrayList<>();
        while(rs.next())
        {
            BingeWatchingContainer bw = new BingeWatchingContainer();
            bw.setFriend_username(rs.getObject("friend_username", String.class));
            bw.setFriend_display_name(rs.getObject("display_name", String.class));
            bw.setFriend_picture(rs.getObject("picture_path", String.class));

            Triplet<Integer, String, String> season_info = new Triplet<>(rs.getObject("season_id", Integer.class),
                                                                         rs.getObject("season_title", String.class),
                                                                         rs.getObject("season_poster", String.class));
            bw.setSeason_info(season_info);

            binge_watching_list.add(bw);
        }

        st.close();
        rs.close();
        con.close();

        return binge_watching_list;
    }

    public static Integer mostEpisodesInADay(String username) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;

        query = "select max(x.episode_count) as max_episodes " +
                "from " +
                "(" +
                "select count(episode_id) as episode_count " +
                "from t_episodes_list " +
                "where username <=> ? " +
                "group by watch_date " +
                ") x;";

        st = con.prepareStatement(query);

        st.setObject(1, username);

        rs = st.executeQuery();

        int most_episodes = 0;
        if(rs.next())
        {
            most_episodes = rs.getObject("max_episodes", Integer.class);
        }

        st.close();
        rs.close();
        con.close();

        return most_episodes;
    }

    public static List<Pair<String, Integer>> timeSpentPerMonth(String username, Integer season_id) throws Exception
    {
        Connection con = C3P0DataSource.getInstance().getConnection(); //establish connection
        String query;
        PreparedStatement st;
        ResultSet rs;

        query = "select monthname(watch_date) as month_name, sum(duration) as time_spent " +
                "from t_episodes_list " +
                "join t_episode using(episode_id) " +
                "where username <=> ? and t_episodes_list.season_id <=> ? " +
                "group by month(watch_date) " +
                "order by 1;";

        st = con.prepareStatement(query);

        st.setObject(1, username);
        st.setObject(2, season_id);

        rs = st.executeQuery();

        List<Pair<String, Integer>> time_spent = new ArrayList<>();
        while(rs.next())
        {
            time_spent.add(new Pair<String, Integer>(rs.getObject("month_name", String.class),
                    rs.getObject("time_spent", Integer.class)));
        }

        st.close();
        rs.close();
        con.close();

        return time_spent;
    }
}
