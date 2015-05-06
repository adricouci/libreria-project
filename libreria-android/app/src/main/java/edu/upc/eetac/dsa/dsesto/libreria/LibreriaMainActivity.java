package edu.upc.eetac.dsa.dsesto.libreria;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.dsesto.libreria.api.AppException;
import edu.upc.eetac.dsa.dsesto.libreria.api.Book;
import edu.upc.eetac.dsa.dsesto.libreria.api.BookCollection;
import edu.upc.eetac.dsa.dsesto.libreria.api.LibreriaAPI;

public class LibreriaMainActivity extends ListActivity {
    private class FetchBooksTask extends
            AsyncTask<Void, Void, BookCollection> {
        private ProgressDialog pd;

        @Override
        protected BookCollection doInBackground(Void... params) {
            BookCollection books = null;
            try {
                EditText et = (EditText) findViewById(R.id.inputBook);
                books = LibreriaAPI.getInstance(LibreriaMainActivity.this)
                        .getBooksByName(et.getText().toString());
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
            pd = new ProgressDialog(LibreriaMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    private final static String TAG = LibreriaMainActivity.class.toString();
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
        setContentView(R.layout.first_page_layout);
    }

    public void clickMe(View v) {
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

        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("url", book.getLinks().get("self").getTarget());
        //Intent intent = new Intent(this, BookReviewsActivity.class);
        //intent.putExtra("url", book.getLinks().get("reviews").getTarget());
        intent.putExtra("url_reviews", book.getLinks().get("reviews").getTarget());
        startActivity(intent);
    }

    private void addBooks(BookCollection books){
        booksList.addAll(books.getBooks());
        adapter.notifyDataSetChanged();
    }
}