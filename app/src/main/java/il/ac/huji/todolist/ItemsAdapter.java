package il.ac.huji.todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Gravity;
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

    private class ItemDialog extends Dialog implements
            android.view.View.OnClickListener {
        Button deleteButton;
        int m_Position, m_posX, m_posY;
        String m_Name;
        public ItemDialog(Activity activity, int position, String name, int posX, int posY){
            super(activity);
            m_Position = position;
            m_Name = name;
            m_posX = posX;
            m_posY = posY;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            setContentView(R.layout.item_dialog);
            WindowManager.LayoutParams wmlp = getWindow().getAttributes();
            wmlp.gravity = Gravity.LEFT;

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.x = m_posX;
            params.y = m_posY + 200;


            getWindow().setAttributes(params);

            TextView dialogName = (TextView)findViewById(R.id.itemName);

            dialogName.setText(m_Name);

            deleteButton = (Button) findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            m_Objects.remove(m_Position);
            notifyDataSetChanged();
            dismiss();
        }
    }

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
                    ItemDialog itemDialog = new ItemDialog((Activity)getContext(),
                            position, item, (int) event.getX(), (int) event.getY());
                    itemDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    itemDialog.show();

                    /*
                    final Dialog inviteBuilder = new Dialog(dialogView.getContext());
                    inviteBuilder.setTitle(item);
                    LayoutInflater inflater = (LayoutInflater)
                            dialogView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View bodyView = inflater.inflate(R.layout.item_dialog, null);
                    Button deleteButton = (Button) bodyView.findViewById(R.id.deleteButton);
                    inviteBuilder.setContentView(bodyView);

                    WindowManager.LayoutParams params = inviteBuilder.getWindow().getAttributes();
                    params.x = (int) event.getX();
                    params.y = (int) event.getY();
                    inviteBuilder.getWindow().setGravity(Gravity.LEFT|Gravity.TOP);
                    inviteBuilder.getWindow().setAttributes(params);


                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View buttonView) {
                            m_Objects.remove(position);
                            notifyDataSetChanged();
                            inviteBuilder.dismiss();
                        }
                    });
                    inviteBuilder.show();*/
                    return true;
                }
            });
        }



        return rawView;
    }


}
