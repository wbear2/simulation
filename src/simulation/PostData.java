package simulation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.json.JSONObject;

public class PostData {	
	
	private String rootURL = "http://103.6.221.238:8000";
	private String requestMethod = "POST";
	private String contentType = "application/json";
	
	private String reasons = "/reasons/";
	private String warrantyrecords = "/warrantyrecords/";
	private String customers = "/customers/";
	private String batches = "/batches/";
	private String products = "/products/";
	
	public static void main(String[] args){
		SimulationData sd = new SimulationData();
		sd.createData();
		
		PostData pd = new PostData();		
		/*pd.postBatchs(sd.getBatch());
		pd.postCustomers(sd.getConsumer());
		pd.postProducts(sd.getProduction());
		pd.postWarrantyRecords(sd.getRepairRecord());*/
		pd.postReasons(sd.getKpd());
		
		sd.updateProperties();
	}
	
	public void postBatchs(List<JSONObject> jsonObjs){
		/*HttpURLConnection connection =  connect(rootURL + batches);
		doPost(connection, jsonObjs);
		connection.disconnect();*/
		
		doPost(rootURL + batches, jsonObjs);
	}
	
	public void postCustomers(List<JSONObject> jsonObjs){
		/*HttpURLConnection connection =  connect(rootURL + customers);
		  doPost(connection,jsonObjs);*/	
		
		doPost(rootURL + customers, jsonObjs);
	}
	
	public void postProducts(List<JSONObject> jsonObjs){
		/*HttpURLConnection connection =  connect(rootURL + products);
			doPost(connection,jsonObjs);*/		
		
		doPost(rootURL + products, jsonObjs);
	}
	
	public void postWarrantyRecords(List<JSONObject> jsonObjs){
		/*HttpURLConnection connection =  connect(rootURL + warrantyrecords);
			doPost(connection,jsonObjs);*/		
		
		doPost(rootURL + warrantyrecords, jsonObjs);
	}
	
	public void postReasons(List<JSONObject> jsonObjs){
		/*HttpURLConnection connection =  connect(rootURL + reasons);
		doPost(connection,jsonObjs);*/		
		
		doPost(rootURL + reasons, jsonObjs);
	}
	
	private void doPost(String urlStr,List<JSONObject> jsonObjs){
		HttpURLConnection connection = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try{
			for(JSONObject jsonObj : jsonObjs){
				connection = connect(urlStr);
				out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObj.toString());
				out.flush();
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while(in.readLine() != null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				out.close();
				in.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			connection.disconnect();
		}
	}
	
	private void doPost(URLConnection connection,List<JSONObject> jsonObjs){
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try{
			out = new OutputStreamWriter(connection.getOutputStream());
			for(JSONObject jsonObj : jsonObjs){
				out.write(jsonObj.toString());
				out.flush();
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String str = null;
				while((str = in.readLine()) != null);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(out != null)
					out.close();
				if(in != null)
					in.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private HttpURLConnection connect(String urlStr){
		URL url = null;
		HttpURLConnection connection = null; 
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(requestMethod);
			connection.setRequestProperty("Content-Type", contentType);
			connection.setDoInput(true);
			connection.setDoOutput(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}
	
}
