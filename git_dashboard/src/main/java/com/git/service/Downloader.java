package com.git.service;

import java.io.IOException;

public class Downloader implements Runnable{
    private String cmd;
    public Downloader (String cmd) {
        this.cmd = cmd;
    }

    @Override
    public void run() {
        try {
            System.out.println("this" + cmd);
            Process p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", this.cmd});
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
