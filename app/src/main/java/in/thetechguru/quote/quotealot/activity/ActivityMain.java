package in.thetechguru.quote.quotealot.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import in.thetechguru.quote.quotealot.R;

public class ActivityMain extends AppCompatActivity {

    private final String CATEGORY = "famous";

    @Override
    public void onBackPressed() {
        //just get it over with
        finish();
        //
    }

    private final int COUNT = 10;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentQuoteOnline fragmentQuote = new FragmentQuoteOnline();
                    Bundle args = new Bundle();
                    args.putString("category", CATEGORY);
                    args.putInt("count", COUNT);
                    fragmentQuote.setArguments(args);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, fragmentQuote, fragmentQuote.getClass().getName()).commit();

                    return true;

                case R.id.navigation_dashboard:
                    FragmentQuoteSaved fragmentQuoteSaved = new FragmentQuoteSaved();
                    Bundle argsSaved = new Bundle();
                    argsSaved.putString("category", CATEGORY);
                    argsSaved.putInt("count", COUNT);
                    fragmentQuoteSaved.setArguments(argsSaved);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, fragmentQuoteSaved, fragmentQuoteSaved.getClass().getName())
                            .addToBackStack(null).commit();

                    return true;

                case R.id.navigation_notifications:
                    FragmentAbout fragmentAbout = new FragmentAbout();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, fragmentAbout, fragmentAbout.getClass().getName())
                            .addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //currently ignored
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //create fragment object only when its not already created
        if(savedInstanceState==null) {
            //load quotes fragment
            FragmentQuoteOnline fragmentQuote = new FragmentQuoteOnline();
            Bundle args = new Bundle();
            args.putString("category", CATEGORY);
            args.putInt("count", COUNT);
            fragmentQuote.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_container, fragmentQuote, "quote").commit();
        }
    }

}
