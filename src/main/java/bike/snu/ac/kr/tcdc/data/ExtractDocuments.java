package bike.snu.ac.kr.tcdc.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ExtractDocuments {

	public void extractDocuments(String label, String inputPath, String outputPath) {
		long startTime = System.currentTimeMillis();
		
		TreeSet<String> tree = new TreeSet<String>();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(inputPath));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\t");
				String drug1 = split[2];
				String drug2 = split[3];
				String abs = split[7];
				
				String[] absSplit = abs.split("\\s+");
				
				TreeSet<String> cntWords = new TreeSet<String>();
				for(String word : absSplit) {
					if(word.equals(drug1) || word.equals(drug2)) {
						cntWords.add(word);
					}
				}
				
				if(cntWords.size() > 1) {
					tree.add(split[5] + "\t" + split[6] + "\t" + split[7]);
				}
				
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<Integer, String> setMap = new LinkedHashMap<Integer, String>();
		int cnt = 0;
		for(String s : tree) {
			setMap.put(cnt, s);
			++cnt;
		}
		
		DocumentResults p = new DocumentResults();
		Map<Integer, List<Map<String, List<String>>>> results = p.results(setMap);
		
		for (int key : results.keySet()) {
			System.out.println(key + "\t" + results.get(key));
		}
		
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

}
