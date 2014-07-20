package cn.edu.sjtu.se.dclab.simulation.version2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class TestPost {

	public static void main(String[] args) throws IOException {
		
		//URL url = new URL("http://103.6.221.228:8888/kpd/1/id:number");
		URL url = new URL("http://103.6.221.238:8000/kpd/1/info:start_time");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("PUT");
		//conn.setRequestProperty("Context-Type", "application/json");
		//conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		//out.write("[{\"column_name\":\"id:number\",\"value\":\"123\"}]");
		out.write("2013-7");
		out.flush();
		//out.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String str;
		while((str = in.readLine()) != null)
			System.out.println(str);
		in.close();
	}
}
