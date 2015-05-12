package edu.upc.eetac.dsa.acouceiro.libreria;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import edu.upc.eetac.dsa.acouceiro.libreria.api.AppException;
import edu.upc.eetac.dsa.acouceiro.libreria.api.Book;
import edu.upc.eetac.dsa.acouceiro.libreria.api.LibreriaAPI;

public class BookDetailActivity extends Activity {
    private final static String TAG = BookDetailActivity.class.getName();
    Book book = null;
    String urlReviews = null;
    String urlBook = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_layout);
        urlBook = (String) getIntent().getExtras().get("url");
        urlReviews = (String) getIntent().getExtras().get("url_reviews");
        (new FetchBookTask()).execute(urlBook);
    }

    private void loadBook(Book book) {
        TextView tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
        TextView tvDetailAuthor = (TextView) findViewById(R.id.tvDetailAuthor);
        TextView tvDetailPublisher = (TextView) findViewById(R.id.tvDetailPublisher);
        TextView tvDetailDate = (TextView) findViewById(R.id.tvDetailDate);
        TextView tvDetailEdition = (TextView) findViewById(R.id.tvDetailEdition);
        TextView tvDetailLanguage = (TextView) findViewById(R.id.tvDetailLanguage);

        tvDetailTitle.setText(book.getTitle());
        tvDetailAuthor.setText(book.getAuthor());
        tvDetailPublisher.setText(book.getPublisher());
        tvDetailDate.setText(book.getPrintingDate());
        tvDetailEdition.setText("Edition " + book.getEdition());
        tvDetailLanguage.setText(book.getLanguage());
    }

    private class FetchBookTask extends AsyncTask<String, Void, Book> {
        private ProgressDialog pd;

        @Override
        protected Book doInBackground(String... params) {
            Book book = null;
            try {
                book = LibreriaAPI.getInstance(BookDetailActivity.this)
                        .getBook(params[0]);
            } catch (AppException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            return book;
        }

        @Override
        protected void onPostExecute(Book result) {
            loadBook(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(BookDetailActivity.this);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_libreria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reviewsMenuItem:
                Intent intent = new Intent(this, BookReviewsActivity.class);
                intent.putExtra("url", urlReviews);
                intent.putExtra("url_book", urlBook);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
