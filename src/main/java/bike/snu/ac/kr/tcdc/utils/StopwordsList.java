package bike.snu.ac.kr.tcdc.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class StopwordsList {
	public List<String> stopwords() {
		List<String> stopwords = new Vector<String>();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("./data/stopwords.txt"));
			String line = "";
			while ((line = br.readLine()) != null) {
				stopwords.add(line);
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		stopwords.add("a");
//		stopwords.add("about");
//		stopwords.add("above");
//		stopwords.add("after");
//		stopwords.add("again");
//		stopwords.add("am");
//		stopwords.add("an");
//		stopwords.add("and");
//		stopwords.add("are");
//		stopwords.add("aren't");
//		stopwords.add("as");
//		stopwords.add("at");
//		stopwords.add("be");
//		stopwords.add("been");
//		stopwords.add("but");
//		stopwords.add("had");
//		stopwords.add("hadn't");
//		stopwords.add("has");
//		stopwords.add("hasn't");
//		stopwords.add("have");
//		stopwords.add("haven't");
//		stopwords.add("having");
//		stopwords.add("for");
//		stopwords.add("from");
//		stopwords.add("if");
//		stopwords.add("in");
//		stopwords.add("into");
//		stopwords.add("is");
//		stopwords.add("it");
//		stopwords.add("no");
//		stopwords.add("not");
//		stopwords.add("of");
//		stopwords.add("on");
//		stopwords.add("or");
//		stopwords.add("such");
//		stopwords.add("that");
//		stopwords.add("the");
//		stopwords.add("their");
//		stopwords.add("then");
//		stopwords.add("there");
//		stopwords.add("these");
//		stopwords.add("they");
//		stopwords.add("this");
//		stopwords.add("to");
//		stopwords.add("us");
//		stopwords.add("we");
//		stopwords.add("was");
//		stopwords.add("were");
//		stopwords.add("which");
//		stopwords.add("will");
//		stopwords.add("with");
//		stopwords.add(".");
//		stopwords.add("!!");
//		stopwords.add("?!");
//		stopwords.add("??");
//		stopwords.add("!?");
//		stopwords.add("`");
//		stopwords.add("``");
//		stopwords.add("''");
//		stopwords.add("-lrb-");
//		stopwords.add("-rrb-");
//		stopwords.add("-lsb-");
//		stopwords.add("-rsb-");
//		stopwords.add(",");
//		stopwords.add(":");
//		stopwords.add(";");
//		stopwords.add("\"");
//		stopwords.add("'");
//		stopwords.add("?");
//		stopwords.add("<");
//		stopwords.add(">");
//		stopwords.add("{");
//		stopwords.add("}");
//		stopwords.add("[");
//		stopwords.add("]");
		
		return stopwords;
	}
}
