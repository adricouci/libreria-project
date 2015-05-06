package edu.upc.eetac.dsa.dsesto.libreria;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.dsesto.libreria.api.AppException;
import edu.upc.eetac.dsa.dsesto.libreria.api.Book;
import edu.upc.eetac.dsa.dsesto.libreria.api.BookCollection;
import edu.upc.eetac.dsa.dsesto.libreria.api.LibreriaAPI;

public class LibreriaMainActivity_copy extends ListActivity {
    private class FetchBooksTask extends
            AsyncTask<Void, Void, BookCollection> {
        private ProgressDialog pd;

        @Override
        protected BookCollection doInBackground(Void... params) {
            BookCollection books = null;
            try {
                books = LibreriaAPI.getInstance(LibreriaMainActivity_copy.this)
                        .getBooks();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return books;
        }

        @Override
        protected void onPostExecute(BookCollection result) {
            addBooks(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LibreriaMainActivity_copy.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    private final static String TAG = LibreriaMainActivity_copy.class.toString();
//    private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
//            "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
//            "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
//            "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
//            "augue", "purus" };
//    private ArrayAdapter<String> adapter;
    private ArrayList<Book> booksList;
    private BookAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libreria_main);

        booksList = new ArrayList<Book>();
        adapter = new BookAdapter(this, booksList);
        setListAdapter(adapter);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("test", "test"
                        .toCharArray());
            }
        });
        (new FetchBooksTask()).execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Book book = booksList.get(position);
        Log.d(TAG, book.getLinks().get("self").getTarget());

        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("url", book.getLinks().get("self").getTarget());
        startActivity(intent);
    }

    private void addBooks(BookCollection books){
        booksList.addAll(books.getBooks());
        adapter.notifyDataSetChanged();
    }
}