package model;

/**
 * Created by amachado on 24/06/2015.
 */
public class Status {

    private int code;
    private String name;
    private String tableName;


    public Status() {
    }


    public Status(int statusCode, String statusName, String tableName) {
        this.code = statusCode;
        this.name = statusName;
        this.tableName = tableName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}