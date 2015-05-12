package edu.upc.eetac.dsa.acouceiro.libreria;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.acouceiro.libreria.api.AppException;
import edu.upc.eetac.dsa.acouceiro.libreria.api.Book;
import edu.upc.eetac.dsa.acouceiro.libreria.api.LibreriaAPI;
import edu.upc.eetac.dsa.acouceiro.libreria.api.Review;

public class WriteReviewActivity extends Activity {
    private final static String TAG = WriteReviewActivity.class.getName();
    String urlBook = null;

    private class PostReviewTask extends AsyncTask<String, Void, Review> {
        private ProgressDialog pd;
        Book book = null;

        @Override
        protected Review doInBackground(String... params) {
            Review review = null;
            try {
                book = LibreriaAPI.getInstance(WriteReviewActivity.this)
                        .getBook(params[1]);

                String username = "test1";
                String name = "test1";
                int bookid = book.getBookid();

                review = LibreriaAPI.getInstance(WriteReviewActivity.this).createReview(params[0], username, name, bookid);

            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return review;
        }

        @Override
        protected void onPostExecute(Review result) {
            showReviews(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(WriteReviewActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review_layout);

        urlBook = (String) getIntent().getExtras().get("url_book");
    }

    public void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void postReview(View v) {
        EditText etContent = (EditText) findViewById(R.id.etContent);

        String content = etContent.getText().toString();

        (new PostReviewTask()).execute(content, urlBook);
    }

    private void showReviews(Review result) {
        String json = new Gson().toJson(result);
        Bundle data = new Bundle();
        data.putString("json-review", json);
        Intent intent = new Intent();
        intent.putExtras(data);
        setResult(RESULT_OK, intent);
        finish();
    }

}