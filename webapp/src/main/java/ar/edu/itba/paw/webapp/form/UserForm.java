package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by juanfra on 27/03/17.
 */
public class UserForm {
    public int getMinUsernameLenght() {
        return minUsernameLenght;
    }

    public int getMaxUsernameLenght() {
        return maxUsernameLenght;
    }

    public int getMinPassLenght() {
        return minPassLenght;
    }

    public int getMaxPassLenght() {
        return maxPassLenght;
    }

    public String getUsernameRegex() {
        return usernameRegex;
    }

    public String getPassRegex() {
        return passRegex;
    }

    private final static int  minUsernameLenght = 4;
    private final static int  maxUsernameLenght = 20;
    private final static int  minPassLenght = 4;
    private final static int  maxPassLenght = 30;

    private final static String  usernameRegex = "[a-zA-Z0-9]+";
    private final static String  passRegex = "[a-zA-Z0-9]+";


    @Size(min = minUsernameLenght , max = maxUsernameLenght)
    @Pattern(regexp = usernameRegex)
    private String username;

    @Size(min = minPassLenght, max = maxPassLenght)
    @Pattern(regexp = passRegex)
    private String password;

    @Size(min = minPassLenght, max = maxPassLenght)
    @Pattern(regexp = passRegex)
    private String repeatPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
