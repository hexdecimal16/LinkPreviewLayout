package com.dhairytripathi.linkpreviewlayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dhairytripathi.linkpreviewlayout.model.MetaData;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkPreviewLayoutTwitter extends RelativeLayout {

    private View view;
    private Context context;
    private MetaData meta;

    private ConstraintLayout constraintLayout;
    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewDesp;
    private TextView textViewContent;

    private String main_url;
    private String text;

    private boolean isDefaultClick = true;

    private LinkPreviewLayoutListener richLinkListener;


    public LinkPreviewLayoutTwitter(Context context) {
        super(context);
        this.context = context;
    }

    public LinkPreviewLayoutTwitter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public LinkPreviewLayoutTwitter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinkPreviewLayoutTwitter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void initView() {

        if(findLinearLayoutChild() != null) {
            this.view = findLinearLayoutChild();
        } else  {
            this.view = this;
            inflate(context, R.layout.twitter_link_layout,this);
        }


        constraintLayout = findViewById(R.id.rich_link_card);
        imageView = findViewById(R.id.rich_link_image);
        textViewTitle = findViewById(R.id.rich_link_title);
        textViewDesp = findViewById(R.id.rich_link_desp);
        textViewContent = findViewById(R.id.tv_content_twitter);
        textViewContent.setText(text);

        if(meta.getImageurl().equals("") || meta.getImageurl().isEmpty()) {
            imageView.setVisibility(GONE);
            Log.e("LinkPreViewLayout", "Twitter: image empty");
        } else {
            imageView.setVisibility(VISIBLE);
            Picasso.get()
                    .load(meta.getImageurl())
                    .into(imageView);
        }

        if(meta.getTitle().isEmpty() || meta.getTitle().equals("")) {
            textViewTitle.setVisibility(GONE);
            Log.e("LinkPreViewLayout", "Twitter: title empty");
        } else {
            textViewTitle.setVisibility(VISIBLE);
            textViewTitle.setText(meta.getTitle());
        }

        if(meta.getDescription().isEmpty() || meta.getDescription().equals("")) {
            textViewDesp.setVisibility(GONE);
            Log.e("LinkPreViewLayout", "Twitter: description empty");
        } else {
            textViewDesp.setVisibility(VISIBLE);
            textViewDesp.setText(meta.getDescription());
        }


        constraintLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDefaultClick) {
                    richLinkClicked();
                } else {
                    if(richLinkListener != null) {
                        richLinkListener.onClicked(view, meta);
                    } else {
                        richLinkClicked();
                    }
                }
            }
        });

    }

    private void initText() {
    }


    private void richLinkClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(main_url));
        context.startActivity(intent);
    }

    protected LinearLayout findLinearLayoutChild() {
        if (getChildCount() > 0 && getChildAt(0) instanceof LinearLayout) {
            return (LinearLayout) getChildAt(0);
        }
        return null;
    }

    public void setLinkFromMeta(MetaData metaData) {
        meta = metaData;
        initView();
    }

    public MetaData getMetaData() {
        return meta;
    }


    public void setDefaultClickListener(boolean isDefault) {
        isDefaultClick = isDefault;
    }

    public void setClickListener(LinkPreviewLayoutListener richLinkListener1) {
        richLinkListener = richLinkListener1;
    }

    public void setText(String text, final ViewListener viewListener) {
        Pattern pattern = Patterns.WEB_URL;
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        this.text = text;
        try {
            main_url = matcher.group(0);
            LinkPreview richPreview = new LinkPreview(new ResponseListener() {
                @Override
                public void onData(MetaData metaData) {
                    meta = metaData;

                    if(meta.getTitle().isEmpty() || meta.getTitle().equals("")) {
                        viewListener.onSuccess(true);
                    }

                    initView();
                }

                @Override
                public void onError(Exception e) {
                    viewListener.onError(e);
                }
            });
            richPreview.getPreview(main_url);
        } catch (Exception e) {
            Log.e("LinkPreviewLayout", e.getMessage());
            initText();
        }

    }


}
