package in.thetechguru.quote.quotealot.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.util.Log;


import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.thetechguru.quote.quotealot.Constants.Constants;
import in.thetechguru.quote.quotealot.MyAPP;
import in.thetechguru.quote.quotealot.viewmodel.OnlineData.QuoteAPI;
import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
import in.thetechguru.quote.quotealot.viewmodel.POJO.SavedQuote;
import in.thetechguru.quote.quotealot.viewmodel.PersistentData.MyQuoteDB;
import in.thetechguru.quote.quotealot.viewmodel.PersistentData.QuoteDAO;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AB on 6/22/2017.
 */

class QuoteRepository {

    private QuoteAPI quoteAPI;   //for webservice call
    private QuoteDAO quoteDAO;   //for db access
    private  Executor executor;  //threading, apparently we are crazy if we call db on main thread!

    QuoteRepository(){

        this.executor = Executors.newSingleThreadExecutor();

        //get the room db object, and thus DAO
        MyQuoteDB db = Room.databaseBuilder(MyAPP.getContext(),
                MyQuoteDB.class, "database-name").build();
        quoteDAO=db.quoteDAO();

    }

    // ...
    LiveData<List<Quote>> getQuoteOnline(String category, int count) {
        //fetch new quotes
        refreshQuote(category);
        //meanwhile show db cached quotes
        return quoteDAO.getCachedQuotes();
    }

    List<SavedQuote> getSavedQuote() {
        //no need to fetch online, directly return data stored in db
        return quoteDAO.getSavedQuotes();
    }

    private void refreshQuote(String category){

        //logging purpose
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //build retrofit object and call web service
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://andruxnet-random-famous-quotes.p.mashape.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        quoteAPI = retrofit.create(QuoteAPI.class);

        //enqueue makes sure call is asynchronous
        quoteAPI.getQuote(category, 10).enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, final Response<List<Quote>> response) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        //insert the quotes in db, UI will be informed, LiveData duh!
                        quoteDAO.cacheOnDB(response.body());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                Log.v(Constants.TAG, t.getMessage());
            }
        });
    }

    void saveQuote(Quote quote){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                quoteDAO.saveOnDevice(new SavedQuote(quote) );
            }
        });
    }

    void deleteQuote(SavedQuote quote){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                quoteDAO.deleteQuote(quote);
            }
        });
    }
}
