package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;


public class ItemsAdapter extends ArrayAdapter<TodoItem> {
    final Context m_Context;
    final int m_Resource;
    List<TodoItem> m_Objects;


    public ItemsAdapter(Context context, int resource, List<TodoItem> objects){
        super(context, resource, objects);
        m_Context = context;
        m_Resource = resource;
        m_Objects = objects;

    }

    private String getDateFormat(Calendar date){
        String res = "";

        res += date.get(Calendar.DAY_OF_MONTH) + "/"
            +  (date.get(Calendar.MONTH) + 1) + "/"
            +  date.get(Calendar.YEAR);
        return res;
    }

    private static boolean isToday(Calendar date){
        Calendar currentDate = Calendar.getInstance();
        return (date.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH) &&
                date.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                date.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR));
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
            tvItemDate.setText(getDateFormat(item.getDate()));

            Calendar currentDate = Calendar.getInstance();
            if (item.getDate().after(currentDate) || isToday(item.getDate())){
                tvItemDate.setTextColor(Color.BLACK);
                tvItemName.setTextColor(Color.BLACK);
            } else {
                tvItemDate.setTextColor(Color.RED);
                tvItemName.setTextColor(Color.RED);
            }
        }
        return rawView;
    } //getView()

} //ItemsAdapter
