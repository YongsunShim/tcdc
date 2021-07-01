package bike.snu.ac.kr.tcdc.features;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

public class MergeFeatures {
	public void mergeFeatures(int layer, String inputPath, String modelPath, String outputPath) {
		long startTime = System.currentTimeMillis();
		
		List<Map<String, List<Double>>> mapList = new Vector<Map<String, List<Double>>>();
		BufferedWriter bw;
		BufferedReader br;
		
		try {
			Word2Vec nlWord2Vec = WordVectorSerializer.readWord2VecModel(modelPath);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "UTF-8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				Map<String, List<Double>> map = new HashMap<String, List<Double>>();
				
				String[] lineSplit = line.split("\t");
				String label = lineSplit[0];
				String textLine = lineSplit[1];

				double[] lineVector = new double[layer];
				
				String[] split = textLine.split(" ");
				List<String> words = new ArrayList<String>();
				List<String> words2 = new ArrayList<String>();
				for(int i=0; i<split.length; i++) {
					words.add(split[i]);
					words2.add(split[i]);
				}
				
				
				List<String> modelWords = new ArrayList<String>();
				int cnt = 0;
				BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(modelPath), "UTF-8"));
				String line2 = "";
				while ((line2 = br2.readLine()) != null) {
					if(cnt != 0) {
						String[] split2 = line2.split(" ");
						String word = split2[0];
						modelWords.add(word);
					} else {
						++cnt;
					}
				}
				
				br2.close();
					
				for(String word : words) {
					if(!modelWords.contains(word)) {
						words2.remove(word);
					}
				}
				
				for(int i=0; i<words2.size()-1; i++) {
					if(i == 0) {
						double[] vec1 = nlWord2Vec.getWordVector(words2.get(i));
						double[] vec2 = nlWord2Vec.getWordVector(words2.get(i+1));
						
						lineVector = add(vec1, vec2);
					} else {
						double[] vec1 = nlWord2Vec.getWordVector(words2.get(i+1));
						lineVector = add(lineVector, vec1);
					}
				}
				
				List<Double> vector = new Vector<Double>();
				for(double d : lineVector) {
					vector.add(d);
				}
				
				map.put(label, vector);
				mapList.add(map);
			}
			br.close();
			
			/*------------model writing------------*/
			bw = new BufferedWriter(new FileWriter(outputPath, true));
			
			for(Map<String, List<Double>> m : mapList) {
				for(String key : m.keySet()) {
					if(key.startsWith("synergy_")) {
						bw.write("1");	
					} else {
						bw.write("0");
					}
					
//					bw.write(key);
					for(Double value : m.get(key)) {
						bw.write("," + value);
					}
					
					bw.newLine();
				}
			}
			bw.flush();
			bw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
        
		// Total time
		long lTime = (endTime - startTime)/1000;
		System.out.println("TIME : " + lTime + "(s)");
	}

	public static double[] add(double[] first, double[] second) {
		int length = first.length < second.length ? first.length : second.length;
		double[] result = new double[length];
		for (int i = 0; i < length; i++) {
			result[i] = first[i] + second[i];
		}
		return result;
	}
	
}
