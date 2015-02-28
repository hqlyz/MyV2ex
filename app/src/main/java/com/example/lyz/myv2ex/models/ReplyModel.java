package com.example.lyz.myv2ex.models;


import android.os.Parcel;
import android.os.Parcelable;

public class ReplyModel implements Parcelable {
    private int id;
    private int thanks;
    private String content;
    private String contentRendered;
    private MemberModel memberModel;
    private long created;
    private long lastModified;

    public ReplyModel(int id, int thanks, String content, String contentRendered, MemberModel memberModel, long created, long last_modified) {
        this.id = id;
        this.thanks = thanks;
        this.content = content;
        this.contentRendered = contentRendered;
        this.memberModel = memberModel;
        this.created = created;
        this.lastModified = last_modified;
    }

    public ReplyModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThanks() {
        return thanks;
    }

    public void setThanks(int thanks) {
        this.thanks = thanks;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentRendered() {
        return contentRendered;
    }

    public void setContentRendered(String contentRendered) {
        this.contentRendered = contentRendered;
    }

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(thanks);
        dest.writeString(content);
        dest.writeString(contentRendered);
        dest.writeValue(memberModel);
        dest.writeLong(created);
        dest.writeLong(lastModified);
    }

    public static final Creator<ReplyModel> CREATOR = new Creator<ReplyModel>() {
        @Override
        public ReplyModel createFromParcel(Parcel source) {
            ReplyModel replyModel = new ReplyModel();
            replyModel.setId(source.readInt());
            replyModel.setThanks(source.readInt());
            replyModel.setContent(source.readString());
            replyModel.setContentRendered(source.readString());
            replyModel.setMemberModel((MemberModel)source.readValue(MemberModel.class.getClassLoader()));
            replyModel.setCreated(source.readLong());
            replyModel.setLastModified(source.readLong());

            return replyModel;
        }

        @Override
        public ReplyModel[] newArray(int size) {
            return new ReplyModel[size];
        }
    };
}
