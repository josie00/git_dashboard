
from datetime import datetime, timedelta
from subprocess import call
if __name__ == "__main__":
    now = datetime.today() - timedelta(days=1)
    date = str(now.year) + "-" + str(now.month) + "-" + str(now.day);
    for x in range(0, 1):
        os.system("wget http://data.gharchive.org/" + date +"-{0..23}.json.gz -O ./test_data")
