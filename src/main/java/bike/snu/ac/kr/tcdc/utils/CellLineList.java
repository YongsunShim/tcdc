package bike.snu.ac.kr.tcdc.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class CellLineList {
	public TreeSet<String> cellLineList () {
		TreeSet<String> cellLineList = new TreeSet<String>();
		
		String filename = "./data/drugcomb_v1.4.csv";
    	
        List<String[]> data = new ArrayList<String[]>();
        
        CSVProcessor read = new CSVProcessor();
        data = read.readCsv(filename);
        
        Iterator<String[]> it = data.iterator();
        int lineCnt=0;
        
        while (it.hasNext()) {
        	String[] array = (String[]) it.next();
        	if(lineCnt != 0) {
                String drug1 = array[1];
                String drug2 = array[2];
                String cellLine = array[3];
                
                double zipSynergy = Double.parseDouble(array[7]);
                double blissSynergy = Double.parseDouble(array[8]);
                double loeweSynergy = Double.parseDouble(array[9]);
                double hsaSynergy = Double.parseDouble(array[10]);
                double sSynergy = Double.parseDouble(array[17]);
            	
                if(!drug1.equals("NA") && !drug2.equals("NA")) {
                    if((zipSynergy >= 0.0) && (blissSynergy >= 0.0) && (loeweSynergy >= 0.0) && (hsaSynergy >= 0.0) && (sSynergy >= 0.0)) {
                    	// all positive
                    	cellLineList.add(cellLine);
                    	
                    } else if((zipSynergy < 0.0) && (blissSynergy < 0.0) && (loeweSynergy < 0.0) && (hsaSynergy < 0.0) && (sSynergy < 0.0)) {
                    	// all negative
                    	cellLineList.add(cellLine);
                    }
                }
        	}
        	lineCnt++;
        }
		
		return cellLineList;
	}
}
