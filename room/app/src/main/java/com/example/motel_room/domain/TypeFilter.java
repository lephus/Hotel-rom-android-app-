package com.example.motel_room.domain;

public class TypeFilter extends myFilter {

    public TypeFilter(){

    }

    public TypeFilter(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void replace(myFilter filter) {

    }
}
