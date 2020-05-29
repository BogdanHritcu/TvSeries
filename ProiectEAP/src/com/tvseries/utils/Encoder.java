package com.tvseries.utils;

public class Encoder
{
    public static String encodeUsername(String username)
    {
        int length = username.length();
        String encoded_username = "";

        for(int i = 0; i < length; i++)
        {
            encoded_username += Character.toString(username.charAt(i) ^ 4);
        }

        return encoded_username;
    }

    public static String decodeUsername(String encoded_username)
    {
        int length = encoded_username.length();
        String decoded_username = "";

        for(int i = 0; i < length; i++)
        {
            decoded_username += Character.toString(encoded_username.charAt(i) ^ 4);
        }

        return decoded_username;
    }
}
