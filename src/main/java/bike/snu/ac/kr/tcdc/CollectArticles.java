package bike.snu.ac.kr.tcdc;

import bike.snu.ac.kr.tcdc.data.Pubmed;

public class CollectArticles {

	public static void main(String[] args) {
		String synergyDocOutputPath = "./data/synergy.txt";
		String unsynergyDocOutputPath = "./data/unsynergy.txt";
		
		Pubmed pa = new Pubmed();
		pa.getPubmedArticles(synergyDocOutputPath, unsynergyDocOutputPath);
	}

}
