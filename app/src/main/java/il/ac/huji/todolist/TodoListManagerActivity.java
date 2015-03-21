package il.ac.huji.todolist;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class TodoListManagerActivity extends ActionBarActivity {
    public static final String ITEM_NAME = "0";
    public static final String DAY = "1";
    public static final String MONTH = "2";
    public static final String YEAR = "3";
    public static final int DELETE_OPTION = 0;
    public static final int CALL_OPTION = 1;
    public static final int ADD_REQ_CODE = 0;
    public static final String CALL_STRING_REGEX = "\\s*\\d[\\d-]*\\s*";

    ArrayList<TodoItem> m_ItemsArrayList;
    ItemsAdapter m_TodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        prepareListAdapter();
    }

    private void prepareListAdapter(){
        ListView lstTodoItems;

        lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);
        m_ItemsArrayList = new ArrayList<>();
        m_TodoAdapter = new ItemsAdapter(this,
                                         R.layout.list_raw,
                                         m_ItemsArrayList);

        lstTodoItems.setAdapter(m_TodoAdapter);
        registerForContextMenu(lstTodoItems);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_todo_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuItemAdd) {
            Intent intent = new Intent(this, AddNewTodoItemActivity.class);
            startActivityForResult(intent, ADD_REQ_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(
            int reqCode, int resCode, Intent data) {
        if (data == null){
            return;
        }
        switch (reqCode) {
            case ADD_REQ_CODE:
                String itemName = data.getStringExtra(ITEM_NAME);
                if (itemName == null){
                    return;
                }
                Calendar date = new GregorianCalendar();

                date.set(data.getIntExtra(YEAR, 0),
                         data.getIntExtra(MONTH, 0),
                         data.getIntExtra(DAY, 0));

                m_TodoAdapter.add(new TodoItem(itemName, date));
        }
    }

    /**
     * @param str the string to check
     * @return true iff the string is like the following formats: "call 123456789"
     * or "caLL 123456-789" or "Call 123-456-789" etc.
     */
    private boolean isCallFormat(String str){
        String callString;

        str = str.trim().toLowerCase();
        callString = getString(R.string.call).trim().toLowerCase();
        return (str.matches(callString + CALL_STRING_REGEX));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        TodoItem item;
        AdapterView.AdapterContextMenuInfo info;
        String itemName;
        TextView menuTitle;

        //Set the header title based on context
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        item = m_TodoAdapter.getItem(info.position);
        itemName = item.getItemName();

        //Set the title of the menu
        menuTitle = new TextView(this);
        menuTitle.setText(itemName);
        menuTitle.setGravity(Gravity.CENTER);
        menuTitle.setTextAppearance(this, android.R.style.TextAppearance_Large);
        menu.setHeaderView(menuTitle);

        //Prepare the menu and hide/unhide "call" option
        menu.add(Menu.NONE, DELETE_OPTION , Menu.NONE, R.string.delete_item);
        if (isCallFormat(itemName)) {
            menu.add(Menu.NONE, CALL_OPTION , Menu.NONE, itemName.trim());
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case DELETE_OPTION:
                m_ItemsArrayList.remove(info.position);
                m_TodoAdapter.notifyDataSetChanged();
                break;
            case CALL_OPTION: //If we reached here then the item have the correct "call" format
                String callNumber = m_ItemsArrayList.get(info.position).getItemName().trim();
                callNumber = callNumber.substring(callNumber.indexOf(' ')).trim();
                Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callNumber));
                startActivity(dial);
                break;
        }
        return true;
    }
}