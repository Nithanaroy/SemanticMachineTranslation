#!/usr/bin/env python
# -*- coding: latin-1 -*-

import os
import re
import urllib2
import json
import codecs
import base64

# Function to map input tense with the tense presen tin verbix.com
def tenseMapping(inTense):
    # 27. VB  Verb, base form

    # 28. VBD Verb, past tense
    # 30. VBN Verb, past participle

    # 29. VBG Verb, gerund or present participle
    # 31. VBP Verb, non-3rd person singular present
    # 32. VBZ Verb, 3rd person singular present
    if(inTense == 'VB' or inTense == 'VBG' or inTense == 'VBP' or inTense == 'VBZ'):
        return 'Present'
    elif(inTense == 'VBD' or inTense == 'VBN'):
        return 'Past'
    else:
        return 'Present'


# Finds the required German word for a given German word and tense in the form <<WORD>>####<<TENSE>>. Eg: sein####VBD
def wordInTense(text):
    word, _tense = text.split('####')
    tense = tenseMapping(_tense)

    filename = '/Volumes/350GB/Projects/GermanTenseDictionary/german_verbs_jsons/' + base64.urlsafe_b64encode(word.encode('utf-8')) + '.json'
    filepath = os.path.abspath(filename)
    f = open(filepath)
    contents = f.read()
    f.close()
    tenses = json.loads(contents)
    germanTenses = tenses['Indicative'][tense].values()
    return max(set(germanTenses), key=germanTenses.count)   # Most frequent
