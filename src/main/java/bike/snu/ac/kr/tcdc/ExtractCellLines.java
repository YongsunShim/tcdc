package bike.snu.ac.kr.tcdc;

import java.io.IOException;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bike.snu.ac.kr.tcdc.utils.CellLineList;


public class ExtractCellLines {

	public static void main(String[] args) {
		CellLineList c = new CellLineList();
		TreeSet<String> cellLines = c.cellLineList();
		
		String filePath = "./data/cellosaurus.xml";
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
			doc = dBuilder.parse(filePath);
			
			NodeList cellLineList = doc.getElementsByTagName("cell-line");
			
			for(int i=0; i<cellLineList.getLength(); i++) {
				Node cellLine = cellLineList.item(i);
				
				String type = cellLineList.item(i).getAttributes().getNamedItem("category").getNodeValue();
				
				System.out.println(type);
				if(type.toLowerCase().contains("cancer")) {
					Element cellLineElement = (Element) cellLine;
					
					NodeList nameList = cellLineElement.getElementsByTagName("name");
					for(int j=0; j<nameList.getLength(); j++) {
						String name = nameList.item(j).getTextContent();
						System.out.println(name);
						if(cellLines.contains(name)) {
							cellLines.remove(name);
						}
						
						if(cellLines.size() == 0) {
							System.out.println("All cancer Cell Line");
							System.exit(0);
						}
					}
				}
			}
			
			for(String cl : cellLines) {
				System.out.println(cl);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
