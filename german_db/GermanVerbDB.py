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
    htmltext=htmltext.replace('&ouml;','ö')
    htmltext=htmltext.replace('&Ouml;','Ö')
    htmltext=htmltext.replace('&auml;','ä')
    htmltext=htmltext.replace('&Auml;','Ä')
    htmltext=htmltext.replace('&uuml;','ü')
    htmltext=htmltext.replace('&Uuml;','É')
    htmltext=htmltext.replace('&uuml;','é')
    htmltext=htmltext.replace('&Uuml;','Ü')
    htmltext=htmltext.replace('&szlig;','ß')

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
# eg: töten
print perfectWord('beißen####VB')