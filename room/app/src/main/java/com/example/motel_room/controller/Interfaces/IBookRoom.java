package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.model.UserModel;


public interface IBookRoom {
    public void getListBookRoom(UserModel userModel);
    public void makeToast(String message);
    public void setView();
}
