package com.example.motel_room.controller.Interfaces;

public interface ICallBackPostRoom {
    public void onMsgFromFragToPostRoom(String sender,boolean isComplete);
    public boolean isStepOneComplete();
    public boolean isStepTwoComplete();
    public boolean isStepTreeComplete();
    public void setCurrentPage(int position);
}
