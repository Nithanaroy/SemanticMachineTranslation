# http://www.nltk.org/api/nltk.stem.html#module-nltk.stem


import nltk
from nltk.stem.porter import PorterStemmer
from nltk.stem.lancaster import LancasterStemmer
from nltk.stem.snowball import DanishStemmer
from nltk.stem import WordNetLemmatizer

def porter(words):
	p = PorterStemmer()
	return [p.stem(w) for w in words]

def porter_single(w):
	p = PorterStemmer()
	return p.stem(w)


def lancaster(words):
	l = LancasterStemmer()
	return [l.stem(w) for w in words]

def lancaster_single(w):
	l = LancasterStemmer()
	return l.stem(w)


def snowball(words):
	s = DanishStemmer()
	return [s.stem(w) for w in words]

def snowball_single(w):
	s = DanishStemmer()
	return s.stem(w)


def wordnet(words):
	wnl = WordNetLemmatizer()
	return [wnl.lemmatize(w) for w in words]

def wordnet_single(w):
	wnl = WordNetLemmatizer()
	return wnl.lemmatize(w)


