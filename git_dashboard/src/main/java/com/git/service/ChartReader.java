package com.git.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class ChartReader implements Runnable {
    private String date;
    private Map<String, Integer> map;
    private String repoName;
    public ChartReader(String date, Map<String, Integer> map, String repoName) {
        this.date = date;
        this.map = map;
        this.repoName = repoName;
    }
    @Override
    public void run() {
        String filename;
        JSONParser parser = new JSONParser();

        for (int i = 0; i < 24; i++) {
            filename = "./test_data/"+date+"-"+i+".json";
            try {
                Scanner s = new Scanner(new File(filename));
                while(s.hasNextLine()) {
                    String line = s.nextLine();
                    try {
                        Object obj = parser.parse(line);
                        JSONObject record = (JSONObject) obj;
                        JSONObject repo = (JSONObject)record.get("repo");
                        String name = (String)repo.get("name");
                        String type = (String)record.get("type");
                        if(name.equals(repoName) && (type.equals("ForkEvent") || type.equals("PullRequestEvent"))) {
                            synchronized (this) {
                                map.put(date, map.getOrDefault(date, 0)+1);
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
