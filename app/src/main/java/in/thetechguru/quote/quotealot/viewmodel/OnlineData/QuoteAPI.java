package in.thetechguru.quote.quotealot.viewmodel.OnlineData;

/**
 * Created by AB on 6/22/2017.
 */



import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface QuoteAPI {

    @Headers({
            "X-Mashape-Key: wrojcHJVhLmshHjcLfMRm3G8W6gPp12PqAGjsnLSwZdhqoqUMY",
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: application/json"
    })
    @GET("/")
    Call<Quote> getQuote(@Query("cat") String category,
                          @Query("count") int count);

}
