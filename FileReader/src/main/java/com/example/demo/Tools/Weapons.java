package com.example.demo.Tools;

public class Weapons {
    private long id;
    private String subject;
    private String predict;
    private String object;

    public void setSubject(String s) {
        subject = s;
    }

    public String getSubject() {
        return subject;
    }

    public void setPredict(String s) {
        predict = s;
    }

    public String getPredict() {
        return predict;
    }

    public void setObject(String s) {
        object = s;
    }

    public String getObject() {
        return object;
    }

    @Override
    public String toString() {
        return String.format("{Weapon: subject=%s, predict=%s, object=%d}", this.subject, this.predict,
                this.object);
    }

    public void getRDF() {
        System.out.println("(" + subject + ", " + predict + ", " + object + ")");
    }
}

