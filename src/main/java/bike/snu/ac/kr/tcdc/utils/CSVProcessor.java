package bike.snu.ac.kr.tcdc.utils;
 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import au.com.bytecode.opencsv.CSVReader;
 
public class CSVProcessor {
    public List<String []> readCsv(String filename) {
    	List<String[]> data = new ArrayList<String[]>();
        try {
			CSVReader r = new CSVReader(new FileReader(filename));
			
            String[] s;
            while ((s = r.readNext()) != null) {
                data.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return data;
    }
}