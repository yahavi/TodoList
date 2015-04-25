package il.ac.huji.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListDbHelper extends SQLiteOpenHelper{

    private static final int DB_INITIAL_VERSION = 1;
    private static final String DB_NAME = "todo_db";
    private static final String DB_ITEM_ID = "itemId";
    private static final String DB_ITEM_NAME = "itemName";
    private static final String DB_ITEM_DATE = "date";
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    private List<TodoItem> m_ItemsList;
    private final SimpleDateFormat m_DateFormatter;

    public ListDbHelper(Context context, List<TodoItem> itemsList) {

        super(context, "TodoListDb", null, DB_INITIAL_VERSION);

        m_ItemsList = itemsList;
        m_DateFormatter = new SimpleDateFormat(DATE_FORMAT,
                                               Locale.getDefault());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
        fillTableFromList(db, m_ItemsList);
    }

    public void saveState(List<TodoItem> itemsList){
        SQLiteDatabase db = getWritableDatabase();

        dropTable(db);
        createTable(db);
        fillTableFromList(db, itemsList);
    }

    private void createTable(SQLiteDatabase db){
        db.execSQL("create table " + DB_NAME + "( " +
                DB_ITEM_ID + " integer primary key autoincrement, " +
                DB_ITEM_NAME + " string, " +
                DB_ITEM_DATE + " string);"
        );
    }

    private void dropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
    }

    private void fillTableFromList(SQLiteDatabase db, List<TodoItem> itemsList){
        if (null != itemsList){
            for (TodoItem iItem : itemsList){
                ContentValues contentValues = new ContentValues();
                contentValues.put(DB_ITEM_NAME, iItem.getItemName());
                contentValues.put(DB_ITEM_DATE, m_DateFormatter.format(iItem.getDate()));
                db.insert(DB_NAME, null, contentValues);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
        onCreate(db);
    }

    public ArrayList<TodoItem> getAllItems()
    {
        TodoItem item;
        String itemName;
        Date date = null;
        ArrayList<TodoItem> array_list = new ArrayList<>();

        Cursor res = getReadableDatabase().rawQuery("select * from todo_db", null);
        res.moveToFirst();
        while(!res.isAfterLast()){

            itemName = res.getString(res.getColumnIndex(DB_ITEM_NAME));
            try {
                date = m_DateFormatter.parse(res.getString(res.getColumnIndex(DB_ITEM_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            item = new TodoItem(itemName, date);
            array_list.add(item);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
}

