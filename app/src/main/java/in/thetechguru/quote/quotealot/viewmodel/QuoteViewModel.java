package in.thetechguru.quote.quotealot.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;

/**
 * Created by AB on 6/24/2017.
 */

public class QuoteViewModel extends ViewModel {
    private LiveData<Quote> quote;
    private String category;
    private int count;
    QuoteRepository quoteRepository;

    public void init(String category, int count){
        if(this.quote!=null){
            return ;
        }
        this.category = category;
        this.count=count;
        quoteRepository=new QuoteRepository();
        quote = quoteRepository.getQuote(category, count);
    }

    public LiveData<Quote> getQuote(){
        return quote;
    }

    public void  refreshData(){
        quote = quoteRepository.getQuote(category, count);
    }
}
