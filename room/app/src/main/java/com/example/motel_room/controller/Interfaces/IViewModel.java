package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.model.ViewModel;

import java.util.List;

public interface IViewModel {
    public void getViewInfo(ViewModel viewModel);
    public void setView();
    public void setLinearLayoutTopAllView(List<ViewModel> listViewsModel);
    public void setProgressBarLoadMore();
    public void setQuantityViews(int quantity);
}
