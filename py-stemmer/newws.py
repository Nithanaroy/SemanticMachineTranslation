__author__ = 'Ramakrishna'

# Libraries intialization
import urllib
import re
from collections import Counter

#Variable declarations
pronoun = ['ich','du','er','wir','ihr','sie','Sie']
tense = ['present','perfect','past','pluperfect','future']
all_tenses = dict()
base_word="kommen"
list_div=3
j=0


#Extracting data from URL
url = "http://www.verbix.com/webverbix/German/"+base_word+".html"
htmlfile = urllib.urlopen(url)
htmltext = htmlfile.read()

#Regular expression to pick only tenses related data
regex = '<span[^>]*>(.+?)</span>'
pattern = re.compile(regex)
span = re.findall(pattern,htmltext)

#Assignment of data in a dictionary with list of tenses
for tens in tense:
    all_tenses[tens]=span[list_div:list_div+13]
    list_div=list_div+13

#Fuction to pick up the right specific word
def perfectSpecificWord(tense, pn):
    j=all_tenses[tense].index(pn)
    if pn!="sie":
        return all_tenses[tense][j+1]
    else:
        return all_tenses[tense][j+2]

#Function to pick up the right frequent word
def perfectWord(tense):
    group = Counter(all_tenses[tense])
    return group.most_common(1)[0][0]

#Print all the acquired data
print all_tenses
print perfectSpecificWord('perfect','sie')
print perfectWord('perfect')