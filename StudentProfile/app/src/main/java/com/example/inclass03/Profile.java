package com.example.inclass03;

/**
* a. Assignment : #03.
* b. File Name : Profile (com.example.inclass03).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    private String name;
    private String email;
    private String id;
    private String department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Profile(){
    }

    public Profile(String name, String email, String id, String department) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.department = department;
    }

    protected Profile(Parcel in) {
        name = in.readString();
        email = in.readString();
        id = in.readString();
        department = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.id);
        dest.writeString(this.department);
    }
}
