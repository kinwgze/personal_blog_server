package zeee.blog.demo.entity;

/**
 * @author zeee
 * 2022/4/6
 */
public class User {

    private String username;

    private Integer level;

    private String phonenumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", level=" + level +
                ", phoneNumber='" + phonenumber + '\'' +
                '}';
    }
}
