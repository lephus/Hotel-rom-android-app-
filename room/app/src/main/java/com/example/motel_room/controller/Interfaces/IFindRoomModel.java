package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.model.FindRoomModel;

public interface IFindRoomModel {
    public void getListFindRoom(FindRoomModel valueRoom);

    public void getSuccessNotify(int quantity);

    public void setProgressBarLoadMore();

    public void addSuccessNotify();
}
