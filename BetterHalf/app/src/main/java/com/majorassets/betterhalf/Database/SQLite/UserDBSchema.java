package com.majorassets.betterhalf.Database.SQLite;

/**
 * Created by dgbla on 3/30/2016.
 */
public class UserDBSchema
{
    public static final class UserDBTable
    {
        public static final String NAME = "User";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String LOGGED_ON_LAST = "logged_on_last";
        }
    }
}
