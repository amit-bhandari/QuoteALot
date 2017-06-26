package in.thetechguru.quote.quotealot.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import in.thetechguru.quote.quotealot.Constants.Constants;
import in.thetechguru.quote.quotealot.R;

public class ActivityMain extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState==null) {
            //load quotes fragment
            FragmentQuote fragmentQuote = new FragmentQuote();
            Bundle args = new Bundle();
            args.putString(Constants.stringConstants.CATEGORY_ID, Constants.quoteParam.MOVIES);
            fragmentQuote.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_container, fragmentQuote, "quote").commit();
        }
    }

}
