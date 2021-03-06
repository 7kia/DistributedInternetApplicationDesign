package Rule;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import DiscountModule.CostGenerator;
import DiscountModule.IProduct;
import DiscountModule.Pair;
import DiscountModule.ProductType;
import DiscountModule.Range;


/*
 * Content rule as RuleFunction. 
 * When will create rule function use RuleExecutor.setNewCost for
 * simplify cost setting.
 * 
 * @see Rule
 */
public class RuleExecutor {
	private Vector<Rule> rules;
	public RuleExecutor(Vector<Rule> rules) {
		this.rules = rules;
	}
	
	/*
	 * @return Dictionary<IProduct, Pair<Float, Boolean>> - pair of IProduct 
	 * and it new cost(product not change)
	 */
	public Dictionary<IProduct, Pair<Float, Boolean>> applyRules(final Vector<IProduct> products) {
		Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts = this.fillProductsAndCosts(products);	
		for (Rule rule : rules) {
			this.appluRule(rule, productsAndCosts);;
		}
		return productsAndCosts;
	}

	/*
	 * If necessary set cost by use standard set-cost methods then
	 * use this method.
	 * Simplify creation rule function. Found products with pass type.
	 * From found products search product with pass index and 
	 * set cost for found product.
	 * 
	 * @param searchData - see SearchData
	 * @param arguments - see CulculateCostArguments
	 * @param ruleFunctionData - see RuleFunctionData
	 * 
	 * @see CulculateCostArguments
	 * @see CulculateCostArguments
	 * @see RuleFunctionData
	 */
	public static void calculateAndSetNewCost(
		final SearchData searchData,
		final CulculateCostArguments arguments,
		RuleFunctionData ruleFunctionData
	) throws Exception {
		final ProductType type = searchData.type;
		final Integer productIndex = searchData.productIndex;
		
		final CostGenerator.Method method = arguments.method;
		final Float argument = arguments.argument;
		final Boolean useStartCost = arguments.useStartCost;
		final Boolean canParticipantToDiscount = arguments.canParticipantToDiscount;
		
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts = ruleFunctionData.foundProducts;
		Float cost = 0.0f;
		IProduct product = RuleExecutor.foundProduct(type, productIndex, foundProducts);
		switch (method) {
			case CostFactor:
				cost = CostGenerator.useCostFactor(product, argument, useStartCost);
				break;
			case PercentageDiscount:
				cost = CostGenerator.usePercentageDiscount(product, argument, useStartCost);
				break;
			case AbsoluteDiscount:
				cost = CostGenerator.useAbsoluteDiscount(product, argument, useStartCost);
				break;
			default:
				throw new Exception("Not the method");
		}
		RuleExecutor.setNewCost(searchData, cost, canParticipantToDiscount, ruleFunctionData);		
	}
	
	/*
	 * Set cost which will calculate by use custom cost function.
	 * 
	 * @param searchData - see SearchData
	 * @param func - custom set cost function
	 * @param canParticipantToDiscount - if true can use the product in other discounts
	 * @param ruleFunctionData - see RuleFunctionData
	 * 
	 * @see CulculateCostArguments
	 * @see CulculateCostArguments
	 * @see RuleFunctionData
	 * 
	 * @see RuleExecutor#calculateAndSetNewCost
	 */
	public static void setNewCost(
		final SearchData searchData,
		final GenerateCostFunc func,
		final Boolean canParticipantToDiscount,
		RuleFunctionData ruleFunctionData
	) throws Exception {
		final ProductType type = searchData.type;
		final Integer productIndex = searchData.productIndex;

		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts = ruleFunctionData.foundProducts;
		
		IProduct product = RuleExecutor.foundProduct(type, productIndex, foundProducts);
		RuleExecutor.setNewCost(
			searchData, 
			func.generateCost(product.getCurrentCost(), product.getStartCost()), 
			canParticipantToDiscount, 
			ruleFunctionData
		);
	}
	
	
	public static IProduct foundProduct(
		final ProductType type,
		final Integer productIndex,
		final Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts
	) throws Exception {
		Vector<Pair<IProduct, Boolean>> aProducts;
		if (type == ProductType.anyType) {
			aProducts = foundProducts.get(ProductType.anyType);
		} else {
			aProducts = foundProducts.get(type);
		}
		
		if (aProducts != null) {
			return aProducts.get(productIndex).getFirst();
		} else {
			throw new Exception("Product not found");
		}
	}
		
	private Dictionary<IProduct, Pair<Float, Boolean>> fillProductsAndCosts(Vector<IProduct> products) {
		Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts = new Hashtable<IProduct, Pair<Float, Boolean>>();
		for (int i = 0; i < products.size(); i++) {
			IProduct product = products.get(i);
			productsAndCosts.put(product, new Pair<Float, Boolean>(product.getCurrentCost(), true));
		}
		return productsAndCosts;
	}
	
