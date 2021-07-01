package bike.snu.ac.kr.tcdc;

import bike.snu.ac.kr.tcdc.features.ModelByWord2Vec;

public class CreateModel {

	public static void main(String[] args) {
		int layer = 200;
		int epochs = 300;
		String inputPath = "./data/unlabeled_documents.txt";
		String outputPath = "./data/doc_level.model";

		ModelByWord2Vec w = new ModelByWord2Vec();
		w.createModel(layer, epochs, inputPath, outputPath);
	}

}
