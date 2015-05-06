package edu.upc.eetac.dsa.dsesto.libreria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.dsesto.libreria.api.Book;

public class BookAdapter extends BaseAdapter {
    private final ArrayList<Book> data;
    private LayoutInflater inflater;

    public BookAdapter(Context context, ArrayList<Book> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvDate;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    //Aquí no funcionaba, por eso tiene el parseLong, pero de momento no sé tampoco si funciona
    @Override
    public long getItemId(int position) {
        return 0;
    }

//    @Override
//    public long getItemId(int position) {
//        return Long.parseLong(((Book) getItem(position)).getTitle());
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_book, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView
                    .findViewById(R.id.tvTitle);
            viewHolder.tvAuthor = (TextView) convertView
                    .findViewById(R.id.tvAuthor);
            viewHolder.tvDate = (TextView) convertView
                    .findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String title = data.get(position).getTitle();
        String author = data.get(position).getAuthor();
        String date = data.get(position).getPrintingDate();
        viewHolder.tvTitle.setText(title);
        viewHolder.tvAuthor.setText(author);
        viewHolder.tvDate.setText(date);
        return convertView;
    }
}