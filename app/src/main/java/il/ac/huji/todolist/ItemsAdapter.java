package il.ac.huji.todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;


public class ItemsAdapter extends ArrayAdapter<String> {
    final Context m_Context;
    final int m_Resource;
    List<String> m_Objects;

    /**
     * Inner class represents an item delete dialog
     */
    private class ItemDialog extends Dialog {
        final int m_Position;
        final String m_Name;

        public ItemDialog(Activity activity, int position, String name){
            super(activity);
            m_Position = position;
            m_Name = name;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            TextView dialogName;
            Button deleteButton;

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            setContentView(R.layout.item_dialog);

            dialogName = (TextView)findViewById(R.id.itemName);
            dialogName.setText(m_Name);

            deleteButton = (Button) findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_Objects.remove(m_Position);
                    notifyDataSetChanged();
                    dismiss();
                }
            });
        } // onCreate
    } // ItemDialog

    public ItemsAdapter(Context context, int resource, List<String> objects){
        super(context, resource, objects);
        m_Context = context;
        m_Resource = resource;
        m_Objects = objects;
    }

    /**
     * Used when adding to the adapter
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rawView;
        final String item;

        if (convertView == null) {
            LayoutInflater vi;

            vi = LayoutInflater.from(getContext());
            rawView = vi.inflate(R.layout.list_raw, null);
        } else {
            rawView = convertView;
        }
        item = getItem(position);

        if (item != null){
            TextView textViewItem;

            textViewItem = (TextView) rawView.findViewById(R.id.item);
            textViewItem.setText(item);
            if (position % 2 == 1){
                textViewItem.setTextColor(Color.BLUE);
            } else {
                textViewItem.setTextColor(Color.RED);
            }

            textViewItem.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    ItemDialog itemDialog;

                    itemDialog = new ItemDialog((Activity) getContext(), position, item);
                    itemDialog.getWindow().
                            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    itemDialog.show();
                    return false;
                }
            });
        }

        return rawView;
    } //getView()


} //ItemsAdapter
