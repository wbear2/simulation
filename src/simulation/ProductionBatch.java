package simulation;

public class ProductionBatch {
	
	private int id;
	private int year;
	private String type;
	
	public ProductionBatch(int id, int year, String type) {
		super();
		this.id = id;
		this.year = year;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}