package com.example.motel_room.view.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.example.motel_room.R;
import com.example.motel_room.adapter.AdapterBookRoomRequest;
import com.example.motel_room.model.BookRoomModel;
import com.example.motel_room.view.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestBookActivity extends AppCompatActivity {
    ListView lstViewBookRoom;
    SharedPreferences sharedPreferences;
    String UID;
    int userRole;

    @Override
    protected void onRestart() {
        super.onRestart();
        new  AsyncTaskNetworkRequest(RequestBookActivity.this,lstViewBookRoom, UID, userRole).execute(UID);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_book);

        sharedPreferences = getSharedPreferences(LoginActivity.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginActivity.SHARE_UID, "n1oc76JrhkMB9bxKxwXrxJld3qH2");
        userRole = sharedPreferences.getInt(LoginActivity.USER_ROLE, 1);
        System.out.println(UID);
        lstViewBookRoom = (ListView) findViewById(R.id.listRequsetBookRoom);
        new  AsyncTaskNetworkRequest(RequestBookActivity.this,lstViewBookRoom, UID, userRole).execute(UID);
    }
}

class AsyncTaskNetworkRequest extends AsyncTask<String, Void, String> {
    List<BookRoomModel> bookRoomModelList = new ArrayList<>();
    AdapterBookRoomRequest adapterBookRoom;
    ProgressDialog progressDialog;
    Context context;
    ListView listView;
    String UID;
    final Handler handler = new Handler();
    int userRole;
    public AsyncTaskNetworkRequest(Context context, ListView li, String UID, int ok) {
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
                    if(roomModel.getAuthID().equals(lists[0])){
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
                // Hi???n th??? IP l??n TextView.
                adapterBookRoom = new AdapterBookRoomRequest(context, R.layout.element_book_room, bookRoomModelList, userRole);
                listView.setAdapter(adapterBookRoom);
            }
        }, 3000);

    }
}