package DiscountModule;
/*
 * Content type and currentCost. Start cost content to type.
 * 
 * @see ProductType
 */
public class CProduct implements IProduct {
	private ProductType type;
	private Float currentCost;
	
	public CProduct(ProductType type) {
		this.type = type;
		this.currentCost = type.getCost();
	}
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}
	
	public Float getCurrentCost() {
		return currentCost;
	}
	public void setCurrentCost(Float value) {
		currentCost = value;
	}
	
	public Float getStartCost() {
		return type.getCost();
	}

}
