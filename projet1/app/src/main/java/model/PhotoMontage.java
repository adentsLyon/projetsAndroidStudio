package model;

import java.util.Date;

/**
 * Created by amachado on 23/06/2015.
 */
public class PhotoMontage {

    private int id;
    private String filePath;
    private String name;
    private Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public PhotoMontage(){

    }

    public PhotoMontage(int id, String filePath, String name, Date creationDate) {
        this.id = id;
        this.filePath = filePath;
        this.name = name;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
