package com.dhairytripathi.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dhairytripathi.linkpreviewlayout.model.MetaData;
import com.dhairytripathi.linkpreviewlayout.LinkPreviewLayoutListener;
import com.dhairytripathi.linkpreviewlayout.LinkPreviewLayout;
import com.dhairytripathi.linkpreviewlayout.LinkPreviewLayoutSkype;
import com.dhairytripathi.linkpreviewlayout.LinkPreviewLayoutTelegram;
import com.dhairytripathi.linkpreviewlayout.LinkPreviewLayoutTwitter;
import com.dhairytripathi.linkpreviewlayout.ViewListener;


public class MainActivity extends AppCompatActivity {

    LinkPreviewLayout linkPreviewLayout;
    LinkPreviewLayoutTelegram linkPreviewLayoutTelegram;
    LinkPreviewLayoutSkype linkPreviewLayoutSkype;
    LinkPreviewLayoutTwitter linkPreviewLayoutTwitter;

    Button goToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkPreviewLayout = findViewById(R.id.linkPreview);
        linkPreviewLayoutTelegram = findViewById(R.id.linkPreview1);
        linkPreviewLayoutSkype = findViewById(R.id.linkPreview2);
        linkPreviewLayoutTwitter = findViewById(R.id.linkPreview3);

        linkPreviewLayout.setText("https://whatsapp.com Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel diam ultricies, volutpat erat eget, aliquam erat. Vivamus non nibh ac felis hendrerit molestie. Duis luctus vestibulum neque eget congue.", new ViewListener() {
            @Override
            public void onSuccess(boolean status) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        linkPreviewLayoutTelegram.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel diam ultricies, volutpat erat eget, aliquam erat. Vivamus non nibh ac felis hendrerit molestie. Duis luctus vestibulum neque eget congue. https://telegram.org", new ViewListener() {
            @Override
            public void onSuccess(boolean status) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        linkPreviewLayoutSkype.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel diam ultricies, volutpat erat eget, aliquam erat https://www.skype.com Aenean id hendrerit est. Cras justo orci, finibus facilisis tristique vitae, efficitur ac risus. In hac habitasse platea dictumst. Maecenas dictum ex egestas consectetur vestibulum", new ViewListener() {
            @Override
            public void onSuccess(boolean status) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        linkPreviewLayoutTwitter.setText("https://instagram.com https://google.com", new ViewListener() {
            @Override
            public void onSuccess(boolean status) {

            }

            @Override
            public void onError(Exception e) {

            }
        });


        //custom clickListener
        linkPreviewLayout.setDefaultClickListener(false);
        linkPreviewLayout.setClickListener(new LinkPreviewLayoutListener() {
            @Override
            public void onClicked(View view, MetaData meta) {
                Toast.makeText(getApplicationContext(), meta.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}