package simulation;


public class Production {
	
	private int id;
	private ProductionBatch batch;
	//rated power, the unit is Watt
	private int power;
	private String type;
	
	public Production(int id, ProductionBatch batch, int power, String type) {
		super();
		this.id = id;
		this.batch = batch;
		this.power = power;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProductionBatch getBatch() {
		return batch;
	}
	public void setBatch(ProductionBatch batch) {
		this.batch = batch;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}