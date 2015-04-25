package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ItemsAdapter extends ArrayAdapter<TodoItem> {

    public ItemsAdapter(Context context, int resource, List<TodoItem> objects){
        super(context, resource, objects);
    }

    private String getDateFormat(Date date){
        String res = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        res += cal.get(Calendar.DAY_OF_MONTH) + "/"
            +  (cal.get(Calendar.MONTH) + 1) + "/"
            +  cal.get(Calendar.YEAR);
        return res;
    }

    private static boolean isToday(Date date){
        Calendar currentDate = Calendar.getInstance();
        Calendar chkDate = Calendar.getInstance();
        chkDate.setTime(date);
        return (chkDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH) &&
                chkDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                chkDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR));
    }

    /**
     * Used in adding to the adapter
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rawView;
        final TodoItem item;

        if (convertView == null) {
            LayoutInflater vi;

            vi = LayoutInflater.from(getContext());
            rawView = vi.inflate(R.layout.list_raw, parent, false);
        } else {
            rawView = convertView;
        }
        item = getItem(position);

        if (item != null){
            TextView tvItemName;
            TextView tvItemDate;

            tvItemName = (TextView) rawView.findViewById(R.id.txtTodoTitle);
            tvItemDate = (TextView) rawView.findViewById(R.id.txtTodoDueDate);
            tvItemName.setText(item.getItemName());
            if (item.getDate() != null) {
                tvItemDate.setText(getDateFormat(item.getDate()));

                Calendar currentDate = Calendar.getInstance();
                if (item.getDate().after(currentDate.getTime()) || isToday(item.getDate())) {
                    tvItemDate.setTextColor(Color.BLACK);
                    tvItemName.setTextColor(Color.BLACK);
                } else {
                    tvItemDate.setTextColor(Color.RED);
                    tvItemName.setTextColor(Color.RED);
                }
            } // Else, use the default value, "No due date"
        }
        return rawView;
    } //getView()

} //ItemsAdapter
