package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.domain.myFilter;


public interface ICallBackSearchView {
    public void addFilter(myFilter filter);
    public void replaceFilter(myFilter filter);
    public void removeFilter(myFilter filter);
}
