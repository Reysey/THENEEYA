package info.wpf.calculatorof10.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import info.wpf.calculatorof10.R;

public class CustomAdapter extends ArrayAdapter<String> {

    private List<String> dataList;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, List<String> dataList) {
        super(context, 0, dataList);
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item_layout, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.textview_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the text for the TextView
        String itemText = dataList.get(position);
        holder.textView.setText(itemText);

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
