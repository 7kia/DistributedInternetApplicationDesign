package DiscountModule;

public class ProductType {
	private String name;
	private Float cost;
	public ProductType(String name, Float cost) {
		this.name = name;
		this.cost = cost;
	}
	public Float getCost() {
		return cost;
	}
	public String getName() {
		return name;
	}
}
