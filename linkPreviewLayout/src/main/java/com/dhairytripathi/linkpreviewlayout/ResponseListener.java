package com.dhairytripathi.linkpreviewlayout;

import com.dhairytripathi.linkpreviewlayout.model.MetaData;

public interface ResponseListener {

    void onData(MetaData metaData);

    void onError(Exception e);
}
