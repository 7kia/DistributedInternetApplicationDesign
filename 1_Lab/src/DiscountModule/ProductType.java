package DiscountModule;

public class ProductType {
	public static final ProductType anyType = new ProductType("Any", 1.0f);
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
