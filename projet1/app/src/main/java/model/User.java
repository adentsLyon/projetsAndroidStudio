package model;

import com.adents.projet1.tools.ConstantsTool;

import java.util.regex.Pattern;

/**
 * Created by amachado on 24/06/2015.
 */
public class User {

    private int userId;
    private String login;
    private String password;
    private String email;
    private String langId;
    private Status status;
    private int customerId;

    public User() {
    }

    public User(int userId, String login, String password, String email, String langId, Status status, int customerId) throws Exception {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.setEmail(email);
        this.langId = langId;
        this.status = status;
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        Pattern p = Pattern.compile(ConstantsTool.EMAIL_PATTERN);
        boolean isMatch = p.matcher(email).matches();
        if(isMatch)
            this.email = email;
        else
            throw new Exception("Email invalide");
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return login + " - " + password + " - " +status.getName() + " - " +status.getCode();
    }
}
