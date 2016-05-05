package com.majorassets.betterhalf.Database.SQLite.CursorWrappers;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.UserDBSchema.UserDBTable;
import com.majorassets.betterhalf.Model.User;

import java.util.UUID;

/**
 * Created by dgbla on 3/30/2016.
 */
public class UserCursorWrapper extends CursorWrapper
{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public User getUser()
    {
        String userID = getString(getColumnIndex(UserDBTable.Cols.UUID));
        String soID = getString(getColumnIndex(UserDBTable.Cols.SOID));
        String email = getString(getColumnIndex(UserDBTable.Cols.EMAIL));
        String password = getString(getColumnIndex(UserDBTable.Cols.PASSWORD));
        String loggedOnLastStr = getString(getColumnIndex(UserDBTable.Cols.LOGGED_ON_LAST));

        boolean loggedOnLast = Integer.parseInt(loggedOnLastStr) == 1;

        User user = new User(UUID.fromString(userID));
        user.setEmail(email);
        user.setPassword(password);
        user.setLoggedOnLast(loggedOnLast);

        //try to set the user's significant other user object
        User significantOther;
        if(soID != null)
        {
            significantOther = new User(UUID.fromString(soID));
            user.setSignificantOther(significantOther);
        }



        return user;
    }
}
