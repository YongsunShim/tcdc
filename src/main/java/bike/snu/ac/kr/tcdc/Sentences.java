package bike.snu.ac.kr.tcdc;

import bike.snu.ac.kr.tcdc.data.ExtractSentences;

public class Sentences {

	public static void main(String[] args) {
		String label = "unsynergy"; //synergy or unsynergy
		String inputPath = "./data/"+label+".txt";
		String outputPath = "./data/sentences.txt";
		
		ExtractSentences es = new ExtractSentences();
		es.extractSentences(label, inputPath, outputPath);

	}

}
