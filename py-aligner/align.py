import nltk
import os
from nltk.corpus.util import LazyCorpusLoader
from nltk.corpus.europarl_raw import german, english
from nltk.corpus.reader import AlignedCorpusReader
from nltk.translate import AlignedSent, Alignment
from nltk.corpus.reader.plaintext import PlaintextCorpusReader


def align(filename):

	files = filename.split('(')
	ripe_file = os.path.abspath(files[1])
	raw_file = os.path.abspath(files[0])
	raw_for_nltk = os.path.abspath('../newcorpus/source.txt')
	with open(files[0]) as f:
		with open(raw_for_nltk,"w") as f1:
			for line in f:
				f1.write(line)

	corpusdir = '../newcorpus/'
	newcorpus = PlaintextCorpusReader(corpusdir, '.*',sent_tokenizer=nltk.data.LazyLoader('tokenizers/punkt/german.pickle'))
	out = open(ripe_file, "w")
	i = 0
	temp =[]
	temp.append(newcorpus.sents(raw_for_nltk))
	tempVal = str(temp[i])
	tempVal = tempVal.replace(",", "")
	tempVal = tempVal.replace("u'", "")
	tempVal = tempVal.replace("'", "")
	tempVal = tempVal.replace("[", "")
	tempVal = tempVal.replace("]", "")
	out.write(tempVal+os.linesep)
	out.close()
	return


