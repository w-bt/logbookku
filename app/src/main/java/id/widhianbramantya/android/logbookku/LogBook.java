package id.widhianbramantya.android.logbookku;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogBook extends AppCompatActivity {
    private static final String TAG = "LogBook";
    private RecyclerView recyclerView;
    private List<MyDataFront> data_front;
    private LogDBHelper mHelper;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_title, txt_room, txt_date, txt_activity, txt_next_activity;
    Spinner spinner_typex;
    ImageButton img;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapterFront adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHelper = new LogDBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_front = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm();
            }
        });

        updateUI(1);

        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CustomAdapterFront(this,data_front);
        recyclerView.setAdapter(adapter);
    }

    private void updateUI(final int no) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integer) {
                try {
                    data_front.clear();
                    SQLiteDatabase db = mHelper.getReadableDatabase();
                    Cursor cursor = db.query(LogContract.LogEntry.TABLE,
                            new String[]{LogContract.LogEntry._ID, LogContract.LogEntry.COL_LOG_TITLE, LogContract.LogEntry.COL_LOG_DATE, LogContract.LogEntry.COL_LOG_TYPE, LogContract.LogEntry.COL_LOG_ROOM, LogContract.LogEntry.COL_LOG_ACTIVITY, LogContract.LogEntry.COL_LOG_NEXT_ACTIVITY},
                            null, null, null, null, "_id DESC", null);
                    while (cursor.moveToNext()) {
                        String idx = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry._ID));
                        String titlex = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry.COL_LOG_TITLE));
                        String datex = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry.COL_LOG_DATE));
                        String typex = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry.COL_LOG_TYPE));
                        String roomx = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry.COL_LOG_ROOM));
                        String activityx = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry.COL_LOG_ACTIVITY));
                        String nactivityx = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry.COL_LOG_NEXT_ACTIVITY));
                        data_front.add(new MyDataFront(idx, titlex, datex, typex, roomx, activityx, nactivityx));
                    }

                    cursor.close();
                    db.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                adapter.notifyDataSetChanged();;
            }
        };

        task.execute(no);
    }

    public void kosong(){
        txt_title = (EditText) dialogView.findViewById(R.id.txt_judul);
        txt_room = (EditText) dialogView.findViewById(R.id.txt_ruang);
        txt_date = (EditText) dialogView.findViewById(R.id.txt_tanggal);
        txt_activity = (EditText) dialogView.findViewById(R.id.txt_kegiatan);
        txt_next_activity = (EditText) dialogView.findViewById(R.id.txt_next_kegiatan);

        txt_title.setText("");
        txt_room.setText("");
        txt_date.setText("");
        txt_activity.setText("");
        txt_next_activity.setText("");
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(LogBook.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_log_book, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(android.R.drawable.ic_menu_add);
        dialog.setTitle("Tambah Log Book");

        kosong();

        txt_title = (EditText) dialogView.findViewById(R.id.txt_judul);
        txt_room = (EditText) dialogView.findViewById(R.id.txt_ruang);
        txt_date = (EditText) dialogView.findViewById(R.id.txt_tanggal);
        spinner_typex = (Spinner) dialogView.findViewById(R.id.spinner_type);
        txt_activity = (EditText) dialogView.findViewById(R.id.txt_kegiatan);
        txt_next_activity = (EditText) dialogView.findViewById(R.id.txt_next_kegiatan);

        dialog.setPositiveButton("TAMBAH", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = String.valueOf(txt_title.getText());
                String room = String.valueOf(txt_room.getText());
                String date = String.valueOf(txt_date.getText());
                String type = String.valueOf(spinner_typex.getSelectedItem().toString());
                String activity = String.valueOf(txt_activity.getText());
                String next_activity = String.valueOf(txt_next_activity.getText());
                SQLiteDatabase db = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(LogContract.LogEntry.COL_LOG_TITLE, title);
                values.put(LogContract.LogEntry.COL_LOG_ROOM, room);
                values.put(LogContract.LogEntry.COL_LOG_DATE, date);
                values.put(LogContract.LogEntry.COL_LOG_TYPE, type);
                values.put(LogContract.LogEntry.COL_LOG_ACTIVITY, activity);
                values.put(LogContract.LogEntry.COL_LOG_NEXT_ACTIVITY, next_activity);
                db.insertWithOnConflict(LogContract.LogEntry.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                db.close();
                updateUI(1);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void updateLog (View view) {
        View u = (View) view.getParent();
        View v = (View) u.getParent();
        TextView v_log_id = (TextView) u.findViewById(R.id.log_id);
        TextView v_log_title = (TextView) v.findViewById(R.id.log_title);
        TextView v_log_date = (TextView) v.findViewById(R.id.log_date);
        TextView v_log_activity = (TextView) v.findViewById(R.id.log_activity);
        TextView v_log_next_activity = (TextView) v.findViewById(R.id.log_next_activity);
        String[] v_detil = v_log_date.getText().toString().split(" > ");
        final int id = Integer.parseInt(v_log_id.getText().toString());

        dialog = new AlertDialog.Builder(LogBook.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_log_book, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(android.R.drawable.ic_menu_edit);
        dialog.setTitle("Ubah Log Book");

        txt_title = (EditText) dialogView.findViewById(R.id.txt_judul);
        txt_room = (EditText) dialogView.findViewById(R.id.txt_ruang);
        txt_date = (EditText) dialogView.findViewById(R.id.txt_tanggal);
        spinner_typex = (Spinner) dialogView.findViewById(R.id.spinner_type);
        txt_activity = (EditText) dialogView.findViewById(R.id.txt_kegiatan);
        txt_next_activity = (EditText) dialogView.findViewById(R.id.txt_next_kegiatan);

        txt_title.setText(v_log_title.getText().toString());
        txt_room.setText(v_detil[2]);
        txt_date.setText(v_detil[0]);
        txt_activity.setText(v_log_activity.getText().toString());
        txt_next_activity.setText(v_log_next_activity.getText().toString());

        dialog.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = String.valueOf(txt_title.getText());
                String room = String.valueOf(txt_room.getText());
                String date = String.valueOf(txt_date.getText());
                String type = String.valueOf(spinner_typex.getSelectedItem().toString());
                String activity = String.valueOf(txt_activity.getText());
                String next_activity = String.valueOf(txt_next_activity.getText());
                SQLiteDatabase db = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(LogContract.LogEntry._ID, id);
                values.put(LogContract.LogEntry.COL_LOG_TITLE, title);
                values.put(LogContract.LogEntry.COL_LOG_ROOM, room);
                values.put(LogContract.LogEntry.COL_LOG_DATE, date);
                values.put(LogContract.LogEntry.COL_LOG_TYPE, type);
                values.put(LogContract.LogEntry.COL_LOG_ACTIVITY, activity);
                values.put(LogContract.LogEntry.COL_LOG_NEXT_ACTIVITY, next_activity);
                db.replace(LogContract.LogEntry.TABLE, null, values);
                db.close();
                updateUI(1);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void deleteLog(View view) {
        View parent = (View) view.getParent();
        TextView logTextView = (TextView) parent.findViewById(R.id.log_id);
        String logx = String.valueOf(logTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(LogContract.LogEntry.TABLE,
                LogContract.LogEntry._ID + " = ?",
                new String[]{logx});
        db.close();
        updateUI(1);
    }
}
