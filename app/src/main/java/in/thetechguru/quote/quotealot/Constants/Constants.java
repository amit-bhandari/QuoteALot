package in.thetechguru.quote.quotealot.Constants;

/**
 * Created by AB on 6/22/2017.
 */

public class Constants {

    public static final String TAG = "quote-a-lot";

    public interface stringConstants{
        String CATEGORY_ID="cat_id";
    }

    public interface quoteParam {
        String MOVIES = "movies";
        int count = 10;
    }

    public interface STATUS{
        int SUCCESS = 0;
        int FAIL = 1;
        int LOADING = 2;
    }
}
