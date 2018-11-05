package Rule;

import java.util.Dictionary;
import java.util.Vector;

import DiscountModule.IProduct;
import DiscountModule.Pair;
import DiscountModule.ProductType;

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
