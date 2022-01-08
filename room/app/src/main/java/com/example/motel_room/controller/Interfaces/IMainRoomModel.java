package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.model.RoomModel;

public interface IMainRoomModel {
    public void getListMainRoom(RoomModel valueRoom);
    public void makeToast(String message);
    public void setIconFavorite(int iconRes);
    public void setButtonLoadMoreVerifiedRooms();
    public void setProgressBarLoadMore();
    public void setQuantityTop(int quantity);
    public void setQuantityLoadMore(int quantityLoaded);
    public void setSumRoomsAdminView(long quantity);
}
