package main;

import java.util.HashMap;

import module.graph.resources.NamedEntityTagger;

public class TestKparser {
	public static void main(String[] args) {
		// SentenceToGraph stg = new SentenceToGraph();
		// String sent = "John loves Mia.";
		// GraphPassingNode gpn = stg.extractGraph(sent, false, true, false);
		// for(String s : gpn.getAspGraph()){
		// System.out.println(s);
		// }

		// ParserHelper ph = new ParserHelper();
		// System.out.println(ph.getJsonString(sent, false));
		//
		// MakeGraph mg = new MakeGraph();
		// ArrayList<NodePassedToViewer> list = mg.createGraphUsingSentence(sent, false, true, false);
		// System.out.println(list);
		//
		// DependencyParserResource dr = new DependencyParserResource();
		// InputDependencies iDeps= dr.extractDependencies("John loves Mia.", false, 0);
		// System.out.println();

		// JAWSutility j = new JAWSutility();
		// String baseForm = j.getBaseForm("ate", "v");
		// System.out.println("Done");

		NamedEntityTagger.tagNamedEntities("John loves Mia");
		String sent = NamedEntityTagger.getModifiedSentence();
		HashMap<String, String> sMap = NamedEntityTagger.getStringToNamedEntityMap();
		System.exit(0);
	}
}
