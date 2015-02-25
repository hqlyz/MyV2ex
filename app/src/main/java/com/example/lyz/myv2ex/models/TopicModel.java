package com.example.lyz.myv2ex.models;


import android.os.Parcel;
import android.os.Parcelable;

public class TopicModel implements Parcelable {
    private int id;
    private String title;
    private String url;
    private String content;
    private String contentRendered;
    private int replies;
    private MemberModel memberModel;
    private NodeModel nodeModel;
    private long created;
    private long lastModified;
    private long lastTouched;

    public TopicModel(int id, String title, String url, String content, String contentRendered, int replies, MemberModel memberModel, NodeModel nodeModel, long created, long lastModified, long lastTouched) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.content = content;
        this.contentRendered = contentRendered;
        this.replies = replies;
        this.memberModel = memberModel;
        this.nodeModel = nodeModel;
        this.created = created;
        this.lastModified = lastModified;
        this.lastTouched = lastTouched;
    }

    public TopicModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }

    public NodeModel getNodeModel() {
        return nodeModel;
    }

    public void setNodeModel(NodeModel nodeModel) {
        this.nodeModel = nodeModel;
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

    public long getLastTouched() {
        return lastTouched;
    }

    public void setLastTouched(long lastTouched) {
        this.lastTouched = lastTouched;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(content);
        dest.writeString(contentRendered);
        dest.writeInt(replies);
        dest.writeValue(memberModel);
        dest.writeValue(nodeModel);
        dest.writeLong(created);
        dest.writeLong(lastModified);
        dest.writeLong(lastTouched);
    }

    public static final Creator<TopicModel> CREATOR = new Creator<TopicModel>() {
        @Override
        public TopicModel createFromParcel(Parcel source) {
            TopicModel topicModel = new TopicModel();
            topicModel.id = source.readInt();
            topicModel.title = source.readString();
            topicModel.url = source.readString();
            topicModel.content = source.readString();
            topicModel.contentRendered = source.readString();
            topicModel.replies = source.readInt();
            topicModel.memberModel = (MemberModel)source.readValue(MemberModel.class.getClassLoader());
            topicModel.nodeModel = (NodeModel)source.readValue(NodeModel.class.getClassLoader());
            topicModel.created = source.readLong();
            topicModel.lastModified = source.readLong();
            topicModel.lastTouched = source.readLong();

            return topicModel;
        }

        @Override
        public TopicModel[] newArray(int size) {
            return new TopicModel[size];
        }
    };
}
