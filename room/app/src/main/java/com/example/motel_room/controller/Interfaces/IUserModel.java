package com.example.motel_room.controller.Interfaces;


import com.example.motel_room.model.UserModel;

public interface IUserModel {
    public void getListUsers(UserModel valueUser);
    public void setProgressBarLoadMore();
    public void setQuantityTop(int quantity);
    public void setSumHostsAdminView(long quantity);
}
