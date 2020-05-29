package com.tvseries.utils;

public class RandomString
{
    public static int randomInt(int min, int max)
    {
        return (int)(Math.random() * (max - min) + min);
    }

    public static String generateString(int length)
    {
        String result = "";
        int i;

        for(i = 0; i < length; i++)
        {
            result += Character.toString((char)randomInt(33, 255));
        }

        return result;
    }
}
