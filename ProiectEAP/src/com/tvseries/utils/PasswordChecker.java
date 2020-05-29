package com.tvseries.utils;

public class PasswordChecker
{
    private static boolean isLetter(char ch)
    {
        ch = Character.toLowerCase(ch);

        return (ch >= 'a' && ch <= 'z');
    }

    private static boolean isDigit(char ch)
    {
        return (ch >= '0' && ch <= '9');
    }

    private static boolean isSpecial(char ch)
    {
        String special = "~!@#$%^*()-_+=[]{};:/?|.";

        return (special.indexOf(ch) >= 0); //indexOf(ch) returns -1 if the ch is not present in the string
    }

    public static boolean onlyDigits(String password)
    {
        int i;
        for(i = 0; i < password.length(); i++)
        {
            if(!isDigit(password.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean onlyLetters(String password)
    {
        int i;
        for(i = 0; i < password.length(); i++)
        {
            if(!isLetter(password.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean onlyDigitsAndLetters(String password)
    {
        int i;
        for(i = 0; i < password.length(); i++)
        {
            if(!isDigit(password.charAt(i)) && !isLetter(password.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean check(String password)
    {
        int i;
        for(i = 0; i < password.length(); i++)
        {
            if(!isDigit(password.charAt(i)) && !isLetter(password.charAt(i)) && !isSpecial(password.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }
}
