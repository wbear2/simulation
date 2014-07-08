package simulation;

import java.util.List;

public class Consumer {
	
	private int id;
	private String name;
	private int age;
	private List<Production> buys;
	
	public Consumer(int id, String name, int age, List<Production> buys) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.buys = buys;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<Production> getBuys() {
		return buys;
	}
	public void setBuys(List<Production> buys) {
		this.buys = buys;
	}
}