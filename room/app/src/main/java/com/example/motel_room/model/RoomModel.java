package com.example.motel_room.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import android.util.Log;

import com.example.motel_room.domain.FilePath;
import com.example.motel_room.controller.Interfaces.ICallBackFromAddRoom;
import com.example.motel_room.controller.Interfaces.IInfoOfAllRoomUser;
import com.example.motel_room.controller.Interfaces.IMainRoomModel;
import com.example.motel_room.controller.Interfaces.IMapViewModel;
import com.example.motel_room.controller.Interfaces.IPostRoomUpdateModel;
import com.example.motel_room.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.RequestCreator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class RoomModel implements Parcelable {
    String describe, name, owner, timeCreated;
    long currentNumber, maxNumber;
    double length, width, rentalCosts;
    boolean authentication;
    boolean gender;
    boolean approve;
    String apartmentNumber, county, street, ward, city;
    String typeID;
    String roomType;
    String compressionImage;

    //????nh gi?? c???a ng?????i xem tr???
    long medium;
    long great;
    long prettyGood;
    long bad;

    //M?? Ph??ng tr???
    String roomID;

    //Ch??? ph??ng tr???
    UserModel roomOwner;

    // L??u dnh s??ch ti???n nghi ph??ng tr???
    List<ConvenientModel> listConvenientRoom;

    // L??u dnh s??ch gi?? ph??ng tr???
    List<RoomPriceModel> listRoomPrice;

    //L??u m???ng t??n h??nh tr??n firebase
    List<String> listImageRoom;

    RequestCreator compressionImageFit;

    public static List<String> ListFavoriteRoomsID = new ArrayList<>();

    //L??u m???ng comment c???a ph??ng tr???
    List<CommentModel> listCommentRoom;

    //L??u l?????t xem
    int views;

    protected RoomModel(Parcel in) {

        describe = in.readString();
        name = in.readString();
        owner = in.readString();
        timeCreated = in.readString();
        currentNumber = in.readLong();
        maxNumber = in.readLong();
        length = in.readDouble();
        width = in.readDouble();
        rentalCosts = in.readDouble();
        authentication = in.readByte() != 0;
        gender = in.readByte() != 0;
        typeID = in.readString();
        roomType = in.readString();
        medium = in.readLong();
        great = in.readLong();
        prettyGood = in.readLong();
        bad = in.readLong();
        roomID = in.readString();
        apartmentNumber = in.readString();
        county = in.readString();
        street = in.readString();
        ward = in.readString();
        city = in.readString();
        roomOwner = in.readParcelable(UserModel.class.getClassLoader());
        listImageRoom = in.createStringArrayList();

        listConvenientRoom = new ArrayList<ConvenientModel>();
        in.readTypedList(listConvenientRoom, ConvenientModel.CREATOR);


        listRoomPrice = new ArrayList<>();
        in.readTypedList(listRoomPrice, RoomPriceModel.CREATOR);

        listCommentRoom = new ArrayList<CommentModel>();
        in.readTypedList(listCommentRoom, CommentModel.CREATOR);
    }

    public static final Creator<RoomModel> CREATOR = new Creator<RoomModel>() {
        @Override
        public RoomModel createFromParcel(Parcel in) {
            return new RoomModel(in);
        }

        @Override
        public RoomModel[] newArray(int size) {
            return new RoomModel[size];
        }
    };

    public String getCompressionImage() {
        return compressionImage;
    }

    public void setCompressionImage(String compressionImage) {
        this.compressionImage = compressionImage;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public UserModel getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(UserModel roomOwner) {
        this.roomOwner = roomOwner;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public List<ConvenientModel> getListConvenientRoom() {
        return listConvenientRoom;
    }

    public void setListConvenientRoom(List<ConvenientModel> listConvenientRoom) {
        this.listConvenientRoom = listConvenientRoom;
    }

    public List<RoomPriceModel> getListRoomPrice() {
        return listRoomPrice;
    }

    public void setListRoomPrice(List<RoomPriceModel> listRoomPrice) {
        this.listRoomPrice = listRoomPrice;
    }

    public List<CommentModel> getListCommentRoom() {
        return listCommentRoom;
    }

    public void setListCommentRoom(List<CommentModel> listCommentRoom) {
        this.listCommentRoom = listCommentRoom;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public long getMedium() {
        return medium;
    }

    public void setMedium(long medium) {
        this.medium = medium;
    }

    public long getGreat() {
        return great;
    }

    public void setGreat(long great) {
        this.great = great;
    }

    public long getPrettyGood() {
        return prettyGood;
    }

    public void setPrettyGood(long prettyGood) {
        this.prettyGood = prettyGood;
    }

    public long getBad() {
        return bad;
    }

    public void setBad(long bad) {
        this.bad = bad;
    }

    public List<String> getListImageRoom() {
        return listImageRoom;
    }

    public void setListImageRoom(List<String> listImageRoom) {
        this.listImageRoom = listImageRoom;
    }

    public RequestCreator getCompressionImageFit() {
        return compressionImageFit;
    }

    public void setCompressionImageFit(RequestCreator compressionImageFit) {
        this.compressionImageFit = compressionImageFit;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    //Bi???n l??u root c???a firebase, l??u ?? ????? bi???n l?? private
    private DatabaseReference nodeRoot;

    //L??u ?? ph???i c?? h??m kh???i t???o r???ng
    public RoomModel() {
        //Tr??? v??? node root c???a database
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(long currentNumber) {
        this.currentNumber = currentNumber;
    }

    public long getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(long maxNumber) {
        this.maxNumber = maxNumber;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getRentalCosts() {
        return rentalCosts;
    }

    public void setRentalCosts(double rentalCosts) {
        this.rentalCosts = rentalCosts;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    private DataSnapshot dataRoot;
    private DataSnapshot dataNode;
    private List<String> listRoomsID = new ArrayList<>();

    public void ListRoom(final IMainRoomModel mainRoomModelInterface, int quantityRoomToLoad, int quantityRoomLoaded) {
        Query nodeRoomApprove = nodeRoot.child("Room")
                .orderByChild("approve")
                .equalTo(true);

        //T???o listen cho nodeRoomApprove
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataNode = dataSnapshot;

                // T???o listen cho nodeRoot.
                ValueEventListener valueSpecialListRoomEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataRoot = dataSnapshot;

                        List<RoomModel> listRoomsModel = new ArrayList<>();

                        //Duy???t h???t trong danh s??ch ph??ng tr???
                        for (DataSnapshot valueRoom : dataNode.getChildren()) {
                            //listRoomsID.add(valueRoom.getKey());

                            //L???c ra danh s??ch favorite rooms.
                            RoomModel roomModel = valueRoom.getValue(RoomModel.class);
                            roomModel.setRoomID(valueRoom.getKey());

                            listRoomsModel.add(roomModel);
                        }

                        sortListRooms(listRoomsModel);

                        for (RoomModel roomModel : listRoomsModel) {
                            listRoomsID.add(roomModel.getRoomID());
                        }

                        mainRoomModelInterface.setQuantityTop(listRoomsID.size());

                        getPartListRoom(dataRoot, mainRoomModelInterface, quantityRoomToLoad, quantityRoomLoaded);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                //G??n s??? ki???n listen cho nodeRoot
                nodeRoot.addListenerForSingleValueEvent(valueSpecialListRoomEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        if (dataNode != null) {
            if (dataRoot != null) {
                //Th??m d??? li???u v?? g???i v??? l???i UI
                getPartListRoom(dataRoot, mainRoomModelInterface, quantityRoomToLoad, quantityRoomLoaded);
            }
        } else {
            //G??n s??? ki???n listen cho nodeRoomApprove
            nodeRoomApprove.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    public void ListRoomWaitForApproval(final IMainRoomModel mainRoomModelInterface, int quantityRoomToLoad, int quantityRoomLoaded) {
        Query nodeRoomApprove = nodeRoot.child("Room")
                .orderByChild("approve")
                .equalTo(false);

        //T???o listen cho nodeRoomApprove
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataNode = dataSnapshot;

                // T???o listen cho nodeRoot.
                ValueEventListener valueSpecialListRoomEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataRoot = dataSnapshot;

                        //Duy???t h???t trong danh s??ch ph??ng tr???
                        for (DataSnapshot valueRoom : dataNode.getChildren()) {
                            listRoomsID.add(valueRoom.getKey());
                        }

                        mainRoomModelInterface.setQuantityTop(listRoomsID.size());

                        getPartListRoom(dataRoot, mainRoomModelInterface, quantityRoomToLoad, quantityRoomLoaded);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                //G??n s??? ki???n listen cho nodeRoot
                nodeRoot.addListenerForSingleValueEvent(valueSpecialListRoomEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        if (dataNode != null) {
            if (dataRoot != null) {
                //Th??m d??? li???u v?? g???i v??? l???i UI
                getPartListRoom(dataRoot, mainRoomModelInterface, quantityRoomToLoad, quantityRoomLoaded);
            }
        } else {
            //G??n s??? ki???n listen cho nodeRoomApprove
            nodeRoomApprove.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    private void getPartListRoom(DataSnapshot dataSnapshot, IMainRoomModel mainRoomModelInterface, int quantityRoomToLoad, int quantityRoomLoaded) {
        int i = 0;

        //Duy???t h???t trong danh s??ch ph??ng tr???
        for (String RoomID : listRoomsID) {

            // N???u ???? l???y ????? s??? l?????ng rooms ti???p theo th?? ra kh???i v??ng l???p
            if (i == quantityRoomToLoad) {
                break;
            }

            // B??? qua nh???ng room ???? load
            if (i < quantityRoomLoaded) {
                i++;
                continue;
            }

            i++;

            //Duy???t v??o room c???n l???y d??? li???u
            DataSnapshot dataSnapshotValueRoom = dataRoot.child("Room").child(RoomID);

            //L???y ra gi?? tr??? ??p ki???u qua ki???u RoomModel
            RoomModel roomModel = dataSnapshotValueRoom.getValue(RoomModel.class);
            //Set m?? ph??ng tr???
            roomModel.setRoomID(dataSnapshotValueRoom.getKey());

            //Set lo???i ph??ng tr???
            String tempType = dataSnapshot.child("RoomTypes")
                    .child(roomModel.getTypeID())
                    .getValue(String.class);

            roomModel.setRoomType(tempType);

            //Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

            //Duy???t v??o node RoomImages tr??n firebase v?? duy???t v??o node c?? m?? room t????ng ???ng
            DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(dataSnapshotValueRoom.getKey());
            List<String> tempImageList = new ArrayList<String>();
            //Duy??t t???t c??? c??c gi?? tr??? c???a node t????ng ???ng
            for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                tempImageList.add(valueImage.getValue(String.class));
            }

            //set m???ng h??nh v??o list
            roomModel.setListImageRoom(tempImageList);

            //Th??m v??o h??nh dung l?????ng th???p c???a ph??ng tr???
            DataSnapshot dataSnapshotComPress = dataSnapshot.child("RoomCompressionImages").child(dataSnapshotValueRoom.getKey());
            //Ki???m tra n???u c?? d??? li???u
            if (dataSnapshotComPress.getChildrenCount() > 0) {
                for (DataSnapshot valueCompressionImage : dataSnapshotComPress.getChildren()) {
                    roomModel.setCompressionImage(valueCompressionImage.getValue(String.class));
                }
            } else {
                roomModel.setCompressionImage(tempImageList.get(0));
            }

            //End Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

            //Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

            DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(dataSnapshotValueRoom.getKey());
            List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
            //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
            for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                commentModel.setCommentID(CommentValue.getKey());
                //Duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n
                UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                tempUser.setUserID(commentModel.getUser());
                commentModel.setUserComment(tempUser);
                //End duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n

                tempCommentList.add(commentModel);
            }

            roomModel.setListCommentRoom(tempCommentList);

            //End Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

            //Th??m danh s??ch ti???n nghi c???a ph??ng tr???

            DataSnapshot dataSnapshotConvenientRoom = dataSnapshot.child("RoomConvenients").child(dataSnapshotValueRoom.getKey());
            List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
            //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
            for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                String convenientId = valueConvenient.getValue(String.class);
                ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                convenientModel.setConvenientID(convenientId);

                tempConvenientList.add(convenientModel);
            }

            roomModel.setListConvenientRoom(tempConvenientList);

            //End Th??m danh s??ch ti???n nghi c???a ph??ng tr???

            //Th??m danh s??ch gi?? c???a ph??ng tr???

            DataSnapshot dataSnapshotRoomPrice = dataSnapshot.child("RoomPrice").child(dataSnapshotValueRoom.getKey());
            List<RoomPriceModel> tempRoomPriceList = new ArrayList<RoomPriceModel>();
            //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
            for (DataSnapshot valueRoomPrice : dataSnapshotRoomPrice.getChildren()) {
                String roomPriceId = valueRoomPrice.getKey();
                double price = valueRoomPrice.getValue(double.class);

                if (roomPriceId.equals("IDRPT4")) {
                    continue;
                }
                RoomPriceModel roomPriceModel = dataSnapshot.child("RoomPriceType").child(roomPriceId).getValue(RoomPriceModel.class);
                roomPriceModel.setRoomPriceID(roomPriceId);
                roomPriceModel.setPrice(price);

                tempRoomPriceList.add(roomPriceModel);
            }

            roomModel.setListRoomPrice(tempRoomPriceList);

            //End Th??m danh s??ch gi?? c???a ph??ng tr???

            //Th??m th??ng tin ch??? s??? h???u cho ph??ng tr???
            UserModel tempUser = dataSnapshot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
            tempUser.setUserID(roomModel.getOwner());
            roomModel.setRoomOwner(tempUser);

            //End th??m th??ng tin ch??? s??? h???u cho ph??ng tr???

            //Th??m v??o l?????t xem c???a ph??ng tr???
            int tempViews = (int) dataSnapshot.child("RoomViews").child(dataSnapshotValueRoom.getKey()).getChildrenCount();
            roomModel.setViews(tempViews);
            //End th??m v??o l?????t xem c???a ph??ng tr???

            //K??ch ho???t interface
            mainRoomModelInterface.getListMainRoom(roomModel);
        }

        // ???n progress bar load more.
        mainRoomModelInterface.setProgressBarLoadMore();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(describe);
        dest.writeString(name);
        dest.writeString(owner);
        dest.writeString(timeCreated);
        dest.writeLong(currentNumber);
        dest.writeLong(maxNumber);
        dest.writeDouble(length);
        dest.writeDouble(width);
        dest.writeDouble(rentalCosts);
        dest.writeByte((byte) (authentication ? 1 : 0));
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(typeID);
        dest.writeString(roomType);
        dest.writeLong(medium);
        dest.writeLong(great);
        dest.writeLong(prettyGood);
        dest.writeLong(bad);
        dest.writeString(roomID);

        //update 21/4/2019
        dest.writeString(apartmentNumber);
        dest.writeString(county);
        dest.writeString(street);
        dest.writeString(ward);
        dest.writeString(city);
        dest.writeParcelable(roomOwner, flags);
        //end update 21/4/2019

        dest.writeStringList(listImageRoom);
        dest.writeTypedList(listConvenientRoom);
        // Linh th??m
        dest.writeTypedList(listRoomPrice);
        dest.writeTypedList(listCommentRoom);
    }


    //H??m th??m ph??ng m???i |Th??m theo th??? t??? th??m h??nh tr?????c v?? th??m v??o node room sau c??ng ????? tr??nh l???i x???y ra
    public void addRoom(String UID, RoomModel roomModel, List<String> listConvenient, List<String> listPathImage
            , float electricBill, float warterBill, float InternetBill, float parkingBill, ICallBackFromAddRoom iCallBackFromAddRoom, Context context) {

        //L???y ra node room
        DatabaseReference nodeRoom = nodeRoot.child("Room");
        //L???y Key push ?????ng v??o firebase
        String RoomID = nodeRoom.push().getKey();

        //T???i h??nh l??n tr?????c sau khi ho??n t???t t???i h??nh m???i th??m v??o c??c th??ng tin c???n thi???t
        //T???i h??nh l??n
        final int[] count = {0};
        for (String pathImage : listPathImage) {

            //L???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh
            DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
            String date = df.format(Calendar.getInstance().getTime());
            //End l???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh

            //L???y ???????ng d???n Uri c???a h??nh
            Uri file = Uri.parse(pathImage);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("Images/" + date + file.getLastPathSegment());

            //T???i h??nh l??n b???ng h??m putFile
            storageReference.putFile(file).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //L???y URL h??nh m???i upload
                    String dowloadURL = uri.toString();
                    //Push h??nh v??o danh s??ch h??nh t????ng ???ng v???i room
                    nodeRoot.child("RoomImages").child(RoomID).push().setValue(dowloadURL);
                    count[0]++;
                    if (count[0] == listPathImage.size()) {
                        //Th??m v??o th??ng tin kh??c
                        //Th??m danh s??ch ti???n ??ch
                        for (String dataConvenient : listConvenient) {
                            nodeRoot.child("RoomConvenients").child(RoomID).push().setValue(dataConvenient);
                        }
                        //End th??m danh s??ch ti???n ??ch

                        //Th??m chi ti???t c??c gi?? ph??ng
                        addDetailRoomPrice(RoomID, electricBill, warterBill, InternetBill, parkingBill, (float) roomModel.getRentalCosts());
                        //End th??m chi ti???t c??c gi?? ph??ng

                        //Th??m v??o th??ng tin ph??ng
                        nodeRoom.child(RoomID).setValue(roomModel);

                        //Th??m v??o node RoomLocation ????? filter

                        //C???t b??? P. tr?????c ph?????ng
                        String SplitWarn = roomModel.getWard().substring(2);

                        nodeRoot.child("LocationRoom").child(roomModel.getCounty())
                                .child(SplitWarn)
                                .child(roomModel.getStreet())
                                .push().setValue(RoomID);

                        //End th??m v??o node RoomLocation ????? filter

                        //????ng h??nh ???nh ???? n??n ??? trang ?????u ????? load nhanh h??n
                        //T???o ???????ng d???n file

                        //L???y ra ???????ng d???n file
                        File image_filePath = null;
                        String path = FilePath.getPath(context, Uri.parse(listPathImage.get(0)));
                        if (path != null) {
                            //T???o file t????ng ???ng v???i ???????ng d???n ????
                            image_filePath = new File(path);
                        }
                        if (image_filePath != null) {
                            //T???o Bitmap ????? n??n ???nh v?? t???i l??n
                            Bitmap image_bitmap = null;
                            try {

                                image_bitmap = new Compressor(context)
                                        .setMaxHeight(320)
                                        .setMaxWidth(240)
                                        .setQuality(50)
                                        .compressToBitmap(image_filePath);
                                //T???o m???ng byte ????? ????? d??? li???u t??? bitmap sang ????? up l??n storage
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                //Chuy???n ???nh v??? JPG v?? ????? v??o m???ng byte
                                image_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

                                //????? d??? li???u ra m???ng byte
                                final byte[] image_byte = byteArrayOutputStream.toByteArray();

                                //L???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh
                                DateFormat df2 = new SimpleDateFormat("ddMMyyyyHHmmss");
                                String date2 = df2.format(Calendar.getInstance().getTime());
                                //End l???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh

                                //T???o ???????ng d???n ?????n thu m???c upload h??nh
                                StorageReference CompressStorage = FirebaseStorage.getInstance().getReference().child("CompressionImages/" + roomModel.getOwner() + date2 + ".jpg");

                                //Up load h??nh
                                UploadTask uploadTask = CompressStorage.putBytes(image_byte);
                                uploadTask.addOnSuccessListener(taskSnapshot1 -> CompressStorage.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                    //L???y ra ???????ng d???n download
                                    String ComPressDowloadURL = uri1.toString();
                                    //Th??m v??o trong node Room
                                    nodeRoot.child("RoomCompressionImages").child(RoomID).push().setValue(ComPressDowloadURL);

                                    iCallBackFromAddRoom.stopProgess(true);
                                }));

                                // thay ?????i gi?? tr??? owner ??? node Users

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
//                                nodeRoot.child("Users").child(UID).child("owner").setValue(true);
                    }
                }
            }));
            iCallBackFromAddRoom.stopProgess(true);
        }
        //End t???i h??nh l??n

        //push v??o node room

    }

    //H??m th??m v??o chi ti???t gi?? c??? c???a ph??ng
    private void addDetailRoomPrice(String roomID, float electricBill, float warterBill, float InternetBill, float parkingBill, float roomBill) {
        //Th??m ti???n n?????c
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT0").setValue(warterBill);
        //Th??m ti???n ??i???n
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT3").setValue(electricBill);
        //Th??m ti???n m???ng
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT1").setValue(InternetBill);
        //Th??m ti???n gi??? xe
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT2").setValue(parkingBill);
        //Th??m ti???n ph??ng
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT4").setValue(roomBill);
    }

    public void SendData(List<String> ListRoomID, IMainRoomModel mainRoomModelInterface) {
        //T???o listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (String RoomID : ListRoomID) {
                    //Duy???t v??o room c???n l???y d??? li???u
                    DataSnapshot dataSnapshotValueRoom = dataSnapshot.child("Room").child(RoomID);

                    //L???y ra gi?? tr??? ??p ki???u qua ki???u RoomModel
                    RoomModel roomModel = dataSnapshotValueRoom.getValue(RoomModel.class);
                    Log.d("check", RoomID);

                    roomModel.setRoomID(RoomID);

                    //Set lo???i ph??ng tr???
                    String tempType = dataSnapshot.child("RoomTypes")
                            .child(roomModel.getTypeID())
                            .getValue(String.class);

                    roomModel.setRoomType(tempType);

                    //Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

                    //Duy???t v??o node RoomImages tr??n firebase v?? duy???t v??o node c?? m?? room t????ng ???ng
                    DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(RoomID);
                    List<String> tempImageList = new ArrayList<String>();
                    //Duy??t t???t c??? c??c gi?? tr??? c???a node t????ng ???ng
                    for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                        tempImageList.add(valueImage.getValue(String.class));
                    }

                    //set m???ng h??nh v??o list
                    roomModel.setListImageRoom(tempImageList);

                    //End Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

                    //Th??m v??o h??nh dung l?????ng th???p c???a ph??ng tr???
                    DataSnapshot dataSnapshotComPress = dataSnapshot.child("RoomCompressionImages").child(RoomID);
                    //Ki???m tra n???u c?? d??? li???u
                    if (dataSnapshotComPress.getChildrenCount() > 0) {
                        for (DataSnapshot valueCompressionImage : dataSnapshotComPress.getChildren()) {
                            roomModel.setCompressionImage(valueCompressionImage.getValue(String.class));
                        }
                    } else {
                        roomModel.setCompressionImage(tempImageList.get(0));
                    }

                    //Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

                    DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(RoomID);
                    List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
                    //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                    for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                        CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                        commentModel.setCommentID(CommentValue.getKey());
                        //Duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n
                        UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                        tempUser.setUserID(commentModel.getUser());
                        commentModel.setUserComment(tempUser);
                        //End duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n

                        tempCommentList.add(commentModel);
                    }

                    roomModel.setListCommentRoom(tempCommentList);

                    //End Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

                    //Th??m danh s??ch ti???n nghi c???a ph??ng tr???

                    DataSnapshot dataSnapshotConvenientRoom = dataSnapshot.child("RoomConvenients").child(RoomID);
                    List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                    //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                    for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                        String convenientId = valueConvenient.getValue(String.class);
                        ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                        convenientModel.setConvenientID(convenientId);

                        tempConvenientList.add(convenientModel);
                    }

                    roomModel.setListConvenientRoom(tempConvenientList);

                    //End Th??m danh s??ch ti???n nghi c???a ph??ng tr???

                    //Th??m danh s??ch gi?? c???a ph??ng tr???

                    DataSnapshot dataSnapshotRoomPrice = dataSnapshot.child("RoomPrice").child(RoomID);
                    List<RoomPriceModel> tempRoomPriceList = new ArrayList<RoomPriceModel>();
                    //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                    for (DataSnapshot valueRoomPrice : dataSnapshotRoomPrice.getChildren()) {
                        String roomPriceId = valueRoomPrice.getKey();
                        double price = valueRoomPrice.getValue(double.class);

                        if (roomPriceId.equals("IDRPT4")) {
                            continue;
                        }
                        RoomPriceModel roomPriceModel = dataSnapshot.child("RoomPriceType").child(roomPriceId).getValue(RoomPriceModel.class);
                        roomPriceModel.setRoomPriceID(roomPriceId);
                        roomPriceModel.setPrice(price);

                        tempRoomPriceList.add(roomPriceModel);
                    }

                    roomModel.setListRoomPrice(tempRoomPriceList);

                    //End Th??m danh s??ch gi?? c???a ph??ng tr???

                    //Th??m th??ng tin ch??? s??? h???u cho ph??ng tr???
                    UserModel tempUser = dataSnapshot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
                    tempUser.setUserID(roomModel.getOwner());
                    roomModel.setRoomOwner(tempUser);

                    //End th??m th??ng tin ch??? s??? h???u cho ph??ng tr???

                    //Th??m v??o l?????t xem c???a ph??ng tr???
                    int tempViews = (int) dataSnapshot.child("RoomViews").child(RoomID).getChildrenCount();
                    roomModel.setViews(tempViews);
                    //End th??m v??o l?????t xem c???a ph??ng tr???

                    //K??ch ho???t interface
                    mainRoomModelInterface.getListMainRoom(roomModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //G??n s??? ki???n listen cho nodeRoot
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    public void SendData_NoLoadMore(List<String> ListRoomID, IMapViewModel mapViewModelInterface) {
        //T???o listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (String RoomID : ListRoomID) {
                    //Duy???t v??o room c???n l???y d??? li???u
                    DataSnapshot dataSnapshotValueRoom = dataSnapshot.child("Room").child(RoomID);

                    //L???y ra gi?? tr??? ??p ki???u qua ki???u RoomModel
                    RoomModel roomModel = dataSnapshotValueRoom.getValue(RoomModel.class);
                    Log.d("check", RoomID);

                    roomModel.setRoomID(RoomID);

                    //Set lo???i ph??ng tr???
                    String tempType = dataSnapshot.child("RoomTypes")
                            .child(roomModel.getTypeID())
                            .getValue(String.class);

                    roomModel.setRoomType(tempType);

                    //Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

                    //Duy???t v??o node RoomImages tr??n firebase v?? duy???t v??o node c?? m?? room t????ng ???ng
                    DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(RoomID);
                    List<String> tempImageList = new ArrayList<String>();
                    //Duy??t t???t c??? c??c gi?? tr??? c???a node t????ng ???ng
                    for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                        tempImageList.add(valueImage.getValue(String.class));
                    }

                    //set m???ng h??nh v??o list
                    roomModel.setListImageRoom(tempImageList);

                    //End Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

                    //Th??m v??o h??nh dung l?????ng th???p c???a ph??ng tr???
                    DataSnapshot dataSnapshotComPress = dataSnapshot.child("RoomCompressionImages").child(RoomID);
                    //Ki???m tra n???u c?? d??? li???u
                    if (dataSnapshotComPress.getChildrenCount() > 0) {
                        for (DataSnapshot valueCompressionImage : dataSnapshotComPress.getChildren()) {
                            roomModel.setCompressionImage(valueCompressionImage.getValue(String.class));
                        }
                    } else {
                        roomModel.setCompressionImage(tempImageList.get(0));
                    }

                    //Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

                    DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(RoomID);
                    List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
                    //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                    for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                        CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                        commentModel.setCommentID(CommentValue.getKey());
                        //Duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n
                        UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                        tempUser.setUserID(commentModel.getUser());
                        commentModel.setUserComment(tempUser);
                        //End duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n

                        tempCommentList.add(commentModel);
                    }

                    roomModel.setListCommentRoom(tempCommentList);

                    //End Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

                    //Th??m danh s??ch ti???n nghi c???a ph??ng tr???

                    DataSnapshot dataSnapshotConvenientRoom = dataSnapshot.child("RoomConvenients").child(RoomID);
                    List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                    //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                    for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                        String convenientId = valueConvenient.getValue(String.class);
                        ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                        convenientModel.setConvenientID(convenientId);

                        tempConvenientList.add(convenientModel);
                    }

                    roomModel.setListConvenientRoom(tempConvenientList);

                    //End Th??m danh s??ch ti???n nghi c???a ph??ng tr???

                    //Th??m danh s??ch gi?? c???a ph??ng tr???

                    DataSnapshot dataSnapshotRoomPrice = dataSnapshot.child("RoomPrice").child(RoomID);
                    List<RoomPriceModel> tempRoomPriceList = new ArrayList<RoomPriceModel>();
                    //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                    for (DataSnapshot valueRoomPrice : dataSnapshotRoomPrice.getChildren()) {
                        String roomPriceId = valueRoomPrice.getKey();
                        double price = valueRoomPrice.getValue(double.class);

                        if (roomPriceId.equals("IDRPT4")) {
                            continue;
                        }
                        RoomPriceModel roomPriceModel = dataSnapshot.child("RoomPriceType").child(roomPriceId).getValue(RoomPriceModel.class);
                        roomPriceModel.setRoomPriceID(roomPriceId);
                        roomPriceModel.setPrice(price);

                        tempRoomPriceList.add(roomPriceModel);
                    }

                    roomModel.setListRoomPrice(tempRoomPriceList);

                    //End Th??m danh s??ch gi?? c???a ph??ng tr???

                    //Th??m th??ng tin ch??? s??? h???u cho ph??ng tr???
                    UserModel tempUser = dataSnapshot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
                    tempUser.setUserID(roomModel.getOwner());
                    roomModel.setRoomOwner(tempUser);

                    //End th??m th??ng tin ch??? s??? h???u cho ph??ng tr???

                    //Th??m v??o l?????t xem c???a ph??ng tr???
                    int tempViews = (int) dataSnapshot.child("RoomViews").child(RoomID).getChildrenCount();
                    roomModel.setViews(tempViews);
                    //End th??m v??o l?????t xem c???a ph??ng tr???

                    //K??ch ho???t interface
                    mapViewModelInterface.getListRoom(roomModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //G??n s??? ki???n listen cho nodeRoot
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    public void ListRoomUser(final IMainRoomModel mainRoomModelInterface, String userID, int quantityRoomToLoad, int quantityRoomLoaded) {
        Query nodeRoomUser = nodeRoot.child("Room")
                .orderByChild("owner")
                .equalTo(userID);

        // T???o listen cho nodeRoomUser.
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataNode = dataSnapshot;

                // T???o listen cho nodeRoot.
                ValueEventListener valueSpecialListRoomEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataRoot = dataSnapshot;

                        for (DataSnapshot userRoomsSnapShot : dataNode.getChildren()) {
                            RoomModel roomModel = userRoomsSnapShot.getValue(RoomModel.class);

                            if (roomModel.isApprove()) {
                                //L???c ra danh s??ch verified rooms.
                                listRoomsID.add(userRoomsSnapShot.getKey());
                            }
                        }

                        // set view
                        mainRoomModelInterface.setQuantityTop(listRoomsID.size());

                        //Th??m d??? li???u v?? g???i v??? l???i UI
                        getPartSpecialListRoom(mainRoomModelInterface, quantityRoomToLoad, quantityRoomLoaded);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                //G??n s??? ki???n listen cho nodeRoot
                nodeRoot.addListenerForSingleValueEvent(valueSpecialListRoomEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        if (dataNode != null) {
            if (dataRoot != null) {
                //Th??m d??? li???u v?? g???i v??? l???i UI
                getPartSpecialListRoom(mainRoomModelInterface, quantityRoomToLoad, quantityRoomLoaded);
            }
        } else {
            //G??n s??? ki???n listen cho nodeRoomUser
            nodeRoomUser.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    public void getListAuthenticationRoomsAtMainView(final IMainRoomModel iMainRoomModel, int quantity) {
        Query nodeRoomOrderByAuthentication = nodeRoot.child("Room")
                .orderByChild("authentication")
                .equalTo(true);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> listAuthenticationRoomID = new ArrayList<String>();

                for (DataSnapshot authenticationRoomsSnapShot : dataSnapshot.getChildren()) {
                    // L???c ra danh s??ch verified rooms.
                    listAuthenticationRoomID.add(authenticationRoomsSnapShot.getKey());

                    // Ch??? show ??? main view s??? l?????ng nh???t ?????nh.
                    if (listAuthenticationRoomID.size() == quantity) {
                        break;
                    }
                }

                //Th??m d??? li???u v?? g???i v??? l???i UI
                SendData(listAuthenticationRoomID, iMainRoomModel);
                if (dataSnapshot.getChildrenCount() > quantity) {
                    iMainRoomModel.setButtonLoadMoreVerifiedRooms();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //G??n s??? ki???n listen cho nodeRoomOrderByAuthentication
        nodeRoomOrderByAuthentication.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getListAuthenticationRoomsAtVerifiedRoomsView(final IMainRoomModel iMainRoomModel, int quantityRoomToLoad,
                                                              int quantityRoomLoaded) {
        Query nodeRoomOrderByAuthentication = nodeRoot.child("Room")
                .orderByChild("authentication")
                .equalTo(true);

        // T???o listen cho nodeRoomOrderByAuthentication.
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataNode = dataSnapshot;

                // T???o listen cho nodeRoot.
                ValueEventListener valueSpecialListRoomEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataRoot = dataSnapshot;

                        for (DataSnapshot authenticationRoomsSnapShot : dataNode.getChildren()) {
                            //L???c ra danh s??ch verified rooms.
                            listRoomsID.add(authenticationRoomsSnapShot.getKey());
                        }

                        // set view
                        iMainRoomModel.setQuantityTop(listRoomsID.size());

                        //Th??m d??? li???u v?? g???i v??? l???i UI
                        getPartSpecialListRoom(iMainRoomModel, quantityRoomToLoad, quantityRoomLoaded);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                //G??n s??? ki???n listen cho nodeRoot
                nodeRoot.addListenerForSingleValueEvent(valueSpecialListRoomEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        if (dataNode != null) {
            if (dataRoot != null) {
                //Th??m d??? li???u v?? g???i v??? l???i UI
                getPartSpecialListRoom(iMainRoomModel, quantityRoomToLoad, quantityRoomLoaded);
            }
        } else {
            //G??n s??? ki???n listen cho nodeRoomOrderByAuthentication
            nodeRoomOrderByAuthentication.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    public void getPartSpecialListRoom(IMainRoomModel mainRoomModelInterface,
                                       int quantityRoomToLoad, int quantityRoomLoaded) {
        int i = 0;

        // Ch???y t??? cu???i list ?????n ?????u list (list truy???n v??o ???? s???p x???p theo th???i gian)
        for (String RoomID : listRoomsID) {

            // N???u ???? l???y ????? s??? l?????ng rooms ti???p theo th?? ra kh???i v??ng l???p
            if (i == quantityRoomToLoad) {
                break;
            }

            // B??? qua nh???ng room ???? load
            if (i < quantityRoomLoaded) {
                i++;
                continue;
            }

            i++;

            //Duy???t v??o room c???n l???y d??? li???u
            DataSnapshot dataSnapshotValueRoom = dataRoot.child("Room").child(RoomID);

            //L???y ra gi?? tr??? ??p ki???u qua ki???u RoomModel
            RoomModel roomModel = dataSnapshotValueRoom.getValue(RoomModel.class);

            roomModel.setRoomID(RoomID);

            //Set lo???i ph??ng tr???
            String tempType = dataRoot.child("RoomTypes")
                    .child(roomModel.getTypeID())
                    .getValue(String.class);

            roomModel.setRoomType(tempType);

            //Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

            //Duy???t v??o node RoomImages tr??n firebase v?? duy???t v??o node c?? m?? room t????ng ???ng
            DataSnapshot dataSnapshotImageRoom = dataRoot.child("RoomImages").child(RoomID);
            List<String> tempImageList = new ArrayList<String>();
            //Duy??t t???t c??? c??c gi?? tr??? c???a node t????ng ???ng
            for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                tempImageList.add(valueImage.getValue(String.class));
            }

            //set m???ng h??nh v??o list
            roomModel.setListImageRoom(tempImageList);

            //End Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

            //Th??m v??o h??nh dung l?????ng th???p c???a ph??ng tr???
            DataSnapshot dataSnapshotComPress = dataRoot.child("RoomCompressionImages").child(RoomID);
            //Ki???m tra n???u c?? d??? li???u
            if (dataSnapshotComPress.getChildrenCount() > 0) {
                for (DataSnapshot valueCompressionImage : dataSnapshotComPress.getChildren()) {
                    roomModel.setCompressionImage(valueCompressionImage.getValue(String.class));
                }
            } else {
                roomModel.setCompressionImage(tempImageList.get(0));
            }

            //Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

            DataSnapshot dataSnapshotCommentRoom = dataRoot.child("RoomComments").child(RoomID);
            List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
            //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
            for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                commentModel.setCommentID(CommentValue.getKey());
                //Duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n
                UserModel tempUser = dataRoot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                tempUser.setUserID(commentModel.getUser());
                commentModel.setUserComment(tempUser);
                //End duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n

                tempCommentList.add(commentModel);
            }

            roomModel.setListCommentRoom(tempCommentList);

            //End Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

            //Th??m danh s??ch ti???n nghi c???a ph??ng tr???

            DataSnapshot dataSnapshotConvenientRoom = dataRoot.child("RoomConvenients").child(RoomID);
            List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
            //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
            for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                String convenientId = valueConvenient.getValue(String.class);
                ConvenientModel convenientModel = dataRoot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                convenientModel.setConvenientID(convenientId);

                tempConvenientList.add(convenientModel);
            }

            roomModel.setListConvenientRoom(tempConvenientList);

            //End Th??m danh s??ch ti???n nghi c???a ph??ng tr???

            //Th??m danh s??ch gi?? c???a ph??ng tr???

            DataSnapshot dataSnapshotRoomPrice = dataRoot.child("RoomPrice").child(RoomID);
            List<RoomPriceModel> tempRoomPriceList = new ArrayList<RoomPriceModel>();
            //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
            for (DataSnapshot valueRoomPrice : dataSnapshotRoomPrice.getChildren()) {
                String roomPriceId = valueRoomPrice.getKey();
                double price = valueRoomPrice.getValue(double.class);

                if (roomPriceId.equals("IDRPT4")) {
                    continue;
                }
                RoomPriceModel roomPriceModel = dataRoot.child("RoomPriceType").child(roomPriceId).getValue(RoomPriceModel.class);
                roomPriceModel.setRoomPriceID(roomPriceId);
                roomPriceModel.setPrice(price);

                tempRoomPriceList.add(roomPriceModel);
            }

            roomModel.setListRoomPrice(tempRoomPriceList);

            //End Th??m danh s??ch gi?? c???a ph??ng tr???

            //Th??m th??ng tin ch??? s??? h???u cho ph??ng tr???
            UserModel tempUser = dataRoot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
            tempUser.setUserID(roomModel.getOwner());
            roomModel.setRoomOwner(tempUser);

            //End th??m th??ng tin ch??? s??? h???u cho ph??ng tr???

            //Th??m v??o l?????t xem c???a ph??ng tr???
            int tempViews = (int) dataRoot.child("RoomViews").child(RoomID).getChildrenCount();
            roomModel.setViews(tempViews);
            //End th??m v??o l?????t xem c???a ph??ng tr???

            //K??ch ho???t interface
            mainRoomModelInterface.getListMainRoom(roomModel);
        }

        // ???n progress bar load more.
        mainRoomModelInterface.setProgressBarLoadMore();
    }

    // detail room d??ng
    public static void getListFavoriteRoomsId(String UID) {

        //T???o listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListFavoriteRoomsID.clear();

                //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                for (DataSnapshot favoriteRoom : dataSnapshot.getChildren()) {
                    String roomId = favoriteRoom.getKey();
                    ListFavoriteRoomsID.add(roomId);
                }
                //End th??m danh s??ch id tr??? y??u th??ch
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //G??n s??? ki???n listen cho nodeRoot
        DatabaseReference node = FirebaseDatabase.getInstance().getReference();
        node.child("FavoriteRooms").child(UID).addValueEventListener(valueEventListener);
    }

    public void getListFavoriteRooms(final IMainRoomModel iMainRoomModel, final String UID, int quantityRoomToLoad, int quantityRoomLoaded) {
        // T???o listen cho nodeFavoriteRooms.
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataNode = dataSnapshot;

                // T???o listen cho nodeRoot.
                ValueEventListener valueSpecialListRoomEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataRoot = dataSnapshot;

                        List<FavoriteRoomModel> listFavoriteRoomsModel = new ArrayList<>();

                        for (DataSnapshot favoriteRoomsSnapShot : dataNode.getChildren()) {
                            //L???c ra danh s??ch favorite rooms.
                            FavoriteRoomModel favoriteRoomModel = favoriteRoomsSnapShot.getValue(FavoriteRoomModel.class);
                            favoriteRoomModel.setRoomId(favoriteRoomsSnapShot.getKey());

                            listFavoriteRoomsModel.add(favoriteRoomModel);
                        }

                        sortListFavorites(listFavoriteRoomsModel);

                        for (FavoriteRoomModel favoriteRoomModel : listFavoriteRoomsModel) {
                            listRoomsID.add(favoriteRoomModel.getRoomId());
                        }

                        // set view
                        iMainRoomModel.setQuantityTop(listRoomsID.size());

                        //Th??m d??? li???u v?? g???i v??? l???i UI
                        getPartSpecialListRoom(iMainRoomModel,
                                quantityRoomToLoad, quantityRoomLoaded);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                //G??n s??? ki???n listen cho nodeRoot
                nodeRoot.addListenerForSingleValueEvent(valueSpecialListRoomEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        if (dataNode != null) {
            if (dataRoot != null) {
                //Th??m d??? li???u v?? g???i v??? l???i UI
                getPartSpecialListRoom(iMainRoomModel,
                        quantityRoomToLoad, quantityRoomLoaded);
            }
        } else {
            //G??n s??? ki???n listen cho nodeFavoriteRooms
            nodeRoot.child("FavoriteRooms").child(UID).addListenerForSingleValueEvent(valueEventListener);
        }
    }

    public void addToFavoriteRooms(String roomId, IMainRoomModel iMainRoomModel, String UID) {
        DatabaseReference nodeFavoriteRooms = FirebaseDatabase.getInstance().getReference().child("FavoriteRooms");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        nodeFavoriteRooms.child(UID).child(roomId).child("time").setValue(date).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    iMainRoomModel.makeToast("???? th??m v??o danh s??ch y??u th??ch");
                    iMainRoomModel.setIconFavorite(R.drawable.ic_favorite_full_white);
                }
            }
        });
    }

    public void removeFromFavoriteRooms(String roomId, IMainRoomModel iMainRoomModel, String UID) {
        DatabaseReference nodeFavoriteRooms = FirebaseDatabase.getInstance().getReference().child("FavoriteRooms");

        nodeFavoriteRooms.child(UID).child(roomId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    iMainRoomModel.makeToast("???? x??a kh???i danh s??ch y??u th??ch");
                    iMainRoomModel.setIconFavorite(R.drawable.ic_favorite_border_white);
                }
            }
        });
    }

    public void SumRooms(IMainRoomModel iMainRoomModel) {
        Query nodeRooms = nodeRoot.child("Room").orderByChild("approve").equalTo(true);

        // T???o listen cho nodeHosts.
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long quantityRooms = dataSnapshot.getChildrenCount();

                // set view
                iMainRoomModel.setSumRoomsAdminView(quantityRooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRooms.addListenerForSingleValueEvent(valueEventListener);
    }

    public void sortListFavorites(List<FavoriteRoomModel> listFavoriteRoomsModel) {
        Collections.sort(listFavoriteRoomsModel, new Comparator<FavoriteRoomModel>() {
            @Override
            public int compare(FavoriteRoomModel favoriteRoomModel1, FavoriteRoomModel favoriteRoomModel2) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = df.parse(favoriteRoomModel1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date date2 = null;
                try {
                    date2 = df.parse(favoriteRoomModel2.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date2.compareTo(date1);
            }
        });
    }

    public void sortListRooms(List<RoomModel> listRoomsModel) {
        Collections.sort(listRoomsModel, new Comparator<RoomModel>() {
            @Override
            public int compare(RoomModel roomModel1, RoomModel roomModel2) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = df.parse(roomModel1.getTimeCreated());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date date2 = null;
                try {
                    date2 = df.parse(roomModel2.getTimeCreated());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date2.compareTo(date1);
            }
        });
    }

    //Th??ng tin v??? t???t c??? c??c ph??ng c???a ng?????i d??ng. t???ng ph??ng, t???ng l?????t xem, t???ng b??nh lu???n
    public void infoOfAllRoomOfUser(String UID, IInfoOfAllRoomUser iInfoOfAllRoomUser) {
        Query nodeRoomOrderbyUserID = nodeRoot.child("Room")
                .orderByChild("owner")
                .equalTo(UID);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //L???y ra t???ng s??? ph??ng
                int CountRoom = (int) dataSnapshot.getChildrenCount();
                //G???i th??ng tin t???ng s??? ph??ng v??? UI
                iInfoOfAllRoomUser.sendQuantity(CountRoom, 0);

                //Duy???t h???t s??? ph??ng ????? ?????m s??? l?????ng l?????t xem v?? b??nh lu???n
                int count = 0;
                List<String> listRoomID = new ArrayList<>();
                for (DataSnapshot snapshotRoom : dataSnapshot.getChildren()) {
                    count++;
                    listRoomID.add(snapshotRoom.getKey());
                    //L???y ra key v?? t??m trong
                    if (count == CountRoom) {
                        //G???i d??? li???u v??? controller
                        SendInfoAllOfRoom(listRoomID, iInfoOfAllRoomUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoomOrderbyUserID.addListenerForSingleValueEvent(valueEventListener);
    }

    //G???i th??ng tin v??? UI
    private void SendInfoAllOfRoom(List<String> listRoomID, IInfoOfAllRoomUser iInfoOfAllRoomUser) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int CountViews = 0;
                int CountComments = 0;
                for (String roomID : listRoomID) {
                    int views = (int) dataSnapshot.child("RoomViews").child(roomID).getChildrenCount();
                    CountViews += views;

                    int comments = (int) dataSnapshot.child("RoomComments").child(roomID).getChildrenCount();
                    CountComments += comments;

                }

                //iInfoOfAllRoomUser.sendQuantity(CountViews,1);
                //G???i th??ng tin v??? UI
                iInfoOfAllRoomUser.sendQuantity(CountViews, 1);
                iInfoOfAllRoomUser.sendQuantity(CountComments, 2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    //H??m x??a ph??ng
    public void DeleteRoom(String RoomID) {
        //X??a trong node location Room
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //L???y ra t??n ???????ng qu???n ????? x??a
                RoomModel roomModel = dataSnapshot.child("Room").child(RoomID).getValue(RoomModel.class);

                String SplitWarn = roomModel.getWard().substring(2);
                //X??a trong node location
                nodeRoot.child("LocationRoom").child(roomModel.getCounty())
                        .child(SplitWarn).child(roomModel.getStreet()).orderByValue().equalTo(RoomID)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot nodeRoom : dataSnapshot.getChildren()) {
                                    Log.d("check3", nodeRoom.getValue(String.class));
                                    //X??a node
                                    nodeRoom.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                DataSnapshot snapshotFavoriteRoom = dataSnapshot.child("FavoriteRooms");
                for (DataSnapshot snapshotUser : snapshotFavoriteRoom.getChildren()) {
                    for (DataSnapshot snapshotRoomFavorite : snapshotUser.getChildren()) {
                        if (snapshotRoomFavorite.getKey().equals(RoomID)) {
                            Log.d("mycheck", snapshotRoomFavorite.getKey());
                            snapshotRoomFavorite.getRef().removeValue();
                        }
                    }
                }

                //X??a trong node FacoritRoom

                //X??a trong node Room
                nodeRoot.child("Room").child(RoomID).getRef().removeValue();

                //X??a trong node ReportedRoom
                nodeRoot.child("ReportedRoom").child(RoomID).getRef().removeValue();

                //X??a trong node roomComment
                nodeRoot.child("RoomComments").child(RoomID).getRef().removeValue();

                //X??a trong node RoomCompressImage
                nodeRoot.child("RoomCompressionImages").child(RoomID).getRef().removeValue();

                //X??a trong node RoomImage
                nodeRoot.child("RoomImages").child(RoomID).getRef().removeValue();

                //X??a trong node convenient
                nodeRoot.child("RoomConvenients").child(RoomID).getRef().removeValue();

                //X??a trong node RoomPrice
                nodeRoot.child("RoomPrice").child(RoomID).getRef().removeValue();

                //X??a trong node RoomViews
                nodeRoot.child("RoomViews").child(RoomID).getRef().removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);

    }

    //H??m Change State Room: sang ???? thu??
    public void changeState(String RoomID, int MaxNumber) {
        nodeRoot.child("Room").child(RoomID).child("currentNumber").setValue(MaxNumber);
    }

    //H??m Change State Room: sang c??n tr???ng
    public void changeState(String RoomID) {
        nodeRoot.child("Room").child(RoomID).child("currentNumber").setValue(0);
    }


    public void getOneRoomFollowRoomId(String roomId, IPostRoomUpdateModel iPostRoomUpdateModel) {

        //T???o listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotRoom = dataSnapshot.child("Room");

                //Duy???t h???t trong danh s??ch ph??ng tr???
                for (DataSnapshot valueRoom : dataSnapshotRoom.getChildren()) {

                    if (valueRoom.getKey().equals(roomId)) {
                        //L???y ra gi?? tr??? ??p ki???u qua ki???u RoomModel
                        RoomModel roomModel = valueRoom.getValue(RoomModel.class);
                        //Set m?? ph??ng tr???
                        roomModel.setRoomID(valueRoom.getKey());

                        //Set lo???i ph??ng tr???
                        String tempType = dataSnapshot.child("RoomTypes")
                                .child(roomModel.getTypeID())
                                .getValue(String.class);

                        roomModel.setRoomType(tempType);

                        //Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

                        //Duy???t v??o node RoomImages tr??n firebase v?? duy???t v??o node c?? m?? room t????ng ???ng
                        DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(valueRoom.getKey());
                        List<String> tempImageList = new ArrayList<String>();
                        //Duy??t t???t c??? c??c gi?? tr??? c???a node t????ng ???ng
                        for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                            tempImageList.add(valueImage.getValue(String.class));
                        }

                        //set m???ng h??nh v??o list
                        roomModel.setListImageRoom(tempImageList);

                        //Th??m v??o h??nh dung l?????ng th???p c???a ph??ng tr???
                        DataSnapshot dataSnapshotComPress = dataSnapshot.child("RoomCompressionImages").child(valueRoom.getKey());
                        //Ki???m tra n???u c?? d??? li???u
                        if (dataSnapshotComPress.getChildrenCount() > 0) {
                            for (DataSnapshot valueCompressionImage : dataSnapshotComPress.getChildren()) {
                                roomModel.setCompressionImage(valueCompressionImage.getValue(String.class));
                            }
                        } else {
                            roomModel.setCompressionImage(tempImageList.get(0));
                        }

                        //End Th??m t??n danh s??ch t??n h??nh v??o ph??ng tr???

                        //Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

                        DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(valueRoom.getKey());
                        List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
                        //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                        for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                            CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                            commentModel.setCommentID(CommentValue.getKey());
                            //Duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n
                            UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                            commentModel.setUserComment(tempUser);
                            //End duy???t user t????ng ???ng ????? l???y ra th??ng tin user b??nh lu???n

                            tempCommentList.add(commentModel);
                        }

                        roomModel.setListCommentRoom(tempCommentList);

                        //End Th??m danh s??ch b??nh lu???n c???a ph??ng tr???

                        //Th??m danh s??ch ti???n nghi c???a ph??ng tr???

                        DataSnapshot dataSnapshotConvenientRoom = dataSnapshot.child("RoomConvenients").child(valueRoom.getKey());
                        List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                        //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                        for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                            String convenientId = valueConvenient.getValue(String.class);
                            ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                            convenientModel.setConvenientID(convenientId);

                            tempConvenientList.add(convenientModel);
                        }

                        roomModel.setListConvenientRoom(tempConvenientList);

                        //End Th??m danh s??ch ti???n nghi c???a ph??ng tr???

                        //Th??m danh s??ch gi?? c???a ph??ng tr???

                        DataSnapshot dataSnapshotRoomPrice = dataSnapshot.child("RoomPrice").child(valueRoom.getKey());
                        List<RoomPriceModel> tempRoomPriceList = new ArrayList<RoomPriceModel>();
                        //Duy???t t???t c??? c??c gi?? tr??? trong node t????ng ???ng
                        for (DataSnapshot valueRoomPrice : dataSnapshotRoomPrice.getChildren()) {
                            String roomPriceId = valueRoomPrice.getKey();
                            double price = valueRoomPrice.getValue(double.class);

                            if (roomPriceId.equals("IDRPT4")) {
                                continue;
                            }
                            RoomPriceModel roomPriceModel = dataSnapshot.child("RoomPriceType").child(roomPriceId).getValue(RoomPriceModel.class);
                            roomPriceModel.setRoomPriceID(roomPriceId);
                            roomPriceModel.setPrice(price);

                            tempRoomPriceList.add(roomPriceModel);
                        }

                        roomModel.setListRoomPrice(tempRoomPriceList);

                        //End Th??m danh s??ch gi?? c???a ph??ng tr???

                        //Th??m th??ng tin ch??? s??? h???u cho ph??ng tr???
                        UserModel tempUser = dataSnapshot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
                        roomModel.setRoomOwner(tempUser);

                        //End th??m th??ng tin ch??? s??? h???u cho ph??ng tr???

                        //Th??m v??o l?????t xem c???a ph??ng tr???
                        int tempViews = (int) dataSnapshot.child("RoomViews").child(valueRoom.getKey()).getChildrenCount();
                        roomModel.setViews(tempViews);
                        //End th??m v??o l?????t xem c???a ph??ng tr???

                        //K??ch ho???t interface
                        iPostRoomUpdateModel.getRoomFollowId(roomModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    // H??m c???p nh???t post room step 1
    public void postRoomStep1Update(String roomId, String city, String district, String ward, String street, String no, IPostRoomUpdateModel iPostRoomUpdateModel,
                                    String oldCity, String oldDistrict, String oldWard, String oldStreet, String oldNo) {

        //T???o listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot dataSnapshotLocationRoom = dataSnapshot.child("LocationRoom");
                boolean isHaveLocationRoom = false;

                //C???t b??? P. tr?????c ph?????ng
                String SplitWarnOld = oldWard.substring(2);

                for (DataSnapshot valueLocation : dataSnapshotLocationRoom.child(oldDistrict).child(SplitWarnOld).child(oldStreet).getChildren()) {

                    if (valueLocation.getValue().equals(roomId)) {

                        isHaveLocationRoom = true;

                        updateHaveKey(roomId, city, district, ward, street, no, iPostRoomUpdateModel, oldCity,
                                oldDistrict, oldWard, SplitWarnOld, valueLocation.getKey(), oldStreet, oldNo);
                    }
                }

                if (!isHaveLocationRoom) {
                    updateNotHaveKey(roomId, city, district, ward, street, no, iPostRoomUpdateModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    // Update khi ch??a c?? id trong location room
    public void updateNotHaveKey(String roomId, String city, String district, String ward, String street, String no, IPostRoomUpdateModel iPostRoomUpdateModel) {

        //C???t b??? P. tr?????c ph?????ng
        String splitWarn = ward.substring(2);

        //Push ID room v??o
        nodeRoot.child("LocationRoom")
                .child(district)
                .child(splitWarn)
                .child(street)
                .push()
                .setValue(roomId);

        Map<String, Object> map = new HashMap<>();
        map.put("city", city);
        map.put("county", district);
        map.put("ward", ward);
        map.put("street", street);
        map.put("apartmentNumber", no);
        nodeRoot.child("Room").child(roomId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                iPostRoomUpdateModel.getSuccessNotifyPostRoomStep1();

                getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
            }
        });
    }

    // Update khi ch??a c?? id trong location room
    public void updateHaveKey(String roomId, String city, String district, String ward, String street, String no, IPostRoomUpdateModel iPostRoomUpdateModel,
                              String oldCity, String oldDistrict, String oldWard, String SplitWarnOld, String value, String oldStreet, String oldNo) {

        Log.d("delete", oldDistrict + " " + SplitWarnOld + " " + oldStreet + " " + value);

        nodeRoot.child("LocationRoom")
                .child(oldDistrict)
                .child(SplitWarnOld)
                .child(oldStreet)
                .child(value)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        //C???t b??? P. tr?????c ph?????ng
                        String splitWarn = ward.substring(2);

                        //Push ID room v??o
                        nodeRoot.child("LocationRoom").child(district)
                                .child(splitWarn)
                                .child(street)
                                .push()
                                .setValue(roomId);

                        Map<String, Object> map = new HashMap<>();
                        map.put("city", city);
                        map.put("county", district);
                        map.put("ward", ward);
                        map.put("street", street);
                        map.put("apartmentNumber", no);
                        nodeRoot.child("Room").child(roomId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                iPostRoomUpdateModel.getSuccessNotifyPostRoomStep1();

                                getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
                            }
                        });
                    }
                });
    }

    // H??m c???p nh???t post room step 1
    public void postRoomStep2Update(String roomId, String typeId, boolean genderRoom, long maxNumber, float width, float length,
                                    float priceRoom, float electricBill, float warterBill, float InternetBill, float parkingBill, IPostRoomUpdateModel iPostRoomUpdateModel) {

        //T???o listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot dataSnapshotPriceRoom = dataSnapshot.child("RoomPrice");
                boolean isHavePriceRoom = false;

                for (DataSnapshot valuePriceroom : dataSnapshotPriceRoom.getChildren()) {

                    if (valuePriceroom.getKey().equals(roomId)) {

                        nodeRoot.child("RoomPrice")
                                .child(valuePriceroom.getKey())
                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                addDetailRoomPrice(roomId, electricBill, warterBill, InternetBill, parkingBill, priceRoom);

                                Map<String, Object> map = new HashMap<>();
                                map.put("typeID", typeId);
                                map.put("gender", genderRoom);
                                map.put("maxNumber", maxNumber);
                                map.put("width", width);
                                map.put("length", length);
                                map.put("rentalCosts", priceRoom);
                                nodeRoot.child("Room").child(roomId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        iPostRoomUpdateModel.getSuccessNotifyPostRoomStep2();

                                        getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
                                    }
                                });
                            }
                        });
                    }
                }

                if (isHavePriceRoom) {
                    return;
                }

                addDetailRoomPrice(roomId, electricBill, warterBill, InternetBill, parkingBill, priceRoom);

                Map<String, Object> map = new HashMap<>();
                map.put("typeID", typeId);
                map.put("gender", genderRoom);
                map.put("maxNumber", maxNumber);
                map.put("width", width);
                map.put("length", length);
                map.put("rentalCosts", priceRoom);
                nodeRoot.child("Room").child(roomId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        iPostRoomUpdateModel.getSuccessNotifyPostRoomStep2();

                        getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    // H??m c???p nh???t post room step 1
    public void postRoomStep3Update(String roomId, String owner, List<String> listConvenient, List<String> listPathImageChoosed,
                                    boolean isChangeConvenient, boolean isChangeImageRoom, IPostRoomUpdateModel iPostRoomUpdateModel, Context context) {
        if (isChangeConvenient == true && isChangeImageRoom == false) {
            //T???o listen cho firebase
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    DataSnapshot dataSnapshotConvenientsRoom = dataSnapshot.child("RoomConvenients");
                    boolean isHaveConvenient = false;

                    for (DataSnapshot valueConvenient : dataSnapshotConvenientsRoom.getChildren()) {

                        if (valueConvenient.getKey().equals(roomId)) {

                            isHaveConvenient = true;
                            deleteConvenients(roomId, valueConvenient.getKey(), listConvenient, iPostRoomUpdateModel);
                        }
                    }

                    if (!isHaveConvenient) {
                        addConvenients(roomId, listConvenient, iPostRoomUpdateModel);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        } else if (!isChangeConvenient && isChangeImageRoom) {
            //T???o listen cho firebase

            final int[] count = {0};

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    DataSnapshot dataSnapshotRoom = dataSnapshot.child("RoomImages");
                    boolean isHaveImage = false;

                    for (DataSnapshot valueNodeImage : dataSnapshotRoom.getChildren()) {

                        if (valueNodeImage.getKey().equals(roomId)) {
                            isHaveImage = true;

                            for (DataSnapshot valueImage : valueNodeImage.getChildren()) {
                                boolean isFind = false;

                                for (String pathImage : listPathImageChoosed) {
                                    if (valueImage.getValue().equals(pathImage) == true) {
                                        isFind = true;

                                        // S??? l???n t??m th???y l???p l???i ???nh ???? up
                                        count[0]++;
                                        break;
                                    }
                                }

                                if (isFind == false) {
                                    nodeRoot.child("RoomImages")
                                            .child(roomId)
                                            .child(valueImage.getKey())
                                            .removeValue();
                                }
                                //deleteImagesRoom(roomId, valueImage.getKey(), listPathImageChoosed, iPostRoomUpdateModel, context);
                            }
                            //Log.d("countingUpdate", count[0] + "");

                            addImagesRoom(roomId, listPathImageChoosed, iPostRoomUpdateModel, context, count[0]);
                        }
                    }

                    if (isHaveImage == false) {

                        //Log.d("isHaveImage", false + "");

                        addImagesRoom(roomId, listPathImageChoosed, iPostRoomUpdateModel, context, count[0]);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            };

            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        } else if (isChangeConvenient == true && isChangeImageRoom == true) {
            //T???o listen cho firebase
            ValueEventListener valueEventListenerConvenients = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    DataSnapshot dataSnapshotRoom = dataSnapshot.child("RoomConvenients");
                    boolean isHaveConvenient = false;

                    for (DataSnapshot valueConvenient : dataSnapshotRoom.getChildren()) {

                        if (valueConvenient.getKey().equals(roomId)) {

                            isHaveConvenient = true;
                            deleteConvenientsNotNotify(roomId, valueConvenient.getKey(), listConvenient, iPostRoomUpdateModel);
                        }
                    }

                    if (isHaveConvenient == false) {
                        addConvenientsNotNotify(roomId, listConvenient, iPostRoomUpdateModel);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            final int[] count = {0};

            ValueEventListener valueEventListenerImage = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    DataSnapshot dataSnapshotRoom = dataSnapshot.child("RoomImages");
                    boolean isHaveImage = false;

                    for (DataSnapshot valueNodeImage : dataSnapshotRoom.getChildren()) {

                        if (valueNodeImage.getKey().equals(roomId)) {
                            isHaveImage = true;

                            for (DataSnapshot valueImage : valueNodeImage.getChildren()) {
                                boolean isFind = false;

                                for (String pathImage : listPathImageChoosed) {
                                    if (valueImage.getValue().equals(pathImage) == true) {
                                        isFind = true;

                                        // S??? l???n t??m th???y l???p l???i ???nh ???? up
                                        count[0]++;
                                        break;
                                    }
                                }

                                if (isFind == false) {
                                    nodeRoot.child("RoomImages")
                                            .child(roomId)
                                            .child(valueImage.getKey())
                                            .removeValue();
                                }
                            }

                            addImagesRoom(roomId, listPathImageChoosed, iPostRoomUpdateModel, context, count[0]);
                        }
                    }

                    if (isHaveImage == false) {
                        addImagesRoom(roomId, listPathImageChoosed, iPostRoomUpdateModel, context, count[0]);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            };

            nodeRoot.addListenerForSingleValueEvent(valueEventListenerConvenients);

            nodeRoot.addListenerForSingleValueEvent(valueEventListenerImage);
        }
    }

    public void deleteConvenients(String roomId, String key, List<String> listConvenient, IPostRoomUpdateModel iPostRoomUpdateModel) {
        // X??a convenients room hi???n t???i
        nodeRoot.child("RoomConvenients")
                .child(key)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addConvenients(roomId, listConvenient, iPostRoomUpdateModel);

            }
        });
    }

    public void deleteConvenientsNotNotify(String roomId, String key, List<String> listConvenient, IPostRoomUpdateModel iPostRoomUpdateModel) {
        // X??a convenients room hi???n t???i
        nodeRoot.child("RoomConvenients")
                .child(key)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addConvenientsNotNotify(roomId, listConvenient, iPostRoomUpdateModel);

            }
        });
    }

    public void addConvenients(String roomId, List<String> listConvenient, IPostRoomUpdateModel iPostRoomUpdateModel) {
        // Th??m convenients room hi???n t???i
        for (String dataConvenient : listConvenient) {
            nodeRoot.child("RoomConvenients").child(roomId).push().setValue(dataConvenient);
        }

        iPostRoomUpdateModel.getSuccessNotifyPostRoomStep3();

        getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
    }

    public void addConvenientsNotNotify(String roomId, List<String> listConvenient, IPostRoomUpdateModel iPostRoomUpdateModel) {
        // Th??m convenients room hi???n t???i
        for (String dataConvenient : listConvenient) {
            nodeRoot.child("RoomConvenients").child(roomId).push().setValue(dataConvenient);
        }
    }

    public void deleteImagesRoom(String roomId, String key, List<String> listPathImageChoosed,
                                 IPostRoomUpdateModel iPostRoomUpdateModel, Context context, int countImageUploaded) {

        // X??a convenients room hi???n t???i
        nodeRoot.child("RoomImages")
                .child(key)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addImagesRoom(roomId, listPathImageChoosed, iPostRoomUpdateModel, context, countImageUploaded);
            }
        });
    }

    public void addImagesRoom(String roomId, List<String> listPathImageChoosed,
                              IPostRoomUpdateModel iPostRoomUpdateModel, Context context, int countImageUploaded) {
        //L???y ra node room
        DatabaseReference nodeRoom = nodeRoot.child("Room");

        //T???i h??nh l??n tr?????c sau khi ho??n t???t t???i h??nh m???i th??m v??o c??c th??ng tin c???n thi???t
        //T???i h??nh l??n
        final int[] count = {0};
        final boolean isExitsFile = false;
        int countImageInFireBase = 0;


        for (String pathImage : listPathImageChoosed) {
            String header = pathImage.substring(0, Math.min(pathImage.length(), 23));

            // Ki???m tra xem ???????ng d???n n??y c?? ph???i tr??n fire base ko
            if (header.equals("https://firebasestorage") == true) {
                countImageInFireBase++;
            }
        }

        Log.d("countImageInFireBase", countImageInFireBase + "");
        Log.d("ImageChoosed.size()", listPathImageChoosed.size() + "");

        // T???t c??? ???? c?? tr??n fire base
        if (countImageInFireBase == listPathImageChoosed.size()) {

            iPostRoomUpdateModel.getSuccessNotifyPostRoomStep3();

            getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);

            return;
        }

        for (String pathImage : listPathImageChoosed) {
            //L???y ???????ng d???n Uri c???a h??nh
            Uri file = Uri.parse(pathImage);

            //L???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh
            DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
            String date = df.format(Calendar.getInstance().getTime());
            //End l???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh

            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("Images/" + date + file.getLastPathSegment());

            //T???i h??nh l??n b???ng h??m putFile
            storageReference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //isExitsFile = true;

                            //L???y URL h??nh m???i upload
                            String dowloadURL = uri.toString();
                            //Push h??nh v??o danh s??ch h??nh t????ng ???ng v???i room
                            nodeRoot.child("RoomImages").child(roomId).push().setValue(dowloadURL);

                            count[0]++;
                            if ((count[0] + countImageUploaded) == listPathImageChoosed.size()) {

                                String header = listPathImageChoosed.get(0).substring(0, Math.min(listPathImageChoosed.get(0).length(), 23));


                                Log.d("header", header);

                                if (header.equals("https://firebasestorage") == false) {

                                    String path = FilePath.getPath(context, Uri.parse(listPathImageChoosed.get(0)));

                                    // N???u ???????ng d???n [0] file kh??ng t???n t???i ??? m??y
                                    if (new File(path).exists() == true) {
                                        //L???y ra ???????ng d???n file
                                        //T???o file t????ng ???ng v???i ???????ng d???n ????
                                        File image_filePath = new File(path);

                                        //T???o Bitmap ????? n??n ???nh v?? t???i l??n
                                        Bitmap image_bitmap = null;
                                        try {
                                            image_bitmap = new Compressor(context)
                                                    .setMaxHeight(320)
                                                    .setMaxWidth(240)
                                                    .setQuality(50)
                                                    .compressToBitmap(image_filePath);
                                            //T???o m???ng byte ????? ????? d??? li???u t??? bitmap sang ????? up l??n storage
                                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                            //Chuy???n ???nh v??? JPG v?? ????? v??o m???ng byte
                                            image_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

                                            //????? d??? li???u ra m???ng byte
                                            final byte[] image_byte = byteArrayOutputStream.toByteArray();

                                            //L???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh
                                            DateFormat df2 = new SimpleDateFormat("ddMMyyyyHHmmss");
                                            String date2 = df2.format(Calendar.getInstance().getTime());
                                            //End l???y ra ng??y gi??? hi???n t???i ????? ph??n bi???t gi???a c??c ???nh

                                            //T???o ???????ng d???n ?????n thu m???c upload h??nh
                                            StorageReference CompressStorage = FirebaseStorage.getInstance().getReference().child("CompressionImages/" + owner + date2 + ".jpg");

                                            //Up load h??nh
                                            UploadTask uploadTask = CompressStorage.putBytes(image_byte);
                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    CompressStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            //L???y ra ???????ng d???n download
                                                            String ComPressDowloadURL = uri.toString();
                                                            //Th??m v??o trong node Room
                                                            nodeRoot.child("RoomCompressionImages").child(roomId).removeValue();
                                                            nodeRoot.child("RoomCompressionImages").child(roomId).push().setValue(ComPressDowloadURL);

                                                            iPostRoomUpdateModel.getSuccessNotifyPostRoomStep3();
                                                            getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
                                                        }
                                                    });
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Log.d("myroomroom", "run");
                                        iPostRoomUpdateModel.getSuccessNotifyPostRoomStep3();

                                        getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
                                    }
                                } else {
                                    Log.d("myroomroom", "run");
                                    iPostRoomUpdateModel.getSuccessNotifyPostRoomStep3();

                                    getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
                                }
                            }
                        }
                    });
                }
            });
        }

        // Kh??ng c?? file n??o t???i m??y
//        if (isExitsFile == false) {
//            iPostRoomUpdateModel.getSuccessNotifyPostRoomStep3();
//
//            getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
//        }
    }

    // H??m c???p nh???t post room step 1
    public void postRoomStep4Update(String roomId, String name, String describe, IPostRoomUpdateModel iPostRoomUpdateModel) {
        DatabaseReference nodeRoom = FirebaseDatabase.getInstance().getReference().child("Room");

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("describe", describe);
        nodeRoom.child(roomId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                iPostRoomUpdateModel.getSuccessNotifyPostRoomStep4();

                getOneRoomFollowRoomId(roomId, iPostRoomUpdateModel);
            }
        });
    }

    //Duy???t ph??ng
    public void verifyRoom(String RoomID) {
        nodeRoot.child("Room").child(RoomID).child("approve").setValue(true);
    }
}
