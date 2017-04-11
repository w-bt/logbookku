package id.widhianbramantya.android.logbookku;

import android.provider.BaseColumns;

public class LogContract {
    public static final String DB_NAME = "id.widhianbramantya.android.logbookku";
    public static final int DB_VERSION = 1;

    public class LogEntry implements BaseColumns {
        public static final String TABLE = "log_book";

        public static final String COL_LOG_TITLE = "title";
        public static final String COL_LOG_ROOM = "room";
        public static final String COL_LOG_DATE = "date";
        public static final String COL_LOG_TYPE = "type";
        public static final String COL_LOG_ACTIVITY = "activity";
        public static final String COL_LOG_NEXT_ACTIVITY = "next_activity";
    }
}
