package bike.snu.ac.kr.tcdc.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bike.snu.ac.kr.tcdc.utils.CSVProcessor;

public class Pubmed {

	public void getPubmedArticles (String synergyDocOutputPath, String unsynergyDocOutputPath) {
		String filename = "./data/drugcomb_v1.4.csv";
    	
        List<String[]> data = new ArrayList<String[]>();
        
        CSVProcessor read = new CSVProcessor();
        data = read.readCsv(filename);
        
        Iterator<String[]> it = data.iterator();
        int lineCnt=0;
        
        TreeSet<String> synergyComb = new TreeSet<String>();
        TreeSet<String> unsynergyComb = new TreeSet<String>();
        
//      synergy +/-
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
                	if(!cellLine.equals("DIPG25") && !cellLine.equals("MSTO") && !cellLine.equals("SKMEL30") && !cellLine.equals("UWB1289+BRCA1")) { //Cell lines which type is not cancer in cellosaurus
                        if((zipSynergy >= 0.0) && (blissSynergy >= 0.0) && (loeweSynergy >= 0.0) && (hsaSynergy >= 0.0) && (sSynergy >= 0.0)) {
                        	// all positive
                        	if(!synergyComb.contains(drug2 + "\t" + drug1 + "\t" + cellLine)) {
                        		synergyComb.add(drug1 + "\t" + drug2 + "\t" + cellLine);
                        	}
                        	
                        } else if((zipSynergy < 0.0) && (blissSynergy < 0.0) && (loeweSynergy < 0.0) && (hsaSynergy < 0.0) && (sSynergy < 0.0)) {
                        	// all negative
                        	if(!unsynergyComb.contains(drug2 + "\t" + drug1 + "\t" + cellLine)) {
                        		unsynergyComb.add(drug1 + "\t" + drug2 + "\t" + cellLine);
                        	}
                        }
                	}
                }
        	}
        	lineCnt++;
        }
        
        for(String s : synergyComb) {
        	if(unsynergyComb.contains(s)) {
        		unsynergyComb.remove(s);
        	}
        }

        TreeSet<String> synergySet = new TreeSet<String>();
        for(String s : synergyComb) {
        	String[] split = s.split("\t");
        	String drug1 = split[0];
        	String drug2 = split[1];
        	String cellLine = split[2];
        	
        	String keyword = drug1 + "," + drug2 + "," + cellLine;
        	
        	TreeSet<String> getIds = getIds(keyword);
        	for(String id : getIds) {
        		synergySet.add(drug1 + "\t" + drug2 + "\t" + cellLine + "\t" + id);
        	}
        }
        
		for (String s : synergySet) {
			String[] split = s.split("\t");
        	String id = split[3];
			
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(synergyDocOutputPath, true));

				String getTextUrl = "https://www.ncbi.nlm.nih.gov/research/pubtator-api/publications/export/biocjson?pmids="+ id + "&concepts=none";
				System.out.println(getTextUrl);

				// http client 생성
				CloseableHttpClient httpClient = HttpClients.custom()
						.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
						.build();

				// get 메서드와 URL 설정
				HttpGet httpGet = new HttpGet(getTextUrl);

				// agent 정보 설정
				httpGet.addHeader("User-Agent", "Mozila/5.0");
				httpGet.addHeader("Content-type", "application/json");

				// get 요청
				CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

				String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				httpClient.close();
				
				if (result.length() > 0 && !result.startsWith("<!DOCTYPE html>")) {
					JsonParser jsonParser = new JsonParser();
					JsonObject jsonObj = (JsonObject) jsonParser.parse(result);

					JsonArray passagesArray = (JsonArray) jsonObj.get("passages");

					JsonObject titlePassage = passagesArray.get(0).getAsJsonObject();
					String title = titlePassage.get("text").getAsString();
					
					String text = "";
					for (int i = 1; i < passagesArray.size(); i++) {
						JsonObject passage = passagesArray.get(i).getAsJsonObject();
						text = text + " " + passage.get("text").getAsString();
					}
					
					if(text.length() > 0) {
						bw.write(s + "\t" + title + "\t" + text);
						bw.newLine();
						bw.flush();
						bw.close();	
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        /*------------------------------------------------------------------------------------------------------------------------------------------*/
		TreeSet<String> unsynergySet = new TreeSet<String>();
        for(String s : unsynergyComb) {
        	String[] split = s.split("\t");
        	String drug1 = split[0];
        	String drug2 = split[1];
        	String cellLine = split[2];
        	
        	String keyword = drug1 + "," + drug2 + "," + cellLine;
        	
        	TreeSet<String> getIds = getIds(keyword);
        	for(String id : getIds) {
        		unsynergySet.add(drug1 + "\t" + drug2 + "\t" + cellLine + "\t" + id);
        	}
        }
        
		for (String s : unsynergySet) {
			String[] split = s.split("\t");
        	String id = split[3];
			
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(unsynergyDocOutputPath, true));

				String getTextUrl = "https://www.ncbi.nlm.nih.gov/research/pubtator-api/publications/export/biocjson?pmids="+ id + "&concepts=none";
				System.out.println(getTextUrl);

				// http client 생성
				CloseableHttpClient httpClient = HttpClients.custom()
						.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
						.build();

				// get 메서드와 URL 설정
				HttpGet httpGet = new HttpGet(getTextUrl);

				// agent 정보 설정
				httpGet.addHeader("User-Agent", "Mozila/5.0");
				httpGet.addHeader("Content-type", "application/json");

				// get 요청
				CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

				String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				httpClient.close();
				
				if (result.length() > 0 && !result.startsWith("<!DOCTYPE html>")) {
					JsonParser jsonParser = new JsonParser();
					JsonObject jsonObj = (JsonObject) jsonParser.parse(result);

					JsonArray passagesArray = (JsonArray) jsonObj.get("passages");

					JsonObject titlePassage = passagesArray.get(0).getAsJsonObject();
					String title = titlePassage.get("text").getAsString();
					
					String text = "";
					for (int i = 1; i < passagesArray.size(); i++) {
						JsonObject passage = passagesArray.get(i).getAsJsonObject();
						text = text + " " + passage.get("text").getAsString();
					}
					
					if(text.length() > 0) {
						bw.write(s + "\t" + title + "\t" + text);
						bw.newLine();
						bw.flush();
						bw.close();	
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        
	}

	public static TreeSet<String> getIds(String keyword) {
		TreeSet<String> ids = new TreeSet<String>();

		try {
			keyword = URLEncoder.encode(keyword, "US-ASCII");
			String getIdUrl = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=" + keyword + "&retmode=json";

			System.out.println(getIdUrl);
			// http client 생성
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
					.build();

			// get 메서드와 URL 설정
			HttpGet httpGet = new HttpGet(getIdUrl);

			// agent 정보 설정
			httpGet.addHeader("User-Agent", "Mozila/5.0");
			httpGet.addHeader("Content-type", "application/json");

			// get 요청
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

			String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

			httpClient.close();

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(result);

			JsonObject eSearchResult = (JsonObject) jsonObj.get("esearchresult");

			int count = eSearchResult.get("count").getAsInt();
			int retmax = eSearchResult.get("retmax").getAsInt();

			if (count > retmax) {
				getIdUrl = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=" + keyword + "&retmode=json&retmax=" + count;
				System.out.println(getIdUrl);
				try {
					// http client 생성
					httpClient = HttpClients.custom()
							.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
							.build();

					// get 메서드와 URL 설정
					httpGet = new HttpGet(getIdUrl);

					// agent 정보 설정
					httpGet.addHeader("User-Agent", "Mozila/5.0");
					httpGet.addHeader("Content-type", "application/json");

					// get 요청
					httpResponse = httpClient.execute(httpGet);

					result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

					httpClient.close();

					jsonParser = new JsonParser();
					jsonObj = (JsonObject) jsonParser.parse(result);

					eSearchResult = (JsonObject) jsonObj.get("esearchresult");

					count = eSearchResult.get("count").getAsInt();
					retmax = eSearchResult.get("retmax").getAsInt();

					JsonArray idList = (JsonArray) eSearchResult.get("idlist");
					for (int i = 0; i < idList.size(); i++) {
						ids.add(idList.get(i).getAsString());
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				JsonArray idList = (JsonArray) eSearchResult.get("idlist");
				for (int i = 0; i < idList.size(); i++) {
					ids.add(idList.get(i).getAsString());
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ids;
	}
}
