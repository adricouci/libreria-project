package edu.upc.eetac.dsa.dsesto.libreria;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.dsesto.libreria.api.AppException;
import edu.upc.eetac.dsa.dsesto.libreria.api.Book;
import edu.upc.eetac.dsa.dsesto.libreria.api.LibreriaAPI;
import edu.upc.eetac.dsa.dsesto.libreria.api.Review;
import edu.upc.eetac.dsa.dsesto.libreria.api.ReviewCollection;

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

//    private void loadBook(Book book) {
//        TextView tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
//        TextView tvDetailAuthor = (TextView) findViewById(R.id.tvDetailAuthor);
//        TextView tvDetailPublisher = (TextView) findViewById(R.id.tvDetailPublisher);
//        TextView tvDetailDate = (TextView) findViewById(R.id.tvDetailDate);
//
//        tvDetailTitle.setText(book.getTitle());
//        tvDetailAuthor.setText(book.getAuthor());
//        tvDetailPublisher.setText(book.getPublisher());
//        tvDetailDate.setText(book.getPrintingDate());
//    }

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
        //Log.d(TAG, reviews.getReviews().get(0).getContent().toString()); //ESTA FUNCIÓN FUNCIONA ENTERA
        reviewsList.addAll(reviews.getReviews());
        //reviewsList.add(reviews.getReviews().get(0));
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
              //intent.putExtra("url", book.getLinks().get("reviews").getTarget());
                intent.putExtra("url_book", urlBook);
              //startActivity(intent);
                startActivityForResult(intent, WRITE_ACTIVITY); //Para que aparezca la nueva review después de postearla
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Método para que se visualice la nueva review
    private final static int WRITE_ACTIVITY = 0; //Porque solo hay una actividad, si se lanzan varias se asignan números sucesivos
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
