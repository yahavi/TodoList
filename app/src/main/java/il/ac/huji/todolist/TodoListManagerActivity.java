package il.ac.huji.todolist;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
public class TodoListManagerActivity extends ActionBarActivity {
    EditText m_EdtNewItem;
    ArrayAdapter<String> m_TodoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        Resources res = getResources();
        ListView lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);
        m_EdtNewItem = (EditText) findViewById(R.id.edtNewItem);
        String[] todoItemsStrArr = res.getStringArray(R.array.lstTodoItems);
        ArrayList<String> itemsArrayList = new ArrayList<>(Arrays.asList(todoItemsStrArr));
        m_TodoAdapter = new ItemsAdapter(
                this, android.R.layout.simple_list_item_1, itemsArrayList);
        lstTodoItems.setAdapter(m_TodoAdapter);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        if (id == R.id.menuItemAdd) {
            String s = m_EdtNewItem.getText().toString();
            if (s.length() > 0){
                m_TodoAdapter.add(s);
                m_EdtNewItem.setText("");
                return true;
            } else {
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}