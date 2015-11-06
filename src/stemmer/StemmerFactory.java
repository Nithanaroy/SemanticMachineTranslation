package stemmer;

public class StemmerFactory {

	public static Stemmer getStemmer(StemmerType type, String[] words) {
		switch (type) {
		case Porter:
			return new Porter(words);
		case Snowball:
			return new Snowball(words);
		case WordNet:
			return new WordNet(words);
		case Lancaster:
			return new Lancaster(words);
		}
		throw new IllegalArgumentException("Stemmer type was not found");
	}
}
