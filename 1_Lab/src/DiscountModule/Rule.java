package DiscountModule;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;
import java.util.function.Function;

@FunctionalInterface
public interface RuleFunction 
{ 
	Dictionary<IProduct, Pair<Float, Boolean>> execute(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
	); 
} 

@FunctionalInterface
public interface OptionalProductHandler 
{ 
	Boolean canExecuteRule(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
	); 
} 

public class Rule {
	// Input: key: type, value: pairs<product and participant to discount(source value = true)
	// Output: key: product, value: pairs with new cost and  participant to discount
	private RuleFunction rule;
	private OptionalProductHandler handler;
	// Amount compulsory product more or equal one
	// Amount optional product more or equal zero
	// ProductType: X(specific) or null(any)
	// Range(amount): amount contains to [min ; max]
	private Dictionary<ProductType, Range> compulsoryTypes;
	private Dictionary<ProductType, Range> optionalTypes;
	public Rule(
		RuleFunction rule,
		OptionalProductHandler handler,
		Dictionary<ProductType, Range> productTypes,
		Dictionary<ProductType, Range> optionalTypes
	) {
		this.rule = rule;
		this.handler = handler;
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
		return this.handler.canExecuteRule(optionalProducts);
	}
}
