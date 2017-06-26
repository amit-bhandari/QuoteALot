package in.thetechguru.quote.quotealot.activity;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.thetechguru.quote.quotealot.Constants.Constants;
import in.thetechguru.quote.quotealot.R;
import in.thetechguru.quote.quotealot.viewmodel.POJO.Quote;
import in.thetechguru.quote.quotealot.viewmodel.QuoteViewModel;

/**
 * Created by AB on 6/24/2017.
 */

public class FragmentQuote extends LifecycleFragment{
    private QuoteViewModel quoteViewModel;
    private TextView quotetv;
    private TextView authortv;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote,container,false);
        quotetv = (TextView) view.findViewById(R.id.quote);
        authortv = (TextView) view.findViewById(R.id.author);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                quoteViewModel.refreshData();

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);
        quoteViewModel.init( getArguments().getString(Constants.stringConstants.CATEGORY_ID), Constants.quoteParam.count);
        quoteViewModel.getQuote().observe(this, new Observer<Quote>() {
            @Override
            public void onChanged(@NonNull Quote quote) {
                quotetv.setText(quote.getQuote());
                authortv.setText(quote.getAuthor());

                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
