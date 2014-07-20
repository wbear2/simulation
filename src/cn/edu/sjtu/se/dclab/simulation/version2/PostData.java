package cn.edu.sjtu.se.dclab.simulation.version2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostData {	
	
	private static String rootURL = "http://103.6.221.238:8000";
	private static String requestMethod = "PUT";
	private static String contentType = "application/json";
	
	private static String kpdURL = "/kpd/";
	private static String typeURL = "/info:type/";
	private static String start_timeURL = "/info:start_time/";
	private static String recordsURL = "/info:records/";
	private static String questionnaireURL = "/questionnaire/";
	
	public static void main(String[] args){
		KpdFactory kf = new KpdFactory();
		List<Kpd> kpds = kf.getList();
		
		PostData pd = new PostData();
		pd.doPost(kpds);	
	}
	
	public void doPost(List<Kpd> kpds){
		HttpURLConnection connection = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		int i = 1;
		
		for(Kpd kpd : kpds){
			try{
				connection = connect(rootURL + kpdURL + i + typeURL);
				out = new OutputStreamWriter(connection.getOutputStream());
				out.write(String.valueOf(kpd.getType()));
				out.flush();				
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while(in.readLine() != null);		
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					out.close();
					in.close();
					connection.disconnect();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			
			try{
				connection = connect(rootURL + kpdURL + i + start_timeURL);
				out = new OutputStreamWriter(connection.getOutputStream());
				out.write(df.format(kpd.getStartTime()));
				out.flush();				
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while(in.readLine() != null);		
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					out.close();
					in.close();
					connection.disconnect();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			
			try{
				connection = connect(rootURL + kpdURL + i + recordsURL);
				out = new OutputStreamWriter(connection.getOutputStream());
				StringBuffer sb = new StringBuffer();
				int[] rs = kpd.getRecords();
				for(int r : rs)
					sb.append("," + r);
				if(sb.length() > 0)
					sb.deleteCharAt(0);
				out.write(sb.toString());
				out.flush();
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while(in.readLine() != null);
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					out.close();
					in.close();
				}catch(IOException e){
					e.printStackTrace();
				}
				connection.disconnect();
			}
			
			i++;
		}
	}
	
	public void doPost(String urlStr,List<JSONArray> jsonArrs){
		HttpURLConnection connection = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try{
			for(JSONArray jsonArr : jsonArrs){
				connection = connect(urlStr);
				out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonArr.toString());
				out.flush();
				//in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				//while(in.readLine() != null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				out.close();
				//in.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			connection.disconnect();
		}
	}
	
	private HttpURLConnection connect(String urlStr){
		URL url = null;
		HttpURLConnection connection = null; 
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(requestMethod);
			//connection.setRequestProperty("Content-Type", contentType);
			connection.setDoInput(true);
			connection.setDoOutput(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}
	
}
