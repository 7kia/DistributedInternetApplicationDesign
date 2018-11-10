package Rule;

import java.util.Dictionary;
import java.util.Vector;

import DiscountModule.IProduct;
import DiscountModule.Pair;
import DiscountModule.ProductType;

/*
 * Accept product which correspond for the rule
 * 
 * @param compulsoryProducts - product which have count more zero
 * @param optionalProducts - product which have count equal zero or more
 * 
 * @see Rule
 */
@FunctionalInterface
public interface RuleFunction 
{ 
	Dictionary<IProduct, Pair<Float, Boolean>> execute(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
	); 
} 
