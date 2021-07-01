package bike.snu.ac.kr.tcdc.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExtractSentences {
	public void extractSentences(String label, String inputPath, String outputPath) {
		long startTime = System.currentTimeMillis();
		
		Map<Integer, String> setMap = new LinkedHashMap<Integer, String>();
		
		LinkedHashSet<String> tree = new LinkedHashSet<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(inputPath));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\t");
				String drug1 = split[2];
				String drug2 = split[3];
				String abs = split[7];
				
				
				BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
				iterator.setText(abs);
				int start = iterator.first();
				for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
					String sentence = abs.substring(start, end);
					
					if(checkContain(drug1, drug2, sentence) == true) {
						tree.add(sentence);
					}
				}
			}

			br.close();
			
			int cnt = 0;
			for(String s : tree) {
				setMap.put(cnt, s);
				++cnt;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SentenceResults p = new SentenceResults();
		Map<Integer, List<Map<String, List<String>>>> results = p.results(setMap);

		BufferedWriter nlPatternBw;
		try {
			nlPatternBw = new BufferedWriter(new FileWriter(outputPath, true));
			
			for (int key : results.keySet()) {
				nlPatternBw.write(label + "_" + key + "\t");

				for (Map<String, List<String>> map : results.get(key)) {
					for (String key2 : map.keySet()) {
						if (key2.equals("naturalLanguage")) {
							List<String> values = map.get(key2);
							for (String s : values) {
								nlPatternBw.write(s + " ");
							}
						}
					}
				}

				nlPatternBw.newLine();

			}

			nlPatternBw.flush();

			nlPatternBw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
        
		// Total time
		long lTime = (endTime - startTime)/1000;
		System.out.println("TIME : " + lTime + "(s)");
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
