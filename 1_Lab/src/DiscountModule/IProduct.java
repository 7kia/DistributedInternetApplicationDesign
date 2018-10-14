package DiscountModule;

public interface IProduct {
	public ProductType getType();
	public void setType(ProductType type);
	
	public Float getCurrentCost();
	public void setCurrentCost(Float value);
	
	public Float getStartCost();
}
