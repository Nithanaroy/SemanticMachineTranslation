#!/usr/bin/python
# -*- coding: iso-8859-15 -*-
# Use latin-1 or iso-8859-15 for encoding format

# Import libraries for json and url operations
import urllib
import json

# Read and Write file operations
fileRead = open("test.txt","r")
fileData = fileRead.readlines()
fileWrite = open("GermanVerbList.txt","w")
allGermanVerbs = list()

# Iteration over all the English verbs available
for verb in fileData:
    input = verb[:-1]
    print verb
    url = 'https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20151025T082334Z.d1c51ebb15e03fc9.b9f5b9c541bd619e3a4fe3ffbb2823fd23fa2c81&lang=en-de&text='+input
    weburl = urllib.urlopen(url)
    data = weburl.read()
    theJSON = json.loads(data)
    definition = theJSON["def"]

    for deflayer in definition:
        if deflayer.has_key("pos") and deflayer["pos"]=="verb":
            for trlayer in deflayer["tr"]:
                if trlayer.has_key("pos") and trlayer["pos"]=="verb":
                    str = trlayer["text"]
                    allGermanVerbs.append(str)
                else:
                    continue
        else:
            continue


for verb in allGermanVerbs:
    print verb
    newVerb = unicode(verb).encode('utf-8')
    fileWrite.write(newVerb)
    fileWrite.write('\n')



# Sample jason format to be parsed
"""
{
"head":{},
"def":[{"text":"kill",
        "pos":"verb",
        "ts":"k?l",
        "tr":[{"text":"töten",
                "pos":"verb",
                "syn":[{"text":"umbringen",
                        "pos":"verb"}],
                "mean":[{"text":"slay"},
                        {"text":"kill myself"}]},
               {"text":"Ten",
                "pos":"noun",
                "gen":"n",
                "mean":[{"text":"killing"}]},
               {"text":"tet","pos":"verb"}]},
        {"text":"kill",
         "pos":"noun",
         "ts":"k?l",
         "tr":[{"text":"Tötung",
                "pos":"noun",
                "gen":"f",
                "mean":[{"text":"killing"}]}]
        }]
}
"""