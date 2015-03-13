package il.ac.huji.todolist;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.ClipData.Item;
import java.util.List;

/**
 * Created by Win7 on 13/03/2015.
 */
public class ItemsAdapter extends ArrayAdapter<String> {
    public ItemsAdapter(Context context, int resource, List<String> objects){
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_raw, null);


        }
        String item = getItem(position);

        if (item != null){
            TextView textViewItem = (TextView) v.findViewById(R.id.item);

            textViewItem.setText(item);
            if (position % 2 == 1){
                textViewItem.setTextColor(Color.CYAN);
            } else {
                textViewItem.setTextColor(Color.RED);
            }
        }
        return v;
    }
}
