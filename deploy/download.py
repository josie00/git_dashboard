import os
from datetime import datetime, timedelta
from subprocess import call
if __name__ == "__main__":
    now = datetime.today()
    os.system("mkdir test_data")
    for x in range(0, 7):
        now = now - timedelta(days=1)
        month = str(now.month)
        day = str(now.day)
        if(len(month) == 1):
            month = "0" + month
        if (len(day) == 1):
            day = "0" + day
        date = str(now.year) + "-" + month + "-" + day;
        for y in range(0, 24):
            os.system("wget http://data.gharchive.org/" + date +"-" + str(y) + ".json.gz -P ./test_data")
            os.system("gunzip ./test_data/" + date+"-" + str(y) + ".json.gz")


