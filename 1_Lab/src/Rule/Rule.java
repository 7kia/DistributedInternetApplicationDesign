package Rule;
import java.util.Dictionary;
import java.util.Vector;

import DiscountModule.IProduct;
import DiscountModule.Pair;
import DiscountModule.ProductType;
import DiscountModule.Range;

/*
 * Rule for set discount.
 * 
 * @param rule - content actions necessary for execute rule
 * @param optionalProductHandler - see OptionalProductHandler
 * @param compulsoryTypes - product type which have count 
 * more zero(set by range with min and max value)
 * @param optionalTypes - product type which have count zero or more
 * 
 * @see RuleFunction
 * @see OptionalProductHandler
 */
public class Rule {
	private RuleFunction rule;
	private OptionalProductHandler optionalProductHandler;
	private Dictionary<ProductType, Range> compulsoryTypes;
	private Dictionary<ProductType, Range> optionalTypes;
	public Rule(
		RuleFunction rule,
		OptionalProductHandler handler,
		Dictionary<ProductType, Range> productTypes,
		Dictionary<ProductType, Range> optionalTypes
	) {
		this.rule = rule;
		this.optionalProductHandler = handler;
		this.compulsoryTypes = productTypes;
		this.optionalTypes = optionalTypes;
	}

	public Dictionary<ProductType, Range> get—ompulsoryTypes() {
		return this.compulsoryTypes;
	}
	public Dictionary<ProductType, Range> getOptionalTypes() {
		return this.optionalTypes;
	}
	public Dictionary<IProduct, Pair<Float, Boolean>> executeRule(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
	) {
		return rule.execute(compulsoryProducts, optionalProducts);
	}
	public Boolean validateOptionalProducts(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
	) {
		return this.optionalProductHandler.canExecuteRule(optionalProducts);
	}
}
