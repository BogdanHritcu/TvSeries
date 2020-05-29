package com.tvseries.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;

public class C3P0DataSource
{
    private static C3P0DataSource dataSource;
    private ComboPooledDataSource comboPooledDataSource;
    private static String jdbcUrl = "jdbc:mysql://localhost:3306/tvseries_db";
    private static String user = "admin1";
    private static String password = "admin1#password";

    private C3P0DataSource() throws Exception
    {
        this.comboPooledDataSource = new ComboPooledDataSource();
        this.comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        this.comboPooledDataSource.setJdbcUrl(jdbcUrl);
        this.comboPooledDataSource.setUser(user);
        this.comboPooledDataSource.setPassword(password);
    }

    static public C3P0DataSource getInstance() throws Exception
    {
        if (dataSource == null)
        {
            dataSource = new C3P0DataSource();
        }

        return dataSource;
    }

    public Connection getConnection() throws Exception
    {
        return comboPooledDataSource.getConnection();
    }

    static public void setJdbcUrl(String Url)
    {
        jdbcUrl = Url;
    }

    static public void setUser(String User)
    {
        user = User;
    }

    static public void setPassword(String Password)
    {
        password = Password;
    }
}
