package com.git.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Helper {
    public static void read(String date,  Map<String, Integer> map) {
        String filename;
        JSONParser parser = new JSONParser();
        for (int i = 0; i < 23; i++) {
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
                            map.put(key, map.getOrDefault(key, 0)+1);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }




            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
