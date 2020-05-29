package com.tvseries.containers;

import com.tvseries.utils.Triplet;
import java.time.LocalDate;
import java.util.List;

public class UserInfoContainer
{
    private String username;
    private String display_name;
    private String first_name;
    private String last_name;
    private LocalDate birth_date;
    private String picture_path;

    private List<FriendsInfoContainer> friends; //list of (username, display_name, picture_path) for friends

    public UserInfoContainer()
    {

    }

    public UserInfoContainer(String username, String display_name, String first_name, String last_name, LocalDate birth_date, String picture_path, List<FriendsInfoContainer> friends)
    {
        this.username = username;
        this.display_name = display_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.picture_path = picture_path;
        this.friends = friends;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public LocalDate getBirth_date()
    {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date)
    {
        this.birth_date = birth_date;
    }

    public String getPicture_path()
    {
        return picture_path;
    }

    public void setPicture_path(String picture_path)
    {
        this.picture_path = picture_path;
    }

    public List<FriendsInfoContainer> getFriends()
    {
        return friends;
    }

    public void setFriends(List<FriendsInfoContainer> friends)
    {
        this.friends = friends;
    }
}
