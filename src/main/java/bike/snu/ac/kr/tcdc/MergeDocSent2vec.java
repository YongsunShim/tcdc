package bike.snu.ac.kr.tcdc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class MergeDocSent2vec {
	public static String docInputPath = "./data/doc_sent_doc2vec.csv";
	public static String sentInputPath = "./data/doc_sent_sent2vec.csv";
	
	public static String outputPath = "./data/doc2vec_sent2vec.csv";
	
	public static void main(String[] args) {
		BufferedReader docBr;
		BufferedReader sentBr;
		BufferedWriter bw;
		
		try {
			List<String> docVectors = new Vector<String>();
			
			docBr = new BufferedReader(new FileReader(docInputPath));
			String line = "";
			while ((line = docBr.readLine()) != null) {
				docVectors.add(line);
			}
		
			docBr.close();
		
			List<String> sentVectors = new Vector<String>();
			
			sentBr = new BufferedReader(new FileReader(sentInputPath));
			line = "";
			while ((line = sentBr.readLine()) != null) {
				
				String[] split = line.split(",", 2);
				sentVectors.add(split[1]);
			}
		
			sentBr.close();
			
			bw = new BufferedWriter(new FileWriter(outputPath, true));
			for(int i=0; i<docVectors.size(); i++) {
				bw.write(docVectors.get(i) + "," + sentVectors.get(i));
				bw.newLine();
			}
			
			bw.flush();
			bw.close();
			
			System.out.println("done");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
