package edu.gcc.comp350.jmeg;

public class User {
    private final String name;
    private final String major;
    private final String minor;

    public User(String name, String major, String minor){
        this.name = name;
        this.major = major;
        this.minor = minor;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;

        if (this == user) {
            return true;
        }

        return this.name.equals(user.name)
                && this.major.equals(user.major)
                && this.minor.equals(user.minor);
    }
}
