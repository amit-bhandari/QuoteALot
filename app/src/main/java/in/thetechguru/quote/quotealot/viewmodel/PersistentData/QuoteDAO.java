package in.thetechguru.quote.quotealot.viewmodel.PersistentData;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by AB on 6/24/2017.
 */

@Dao
public interface QuoteDAO {

    @Insert(onConflict = REPLACE)
    void save(Quote quote);

    @Update
    void update(Quote quote);

    @Query("SELECT * FROM quote")
    LiveData<Quote> getQuote();
}
