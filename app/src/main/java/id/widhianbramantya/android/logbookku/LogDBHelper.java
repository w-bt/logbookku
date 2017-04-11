package id.widhianbramantya.android.logbookku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogDBHelper extends SQLiteOpenHelper {
    public LogDBHelper(Context context) {
        super(context, LogContract.DB_NAME, null, LogContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + LogContract.LogEntry.TABLE + " ( " +
                LogContract.LogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LogContract.LogEntry.COL_LOG_TITLE + " TEXT NOT NULL, " +
                LogContract.LogEntry.COL_LOG_ROOM + " TEXT NOT NULL, " +
                LogContract.LogEntry.COL_LOG_DATE + " TEXT NOT NULL, " +
                LogContract.LogEntry.COL_LOG_TYPE + " TEXT NOT NULL, " +
                LogContract.LogEntry.COL_LOG_ACTIVITY + " TEXT NOT NULL, " +
                LogContract.LogEntry.COL_LOG_NEXT_ACTIVITY + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LogContract.LogEntry.TABLE);
        onCreate(db);
    }
}
