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
		Dictionary<ProductType, Integer> types = rule.getProductTypes();
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts = this.findProducts(types, productsAndCosts);
		Dictionary<IProduct, Pair<Float, Boolean>> result = rule.executeRule(foundProducts);
		
		this.setNewCosts(result, productsAndCosts);
	}
	
	private Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> findProducts(
			Dictionary<ProductType, Integer> types,
			Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts
	) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setNewCosts(
			Dictionary<IProduct, Pair<Float, Boolean>> result,
			Dictionary<IProduct, Pair<Float, Boolean>> productsAndCosts
	) {
		for (Enumeration<IProduct> e = result.keys(); e.hasMoreElements();) {
			Pair<Float, Boolean> properties = productsAndCosts.get(e);
			properties.setFirst(result.get(e).getFirst());
			properties.setSecond(result.get(e).getSecond());
			e.nextElement();
		}
	}
	
	
//	
//	public Vector<IProduct> applyRules(Vector<IProduct> products) {
//		Vector<IProduct> productsWithNewCost = new Vector<IProduct>(products);
//		for (Rule rule : rules) {
//			Vector<ProductType> productTypes = rule.getProductTypes();
//			
//			Vector<Pair<IProduct, Float>> productAfterUseRule = rule.executeRule(this.getPassTypeProducts(productTypes, productsWithNewCost));
//			productsWithNewCost.clear();
//			for (Pair<IProduct, Float> productAndCost : productAfterUseRule) {
//				IProduct product = productAndCost.getFirst();
//				product.setCurrentCost(productAndCost.getSecond());
//				productsWithNewCost.add(product);
//			}
//			productsWithNewCost.addAll(remainingProduct);
//			remainingProduct.clear();	
//		}
//		return productsWithNewCost;
//	}
//	
//	private static int getTypeIndex(Vector<Pair<ProductType, Vector<IProduct>>> whereSearch, ProductType type) {
//		for (int i = 0; i < whereSearch.size(); i++) {
//			Pair<ProductType, Vector<IProduct>> pair = whereSearch.get(i);
//			if (pair.getFirst() == type) {
//				return i;
//			}
//		}
//		return -1;
//	}
//	
//	public static Vector<IProduct> getProduct(
//			ProductType type,
//			Vector<Pair<ProductType, Vector<IProduct>>> whereSearch
//	) {
//		int typeIndex = getTypeIndex(whereSearch, type);
//		return whereSearch.get(typeIndex).getSecond();
//	}
//	
//	private Vector<Pair<ProductType, Vector<IProduct>>> getPassTypeProducts(
//		Vector<ProductType> types,
//		Vector<IProduct> whereTake
//	) {
//		Vector<Pair<ProductType, Vector<IProduct>>> selectProducts = new Vector<Pair<ProductType, Vector<IProduct>>>();
//		
//		for (IProduct product: whereTake) {
//			boolean isSelected = false;
//			for (ProductType type: types) {
//				if (product.getType() == type) {
//					int typeIndex = getTypeIndex(selectProducts, type);
//					if (typeIndex < 0) {
//						selectProducts.add(new Pair<ProductType, Vector<IProduct>>(type, new Vector<IProduct>()));
//						selectProducts.get(selectProducts.size() - 1).getSecond().add(product);
//					} else {
//						selectProducts.get(typeIndex).getSecond().add(product);
//					}
//					isSelected = true;
//				}
//			}
//			if (!isSelected) {
//				remainingProduct.add(product);
//			}
//			
//		}
//
//		
//		return selectProducts;
//	}
}
