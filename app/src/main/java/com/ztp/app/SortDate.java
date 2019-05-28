package com.ztp.app;

import com.ztp.app.Utils.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SortDate {

    private List<HashMap<String, String>> data = new ArrayList<>();

    public static void main(String[] args) {
        new SortDate();
    }

    private SortDate() {

        data = getData();

        for (int i = 0; i < data.size() - 1; i++) {
            for (int j = 0; j < data.size() - i - 1; j++) {

                HashMap<String, String> map1 = data.get(j);
                String dateLowString = map1.get("date").split(" ")[0];
                String timeLowString = map1.get("date").split(" ")[1];

                HashMap<String, String> map2 = data.get(j + 1);
                String dateHighString = map2.get("date").split(" ")[0];
                String timeHighString = map2.get("date").split(" ")[1];

                Date dateLow = Utility.convertStringToDateWithoutTime(dateLowString);
                Date dateHigh = Utility.convertStringToDateWithoutTime(dateHighString);


                if (dateLow.before(dateHigh)) {
                    Collections.swap(data, j, j + 1);
                } else {
                    int hour1 = Integer.parseInt(timeLowString.split(":")[0]);
                    int minute1 = Integer.parseInt(timeLowString.split(":")[1]);
                    int seconds1 = Integer.parseInt(timeLowString.split(":")[2]);

                    int tempStart = (60 * minute1) + (3600 * hour1) + seconds1;

                    int hour2 = Integer.parseInt(timeHighString.split(":")[0]);
                    int minute2 = Integer.parseInt(timeHighString.split(":")[1]);
                    int seconds2 = Integer.parseInt(timeHighString.split(":")[2]);

                    int tempEnd = (60 * minute2) + (3600 * hour2) + seconds2;

                    if (tempStart > tempEnd) {
                        Collections.swap(data, j, j + 1);
                    }
                }
            }
        }

        System.out.println(data);
    }

    private List<HashMap<String, String>> getData() {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("name", "Event1");
        map1.put("date", "05-28-2019 12:23:12");
        data.add(map1);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("name", "Event2");
        map2.put("date", "05-28-2019 06:23:13");
        data.add(map2);
        HashMap<String, String> map3 = new HashMap<>();
        map3.put("name", "Event3");
        map3.put("date", "05-28-2019 11:25:45");
        data.add(map3);
        HashMap<String, String> map4 = new HashMap<>();
        map4.put("name", "Event4");
        map4.put("date", "05-28-2019 05:34:46");
        data.add(map4);
        HashMap<String, String> map5 = new HashMap<>();
        map5.put("name", "Event5");
        map5.put("date", "05-28-2019 19:45:42");
        data.add(map5);

        return data;
    }
}
