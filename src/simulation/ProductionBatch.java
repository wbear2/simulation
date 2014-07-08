package simulation;

public class ProductionBatch {
	
	private int id;
	private int year;
	
	public ProductionBatch(int id, int year) {
		super();
		this.id = id;
		this.year = year;
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
}