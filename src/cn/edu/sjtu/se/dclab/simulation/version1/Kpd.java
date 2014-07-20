package cn.edu.sjtu.se.dclab.simulation.version1;

public class Kpd {
	
	private int id;
	private String name;
	private RepairRecord repairRecord;
	
	public Kpd(int id, String name, RepairRecord repairRecord) {
		super();
		this.id = id;
		this.name = name;
		this.repairRecord = repairRecord;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RepairRecord getRepairRecord() {
		return repairRecord;
	}
	public void setRepairRecord(RepairRecord repairRecord) {
		this.repairRecord = repairRecord;
	}
	public String toString(){
		return ""+id;
	}
}