package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import DiscountModule.*;

class DiscountLibTest {
	ProductType typeA = new ProductType("A", 100.0f);
	ProductType typeB = new ProductType("B", 100.0f);
	ProductType typeC = new ProductType("C", 100.0f);
	ProductType typeD = new ProductType("D", 100.0f);
	ProductType typeE = new ProductType("E", 100.0f);
	ProductType typeF = new ProductType("F", 100.0f);
	ProductType typeG = new ProductType("G", 100.0f);

	Function<
		Vector<Pair<ProductType, Vector<IProduct>>>,
		Vector<Pair<IProduct, Float>>
	> ruleFunction1 = (Vector<Pair<ProductType, Vector<IProduct>>> products) -> {
		Vector<Pair<IProduct, Float>> result = new Vector<Pair<IProduct, Float>>();
		
		Vector<IProduct> aProducts = RuleExecutor.getProduct(typeA, products);
		Vector<IProduct> bProducts = RuleExecutor.getProduct(typeB, products);

		int i = 0;
		while ((i < aProducts.size()) && (i < bProducts.size())) {
			IProduct productA = aProducts.get(i);
			IProduct productB = bProducts.get(i);

			result.add(new Pair<IProduct, Float>(productA, productA.getCurrentCost() * 0.9f));
			result.add(new Pair<IProduct, Float>(productB, productB.getCurrentCost() * 0.9f));
			i++;
		}
		return result;
	};
	@Test
	void test1Rule() {
		Vector<ProductType> rule1Types = new Vector<ProductType>();
		rule1Types.add(typeA);
		rule1Types.add(typeB);
	
		Rule rule = new Rule(ruleFunction1, rule1Types);
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeB));
		productList.add(new CProduct(typeB));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Vector<IProduct> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costA = 90.f;
		final Float costB = 90.f;
		assertEquals(productWitNewCost.get(0).getCurrentCost(), costA);
		assertEquals(productWitNewCost.get(1).getCurrentCost(), costB);
		assertEquals(productWitNewCost.get(2).getCurrentCost(), typeB.getCost());
		
		assertEquals(productWitNewCost.get(0).getType(), typeA);
		assertEquals(productWitNewCost.get(1).getType(), typeB);
		assertEquals(productWitNewCost.get(2).getType(), typeB);
	}
	Function<
		Vector<Pair<ProductType, Vector<IProduct>>>,
		Vector<Pair<IProduct, Float>>
	> ruleFunction2 = (Vector<Pair<ProductType, Vector<IProduct>>> products) -> {
		Vector<Pair<IProduct, Float>> result = new Vector<Pair<IProduct, Float>>();
		
		Vector<IProduct> dProducts = RuleExecutor.getProduct(typeD, products);
		Vector<IProduct> eProducts = RuleExecutor.getProduct(typeE, products);
	
		int i = 0;
		while ((i < dProducts.size()) && (i < eProducts.size())) {
			IProduct productD = dProducts.get(i);
			IProduct productE = eProducts.get(i);
	
			result.add(new Pair<IProduct, Float>(productD, productD.getCurrentCost() * 0.95f));
			result.add(new Pair<IProduct, Float>(productE, productE.getCurrentCost() * 0.95f));
			i++;
		}
		return result;
	};
	@Test
	void test2Rule() {
		Vector<ProductType> rule1Types = new Vector<ProductType>();
		rule1Types.add(typeD);
		rule1Types.add(typeE);
	
		Rule rule = new Rule(ruleFunction2, rule1Types);
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeD));
		productList.add(new CProduct(typeE));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Vector<IProduct> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costD = 95.f;
		final Float costE = 95.f;
		assertEquals(productWitNewCost.get(0).getCurrentCost(), costD);
		assertEquals(productWitNewCost.get(1).getCurrentCost(), costE);
		
		assertEquals(productWitNewCost.get(0).getType(), typeD);
		assertEquals(productWitNewCost.get(1).getType(), typeE);
	}
	Function<
		Vector<Pair<ProductType, Vector<IProduct>>>,
		Vector<Pair<IProduct, Float>>
	> ruleFunction3 = (Vector<Pair<ProductType, Vector<IProduct>>> products) -> {
		Vector<Pair<IProduct, Float>> result = new Vector<Pair<IProduct, Float>>();
		
		Vector<IProduct> eProducts = RuleExecutor.getProduct(typeE, products);
		Vector<IProduct> fProducts = RuleExecutor.getProduct(typeF, products);
		Vector<IProduct> gProducts = RuleExecutor.getProduct(typeG, products);

		int i = 0;
		while ((i < eProducts.size()) && (i < gProducts.size())) {
			IProduct productE = eProducts.get(i);
			IProduct productF = fProducts.get(i);
			IProduct productG = gProducts.get(i);
			result.add(new Pair<IProduct, Float>(productE, productE.getCurrentCost() * 0.95f));
			result.add(new Pair<IProduct, Float>(productF, productF.getCurrentCost() * 0.95f));
			result.add(new Pair<IProduct, Float>(productG, productG.getCurrentCost() * 0.95f));
			i++;
		}
		return result;
	};
	@Test
	void test3Rule() {
		Vector<ProductType> rule1Types = new Vector<ProductType>();
		rule1Types.add(typeD);
		rule1Types.add(typeE);
	
		Rule rule = new Rule(ruleFunction3, rule1Types);
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeE));
		productList.add(new CProduct(typeF));
		productList.add(new CProduct(typeG));
		
		productList.add(new CProduct(typeE));
		
		productList.add(new CProduct(typeF));
		productList.add(new CProduct(typeE));
		productList.add(new CProduct(typeG));

		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Vector<IProduct> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costE = 95.f;
		final Float costF = 95.f;
		final Float costG = 95.f;
		assertEquals(productWitNewCost.get(0).getCurrentCost(), costE);
		assertEquals(productWitNewCost.get(1).getCurrentCost(), costF);
		assertEquals(productWitNewCost.get(2).getCurrentCost(), costG);
		
		assertEquals(productWitNewCost.get(3).getCurrentCost(), typeE.getCost());
		
		assertEquals(productWitNewCost.get(0).getCurrentCost(), costE);
		assertEquals(productWitNewCost.get(1).getCurrentCost(), costF);
		assertEquals(productWitNewCost.get(2).getCurrentCost(), costG);
	}
}
