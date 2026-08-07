package model;

/**
 * Donor entity matching the reference project structure.
 */
public class Donor {
    private String name;
    private String age;
    private String sex;
    private String email;
    private String pn;  // Phone number
    private String adr; // Address
    private String bg;  // Blood group

    public Donor() {}

    public Donor(String name, String age, String sex, String email, String pn, String adr, String bg) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.pn = pn;
        this.adr = adr;
        this.bg = bg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    @Override
    public String toString() {
        return "Name: " + name + ",\nAge: " + age + ",\nSex: " + sex + ",\nEmail: " + email + ",\nPhone Number: " + pn + ",\nAddress: " + adr + ",\nBlood Group: " + bg;
    }
}
