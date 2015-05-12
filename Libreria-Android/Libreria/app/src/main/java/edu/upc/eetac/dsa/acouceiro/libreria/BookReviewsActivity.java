package edu.upc.eetac.dsa.acouceiro.libreria;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.acouceiro.libreria.api.AppException;
import edu.upc.eetac.dsa.acouceiro.libreria.api.Book;
import edu.upc.eetac.dsa.acouceiro.libreria.api.LibreriaAPI;
import edu.upc.eetac.dsa.acouceiro.libreria.api.Review;
import edu.upc.eetac.dsa.acouceiro.libreria.api.ReviewCollection;

public class BookReviewsActivity extends ListActivity {
    private final static String TAG = BookReviewsActivity.class.getName();
    private ArrayList<Review> reviewsList;
    private ReviewAdapter adapter;
    String urlBook = null;
    Book book = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_reviews_layout);

        reviewsList = new ArrayList<Review>();
        adapter = new ReviewAdapter(this, reviewsList);
        setListAdapter(adapter);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("test", "test"
                        .toCharArray());
            }
        });

        urlBook = (String) getIntent().getExtras().get("url_book");
        String urlReviews = (String) getIntent().getExtras().get("url");
        (new FetchReviewsTask()).execute(urlReviews);
    }


    private class FetchReviewsTask extends
            AsyncTask<String, Void, ReviewCollection> {
        private ProgressDialog pd;

        @Override
        protected ReviewCollection doInBackground(String... params) {
            ReviewCollection reviews = null;
            try {
                reviews = LibreriaAPI.getInstance(BookReviewsActivity.this)
                        .getReviews(params[0]);
            } catch (AppException e) {
                e.printStackTrace();
            }
            return reviews;
        }

        @Override
        protected void onPostExecute(ReviewCollection result) {
            addReviews(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(BookReviewsActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    private void addReviews(ReviewCollection reviews){
        reviewsList.addAll(reviews.getReviews());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_write_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.writeReviewMenuItem:
                Intent intent = new Intent(this, WriteReviewActivity.class);
                intent.putExtra("url_book", urlBook);
                startActivityForResult(intent, WRITE_ACTIVITY);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final static int WRITE_ACTIVITY = 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case WRITE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String jsonReview = res.getString("json-review");
                    Review review = new Gson().fromJson(jsonReview, Review.class);
                    reviewsList.add(0, review);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
