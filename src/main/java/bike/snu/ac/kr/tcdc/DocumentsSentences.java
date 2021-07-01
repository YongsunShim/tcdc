package bike.snu.ac.kr.tcdc;

import bike.snu.ac.kr.tcdc.data.ExtractDocSent;

public class DocumentsSentences {

	public static void main(String[] args) {
		String label = "unsynergy";
		String inputPath = "./data/"+label+".txt";
		
		String docOutputPath = "./data/doc_sent_documents.txt";
		String sentOutputPath = "./data/doc_sent_sentences.txt";
		
		ExtractDocSent ds = new ExtractDocSent();
		ds.extractDocSent(label, inputPath, docOutputPath, sentOutputPath);
	}

}