	private void appluRule(
			Rule rule, 
			Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts
	) {
		Boolean productFounds = true;
		while (productFounds) {
			Dictionary<ProductType, Range> types = rule.get—ompulsoryTypes();
			Dictionary<ProductType, Range> optionalTypes = rule.getOptionalTypes();
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts = this.findProducts(types, productsAndCosts, true);
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts = this.findProducts(optionalTypes, productsAndCosts, false);

			if ((compulsoryProducts.size() > 0) && rule.validateOptionalProducts(optionalProducts)) {
				Dictionary<IProduct, Pair<Float, Boolean>> result = rule.executeRule(compulsoryProducts, optionalProducts);
				this.setNewCosts(result, productsAndCosts);
			} else {
				productFounds = false;
			}
		}

	}
	
	private Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> findProducts(
			final Dictionary<ProductType, Range> types,
			final Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts,
			final Boolean allNecessary
	) {
		Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts = new Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>>();
		
		for (Enumeration<IProduct> enumerator = productsAndCosts.keys(); enumerator.hasMoreElements();) {
			IProduct product = enumerator.nextElement();
			Pair<Float, Boolean> properties = productsAndCosts.get(product);
			Boolean participantToDiscount = properties.getSecond();

			if (participantToDiscount) {
				ProductType type = this.correspondToRule(product, types, foundProducts);
				if (type != null) {
					this.addFoundProduct(type, product, foundProducts);
				}
			}
			if (this.enoughProducts(types, foundProducts)) {
				break;
			}
		}
		
		if (this.enoughProducts(types, foundProducts) || !allNecessary) {
			return foundProducts;
		}
		return new Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>>();
	}
	
	private boolean enoughProducts(
		Dictionary<ProductType, Range> typeAndAmounts,
		Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts
	) {
		boolean enoughProducts = true;
		for (Enumeration<ProductType> enumerator = typeAndAmounts.keys(); enumerator.hasMoreElements() && enoughProducts;) {
			ProductType type = enumerator.nextElement();
			if(!this.productAmountEnough(type, typeAndAmounts, foundProducts)) {
				enoughProducts = false;
			}
		}
		return enoughProducts;
	}

	private void addFoundProduct(
		final ProductType type,
		final IProduct product,
		Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts
	) {
		if (foundProducts.containsKey(type)) {
			Vector<Pair<IProduct, Boolean>> products = foundProducts.get(type);
			products.addElement(new Pair<IProduct, Boolean>(product, true));
		} else {
			Vector<Pair<IProduct, Boolean>> newVector = new Vector<Pair<IProduct, Boolean>>();
			newVector.add(new Pair<IProduct, Boolean>(product, true));
			
			foundProducts.put(type, newVector);
		}
		
	}

	private ProductType correspondToRule(
		final IProduct product, 
		final Dictionary<ProductType, Range> types,
		final Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts
	) {
		Boolean typeEqual = false;
		ProductType type = null;
		for (Enumeration<ProductType> enumerator = types.keys(); enumerator.hasMoreElements();) {
			type = enumerator.nextElement();
			Boolean isAnyType = (type == ProductType.anyType);
			if ((type == product.getType()) || isAnyType) {
				if (isAnyType) {
					type = ProductType.anyType;
				}
				typeEqual = true;
				break;
			}
		}
		Boolean amountCorrect = false;
		if (typeEqual) {
			amountCorrect = !this.productAmountEnough(product.getType(), types, foundProducts);	
		}
		
		if(typeEqual && amountCorrect) {
			return type;
		}
		return null;
	}

	private Boolean productAmountEnough(
		final ProductType type,
		final Dictionary<ProductType, Range> types,
		final Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts
	) {
		Boolean amountEnough = false;
		if (foundProducts.containsKey(type)) {
			Integer productCount = foundProducts.get(type).size();
			if (types.get(type).contains(productCount)) {
				amountEnough = true;
			}
		}

		return amountEnough;
	}
	
	private void setNewCosts(
			Dictionary<IProduct, Pair<Float, Boolean>> result,
			Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts
	) {
		for (Enumeration<IProduct> enumerator = result.keys(); enumerator.hasMoreElements();) {
			IProduct product = enumerator.nextElement();
			Pair<Float, Boolean> properties = productsAndCosts.get(product);
			properties.setFirst(result.get(product).getFirst());
			properties.setSecond(result.get(product).getSecond());
		}
	}
	
	private static void setNewCost(
		final SearchData searchData,
		final Float cost,
		final Boolean canParticipantToDiscount,
		RuleFunctionData ruleFunctionData
	) throws Exception {
		final ProductType type = searchData.type;
		final Integer productIndex = searchData.productIndex;
		
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts = ruleFunctionData.foundProducts;
		Dictionary<IProduct, Pair<Float, Boolean>> result = ruleFunctionData.result;
		
		IProduct productA = RuleExecutor.foundProduct(type, productIndex, foundProducts);
		Pair<Float, Boolean> aProperty = new Pair<Float, Boolean>(cost, canParticipantToDiscount);
		result.put(productA, aProperty);
	};
	
}
