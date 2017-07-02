package in.thetechguru.quote.quotealot.viewmodel.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by AB on 6/30/2017.
 */

@Entity
public class SavedQuote  {

    @PrimaryKey
    private String quote;
    private String author;
    private String category;

    private long timeOfSaving = 0L;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SavedQuote && this.quote.equals(((SavedQuote) obj).getQuote());
    }

    //Room needs empty constructor
    public SavedQuote(){}

    public SavedQuote(Quote quote){
        this.quote = quote.getQuote();
        this.author = quote.getAuthor();
        this.category = quote.getCategory();
        timeOfSaving = System.currentTimeMillis();
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTimeOfSaving() {
        return timeOfSaving;
    }

    public void setTimeOfSaving(long timeOfSaving) {
        this.timeOfSaving=timeOfSaving;
    }
}
