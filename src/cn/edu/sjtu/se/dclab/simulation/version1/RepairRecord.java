package cn.edu.sjtu.se.dclab.simulation.version1;

import java.util.Date;

public class RepairRecord {
	
	private int id;
	private Production production;
	private Consumer consumer;
	private Date time;
	
	public RepairRecord(int id, Production production, Consumer consumer,Date time) {
		super();
		this.id = id;
		this.production = production;
		this.consumer = consumer;
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Production getProduction() {
		return production;
	}
	public void setProduction(Production production) {
		this.production = production;
	}
	public Consumer getConsumer() {
		return consumer;
	}
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}