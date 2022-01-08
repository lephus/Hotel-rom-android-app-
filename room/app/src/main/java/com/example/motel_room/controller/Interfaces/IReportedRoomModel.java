package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.model.ReportedRoomModel;

public interface IReportedRoomModel {
    public void makeToast(String message);
    public void getListReportRooms(ReportedRoomModel reportedRoomModel);
    public void setProgressBarLoadMore();
    public void setQuantityTop(int quantity);
    public void setQuantityLoadMore(int quantityLoaded);
}
