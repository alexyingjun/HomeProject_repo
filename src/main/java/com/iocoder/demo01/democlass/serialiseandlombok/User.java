package com.iocoder.demo01.democlass.serialiseandlombok;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.*;

@EqualsAndHashCode(exclude={"gender"})
public class User implements Externalizable {
    private String userName ;
    private String gender ;
    private String age ;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        //将 userName 和 gender 写入二进制流
        out.writeObject(this.userName);
        out.writeObject(this.gender);
        out.writeObject(this.age);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        //从二进制流中反写  userName 和 gender
        this.userName = in.readObject().toString();
        this.gender = in.readObject().toString();
        this.age = in.readObject().toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.userName);
        out.writeObject(this.gender);
        out.writeObject(this.age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.userName = in.readObject().toString();
        this.gender = in.readObject().toString();
        this.age = in.readObject().toString();
    }

    public static void printdata(@NonNull User object1)
    {
        System.out.println("name = " + object1.userName);
        System.out.println("gener = " + object1.gender);
        System.out.println("age = " + object1.age);
    }
}