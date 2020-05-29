package com.tvseries.containers;

import com.tvseries.utils.Triplet;

public class BingeWatchingContainer
{
    String friend_username;
    String friend_display_name;
    String friend_picture;

    Triplet<Integer, String, String> season_info;

    public BingeWatchingContainer()
    {

    }

    public String getFriend_username()
    {
        return friend_username;
    }

    public void setFriend_username(String friend_username)
    {
        this.friend_username = friend_username;
    }

    public String getFriend_display_name()
    {
        return friend_display_name;
    }

    public void setFriend_display_name(String friend_display_name)
    {
        this.friend_display_name = friend_display_name;
    }

    public String getFriend_picture()
    {
        return friend_picture;
    }

    public void setFriend_picture(String friend_picture)
    {
        this.friend_picture = friend_picture;
    }

    public Triplet<Integer, String, String> getSeason_info()
    {
        return season_info;
    }

    public void setSeason_info(Triplet<Integer, String, String> season_info)
    {
        this.season_info = season_info;
    }
}
