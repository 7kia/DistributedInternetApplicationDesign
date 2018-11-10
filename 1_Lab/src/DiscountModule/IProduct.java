package DiscountModule;
/*
 * Interface for products. Product must have type, start and current cost.
 * 
 * @see ProductType
 */
public interface IProduct {
	public ProductType getType();
	public void setType(ProductType type);
	
	public Float getCurrentCost();
	public void setCurrentCost(Float value);
	
	public Float getStartCost();
}
