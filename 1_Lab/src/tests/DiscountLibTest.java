package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import DiscountModule.*;


class DiscountLibTest {
	private ProductType typeA;
	private ProductType typeB;
	private ProductType typeC;
	private ProductType typeD;
	private ProductType typeE;
	private ProductType typeF;
	private ProductType typeG;
	private ProductType typeK;
	private ProductType typeL;
	private ProductType typeM;
	
	private RuleFunction ruleFunction1;
	private RuleFunction ruleFunction3;
	private RuleFunction ruleFunction4;
	private RuleFunction ruleFunction5;
	private OptionalProductHandler handler1;
	private OptionalProductHandler handler3;
	private OptionalProductHandler handler4;
	private OptionalProductHandler handler5;
	private Dictionary<ProductType, Range> rule1Types;
	private Dictionary<ProductType, Range> rule3Types;
	private Dictionary<ProductType, Range> rule4Types;
	private Dictionary<ProductType, Range> rule5Types;
	private Dictionary<ProductType, Range> rule1OptionalTypes;
	private Dictionary<ProductType, Range> rule3OptionalTypes;
	private Dictionary<ProductType, Range> rule4OptionalTypes;
	private Dictionary<ProductType, Range> rule5OptionalTypes;
	private Rule rule1;
	private Rule rule3;
	private Rule rule4;
	private Rule rule5;
	private RuleExecutor discountSystem;
	public DiscountLibTest() {
		this.initTypes();
		this.initTest1Data();
		this.initTest3Data();
		this.initTest4Data();
		this.initTest5Data();
		this.initComplexTestData();
	}

	private void initTypes() {
		this.typeA = new ProductType("A", 100.0f);
		this.typeB = new ProductType("C", 100.0f);
		this.typeC = new ProductType("C", 100.0f);
		this.typeD = new ProductType("D", 100.0f);
		this.typeE = new ProductType("E", 100.0f);
		this.typeF = new ProductType("F", 100.0f);
		this.typeG = new ProductType("G", 100.0f);
		this.typeK = new ProductType("K", 100.0f);
		this.typeL = new ProductType("L", 100.0f);
		this.typeM = new ProductType("M", 100.0f);
	}

	private void initTest1Data() {
		this.ruleFunction1 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			setNewCost(this.typeB, 0, 0.9f, compulsoryProducts, result);
			setNewCost(this.typeA, 0, 0.9f, compulsoryProducts, result);

			return result;
		};
		
