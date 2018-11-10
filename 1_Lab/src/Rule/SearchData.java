package Rule;

import DiscountModule.ProductType;

/*
 * Data for search in found products(see RuleFunction)
 * 
 * @param type - product type
 * @param productIndex - product number from found products
 * 
 * @see ProductType
 * @see RuleFunction
 * @see Rule
 * @see RuleExecutor
 */
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
