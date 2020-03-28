#!/usr/bin/python3
from bs4 import BeautifulSoup
import requests
from pprint import pprint
import re
import json

comm = re.compile("<!--|-->")

def make_soup(url):
    file = open(url)
    soupdata = BeautifulSoup(file.read(), 'html.parser')
    return soupdata

def get_html(url):
    result = requests.get(url)

    if result.status_code == 200:
        return result.text
    else:
        print("Fetching", url, "failed.")
        return

def get_fish():
    soup = make_soup("scripts/fish.html")
    allFish = []

    table = soup.find('table')
    table_body = table.find('tbody')
    rows = table_body.find_all('tr')
    for row in rows:
        columns = row.find_all('td')
        fish = {}
        fish['name'] = columns[0].text.strip()
        fish['image'] = columns[1].find('a')['href']
        fish['price'] = columns[2].text.strip()
        fish['location'] = columns[3].text.strip()
        fish['size'] = columns[4].text.strip()
        fish['startHour'], fish['endHour'] = get_start_and_end_time(columns[5].text.strip())
        fish['northernHemisphereMonths'], fish['southernHemisphereMonths'] = get_months(columns[6:])
        allFish.append(fish)

    print("Found", str(len(allFish)), "fish")
    return allFish

def get_bugs():
    soup = make_soup("scripts/bugs.html")
    allBugs = []

    table = soup.find('table')
    table_body = table.find('tbody')
    rows = table_body.find_all('tr')
    for row in rows:
        columns = row.find_all('td')
        bug = {}
        bug['name'] = columns[0].text.strip()
        bug['image'] = None if (not columns[1].find('a')) else columns[1].find('a')['href']
        bug['price'] = columns[2].text.strip()
        bug['location'] = columns[3].text.strip()
        bug['startHour'], bug['endHour'] = get_start_and_end_time(columns[4].text.strip())
        bug['northernHemisphereMonths'], bug['southernHemisphereMonths'] = get_months(columns[5:])
        allBugs.append(bug)

    print("Found", str(len(allBugs)), "bugs")
    return allBugs

def get_months(monthColumns):
    months = [ele.text.strip() for ele in monthColumns]
    monthMap = [{'available':x, 'month':i+1} for i,x in enumerate(months)]
    northern = list(map(lambda x: x['month'], filter(lambda x: x['available'] == "✓", monthMap)))
    southern = sorted(list(map(lambda x: 12 if (x == 6) else (x + 6) % 12, northern)))
    return northern, southern

def get_start_and_end_time(timeText):
    if timeText.lower() == "all day":
        return 0, 24
    else:
        split = timeText.lower().replace(" ", "").split('-')
        start = split[0]
        startHour = int(start[0]) + 12 if ("pm" in start) else int(start[0])
        end = split[-1]
        endHour = int(end[0]) + 12 if ("pm" in end) else int(end[0])
        return startHour, endHour

def save_json(filepath, data):
    print("Saved JSON to: %s" % filepath)
    with open(filepath, "w", encoding="utf-8", newline="\n") as f:
        json.dump(data, f, sort_keys=False, indent=2, separators=(',', ': '))

save_json("scripts/fish.json", get_fish())
save_json("scripts/bugs.json", get_bugs())
