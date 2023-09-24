package com.example.mybulksms.domains;
import com.google.gson.annotations.SerializedName;
public class Group {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
