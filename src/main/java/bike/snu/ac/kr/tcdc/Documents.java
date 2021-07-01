package bike.snu.ac.kr.tcdc;

import bike.snu.ac.kr.tcdc.data.ExtractDocuments;

public class Documents {

	public static void main(String[] args) {
		String label = "synergy"; //synergy or unsynergy
		String inputPath = "./data/"+label+".txt";
		String outputPath = "./data/documents.txt";
		
		ExtractDocuments ed = new ExtractDocuments();
		ed.extractDocuments(label, inputPath, outputPath);

	}

}
