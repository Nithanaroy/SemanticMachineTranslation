# Libraries intialization
import urllib
import re

file = open("EnglishVerbList.txt","w")
allVerbs = list()

url = "https://verbs.colorado.edu/propbank/framesets-english/"
htmlfile = urllib.urlopen(url)
htmltext = htmlfile.read()

regex = '<a href=".*".*> (.*)-v.html</a>'
pattern = re.compile(regex)
span = re.findall(pattern,htmltext)

for verb in span:
    file.write(verb)
    file.write("\n")

file.close()