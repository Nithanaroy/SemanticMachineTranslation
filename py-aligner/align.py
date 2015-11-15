import nltk
import os
from nltk.corpus.util import LazyCorpusLoader
from nltk.corpus.europarl_raw import german, english
from nltk.corpus.reader import AlignedCorpusReader
from nltk.translate import AlignedSent, Alignment

comtrans = LazyCorpusLoader(
    'comtrans', AlignedCorpusReader, r'(?!\.).*\.txt',
     encoding='iso-8859-1')
out = open("alignedtext.txt", "w")
i = 0
temp =[]
while i<20:
	temp.append(comtrans.aligned_sents('alignment-de-en.txt')[i].words)
	tempVal = str(temp[i])
	tempVal = tempVal.replace(",", "")
	tempVal = tempVal.replace("u'", "")
	tempVal = tempVal.replace("'", "")
	tempVal = tempVal.replace("[", "")
	tempVal = tempVal.replace("]", "")
	out.write(tempVal+os.linesep)
	i += 1
out.close()



