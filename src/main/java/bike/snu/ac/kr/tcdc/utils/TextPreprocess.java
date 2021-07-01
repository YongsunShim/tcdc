package bike.snu.ac.kr.tcdc.utils;

import java.util.List;
import java.util.Vector;

import edu.stanford.nlp.simple.Sentence;

public class TextPreprocess {
	public List<String> textPreprocessing (List<Sentence> sentences) {
		StopwordsList sw = new StopwordsList();
		List<String> stopwords = sw.stopwords();
		
		List<String> list = new Vector<String>();
		
		for (Sentence sent : sentences) {
			for(String w : sent.lemmas()) {
				if(!stopwords.contains(w.toLowerCase())) {
					list.add(w.toLowerCase());
				}
			}
		}
		
		return list;
	}
}
