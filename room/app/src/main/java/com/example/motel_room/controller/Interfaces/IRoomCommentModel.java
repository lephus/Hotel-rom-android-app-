package com.example.motel_room.controller.Interfaces;

import com.example.motel_room.model.CommentModel;

import java.util.List;

public interface IRoomCommentModel {
    public void getListRoomComments(CommentModel valueComment);
    public void makeToast(String message);
    public void setView();
    public void setLinearLayoutTopAllComments(List<CommentModel> listCommentsModel);
    public void setProgressBarLoadMore();
    public void setQuantityComments(int quantity);
}
