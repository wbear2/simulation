package simulation;

import java.util.Date;
import java.util.List;

public class RepairRecord {
	
	private int id;
	private Production production;
	private Consumer consumer;
	private List<Kpd> reasons;
	private Date time;
	
	public RepairRecord(int id, Production production, Consumer consumer,
			List<Kpd> reasons, Date time) {
		super();
		this.id = id;
		this.production = production;
		this.consumer = consumer;
		this.reasons = reasons;
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
	public List<Kpd> getReasons() {
		return reasons;
	}
	public void setReasons(List<Kpd> reasons) {
		this.reasons = reasons;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}