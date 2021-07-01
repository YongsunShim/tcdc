package bike.snu.ac.kr.tcdc.data;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bike.snu.ac.kr.tcdc.utils.TextPreprocess;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

public class DocSentResults {

	public Map<Integer, List<Map<String, List<String>>>> docResults(Map<Integer, String> setMap) {
		Map<Integer, List<Map<String, List<String>>>> results = new HashMap<Integer, List<Map<String,List<String>>>>();
		
		for(int key : setMap.keySet()) {
			String s = setMap.get(key);
			
			List<Map<String, List<String>>> mapList = new ArrayList<Map<String,List<String>>>();
			Map<String, List<String>> nlMap = new HashMap<String, List<String>>();
			
			String[] split = s.split("\t");
			String abs = split[4];
			
			Document doc = new Document(abs);
			List<Sentence> sentences = doc.sentences();
			
			TextPreprocess tp = new TextPreprocess();
			List<String> textSet = tp.textPreprocessing(sentences);
			
			nlMap.put("naturalLanguage", textSet);
			
			mapList.add(nlMap);
			
			results.put(key, mapList);
			
		}
		
		return results;
	}

	public Map<Integer, List<Map<String, List<String>>>> sentResults(Map<Integer, String> setMap) {
		Map<Integer, List<Map<String, List<String>>>> results = new HashMap<Integer, List<Map<String,List<String>>>>();
		
		for(int key : setMap.keySet()) {
			String s = setMap.get(key);
			
			List<Map<String, List<String>>> mapList = new ArrayList<Map<String,List<String>>>();
			Map<String, List<String>> nlMap = new HashMap<String, List<String>>();
			
			String[] split = s.split("\t");
			String drug1 = split[0];
			String drug2 = split[1];
			String abs = split[4];
			
			String sent = "";
			BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
			iterator.setText(abs);
			int start = iterator.first();
			for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
				String sentence = abs.substring(start, end);
				
				if(checkContain(drug1, drug2, sentence) == true) {
					sent = sent + sentence + " ";
				}
			}
			
			Document doc = new Document(sent.trim());
			List<Sentence> sentences = doc.sentences();
			
			TextPreprocess tp = new TextPreprocess();
			List<String> textSet = tp.textPreprocessing(sentences);
			
			nlMap.put("naturalLanguage", textSet);
			
			mapList.add(nlMap);
			
			results.put(key, mapList);
			
		}
		
		return results;
	}
	
	private static boolean checkContain(String drug1, String drug2, String sentence) {
		boolean check = false;
		
		boolean d1Check = false;
		boolean d2Check = false;
		
		String[] words = sentence.split(" ");
		
		for(String word : words) {
			if(word.equals(drug1)) {
				d1Check = true;
			}
			if(word.equals(drug2)) {
				d2Check = true;
			}
		}
		
		if(d1Check == true && d2Check == true) {
			check = true;
		}
		
		
		return check;
	}
	
}
