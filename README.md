# LinkPreviewLayout
A Link Preview Layout Library for Android (@inspired from https://raw.githubusercontent.com/ponnamkarthik/RichLinkPreview)

> Sample Image
<img src="https://github.com/hexdecimal16/LinkPreviewLayout/blob/master/screenshots/device-2020-09-04-050031.png" width="300" alt="ScreenShot">


#### Import using Gradle

~~~gradle
implementation 'com.github.hexdecimal16:LinkPreviewLayout:1.0.0'
~~~

#### To implement existing layout using XML

Add below code in activity_main.xml

~~~xml

<!--default view or whatsapp -->
<com.dhairytripathi.linkpreviewlayout.LinkPreviewLayou
    android:id="@+id/linkPreview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
    
<!-- Telegram -->
<com.dhairytripathi.linkpreviewlayout.LinkPreviewLayoutTelegram
    android:id="@+id/linkPreview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
    
<!-- Skype -->
<com.dhairytripathi.linkpreviewlayout.LinkPreviewLayoutSkype
    android:id="@+id/linkPreview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
    
<!-- Twitter -->
<com.dhairytripathi.linkpreviewlayout.LinkPreviewLayoutTwitter
    android:id="@+id/linkPreview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
~~~

In your MainActivity.java add below code

~~~java
public class MainActivity extends AppCompatActivity {
    
    LinkPreviewLayout linkPreviewLayout;; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ...
        // 
        linkPreviewLayout = findViewById(R.id.linkPreview);
        
        
        
        linkPreviewLayout.setText("https://stackoverflow.com", new ViewListener() {
            
            @Override
            public void onSuccess(boolean status) {
                
            }
            
            @Override
            public void onError(Exception e) {
                
            }
        });
        
    }
}
~~~


~~~java
LinkPreviewLayout linkPreviewLayout;
LinkPreviewLayoutTelegram linkPreviewLayoutTelegram;
LinkPreviewLayoutSkype linkPreviewLayoutSkype;
LinkPreviewLayoutTwitter linkPreviewLayoutTwitter;

//Set Link is same as default

~~~

> **OR**

#### If you want to implement your own layout.

~~~java
private MetaData data;

LinkPreviewLayout linkPreviewLayout = new linkPreviewLayout(new ResponseListener() {
    @Override
    public void onData(MetaData metaData) {
        data = metaData;
       
        //Implement your Layout
    }
    
    @Override
    public void onError(Exception e) {
        //handle error
    }
});
~~~

> if you want to set obtained meta data to view

~~~java

linkPreviewLayout.setLinkFromMeta(metaData)

or

MetaData metaData = new MetaData();
metaData.setTitle("Title");
metaData.setDescription("Custom Meta Data");
metaData.setFavicon("http://favicon url");
metaData.setImageurl("http://image url");
metaData.setSitename("Custom Meta data site");

linkPreviewLayout.setLinkFromMeta(metaData);


~~~


> Set your own CickListener

~~~java

//at first disable default click
linkPreviewLayout.setDefaultClickListener(false);

//set your own click listener
linkPreviewLayout.setClickListener(new LinkPreviewLayoutListener() {
    @Override
    public void onClicked(View view, MetaData meta) {
        //do stuff
        Toast.makeText(getApplicationContext(), meta.getTitle(), Toast.LENGTH_SHORT).show();
    }
});

~~~

> MetaData

```java
metaData.getTitle();

metaData.getImageurl();

metaData.getDescription();

metaData.getSitename();

metaData.getUrl();

metaData.getMediatype();
```

