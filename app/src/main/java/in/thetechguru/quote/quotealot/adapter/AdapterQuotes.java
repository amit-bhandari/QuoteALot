package in.thetechguru.quote.quotealot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.thetechguru.quote.quotealot.R;
import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;

/**
 * Created by AB on 6/22/2017.
 */

public class AdapterQuotes extends RecyclerView.Adapter<AdapterQuotes.MyViewHolder>{
    private LayoutInflater inflater;
    private List<Quote> quoteItems;

    public AdapterQuotes(Context context){
        this.quoteItems=new ArrayList<>();
        inflater= LayoutInflater.from(context);
    }

    @Override
    public AdapterQuotes.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_quote, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterQuotes.MyViewHolder holder, int position) {
        holder.quote.setText(quoteItems.get(position).getQuote());
        holder.author.setText(quoteItems.get(position).getAuthor());

        //just a fancy animation, nothing else
        setFadeAnimation(holder.itemView);
    }

    private void setFadeAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
       //AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return quoteItems.size();
    }

    public void insertItems(List<Quote> quotes){
        //add items at the top
        for(int i=0;i<quotes.size();i++){
            quoteItems.add(0,quotes.get(i));
        }
        notifyItemRangeInserted(0,quotes.size()-1);
    }

    public void clearItems(){
        quoteItems.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView quote, author;

        public MyViewHolder(View itemView) {
            super(itemView);
            quote = (TextView)itemView.findViewById(R.id.quote);
            author = (TextView)itemView.findViewById(R.id.author);
        }
    }
}
