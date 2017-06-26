package in.thetechguru.quote.quotealot.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.util.Log;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.thetechguru.quote.quotealot.Constants.Constants;
import in.thetechguru.quote.quotealot.MyAPP;
import in.thetechguru.quote.quotealot.viewmodel.OnlineData.QuoteAPI;
import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
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
    private QuoteAPI quoteAPI;
    private QuoteDAO quoteDAO;
    private  Executor executor;

    QuoteRepository(){

        this.executor = Executors.newSingleThreadExecutor();
        MyQuoteDB db = Room.databaseBuilder(MyAPP.getContext(),
                MyQuoteDB.class, "database-name").build();
        quoteDAO=db.quoteDAO();

    }

    // ...
    LiveData<Quote> getQuote(String category, int count) {
        refreshQuote(category);
        return quoteDAO.getQuote();
    }

    private void refreshQuote(String category){
        final MutableLiveData<Quote> data = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://andruxnet-random-famous-quotes.p.mashape.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        quoteAPI = retrofit.create(QuoteAPI.class);

        quoteAPI.getQuote(category, 1).enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, final Response<Quote> response) {
                //data.setValue(response.body());
                //Log.v(Constants.TAG,"Webservice!");
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        quoteDAO.save(response.body());
                    }
                });
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                Log.v(Constants.TAG, t.getMessage());
            }
        });
    }

}
