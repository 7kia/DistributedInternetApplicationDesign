package Rule;

import DiscountModule.ProductType;

public class SearchData {
	public ProductType type;
	public Integer productIndex;
	
	public SearchData(
		ProductType type,
		Integer productIndex
	) {
		this.type = type;
		this.productIndex = productIndex;
	}
}
