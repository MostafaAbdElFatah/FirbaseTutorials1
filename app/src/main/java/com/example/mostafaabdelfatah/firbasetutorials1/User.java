package com.example.mostafaabdelfatah.firbasetutorials1;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Mostafa Abd El Fatah on 2/1/2018.
 */

@IgnoreExtraProperties
public class User {

    private String id;
    private String name;
    private String age;
    private String address;

    public User( String name , String age, String address) {
        this.name    = name;
        this.age     = age;
        this.address = address;
    }

    public User( String id , String name , String age, String address) {
        this.id      =id;
        this.name    = name;
        this.age     = age;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean containInArrayList(ArrayList<User> list){
        for(User user : list){
            if(this.getId().equals(user.getId())){
                return  true;
            }
        }
        return false;
    }

    public boolean changedDataList(ArrayList<User> list){
        for(User user : list){
            if(this.getId().equals(user.getId())){
                user.setId( this.id );
                user.setName(this.name);
                user.setAge(this.age);
                user.setAddress(this.address);
                return  true;
            }
        }
        return false;
    }
    public boolean removeDataList(ArrayList<User> list){
        for(int i =0;i<list.size();i++){
            if(this.getId().equals(list.get(i).getId())){
                list.remove(i);
                return  true;
            }
        }
        return false;
    }

}
