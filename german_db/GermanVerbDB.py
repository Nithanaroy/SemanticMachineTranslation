#!/usr/bin/python
# -*- coding: iso-8859-15 -*-
# Use latin-1 or iso-8859-15 for encoding format

# Libraries intialization
import urllib
import re
from collections import Counter

# Function to map input tense with the tense presen tin verbix.com
def tenseMapping(inTense):
    if(inTense == 'VB' or inTense == 'VBG' or inTense == 'VBP' or inTense == 'VBZ'):
        return 'present'
    elif(inTense == 'VBD' or inTense == 'VBN'):
        return 'past'
    else:
        return 'present'


# Function to pick up the right frequent word (e.g sein####present)
def perfectWord(text):
    #Extracting base word and tense from text provided
    pos = text.find('####')
    base_word = text[:pos]
    inTense = text[pos+4:]
    tense = tenseMapping(inTense)

    #Variable declarations
    list_tenses = ['present','perfect','past','pluperfect','future']
    all_tenses = dict()
    list_div=3

    #Extracting data from URL
    url = "http://www.verbix.com/webverbix/German/"+base_word+".html"
    print url
    htmlfile = urllib.urlopen(url)
    htmltext = htmlfile.read()
    htmltext=htmltext.replace('&ouml;','�')
    htmltext=htmltext.replace('&Ouml;','�')
    htmltext=htmltext.replace('&auml;','�')
    htmltext=htmltext.replace('&Auml;','�')
    htmltext=htmltext.replace('&uuml;','�')
    htmltext=htmltext.replace('&Uuml;','�')
    htmltext=htmltext.replace('&uuml;','�')
    htmltext=htmltext.replace('&Uuml;','�')
    htmltext=htmltext.replace('&szlig;','�')

    #Regular expression to pick only tenses related data
    regex = '<span[^>]*>(.+?)</span>'
    pattern = re.compile(regex)
    span = re.findall(pattern,htmltext)

    #Assignment of data in a dictionary with list of tenses
    for tence in list_tenses:
        all_tenses[tence]=span[list_div:list_div+13]
        list_div=list_div+13

    group = Counter(all_tenses[tense])
    return group.most_common(1)[0][0]

# Print all the acquired data
# eg: t�ten
print perfectWord('bei�en####VB')