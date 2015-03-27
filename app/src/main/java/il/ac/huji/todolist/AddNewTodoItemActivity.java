package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Date;


public class AddNewTodoItemActivity extends Activity {
    public static final String TITLE_EXTRA = "title";
    public static final String DUE_DATE_EXTRA = "dueDate";
    Button m_YesButton, m_NoButton;
    EditText m_EdtNewItem;
    DatePicker m_DatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_new_todo_item);

        m_YesButton = (Button)     findViewById(R.id.btnOK);
        m_NoButton  = (Button)     findViewById(R.id.btnCancel);
        m_EdtNewItem = (EditText)   findViewById(R.id.edtNewItem);
        m_DatePicker = (DatePicker) findViewById(R.id.datePicker);

        m_YesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemTxt = m_EdtNewItem.getText().toString();
                Intent result = new Intent();
                if (itemTxt.length() == 0){
                    setResult(RESULT_CANCELED, result);
                    finish();
                }

                final Date date = new Date(m_DatePicker.getCalendarView().getDate());

                result.putExtra(TITLE_EXTRA, itemTxt);
                result.putExtra(DUE_DATE_EXTRA, date);

                setResult(RESULT_OK, result);
                finish();
            }
        });

        m_NoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
