package com.dhairytripathi.linkpreviewlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhairytripathi.linkpreviewlayout.model.MetaData;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkPreviewLayoutTelegram extends RelativeLayout {

    private View view;
    private Context context;
    private MetaData meta;

    private LinearLayout linearLayout;
    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewDesp;
    private TextView textViewUrl;
    private TextView textViewOriginalUrl;

    private String main_url;
    private String text;

    private boolean isDefaultClick = true;

    private LinkPreviewLayoutListener richLinkListener;


    public LinkPreviewLayoutTelegram(Context context) {
        super(context);
        this.context = context;
    }

    public LinkPreviewLayoutTelegram(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public LinkPreviewLayoutTelegram(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LinkPreviewLayoutTelegram(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    private void initView() {

        if(findLinearLayoutChild() != null) {
            this.view = findLinearLayoutChild();
        } else  {
            this.view = this;
            inflate(context, R.layout.telegram_link_layout,this);
        }

        linearLayout = findViewById(R.id.rich_link_card);
        imageView = findViewById(R.id.rich_link_image);
        textViewTitle = findViewById(R.id.rich_link_title);
        textViewDesp = findViewById(R.id.rich_link_desp);
        textViewUrl = findViewById(R.id.rich_link_url);

        textViewOriginalUrl = findViewById(R.id.rich_link_original_url);

        textViewOriginalUrl.setText(text);

        if(meta.getImageurl().equals("") || meta.getImageurl().isEmpty()) {
            imageView.setVisibility(GONE);
            Log.e("LinkPreViewLayout", "Telegram: imageUrl empty");
        } else {
            imageView.setVisibility(VISIBLE);
            Picasso.get()
                    .load(meta.getImageurl())
                    .into(imageView);
        }

        if(meta.getTitle().isEmpty() || meta.getTitle().equals("")) {
            textViewTitle.setVisibility(GONE);
            Log.e("LinkPreViewLayout", "Telegram: title empty");
        } else {
            textViewTitle.setVisibility(VISIBLE);
            textViewTitle.setText(meta.getTitle());
        }
        if(meta.getUrl().isEmpty() || meta.getUrl().equals("")) {
            textViewUrl.setVisibility(GONE);
            Log.e("LinkPreViewLayout", "Telegram: url empty");
        } else {
            textViewUrl.setVisibility(VISIBLE);
            textViewUrl.setText(meta.getUrl());
        }
        if(meta.getDescription().isEmpty() || meta.getDescription().equals("")) {
            textViewDesp.setVisibility(GONE);
            Log.e("LinkPreViewLayout", "Telegram: description empty");
        } else {
            textViewDesp.setVisibility(VISIBLE);
            textViewDesp.setText(meta.getDescription());
        }


        linearLayout.setOnClickListener(new OnClickListener() {
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
    private void initTextView() {
        inflate(context, R.layout.telegram_link_layout,this);
        LinearLayout bgDataTelegram = findViewById(R.id.bg_data_telegram);
        textViewOriginalUrl = findViewById(R.id.rich_link_original_url);
        bgDataTelegram.setVisibility(GONE);
        textViewOriginalUrl.setText(text);
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
        this.text = text;
        matcher.find();
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
            initTextView();
        }

    }

}
