package com.hometasks.view.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.hometasks.view.ui.ViewHolder;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
