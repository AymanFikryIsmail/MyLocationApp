package com.objects.mylocation.mylocation.presenter.searchbylocation;

import android.content.Context;

import com.objects.mylocation.mylocation.view.ui.searchbylocation.SearchView;

/**
 * Created by ayman on 2019-05-16.
 */

public class SearchPresenterImpl implements SearchPresenter {


    private SearchView view;
    private Context context;

    public SearchPresenterImpl(SearchView view) {
        this.view = view;
        this.context = (Context) view;

    }


}
