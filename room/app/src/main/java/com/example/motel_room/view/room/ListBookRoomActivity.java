package com.example.motel_room.view.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.motel_room.R;
import com.example.motel_room.adapter.AdapterBookRoom;
import com.example.motel_room.model.BookRoomModel;
import com.example.motel_room.view.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListBookRoomActivity extends AppCompatActivity {
    ListView lstViewBookRoom;
    List<BookRoomModel> bookRoomModelList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    AdapterBookRoom adapterBookRoom;
    String UID;
    int userRole;
    @Override
    protected void onStart(){
        super.onStart();
//        System.out.println("alo: "+bookRoomModelList.size());
//        for(int i = 0; i< bookRoomModelList.size(); i++){
//            System.out.println(bookRoomModelList.get(i).getNameRoom());
//        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bookRoomModelList.clear();
        new AsyncTaskNetwork(ListBookRoomActivity.this,lstViewBookRoom, UID, userRole).execute(UID);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_room);

        sharedPreferences = getSharedPreferences(LoginActivity.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginActivity.SHARE_UID, "n1oc76JrhkMB9bxKxwXrxJld3qH2");
        userRole = sharedPreferences.getInt(LoginActivity.USER_ROLE, 1);
        System.out.println(UID);
        lstViewBookRoom = (ListView) findViewById(R.id.listBookRoom);
        //GetDataBookRoom();
        new AsyncTaskNetwork(ListBookRoomActivity.this,lstViewBookRoom, UID, userRole).execute(UID);

    }

}
class AsyncTaskNetwork extends AsyncTask<String, Void, String> {
    List<BookRoomModel> bookRoomModelList = new ArrayList<>();
    AdapterBookRoom adapterBookRoom;
    ProgressDialog progressDialog;
    Context context;
    ListView listView;
    String UID;
    int userRole;
    final Handler handler = new Handler();
    public AsyncTaskNetwork(Context context, ListView li, String UID, int ok) {
        this.context = context;
        this.listView = li;
        this.UID = UID;
        this.userRole = ok;
    }

    @Override
    protected String doInBackground(String... lists) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("BookRoom");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    BookRoomModel roomModel = ds.getValue(BookRoomModel.class);
                    if(roomModel.getID_User().equals(lists[0])){
                        bookRoomModelList.add(roomModel);
                        System.out.println(bookRoomModelList.size());
                    }
                }
                System.out.println(bookRoomModelList.size());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Can not get data book room");
            }
        };

        usersRef.addListenerForSingleValueEvent(valueEventListener);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("loading...");
        // Hi???n th??? Dialog khi b???t ?????u x??? l??.
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("G???i y??u c???u");
        progressDialog.setMessage("Dang xu ly...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String aString) {
        super.onPostExecute(aString);
        // H???y dialog ??i.
        System.out.println("ok loading");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                adapterBookRoom = new AdapterBookRoom(context, R.layout.element_book_room, bookRoomModelList, userRole);
                listView.setAdapter(adapterBookRoom);
            }
        }, 3000);


    }
}