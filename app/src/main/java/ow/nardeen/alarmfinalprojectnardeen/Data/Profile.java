package ow.nardeen.alarmfinalprojectnardeen.Data;

public class Profile
{
    public static String phoneNumber="";
    private String  FirstName, SecondName;
    private String owner, key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", SecondName='" + SecondName + '\'' +
                ", owner='" + owner + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
