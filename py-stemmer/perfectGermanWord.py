__author__ = 'Ramakrishna'

# Libraries intialization
import urllib
import re
from collections import Counter


#Function to pick up the right frequent word
def perfectWord(text):
    #Extracting base word and tense from text provided
    pos = text.find('####')
    base_word = text[:pos]
    tense = text[pos+4:]

    #Variable declarations
    list_tenses = ['present','perfect','past','pluperfect','future']
    all_tenses = dict()
    list_div=3

    #Extracting data from URL
    url = "http://www.verbix.com/webverbix/German/"+base_word+".html"
    htmlfile = urllib.urlopen(url)
    htmltext = htmlfile.read()

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

#Print all the acquired data
print perfectWord('sein####perfect')