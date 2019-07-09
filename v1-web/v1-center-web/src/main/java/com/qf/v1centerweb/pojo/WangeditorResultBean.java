package com.qf.v1centerweb.pojo;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/14
 */
public class WangeditorResultBean {

    private String errno;
    private String[] data;

    public WangeditorResultBean() {
    }

    public WangeditorResultBean(String errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
