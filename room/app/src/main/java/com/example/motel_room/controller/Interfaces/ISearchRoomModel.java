package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.model.RoomModel;

public interface ISearchRoomModel {
    public void sendDataRoom(RoomModel roomModel,boolean ishadFound);
    public void setProgressBarLoadMore();
    public void setQuantityTop(int quantity);
}
