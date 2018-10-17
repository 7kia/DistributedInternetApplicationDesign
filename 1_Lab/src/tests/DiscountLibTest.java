package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Dictionary;
import java.util.Hashtable;
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
	ProductType typeK = new ProductType("K", 100.0f);
	ProductType typeL = new ProductType("L", 100.0f);
	ProductType typeM = new ProductType("M", 100.0f);
	
	private void setNewCost(
		ProductType type,
		final Integer productNumber,
		final Float costFactor,
		Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> foundProducts,
		Dictionary<IProduct, Pair<Float, Boolean>> result
	) {
		Vector<Pair<IProduct, Boolean>> aProducts = foundProducts.get(type);
		if (aProducts != null) {
			IProduct productA = aProducts.get(productNumber).getFirst();
			Pair<Float, Boolean> aProperty = new Pair<Float, Boolean>(productA.getCurrentCost() * costFactor, false);
			result.put(productA, aProperty);
		}
	};
	
	@Test
	void test1Rule() {
		RuleFunction ruleFunction1 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			setNewCost(typeB, 0, 0.9f, compulsoryProducts, result);
			setNewCost(typeA, 0, 0.9f, compulsoryProducts, result);

			return result;
		};
		
		OptionalProductHandler handler1 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			return true;
		};
		
		Dictionary<ProductType, Range> rule1Types = new Hashtable<ProductType, Range>();
		rule1Types.put(typeA, new Range(1,1));
		rule1Types.put(typeB, new Range(1,1));
		Dictionary<ProductType, Range> rule1OptionalTypes = new Hashtable<ProductType, Range>();
	
		Rule rule = new Rule(ruleFunction1, handler1, rule1Types, rule1OptionalTypes);
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeB));
		productList.add(new CProduct(typeC));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costA = 90.f;
		final Float costB = 90.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), costA);
		assertEquals(productWitNewCost.get(productList.get(1)).getFirst(), costB);
		assertEquals(productWitNewCost.get(productList.get(2)).getFirst(), typeC.getCost());
		
		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(1)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(2)).getSecond(), true);
	}
	
	@Test
	void test3Rule() {
		RuleFunction ruleFunction3 = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			setNewCost(typeG, 0, 0.95f, compulsoryProducts, result);
			setNewCost(typeF, 0, 0.95f, compulsoryProducts, result);
			setNewCost(typeE, 0, 0.95f, compulsoryProducts, result);
			return result;
		};
		OptionalProductHandler handler1 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			return true;
		};
		
		Dictionary<ProductType, Range> rule1Types = new Hashtable<ProductType, Range>();
		rule1Types.put(typeE, new Range(1,1));
		rule1Types.put(typeF, new Range(1,1));
		rule1Types.put(typeG, new Range(1,1));
		Dictionary<ProductType, Range> rule1OptionalTypes = new Hashtable<ProductType, Range>();

		Rule rule = new Rule(ruleFunction3, handler1, rule1Types, rule1OptionalTypes);
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeE));
		productList.add(new CProduct(typeF));
		productList.add(new CProduct(typeG));
		productList.add(new CProduct(typeF));
		productList.add(new CProduct(typeG));
		productList.add(new CProduct(typeE));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costE = 95.f;
		final Float costF = 95.f;
		final Float costG = 95.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), costE);
		assertEquals(productWitNewCost.get(productList.get(1)).getFirst(), costF);
		assertEquals(productWitNewCost.get(productList.get(2)).getFirst(), costG);
		assertEquals(productWitNewCost.get(productList.get(3)).getFirst(), costF);
		assertEquals(productWitNewCost.get(productList.get(4)).getFirst(), costG);
		assertEquals(productWitNewCost.get(productList.get(5)).getFirst(), costE);
		
		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(1)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(2)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(3)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(4)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(5)).getSecond(), false);
	}
	
	
	@Test
	void test4Rule() {
		RuleFunction ruleFunction4 = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			
			Vector<Pair<IProduct, Boolean>> kProducts = optionalProducts.get(typeK);
			Vector<Pair<IProduct, Boolean>> lProducts = optionalProducts.get(typeL);
			Vector<Pair<IProduct, Boolean>> mProducts = optionalProducts.get(typeM);
			if ((kProducts != null) || (lProducts != null) || (mProducts != null)) {
				setNewCost(typeA, 0, 1.f, compulsoryProducts, result);
			}
			if (kProducts != null) {
				setNewCost(typeK, 0, 0.95f, optionalProducts, result);
			} else if (lProducts != null) {
				setNewCost(typeL, 0, 0.95f, optionalProducts, result);
			} else if (mProducts != null) {
				setNewCost(typeM, 0, 0.95f, optionalProducts, result);
			}

			return result;
		};
		OptionalProductHandler handler1 = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Vector<Pair<IProduct, Boolean>> kProducts = optionalProducts.get(typeK);
			Vector<Pair<IProduct, Boolean>> lProducts = optionalProducts.get(typeL);
			Vector<Pair<IProduct, Boolean>> mProducts = optionalProducts.get(typeM);
			return (kProducts != null) || (lProducts != null) || (mProducts != null);
		};
		
		Dictionary<ProductType, Range> rule1Types = new Hashtable<ProductType, Range>();
		rule1Types.put(typeA, new Range(1,1));
		Dictionary<ProductType, Range> rule1OptionalTypes = new Hashtable<ProductType, Range>();
		rule1OptionalTypes.put(typeK, new Range(1,1));
		rule1OptionalTypes.put(typeL, new Range(1,1));
		rule1OptionalTypes.put(typeM, new Range(1,1));
		
		Rule rule = new Rule(ruleFunction4, handler1, rule1Types, rule1OptionalTypes);
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeK));
		productList.add(new CProduct(typeL));
		productList.add(new CProduct(typeM));
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeA));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costK = 95.f;
		final Float costL = 95.f;
		final Float costM = 95.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), typeA.getCost());
		assertEquals(productWitNewCost.get(productList.get(1)).getFirst(), costK);
		assertEquals(productWitNewCost.get(productList.get(2)).getFirst(), costL);
		assertEquals(productWitNewCost.get(productList.get(3)).getFirst(), costM);
		assertEquals(productWitNewCost.get(productList.get(4)).getFirst(), typeA.getCost());
		assertEquals(productWitNewCost.get(productList.get(5)).getFirst(), typeA.getCost());
		
		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(1)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(2)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(3)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(4)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(5)).getSecond(), false);
	}

	RuleFunction ruleFunction5 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
	) -> {
		Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
		setNewCost(typeA, 0, 0.9f, compulsoryProducts, result);
		setNewCost(typeB, 0, 0.9f, compulsoryProducts, result);
		return result;
	};
	@Test
	void test5Rule() {
		Dictionary<ProductType, Range> rule1Types = new Hashtable<ProductType, Range>();
		rule1Types.put(typeA, new Range(1,1));
		rule1Types.put(typeB, new Range(1,1));
		Dictionary<ProductType, Range> rule1OptionalTypes = new Hashtable<ProductType, Range>();

		Rule rule = new Rule(ruleFunction5, rule1Types, rule1OptionalTypes);
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeB));
		productList.add(new CProduct(typeB));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costA = 90.f;
		final Float costB = 90.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), costA);
		assertEquals(productWitNewCost.get(productList.get(1)).getFirst(), costB);
		assertEquals(productWitNewCost.get(productList.get(2)).getFirst(), typeB.getCost());
		
		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(1)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(2)).getSecond(), true);
	}

}
