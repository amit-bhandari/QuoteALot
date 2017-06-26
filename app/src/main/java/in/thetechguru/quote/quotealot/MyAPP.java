package in.thetechguru.quote.quotealot;

import android.app.Application;
import android.content.Context;

/**
 * Created by AB on 6/24/2017.
 */

public class MyAPP extends Application {
    private static MyAPP myAPP;

    @Override
    public void onCreate() {
        super.onCreate();
        myAPP=this;
    }

    public static Context getContext(){
        return myAPP.getApplicationContext();
    }
}
