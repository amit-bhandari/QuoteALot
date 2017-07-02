package in.thetechguru.quote.quotealot.activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import in.thetechguru.quote.quotealot.R;

/**
 * Created by AB on 7/1/2017.
 */

public class FragmentAbout extends android.support.v4.app.Fragment {

    TextView githubLink, siteLink;
    private final String GITHUB_URL = "http://github.com/amit-bhandari/QuoteALot";
    private final String SITE_URL = "http://localhost";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ((TextView)view.findViewById(R.id.about_text)).setText(getAboutString());

        githubLink = ((TextView)view.findViewById(R.id.github_link));
        siteLink = ((TextView)view.findViewById(R.id.site_link));
        setLinks();

        return view;
    }

    private void setLinks(){
        SpannableString spanGithub = new SpannableString(githubLink.getText());
        ClickableSpan clickableSpanGithub = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL));
                startActivity(browserIntent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setTypeface(Typeface.create(ds.getTypeface(), Typeface.BOLD));
            }
        };
        spanGithub.setSpan(clickableSpanGithub, 0, githubLink.getText().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        SpannableString spanSiteLink = new SpannableString(siteLink.getText());
        ClickableSpan clickableSpanSiteLink = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SITE_URL));
                startActivity(browserIntent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setTypeface(Typeface.create(ds.getTypeface(), Typeface.BOLD));
            }
        };
        spanSiteLink.setSpan(clickableSpanSiteLink, 0, siteLink.getText().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        githubLink.setText(spanGithub);
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());
        siteLink.setText(spanSiteLink);
        siteLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String getAboutString(){
        StringBuffer about = new StringBuffer(getString(R.string.about));
        about.append("\n\nlink");
        return about.toString();
    }
}
