import os
from datetime import datetime, timedelta
from subprocess import call
if __name__ == "__main__":
    now = datetime.today()
    for x in range(0, 2):
        now = now - timedelta(days=1)
        month = str(now.month)
        day = str(now.day)
        if(len(month) == 1):
            month = "0" + month
        if (len(day) == 1):
            day = "0" + day
        date = str(now.year) + "-" + month + "-" + day;
        os.system("wget http://data.gharchive.org/" + date +"-{0..23}.json.gz -P ./test_data")
        os.system("gunzip ./test_data/" + date+"-{0..23}.json.gz")


