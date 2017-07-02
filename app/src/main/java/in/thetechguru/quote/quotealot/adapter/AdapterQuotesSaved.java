package in.thetechguru.quote.quotealot.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.thetechguru.quote.quotealot.R;
import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
import in.thetechguru.quote.quotealot.viewmodel.POJO.SavedQuote;
import in.thetechguru.quote.quotealot.viewmodel.QuoteViewModel;

/**
 * Created by AB on 7/1/2017.
 */

public class AdapterQuotesSaved extends RecyclerView.Adapter<AdapterQuotesSaved.MyViewHolder>{
        private LayoutInflater inflater;
        private List<SavedQuote> quoteItems;
        private Context context;
        private Typeface type;
        private QuoteViewModel quoteViewModel;

    public AdapterQuotesSaved(Context context){
            this.quoteItems=new ArrayList<>();
            inflater= LayoutInflater.from(context);
            this.context = context;
            type = Typeface.createFromAsset(context.getAssets(),"fonts/angelina.TTF");
        }

    public void setModel(final QuoteViewModel quoteViewModel){
        this.quoteViewModel=quoteViewModel;
    }

    @Override
    public AdapterQuotesSaved.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_quote_saved, parent, false);
        return new AdapterQuotesSaved.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterQuotesSaved.MyViewHolder holder, int position) {
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

    public void insertItems(List<SavedQuote> quotes){
        quoteItems.addAll(quotes);
        notifyDataSetChanged();
    }

    private void deleteItem(SavedQuote quote){
        int position = -1;
        if(quoteItems.contains(quote)){
            position = quoteItems.indexOf(quote);
            quoteItems.remove(quote);
            notifyItemRemoved(position);
        }
    }

    public void clearItems(){
        quoteItems.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView quote, author;
        Button remove, share;

        MyViewHolder(View itemView) {
            super(itemView);
            quote = (TextView)itemView.findViewById(R.id.quote);
            author = (TextView)itemView.findViewById(R.id.author);
            remove = (Button) itemView.findViewById(R.id.buttonRemove);
            remove.setOnClickListener(this);

            share = (Button) itemView.findViewById(R.id.buttonShare);
            share.setOnClickListener(this);
            quote.setTypeface(type);
            author.setTypeface(type);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonRemove:
                    quoteViewModel.deleteQuote(quoteItems.get(getPosition()));
                    deleteItem(quoteItems.get(getPosition()));
                    break;

                case R.id.buttonShare:
                    //share the freaking thing
                    String shareString = getSharableString(quoteItems.get(getPosition()));
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quote");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareString);
                    context.startActivity(Intent.createChooser(sharingIntent, "Quote share!"));
                    break;
            }
        }

        private String getSharableString(SavedQuote quote){
            return quote.getQuote() + "\n" + quote.getAuthor() + "\n" + "Shared via QuoteALot!";
        }
    }
}
