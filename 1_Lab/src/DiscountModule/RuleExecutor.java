package DiscountModule;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class RuleExecutor {
	private Vector<Rule> rules;
	public RuleExecutor(Vector<Rule> rules) {
		this.rules = rules;
	}
	
	public Dictionary<IProduct, Pair<Float, Boolean>> applyRules(Vector<IProduct> products) {
		Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts = this.fillProductsAndCosts(products);	
		for (Rule rule : rules) {
			this.appluRule(rule, productsAndCosts);;
		}
		return productsAndCosts;
	}
	
	private Dictionary<IProduct, Pair<Float, Boolean>> fillProductsAndCosts(Vector<IProduct> products) {
		Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts = new Hashtable<IProduct, Pair<Float, Boolean>>();
		for (IProduct product : products) {
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
				Boolean correspondToRule = this.correspondToRule(product, types, foundProducts);
				if (correspondToRule) {
					this.addFoundProduct(product, foundProducts);
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
		final IProduct product,
		Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts
	) {
		ProductType type = product.getType();
		if (foundProducts.containsKey(type)) {
			Vector<Pair<IProduct, Boolean>> products = foundProducts.get(type);
			products.addElement(new Pair<IProduct, Boolean>(product, true));
		} else {
			Vector<Pair<IProduct, Boolean>> newVector = new Vector<Pair<IProduct, Boolean>>();
			newVector.add(new Pair<IProduct, Boolean>(product, true));
			
			foundProducts.put(type, newVector);
		}
		
	}

	private Boolean correspondToRule(
		final IProduct product, 
		final Dictionary<ProductType, Range> types,
		final Hashtable<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts
	) {
		Boolean typeEqual = false;
		for (Enumeration<ProductType> enumerator = types.keys(); enumerator.hasMoreElements();) {
			ProductType type = enumerator.nextElement();
			if (type == product.getType()) {
				typeEqual = true;
				break;
			}
		}
		Boolean amountCorrect = false;
		if (typeEqual) {
			amountCorrect = !this.productAmountEnough(product.getType(), types, foundProducts);	
		}
		return typeEqual && amountCorrect;
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
}
