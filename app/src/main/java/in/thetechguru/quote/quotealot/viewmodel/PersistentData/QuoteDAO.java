package in.thetechguru.quote.quotealot.viewmodel.PersistentData;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
import in.thetechguru.quote.quotealot.viewmodel.POJO.SavedQuote;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by AB on 6/24/2017.
 */

//DAO object, for accessing data from RoomDB

@Dao
public interface QuoteDAO {

    @Insert(onConflict = REPLACE)
    void cacheOnDB(List<Quote> quote);

    @Query("SELECT * FROM quote")
    LiveData<List<Quote>> getCachedQuotes();

    //for saved quotes (starred quotes)
    @Insert(onConflict = REPLACE)
    void saveOnDevice(SavedQuote quote);

    @Query("SELECT * FROM savedquote")
    List<SavedQuote> getSavedQuotes();

    @Delete
    void deleteQuote(SavedQuote quote);
}
