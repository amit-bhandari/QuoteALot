package in.thetechguru.quote.quotealot.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executors;

import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
import in.thetechguru.quote.quotealot.viewmodel.POJO.SavedQuote;

/**
 * Created by AB on 6/24/2017.
 */

public class QuoteViewModel extends ViewModel {
    private LiveData<List<Quote>> quoteOnline;
    private List<SavedQuote> quoteSaved;
    private String category;
    private int count;
    private QuoteRepository quoteRepository;

    public void init(String category, int count){
        if(this.quoteOnline !=null){
            return ;
        }
        this.category = category;
        this.count=count;
        quoteRepository=new QuoteRepository();
        quoteOnline = quoteRepository.getQuoteOnline(category, count);
    }

    //get online fetched quotes
    public LiveData<List<Quote>> getQuoteOnline(){
        return quoteOnline;
    }

    //get saved/starred quotes
    // no need of live data for saved quotes
    public List<SavedQuote> getQuoteSaved(){
        return quoteRepository.getSavedQuote();
    }



    //fetch new quotes and update the db
    public void  refreshData(){
        quoteOnline = quoteRepository.getQuoteOnline(category, count);
    }

    //cacheOnDB the quote in different db, kind of starred quotes
    public void saveQuote(Quote quote){
        quoteRepository.saveQuote(quote);
    }

    //cacheOnDB the quote in different db, kind of starred quotes
    public void deleteQuote(SavedQuote quote){
        quoteRepository.deleteQuote(quote);
    }

}
