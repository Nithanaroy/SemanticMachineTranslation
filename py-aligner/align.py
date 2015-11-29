import nltk
import os
from nltk.corpus.util import LazyCorpusLoader
from nltk.corpus.europarl_raw import german, english
from nltk.corpus.reader import AlignedCorpusReader
from nltk.translate import AlignedSent, Alignment
from nltk.corpus.reader.plaintext import PlaintextCorpusReader


def align(filename):

	files = filename.split('(') 
	with open(files[0]) as f:
		with open("/newcorpus/source.txt","w") as f1:
			for line in f:
				f1.write(line)

	corpusdir = './newcorpus/'
	newcorpus = PlaintextCorpusReader(corpusdir, '.*')
	out = open(files[1], "w")
	i = 0
	temp =[]
	temp.append(newcorpus.sents(('source.txt')))
	tempVal = str(temp[i])
	tempVal = tempVal.replace(",", "")
	tempVal = tempVal.replace("u'", "")
	tempVal = tempVal.replace("'", "")
	tempVal = tempVal.replace("[", "")
	tempVal = tempVal.replace("]", "")
	out.write(tempVal+os.linesep)
	out.close()
	return



