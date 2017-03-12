package com.xiao.bean;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by xiaojie on 17/3/12.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String userId;
    private SortedMap<String, Long> record = new TreeMap<String, Long>();

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SortedMap<String, Long> getRecord() {
        return record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId.equals(user.userId);

    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", record=" + record +
                '}';
    }
}
