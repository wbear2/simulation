package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationData {
	
	private final static int RepairRecordNum = 3000;
	private final static int ProductionNum = 1000;
	private final static int ProductionBatchNum = 1000;
	private final static int ConsumerNum = 10000;
	private final static int KpdNum = 10000;
	
	private Random rand = new Random();
	
	private List<Consumer> consumers = new ArrayList<Consumer>();
	private List<Kpd> kpds = new ArrayList<Kpd>();
	private List<Production> productions = new ArrayList<Production>();
	private List<ProductionBatch> productionBatchs = new ArrayList<ProductionBatch>();
	private List<RepairRecord> repairRecords = new ArrayList<RepairRecord>();
	
	private void createRepairRecord(){
		createConsumer();
		for(int i = 0;i < RepairRecordNum;i++){
			int id = i+1;
			Consumer consumer = consumers.get(rand.nextInt(ConsumerNum));
			List buys = consumer.getBuys();
			Production production = (Production) buys.get(rand.nextInt(buys.size()));
			String type = production.getType();
			ArrayList reasons = new ArrayList();
			int num = rand.nextInt(5) + 1;
			for(int j = 0;j < num;j++)
				reasons.add(kpds.get(rand.nextInt(KpdNum)));
			int productTime = production.getBatch().getYear();
			int year = productTime + rand.nextInt(2014 - productTime);
			RepairRecord repairRecord = new RepairRecord(id,production,consumer,reasons,year);
			repairRecords.add(repairRecord);
		}
	}
	
	//has 10 types:A B C D E F G H I J
	//has 7 powers:1200 1500 1800 2100 2400 2700 3000
	private void createProduction(){
		createProductionBatch();
		createKpd();
		for(int i = 0;i < ProductionNum;i++){
			int id = i+1;
			ProductionBatch productionBatch = productionBatchs.get(rand.nextInt(ProductionBatchNum));
			String type = "" + (rand.nextInt(10)+'A');
			int power = (rand.nextInt(7)+4)*300;
			Production production = new Production(id,productionBatch,power,type);
			productions.add(production);
		}
	}
	
	private void createProductionBatch(){
		for(int i = 0;i < ProductionBatchNum;i++){
			int id = i+1;
			int year = rand.nextInt(5) + 2009;
			ProductionBatch productionBatch = new ProductionBatch(id,year);
			productionBatchs.add(productionBatch);
		}
	}
	
	//at most 5 buys
	private void createConsumer(){
		createProduction();
		for(int i = 0;i < ConsumerNum;i++){
			int id = i+1;
			String name = "" + (rand.nextInt(26)+'A') + id;
			int age = 20 + rand.nextInt(51);
			ArrayList<Production> buys = new ArrayList<Production>();
			int buysNum = rand.nextInt(5) + 1;
			for(int j = 0;j < buysNum;j++)
				buys.add(productions.get(rand.nextInt(ProductionNum)));
			Consumer consumer = new Consumer(id,name,age,buys);
			consumers.add(consumer);
		}
	}
	
	private void createKpd(){
		for(int i = 0;i < KpdNum;i++){
			int id = i + 1;
			String name = "" + ('a' + rand.nextInt(26)) + id;
			Kpd kpd = new Kpd(id,name);
			kpds.add(kpd);
		}
	}
	
	private void getJsonData(){
		
	}
	
	public static void main(String[] args) {
		SimulationData sd = new SimulationData();
		sd.createRepairRecord();
	}
}
