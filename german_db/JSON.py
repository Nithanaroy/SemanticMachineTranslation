#!/usr/bin/python
# -*- coding: iso-8859-15 -*-
# -*- coding: utf-8 -*-
# Use latin-1 or iso-8859-15 for encoding format

# Libraries intialization
import urllib
import re
import codecs
from collections import Counter

# Read and Write files
fileRead = codecs.open("GermanVerbList.txt",encoding='utf-8')
# fileRead = open("GermanVerbList.txt")
fileData = fileRead.readlines()

all_verbs = dict()

# Function to pick up the right frequent word (e.g sein)
def perfectWord(base_word):

    #Variable declarations
    all_cases = ['indicative','conjunctive','conditional']
    all_tenses = ['present','perfect','past','pluperfect','future','futureperfect']
    list_div = 3

    #Extracting data from URL
    url = "http://www.verbix.com/webverbix/German/"+base_word+".html"
    url = url.encode('iso-8859-15')
    print url

    htmlfile = urllib.urlopen(url)
    htmltext = htmlfile.read()

    htmltext=htmltext.replace('&ouml;','ö')
    htmltext=htmltext.replace('&Ouml;','Ö')
    htmltext=htmltext.replace('&auml;','ä')
    htmltext=htmltext.replace('&Auml;','Ä')
    htmltext=htmltext.replace('&uuml;','ü')
    htmltext=htmltext.replace('&Uuml;','É')
    htmltext=htmltext.replace('&uuml;','é')
    htmltext=htmltext.replace('&Uuml;','Ü')
    htmltext=htmltext.replace('&szlig;','ß')

    #Pre Regular expression to pick only tenses related data
    preRegex = '<title>(.+?)</title>'
    prePattern = re.compile(preRegex)
    preSpan = re.findall(prePattern,htmltext)
    print preSpan

    if (not preSpan) or ('not a verb' in preSpan[0])!= 1:
        #Regular expression to pick only tenses related data
        regex = '<span[^>]*>(.+?)</span>'
        pattern = re.compile(regex)
        span = re.findall(pattern,htmltext)
        # print span

        #Assignment of data in a dictionary with list of tenses
        outer_loop = 6
        for case in all_cases:
            all_verbs[case]=dict()
            for tense in all_tenses[:outer_loop]:
                # print all_tenses[:outer_loop]
                inner_loop = 6
                all_verbs[case][tense]=dict()
                while inner_loop>0:
                    if len(span)>20:
                        if span[list_div] != 'sie':
                            all_verbs[case][tense][span[list_div]]=span[list_div+1]
                            list_div=list_div+2
                        else:
                            all_verbs[case][tense][span[list_div]]=span[list_div+2]
                            list_div=list_div+3
                        inner_loop=inner_loop-1
            outer_loop=outer_loop-2
        # print all_verbs
        return len(span)


# Print all the acquired data
# eg: töten beißen


# Write fucntionality of the verb
for verb in fileData:
    if fileData.index(verb)!=0:
        if perfectWord(verb[:-2])>20:
            fileWrite = open('GermanVerbs/'+verb[:-2]+'.txt',"w")
            fileWrite.write(str(all_verbs))
            fileWrite.close()
        else:
            continue

# Test functionality
# perfectWord('haben')