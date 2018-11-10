package Rule;

import java.util.Dictionary;
import java.util.Vector;

import DiscountModule.IProduct;
import DiscountModule.Pair;
import DiscountModule.ProductType;

/*
 * Content data which use to rule function.
 * 
 * @param foundProducts - product correspond for the rule
 * @param result - result apply rule(product and it new cost)
 * 
 * @see Rule
 */
public class RuleFunctionData {
	public Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts;
	public Dictionary<IProduct, Pair<Float, Boolean>> result;
	
	public RuleFunctionData(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts,
		Dictionary<IProduct, Pair<Float, Boolean>> result
	) {
		this.foundProducts = foundProducts;
		this.result = result;
	}
}
