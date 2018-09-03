package com.git.controller;

import com.git.service.ChartReader;
import com.git.service.Helper;
import com.git.service.Reader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.git.model.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GitController {


	@GetMapping("/top_repos")
	public String getTopRepo(Model model) {

		return "result";
	}
	
	@GetMapping("/top_result")
	public String getTopResult(Model model) {
		LocalDate today = LocalDate.now();
		List<String> week = new ArrayList<>();


		Map<String, Integer> result = new HashMap<>();

		List<Thread> threads = new ArrayList<>();

		for (int i = 0; i < 7; i++) {
			today = today.minusDays(1);
            System.out.println(today.toString());
			week.add(today.toString());
            Thread t = new Thread(new Reader(today.toString(), result));
            threads.add(t);
            t.start();
		}

        for(int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(result.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return o2.getValue().compareTo(o1.getValue());

            }
        });

        List<Repo> repoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Repo r = new Repo();
            String[] line = list.get(i).getKey().trim().split("\\*\\*\\*");
            r.setFullName(line[0]);
            r.setUrl(line[1]);
            r.setForkAndPull(list.get(i).getValue());
            repoList.add(r);
        }

        model.addAttribute("list", repoList);
		return "result";
	}

	@GetMapping("/getTrending")
    public String getTrending(Model model, @RequestParam("name") String name) {
	    System.out.println(name);

        return "graph";
    }

    @GetMapping("/getTrendingData")
    @ResponseBody
    public Map<String, Integer> getTrendingData(Model model, @RequestParam("name") String name) {
        Map<String, Integer> result = new TreeMap<>();

        LocalDate today = LocalDate.now();
        List<String> week = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            today = today.minusDays(1);
            System.out.println(today.toString());;
            result.put(today.toString(), 0);
            Thread t = new Thread(new ChartReader(today.toString(), result, name));
            threads.add(t);
            t.start();
        }

        for(int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}
