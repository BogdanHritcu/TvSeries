package com.tvseries.containers;

public class FriendsInfoContainer
{
    String friend_username;
    String friend_display_name;
    String friend_picture;

    public FriendsInfoContainer()
    {

    }

    public FriendsInfoContainer(String friend_username, String friend_display_name, String friend_picture)
    {
        this.friend_username = friend_username;
        this.friend_display_name = friend_display_name;
        this.friend_picture = friend_picture;
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
}
