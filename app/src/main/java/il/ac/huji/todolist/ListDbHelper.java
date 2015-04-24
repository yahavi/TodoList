package il.ac.huji.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListDbHelper extends SQLiteOpenHelper{

    List<TodoItem> m_ItemsList;
    SimpleDateFormat m_DateFormatter;

    public ListDbHelper(Context context, List<TodoItem> itemsList) {

        super(context, "TodoListDb", null, 1);

        this.m_ItemsList = itemsList;
        m_DateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table todo_db ( " +
                   " itemId integer primary key autoincrement, " +
                   " itemName string, " +
                   " date string);"
        );

        if (null != m_ItemsList){
            for (TodoItem iItem : m_ItemsList){
                ContentValues contentValues = new ContentValues();
                contentValues.put("itemName", iItem.getItemName());
                contentValues.put("date", m_DateFormatter.format(iItem.getDate()));
                db.insert("todo_db", null, contentValues);
            }
        }

    }

    public void saveState(List<TodoItem> itemsList){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS todo_db");
        db.execSQL("create table todo_db ( " +
                        " itemId integer primary key autoincrement, " +
                        " itemName string, " +
                        " date string);"
        );
        if (null != itemsList){
            for (TodoItem iItem : itemsList){
                ContentValues contentValues = new ContentValues();
                contentValues.put("itemName", iItem.getItemName());
                contentValues.put("date", m_DateFormatter.format(iItem.getDate()));
                db.insert("todo_db", null, contentValues);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS todo_db");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // super.onDowngrade(db, oldVersion, oldVersion - 1);
        db.execSQL("DROP TABLE IF EXISTS todo_db");
        onCreate(db);
    }

    public boolean insertItem  (String itemName, Date itemDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("itemName", itemName);
        contentValues.put("date", m_DateFormatter.format(itemDate));
        db.insert("todo_db", null, contentValues);
      //  db.close();
        return true;
    }

 /*   public Integer deleteItem (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }*/

    public ArrayList getAllItems()
    {
        TodoItem item;
        String itemName;
        Date date = null;
        ArrayList<TodoItem> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        if (null == db || !db.isOpen()){
            return array_list;
        }
   //     Cursor res = getReadableDatabase().rawQuery("select * from todo_db", null);
        Cursor res = getReadableDatabase().rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='todo_db';", null);
        res.moveToFirst();
        while(!res.isAfterLast() && res.getColumnIndex("itemName") != -1){

            itemName = res.getString(res.getColumnIndex("itemName"));
            try {
                date = m_DateFormatter.parse(res.getString(res.getColumnIndex("date")));
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

