package bike.snu.ac.kr.tcdc.features;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

public class ModelByWord2Vec {
	public void createModel(int layer, int epochs, String inputPath, String outputPath) {
		File file = new File(inputPath);
		
		SentenceIterator iter;
		try {
			iter = new BasicLineIterator(file);
			
			TokenizerFactory t = new DefaultTokenizerFactory();
	        
	        System.out.println("Building model....");
	        Word2Vec vec = new Word2Vec.Builder()
	                .minWordFrequency(1)
	                .epochs(epochs)
	                .layerSize(layer)
	                .seed(42)
	                .windowSize(5)
	                .iterate(iter)
	                .tokenizerFactory(t)
	                .build();
	        
	        System.out.println("Fitting Word2Vec model....");
	        vec.fit();
	        
	        WordVectorSerializer.writeWordVectors(vec, outputPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
