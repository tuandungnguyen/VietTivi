package com.imtuandung.viettivi.object;

/**
 * Created by Dung Nguyen on 12/25/15.
 */
public class Channel {
    String ID;
    String Title;
    String Description;
    String Link;
    String Category;

    public Channel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Channel(String iD, String title, String description, String link,
                   String category) {
        super();
        ID = iD;
        Title = title;
        Description = description;
        Link = link;
        Category = category;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

}
