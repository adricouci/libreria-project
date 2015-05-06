package edu.upc.eetac.dsa.dsesto.libreria;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.dsesto.libreria.api.Book;
import edu.upc.eetac.dsa.dsesto.libreria.api.Review;

public class ReviewAdapter extends BaseAdapter {
    private final ArrayList<Review> data;
    private LayoutInflater inflater;

    public ReviewAdapter(Context context, ArrayList<Review> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    private static class ViewHolder {
        TextView tvContent;
        TextView tvUsername;
        TextView tvBook;
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
            convertView = inflater.inflate(R.layout.list_row_review, null);
            viewHolder = new ViewHolder();
            viewHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tvContent);
            viewHolder.tvUsername = (TextView) convertView
                    .findViewById(R.id.tvUsername);
            viewHolder.tvBook = (TextView) convertView
                    .findViewById(R.id.tvBook);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String content = data.get(position).getContent();
        String username = data.get(position).getUsername();
        int bookid = data.get(position).getBookid();


        String TAG = BookReviewsActivity.class.getName();
        viewHolder.tvContent.setText(content);
        viewHolder.tvUsername.setText(username);
        //viewHolder.tvBook.setText(bookid); //ESTE FALLA NO SÉ POR QUÉ
        return convertView;
    }
}