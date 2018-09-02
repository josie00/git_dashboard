package com.git.model;

public class Repo {
    private String fullName;
    private String desc;
    private String url;
    private int forkAndPull;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getForkAndPull() {
        return forkAndPull;
    }

    public void setForkAndPull(int forkAndPull) {
        this.forkAndPull = forkAndPull;
    }
}
