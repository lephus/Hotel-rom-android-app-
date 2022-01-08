package com.example.motel_room.controller;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motel_room.adapter.AdapterRecyclerSuggestions;
import com.example.motel_room.controller.Interfaces.IDistrictFilterModel;
import com.example.motel_room.model.DistrictFilterModel;
import com.example.motel_room.R;

import java.util.ArrayList;
import java.util.List;

public class locationSearchController {
    Context context;
    DistrictFilterModel districtFilterModel;

    public locationSearchController(Context context){
        this.context = context;
        this.districtFilterModel = new DistrictFilterModel();
    }

    public void loadDistrictInData(RecyclerView recyclerSuggestion, String FilterString, boolean isSearchRoomCall){

        //Khởi tạo list lưu quận
        List<String> stringListDistrict = new ArrayList<String>();

        //Khởi tạo Adapter
        AdapterRecyclerSuggestions adapterRecyclerSuggestions = new AdapterRecyclerSuggestions(context, R.layout.element_location_recycler_view,stringListDistrict,isSearchRoomCall);
        //Set adapter
        recyclerSuggestion.setAdapter(adapterRecyclerSuggestions);

        //Tạo layout cho recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerSuggestion.setLayoutManager(layoutManager);


        IDistrictFilterModel iDistrictFilterModel = new IDistrictFilterModel() {
            @Override
            public void sendDistrict(String District) {
                stringListDistrict.add(District);
                adapterRecyclerSuggestions.notifyDataSetChanged();
            }
        };
        districtFilterModel.listDistrictLocation(FilterString,iDistrictFilterModel);


    }


}
