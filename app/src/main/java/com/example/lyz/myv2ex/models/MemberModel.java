package com.example.lyz.myv2ex.models;


import android.os.Parcel;
import android.os.Parcelable;

public class MemberModel implements Parcelable {
    private int id;
    private String userName;
    private String tagLine;
    private String avatarMini;
    private String avatarNormal;
    private String avatarLarge;

    public MemberModel(int id, String userName, String tagLine, String avatarMini, String avatarNormal, String avatarLarge) {
        this.id = id;
        this.userName = userName;
        this.tagLine = tagLine;
        this.avatarMini = avatarMini;
        this.avatarNormal = avatarNormal;
        this.avatarLarge = avatarLarge;
    }

    public MemberModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getAvatarMini() {
        return avatarMini;
    }

    public void setAvatarMini(String avatarMini) {
        this.avatarMini = avatarMini;
    }

    public String getAvatarNormal() {
        return avatarNormal;
    }

    public void setAvatarNormal(String avatarNormal) {
        this.avatarNormal = avatarNormal;
    }

    public String getAvatarLarge() {
        return avatarLarge;
    }

    public void setAvatarLarge(String avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userName);
        dest.writeString(tagLine);
        dest.writeString(avatarMini);
        dest.writeString(avatarNormal);
        dest.writeString(avatarLarge);
    }

    public static final Creator<MemberModel> CREATOR = new Creator<MemberModel>() {
        @Override
        public MemberModel createFromParcel(Parcel source) {
            MemberModel memberModel = new MemberModel();
            memberModel.id = source.readInt();
            memberModel.userName = source.readString();
            memberModel.tagLine = source.readString();
            memberModel.avatarMini = source.readString();
            memberModel.avatarNormal = source.readString();
            memberModel.avatarLarge = source.readString();

            return memberModel;
        }

        @Override
        public MemberModel[] newArray(int size) {
            return new MemberModel[size];
        }
    };
}
