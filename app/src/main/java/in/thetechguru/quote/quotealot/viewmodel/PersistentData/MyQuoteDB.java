package in.thetechguru.quote.quotealot.viewmodel.PersistentData;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.RoomDatabase;

import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
import in.thetechguru.quote.quotealot.viewmodel.POJO.SavedQuote;

/**
 * Created by AB on 6/24/2017.
 */

@Database(entities = {Quote.class, SavedQuote.class}, version = 6)
public abstract class MyQuoteDB extends RoomDatabase {

    public abstract QuoteDAO quoteDAO();

}
