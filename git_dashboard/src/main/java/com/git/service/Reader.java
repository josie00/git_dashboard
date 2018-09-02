package com.git.service;

import com.git.model.Repo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONException;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Reader implements Runnable {
    private String date;
    private Map<String, Integer> map;
    public Reader(String date, Map<String, Integer> map) {
        this.date = date;
        this.map = map;
    }

    @Override
    public void run() {
        String filename;
        JSONParser parser = new JSONParser();
        for (int i = 0; i < 1; i++) {
            System.out.println("i: " + i);
            filename = "./test_data/"+date+"-"+i+".json";
            try {
                Scanner s = new Scanner(new File(filename));
                while(s.hasNextLine()) {
                    String line = s.nextLine();
                    try {
                        Object obj = parser.parse(line);
                        JSONObject record = (JSONObject) obj;
                        String type = (String)record.get("type");
                        if(type.equals("ForkEvent") || type.equals("PullRequestEvent")) {
                            JSONObject repo = (JSONObject)record.get("repo");
                            String name = (String)repo.get("name");
                            String url = (String)repo.get("url");
                            String key = name + "***" + url;
                            synchronized (this) {
                                map.put(key, map.getOrDefault(key, 0)+1);
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }




            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
