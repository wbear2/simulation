package cn.edu.sjtu.se.dclab.simulation.version1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimulationData {
	
	private static String path = "simulation.properties";
	private static Properties prop = new Properties();
	
	private static int RepairRecordOffset = 0;
	private static int ProductionOffset = 0;
	private static int ProductionBatchOffset = 0;
	private static int ConsumerOffset = 0;
	private static int KpdOffset = 0;
	
	private final static int RepairRecordNum = 3000;
	private final static int ProductionNum = 10000;
	private final static int ProductionBatchNum = 100;
	private final static int ConsumerNum = 8000;
	private final static int KpdNum = 1000;
	
	private Random rand = new Random();
	
	private List<Consumer> consumers = new ArrayList<Consumer>();
	private List<Kpd> kpds = new ArrayList<Kpd>();
	private List<Production> productions = new ArrayList<Production>();
	private List<ProductionBatch> productionBatchs = new ArrayList<ProductionBatch>();
	private List<RepairRecord> repairRecords = new ArrayList<RepairRecord>();
	
	
	static{
		//Properties prop = new Properties();
		//InputStream in = SimulationData.class.getResourceAsStream(path);
		
		try {
			InputStream in = new FileInputStream(path);
			prop.load(in);
			RepairRecordOffset = Integer.valueOf(prop.getProperty("RepairRecordOffset"));
			ProductionBatchOffset = Integer.valueOf(prop.getProperty("ProductionBatchOffset"));
			ProductionOffset = Integer.valueOf(prop.getProperty("ProductionOffset"));
			ConsumerOffset = Integer.valueOf(prop.getProperty("ConsumerOffset"));
			KpdOffset = Integer.valueOf(prop.getProperty("KpdOffset"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateProperties(){
		try {
			OutputStream out = new FileOutputStream(path);
			//prop.setProperty(key, value);
			prop.store(out, "update properties");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createRepairRecord(){
		int end = RepairRecordOffset + RepairRecordNum;
		for(int i = RepairRecordOffset;i < end;i++){
			int id = i;
			Consumer consumer = consumers.get(rand.nextInt(ConsumerNum));
			Production production = productions.get(rand.nextInt(ProductionNum));
			int productTime = production.getBatch().getYear();
			int year = productTime + rand.nextInt(2014 - productTime) - 1900;
			Date time = new Date(year,rand.nextInt(12)+1,rand.nextInt(30)+1,rand.nextInt(12)+8
					,rand.nextInt(60),rand.nextInt(60));
			RepairRecord repairRecord = new RepairRecord(id,production,consumer,time);
			repairRecords.add(repairRecord);
		}
		
		prop.setProperty("RepairRecordOffset", String.valueOf(RepairRecordOffset+RepairRecordNum));
	}
	
	//has 10 types:A B C D E F G H I J
	//has 7 powers:1200 1500 1800 2100 2400 2700 3000
	private void createProduction(){
		int end = ProductionOffset + ProductionNum;
		for(int i = ProductionOffset;i < end;i++){
			int id = i;
			ProductionBatch productionBatch = productionBatchs.get(rand.nextInt(ProductionBatchNum));
			Consumer consumer = consumers.get(rand.nextInt(ConsumerNum));
			int power = (rand.nextInt(7)+4)*300;
			Production production = new Production(id,productionBatch,power,consumer);
			productions.add(production);
		}

		prop.setProperty("ProductionOffset", String.valueOf(ProductionOffset+ProductionNum));
	}
	
	private void createProductionBatch(){
		int end = ProductionBatchOffset + ProductionBatchNum;
		for(int i = ProductionBatchOffset;i < end;i++){
			int id = i;
			int year = rand.nextInt(5) + 2009;
			String type = "" + (char)('A'+ rand.nextInt(10));
			ProductionBatch productionBatch = new ProductionBatch(id,year,type);
			productionBatchs.add(productionBatch);
		}
		
		prop.setProperty("ProductionBatchOffset", String.valueOf(ProductionBatchOffset+ProductionBatchNum));
	}
	
	//age:20-70
	private void createConsumer(){
		int end = ConsumerOffset + ConsumerNum;
		for(int i = ConsumerOffset;i < end;i++){
			int id = i;
			String name = "" + (char)(rand.nextInt(26)+'A') + id;
			int age = 20 + rand.nextInt(51);
			Consumer consumer = new Consumer(id,name,age);
			consumers.add(consumer);
		}
		
		prop.setProperty("ConsumerOffset",String.valueOf(ConsumerOffset+ConsumerNum));
	}
	
	//name:A B C .. X Y Z
	private void createKpd(){
		/*int end = KpdOffset + KpdNum;
		for(int i = KpdOffset;i < end;i++){
			int id = i;
			String name = "" + (char)('a' + rand.nextInt(26)) + id;
			RepairRecord repairRecord = repairRecords.get(rand.nextInt(RepairRecordNum));
			Kpd kpd = new Kpd(id,name,repairRecord);
			kpds.add(kpd);
		}
		
		prop.setProperty("KpdOffset", String.valueOf(KpdOffset+KpdNum));
		*/
		int j = 0;
		boolean[] flag = new boolean[26];
		Arrays.fill(flag, false);
		for(int i = 0;i < RepairRecordNum;i++){
			int num = rand.nextInt(3) + 1;//reasons' num (<=3)
			for(int k = 0;k < num;k++){
				int index = rand.nextInt(26);
				if(!flag[index]){
					String name = "" + (char)('A' + index);
					Kpd kpd = new Kpd(++j,name,repairRecords.get(i));
					kpds.add(kpd);
					flag[index] = true;
				}
				Arrays.fill(flag,false);
			}
		}
		
		prop.setProperty("KpdOffset", String.valueOf(KpdOffset + j));
		
	}
	
	//batch->consumer->production->record->kpd
	public void createData(){
		createProductionBatch();
		createConsumer();
		createProduction();
		createRepairRecord();
		createKpd();
	}
	
	public List<JSONObject> getRepairRecord(){		
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(RepairRecord repairRecord : repairRecords){
			JSONObject jsonObj = new JSONObject();		
			
			try {
				jsonObj.put("product", repairRecord.getProduction().getId());
				jsonObj.put("customer", repairRecord.getConsumer().getId());
				jsonObj.put("time", df.format(repairRecord.getTime()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
			list.add(jsonObj);
		}
		
		return list;
	}
	
	public List<JSONObject> getConsumer(){	
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for(Consumer consumer : consumers){
			JSONObject jsonObj = new JSONObject();
			
			try {
				jsonObj.put("name", consumer.getName());
				jsonObj.put("age", consumer.getAge());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			list.add(jsonObj);
		}
		
		return list;
	}
	
	public List<JSONObject> getBatch(){	
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for(ProductionBatch productionBatch : productionBatchs){
			JSONObject jsonObj = new JSONObject();
			
			try {
				jsonObj.put("ptime", productionBatch.getYear());
				jsonObj.put("version", productionBatch.getType());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			list.add(jsonObj);
		}
		
		return list;
	}
	
	public List<JSONObject> getKpd(){	
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for(Kpd kpd : kpds){
			JSONObject jsonObj = new JSONObject();
			
			try {
				jsonObj.put("description", kpd.getName());
				jsonObj.put("warranty_record", kpd.getRepairRecord().getId());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			list.add(jsonObj);
		}

		return list;
	}
	
	public List<JSONObject> getProduction(){	
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for(Production production : productions){
			JSONObject jsonObj = new JSONObject();
			
			try {
				jsonObj.put("customer", production.getConsumer().getId());
				jsonObj.put("batch", production.getBatch().getId());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			list.add(jsonObj);
		}
		
		return list;
	}
	
	public static void main(String[] args) throws JSONException {
		SimulationData sd = new SimulationData();
		
		sd.createData();
		//sd.updateProperties();
	/*	
		for(JSONObject jsonObj : sd.getBatch())
			System.out.println(jsonObj);
		
		for(JSONObject jsonObj : sd.getConsumer())
			System.out.println(jsonObj);
		
		for(JSONObject jsonObj : sd.getProduction())
			System.out.println(jsonObj);
		
		for(JSONObject jsonObj : sd.getRepairRecord())
			System.out.println(jsonObj);*/
		
		for(JSONObject jsonObj : sd.getKpd())
			System.out.println(jsonObj);
	}
}
