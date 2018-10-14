package DiscountModule;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Vector;
import java.util.function.Function;

public class Rule {
	// Input: key: type, value: pairs<product and participant to discount(source value = true)
	// Output: key: product, value: pairs with new cost and  participant to discount
	private Function<
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>>,
		Dictionary<IProduct, Pair<Float, Boolean>>
	> rule;
	private Dictionary<ProductType, Integer> productTypes;
	public Rule(
		Function<
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>>,
			Dictionary<IProduct, Pair<Float, Boolean>>
		> rule,
		Dictionary<ProductType, Integer> productTypes
	) {
		this.rule = rule;
		this.productTypes = productTypes;
	}
	
	public Dictionary<ProductType, Integer> getProductTypes() {
		return productTypes;
	}
	public Dictionary<IProduct, Pair<Float, Boolean>> executeRule(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> productList
	) {
		return rule.apply(productList);
	}
}
