package Rule;

import java.util.Dictionary;
import java.util.Vector;

import DiscountModule.IProduct;
import DiscountModule.Pair;
import DiscountModule.ProductType;

/*
 * Function define what do when is concrete product type 
 * count can be zero or more.
 * For example if you need set discount for product A 
 * and any from [K, L, M] then function will return true
 * if one from [K, L, M] found, else false)
 * 
 * @see Rule
 */
@FunctionalInterface
public interface OptionalProductHandler 
{ 
	Boolean canExecuteRule(
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
	); 
} 