		this.handler1 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			return true;
		};
		
		this.rule1Types = new Hashtable<ProductType, Range>();
		this.rule1Types.put(this.typeA, new Range(1,1));
		this.rule1Types.put(this.typeB, new Range(1,1));
		this.rule1OptionalTypes = new Hashtable<ProductType, Range>();
		
		this.rule1 = new Rule(ruleFunction1, handler1, rule1Types, rule1OptionalTypes);
	}
	
	private void initTest3Data() {
		this.ruleFunction3 = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			setNewCost(typeG, 0, 0.95f, compulsoryProducts, result);
			setNewCost(typeF, 0, 0.95f, compulsoryProducts, result);
			setNewCost(typeE, 0, 0.95f, compulsoryProducts, result);
			return result;
		};
		
		this.handler3 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			return true;
		};
		
		this.rule3Types = new Hashtable<ProductType, Range>();
		this.rule3Types.put(typeE, new Range(1,1));
		this.rule3Types.put(typeF, new Range(1,1));
		this.rule3Types.put(typeG, new Range(1,1));
		this.rule3OptionalTypes = new Hashtable<ProductType, Range>();
		
		this.rule3 = new Rule(ruleFunction3, handler3, rule3Types, rule3OptionalTypes);
	}
	
	
	private void initTest4Data() {
		this.ruleFunction4 = (
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
		this.handler4 = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Vector<Pair<IProduct, Boolean>> kProducts = optionalProducts.get(typeK);
			Vector<Pair<IProduct, Boolean>> lProducts = optionalProducts.get(typeL);
			Vector<Pair<IProduct, Boolean>> mProducts = optionalProducts.get(typeM);
			return (kProducts != null) || (lProducts != null) || (mProducts != null);
		};
		
		this.rule4Types = new Hashtable<ProductType, Range>();
		this.rule4Types.put(typeA, new Range(1,1));
		
		this.rule4OptionalTypes = new Hashtable<ProductType, Range>();
		this.rule4OptionalTypes.put(typeK, new Range(1,1));
		this.rule4OptionalTypes.put(typeL, new Range(1,1));
		this.rule4OptionalTypes.put(typeM, new Range(1,1));
		
		this.rule4 = new Rule(ruleFunction4, handler4, rule4Types, rule4OptionalTypes);
	}

	private void initTest5Data() {
		this.ruleFunction5 = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			setNewCost(ProductType.anyType, 0, 0.95f, compulsoryProducts, result);
			setNewCost(ProductType.anyType, 1, 0.95f, compulsoryProducts, result);
			setNewCost(ProductType.anyType, 2, 0.95f, compulsoryProducts, result);
			return result;
		};
		
		this.handler5 = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			return true;
		};
		
		this.rule5Types = new Hashtable<ProductType, Range>();
		this.rule5Types.put(ProductType.anyType, new Range(3,3));
		this.rule5OptionalTypes = new Hashtable<ProductType, Range>();
		
		this.rule5 = new Rule(ruleFunction5, handler5, rule5Types, rule5OptionalTypes);
	}
	
	
	private void initComplexTestData() {
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule1);
		rules.add(rule3);
		rules.add(rule4);
		rules.add(rule5);
		this.discountSystem = new RuleExecutor(rules);
	}
	
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
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeB));
		productList.add(new CProduct(typeC));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule1);
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
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeE));
		productList.add(new CProduct(typeF));
		productList.add(new CProduct(typeG));
		productList.add(new CProduct(typeF));
		productList.add(new CProduct(typeG));
		productList.add(new CProduct(typeE));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule3);
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
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeK));
		productList.add(new CProduct(typeL));
		productList.add(new CProduct(typeM));
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeA));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule4);
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

	
	@Test
	void test5Rule() {
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeB));
		productList.add(new CProduct(typeC));
		productList.add(new CProduct(typeD));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule5);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costA = 95.f;
		final Float costB = 95.f;
		final Float costC = 95.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), costA);
		assertEquals(productWitNewCost.get(productList.get(1)).getFirst(), costB);
		assertEquals(productWitNewCost.get(productList.get(2)).getFirst(), costC);
		assertEquals(productWitNewCost.get(productList.get(3)).getFirst(), typeD.getCost());

		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(1)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(2)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(3)).getSecond(), true);
	}
	
	@Test
	void complecxTest() {
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeB));
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeK));
		productList.add(new CProduct(typeK));
		productList.add(new CProduct(typeD));
		productList.add(new CProduct(typeE));
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = this.discountSystem.applyRules(productList);
		
		final Float costA = 90.f;
		final Float costB = 90.f;
		final Float costK = 95.f;
		final Float costKAfter4Rule = 95.f;
		final Float costDAfter4Rule = 95.f;
		final Float costEAfter4Rule = 95.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), costA);
		assertEquals(productWitNewCost.get(productList.get(1)).getFirst(), costB);
		assertEquals(productWitNewCost.get(productList.get(2)).getFirst(), typeA.getCost());
		assertEquals(productWitNewCost.get(productList.get(3)).getFirst(), costK);
		assertEquals(productWitNewCost.get(productList.get(4)).getFirst(), costKAfter4Rule);
		assertEquals(productWitNewCost.get(productList.get(5)).getFirst(), costDAfter4Rule);
		assertEquals(productWitNewCost.get(productList.get(6)).getFirst(), costEAfter4Rule);
		
		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(1)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(2)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(3)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(4)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(5)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(6)).getSecond(), false);

	}

}
