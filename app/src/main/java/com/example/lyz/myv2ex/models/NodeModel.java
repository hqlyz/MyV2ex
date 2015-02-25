package com.example.lyz.myv2ex.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NodeModel implements Parcelable {
    private int id;
    private String name;
    private String title;
    private String titleAlternative;
    private String url;
    private int topics;
    private String header;
    private String footer;
    private long created;
    private String avatarMini;
    private String avatarNormal;
    private String avatarLarge;

    public NodeModel(int id, String name, String title, String titleAlternative, String url, int topics, String header, String footer, long created, String avatarMini, String avatarNormal, String avatarLarge) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.titleAlternative = titleAlternative;
        this.url = url;
        this.topics = topics;
        this.header = header;
        this.footer = footer;
        this.created = created;
        this.avatarMini = avatarMini;
        this.avatarNormal = avatarNormal;
        this.avatarLarge = avatarLarge;
    }

    public NodeModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleAlternative() {
        return titleAlternative;
    }

    public void setTitleAlternative(String titleAlternative) {
        this.titleAlternative = titleAlternative;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
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
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(titleAlternative);
        dest.writeString(url);
        dest.writeInt(topics);
        dest.writeString(header);
        dest.writeString(footer);
        dest.writeLong(created);
        dest.writeString(avatarMini);
        dest.writeString(avatarNormal);
        dest.writeString(avatarLarge);
    }

    public static final Creator<NodeModel> CREATOR = new Creator<NodeModel>() {
        @Override
        public NodeModel createFromParcel(Parcel source) {
            NodeModel nodeModel = new NodeModel();
            nodeModel.id = source.readInt();
            nodeModel.name = source.readString();
            nodeModel.title = source.readString();
            nodeModel.titleAlternative = source.readString();
            nodeModel.url = source.readString();
            nodeModel.topics = source.readInt();
            nodeModel.header = source.readString();
            nodeModel.footer = source.readString();
            nodeModel.created = source.readLong();
            nodeModel.avatarMini = source.readString();
            nodeModel.avatarNormal = source.readString();
            nodeModel.avatarLarge = source.readString();

            return nodeModel;
        }

        @Override
        public NodeModel[] newArray(int size) {
            return new NodeModel[size];
        }
    };
}
