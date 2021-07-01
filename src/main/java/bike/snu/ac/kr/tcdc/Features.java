package bike.snu.ac.kr.tcdc;

import bike.snu.ac.kr.tcdc.features.MergeFeatures;

public class Features {

	public static void main(String[] args) {
		int layer = 200;

		String inputPath = "./data/documents.txt";
		String modelPath = "./data/doc_level.model";
		String outputPath = "./data/doc2vec.csv";
		
//		String inputPath = "./data/sentences.txt";
//		String modelPath = "./data/sent_level.model";
//		String outputPath = "./data/sent2vec.csv";
		
//		String inputPath = "./data/doc_sent_documents.txt";
//		String modelPath = "./data/doc_sent_documents.model";
//		String outputPath = "./data/doc_sent_doc2vec.csv";
		
//		String inputPath = "./data/doc_sent_sentences.txt";
//		String modelPath = "./data/doc_sent_sentences.model";
//		String outputPath = "./data/doc_sent_sent2vec.csv";
		
		MergeFeatures mf = new MergeFeatures();
		mf.mergeFeatures(layer, inputPath, modelPath, outputPath);
	}

}
