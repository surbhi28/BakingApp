package com.example.android.bakingapp.ModalClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Steps(Parcel source){
        id = source.readInt();
        shortDescription = source.readString();
        description = source.readString();
        videoURL = source.readString();
        thumbnailURL = source.readString();
    }


    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel parcel) {
            return new Steps(parcel);
        }

        @Override
        public Steps[] newArray(int i) {
            return new Steps[i];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeString(shortDescription);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);

    }
}
