package net.qsoft.missingchecknew;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    Cursor cursor = null;

    private DatabaseAccess(Context context)
    {
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context)
    {
        if(instance==null)
        {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open()
    {
        this.database = openHelper.getWritableDatabase();
    }
    public void close()
    {
        if(database!=null)
        {
            this.database.close();
        }
    }

    public String getDataFromExternalDatabase()
    {
        cursor = database.rawQuery("select * from Respondents",null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext())
        {
            String date = cursor.getString(5);

            buffer.append(date+"  "+date.length()+"\n");
        }

        return buffer.toString();
    }

}
