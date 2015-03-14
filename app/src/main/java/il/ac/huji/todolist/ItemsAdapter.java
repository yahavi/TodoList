package il.ac.huji.todolist;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.content.ClipData.Item;
import java.util.List;


public class ItemsAdapter extends ArrayAdapter<String> {
    Context m_Context;
    int m_Resource;
    List<String> m_Objects;

    public ItemsAdapter(Context context, int resource, List<String> objects){
        super(context, resource, objects);
        m_Context = context;
        m_Resource = resource;
        m_Objects = objects;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rawView;
        final ViewGroup rawParent = parent;
        if (convertView == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            rawView = vi.inflate(R.layout.list_raw, null);
        } else {
            rawView = convertView;
        }
        final String item = getItem(position);

        if (item != null){
            TextView textViewItem = (TextView) rawView.findViewById(R.id.item);

            textViewItem.setText(item);
            if (position % 2 == 1){
                textViewItem.setTextColor(Color.CYAN);
            } else {
                textViewItem.setTextColor(Color.RED);
            }

            textViewItem.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View dialogView, MotionEvent event) {
                    final Dialog inviteBuilder = new Dialog(dialogView.getContext());
                    inviteBuilder.setTitle(item);
                    LayoutInflater inflater = (LayoutInflater)
                            dialogView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View bodyView = inflater.inflate(R.layout.item_dialog, null);
                    Button deleteButton = (Button) bodyView.findViewById(R.id.deleteButton);
                    inviteBuilder.setContentView(bodyView);

                    WindowManager.LayoutParams params = inviteBuilder.getWindow().getAttributes();
                    params.x = 300;//(int) event.getX();
                    params.y = 300;//(int) event.getY();
                    inviteBuilder.getWindow().setAttributes(params);


                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View buttonView) {
                            m_Objects.remove(position);
                            notifyDataSetChanged();
                            inviteBuilder.dismiss();
                        }
                    });
                    inviteBuilder.show();
                    return true;
                }
            });
        }



        return rawView;
    }


}
