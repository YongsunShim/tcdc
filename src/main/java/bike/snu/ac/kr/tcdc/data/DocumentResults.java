package bike.snu.ac.kr.tcdc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bike.snu.ac.kr.tcdc.utils.TextPreprocess;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

public class DocumentResults {
	public Map<Integer, List<Map<String, List<String>>>> results(Map<Integer, String> setMap) {
		Map<Integer, List<Map<String, List<String>>>> results = new LinkedHashMap<Integer, List<Map<String,List<String>>>>();
		
		for(int key : setMap.keySet()) {
			String s = setMap.get(key);
			
			List<Map<String, List<String>>> mapList = new ArrayList<Map<String,List<String>>>();
			Map<String, List<String>> nlMap = new HashMap<String, List<String>>();
			
			String[] split = s.split("\t");
			String abs = split[2];
			
			Document doc = new Document(abs);
			List<Sentence> sentences = doc.sentences();
			
			TextPreprocess tp = new TextPreprocess();
			List<String> nlPattenrs = tp.textPreprocessing(sentences);
			
			nlMap.put("naturalLanguage", nlPattenrs);
			
			mapList.add(nlMap);
			
			results.put(key, mapList);
		}
		
		return results;
	}
}
