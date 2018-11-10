package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import DiscountModule.*;
import Rule.CulculateCostArguments;
import Rule.GenerateCostFunc;
import Rule.OptionalProductHandler;
import Rule.Rule;
import Rule.RuleExecutor;
import Rule.RuleFunction;
import Rule.RuleFunctionData;
import Rule.SearchData;


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
	
	/*
	 * Custom set-cost functions(see CostGenerator) Test Data
	 * Prefix "standard" is used for brevity 
	 */
	private RuleFunction ruleFunctionStandard;
	private OptionalProductHandler handlerStandard;
	private Dictionary<ProductType, Range> ruleStandardTypes;
	private Dictionary<ProductType, Range> ruleStandardOptionalTypes;
	private Rule ruleStandard;
	/*
	 * Custom set-cost functions(see interface GenerateCostFunc) Test Data
	 * Prefix "Custom" is used for brevity 
	 */
	private RuleFunction ruleFunctionCustom;
	private OptionalProductHandler handlerCustom;
	private Dictionary<ProductType, Range> ruleCustomTypes;
	private Dictionary<ProductType, Range> ruleCustomOptionalTypes;
	private Rule ruleCustom;

	public DiscountLibTest() {
		this.initTypes();
		this.initTest1Data();
		this.initTest3Data();
		this.initTest4Data();
		this.initTest5Data();
		this.initStandartSetCostFuncTestData();
		this.initCustomSetCostFuncTestData();
		this.initComplexTestData();
	}

	private void initTypes() {
		this.typeA = new ProductType("A", 100.0f);
		this.typeB = new ProductType("B", 100.0f);
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
			
			SearchData firstAProduct = new SearchData(this.typeA, 0);
			SearchData firstBProduct = new SearchData(this.typeB, 0);
			
			CulculateCostArguments firstAArgs = new CulculateCostArguments(
				CostGenerator.Method.PercentageDiscount, 
				10.f, 
				true,
				false
			);
			CulculateCostArguments firstBArgs = new CulculateCostArguments(
				CostGenerator.Method.PercentageDiscount, 
				10.f, 
				true,
				false
			);
			
			RuleFunctionData ruleFuncData = new RuleFunctionData(compulsoryProducts, result);
			try {
				RuleExecutor.calculateAndSetNewCost(firstAProduct, firstAArgs, ruleFuncData);
				RuleExecutor.calculateAndSetNewCost(firstBProduct, firstBArgs, ruleFuncData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
			
			SearchData firstEProduct = new SearchData(this.typeE, 0);
			SearchData firstFProduct = new SearchData(this.typeF, 0);
			SearchData firstGProduct = new SearchData(this.typeG, 0);
			
			CulculateCostArguments args = new CulculateCostArguments(
				CostGenerator.Method.PercentageDiscount, 
				5.f, 
				true,
				false
			);
			RuleFunctionData ruleFuncData = new RuleFunctionData(compulsoryProducts, result);

			try {
				RuleExecutor.calculateAndSetNewCost(firstEProduct, args, ruleFuncData);
				RuleExecutor.calculateAndSetNewCost(firstFProduct, args, ruleFuncData);
				RuleExecutor.calculateAndSetNewCost(firstGProduct, args, ruleFuncData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
			
			SearchData firstAProduct = new SearchData(this.typeA, 0);
			SearchData firstKProduct = new SearchData(this.typeK, 0);
			SearchData firstLProduct = new SearchData(this.typeL, 0);
			SearchData firstMProduct = new SearchData(this.typeM, 0);

			CulculateCostArguments aArgs = new CulculateCostArguments(
				CostGenerator.Method.AbsoluteDiscount, 
				0.f, 
				true,
				false
			);
			CulculateCostArguments args = new CulculateCostArguments(
				CostGenerator.Method.PercentageDiscount, 
				5.f, 
				true,
				false
			);
			
			RuleFunctionData compulsoryFuncData = new RuleFunctionData(compulsoryProducts, result);
			RuleFunctionData optionalFuncData = new RuleFunctionData(optionalProducts, result);

			try {
				if ((kProducts != null) || (lProducts != null) || (mProducts != null)) {
					RuleExecutor.calculateAndSetNewCost(firstAProduct, aArgs, compulsoryFuncData);		
				}
				if (kProducts != null) {
					RuleExecutor.calculateAndSetNewCost(firstKProduct, args, optionalFuncData);
				} else if (lProducts != null) {
					RuleExecutor.calculateAndSetNewCost(firstLProduct, args, optionalFuncData);
				} else if (mProducts != null) {
					RuleExecutor.calculateAndSetNewCost(firstMProduct, args, optionalFuncData);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			
			SearchData firstAnyProduct = new SearchData(ProductType.anyType, 0);
			SearchData secondAnyProduct = new SearchData(ProductType.anyType, 1);
			SearchData thirdAnyProduct = new SearchData(ProductType.anyType, 2);

			CulculateCostArguments args = new CulculateCostArguments(
				CostGenerator.Method.PercentageDiscount, 
				5.f, 
				true,
				false
			);
			RuleFunctionData compulsoryFuncData = new RuleFunctionData(compulsoryProducts, result);

			try {
				RuleExecutor.calculateAndSetNewCost(firstAnyProduct, args, compulsoryFuncData);
				RuleExecutor.calculateAndSetNewCost(secondAnyProduct, args, compulsoryFuncData);
				RuleExecutor.calculateAndSetNewCost(thirdAnyProduct, args, compulsoryFuncData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

	private void initStandartSetCostFuncTestData() {
		this.ruleFunctionStandard = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			
			SearchData firstAnyProduct = new SearchData(ProductType.anyType, 0);
			SearchData secondAnyProduct = new SearchData(ProductType.anyType, 1);
			SearchData thirdAnyProduct = new SearchData(ProductType.anyType, 2);

			CulculateCostArguments args1 = new CulculateCostArguments(
				CostGenerator.Method.AbsoluteDiscount, 
				5.f, 
				true,
				false
			);
			CulculateCostArguments args2 = new CulculateCostArguments(
				CostGenerator.Method.CostFactor, 
				0.5f, 
				true,
				false
			);
			CulculateCostArguments args3 = new CulculateCostArguments(
				CostGenerator.Method.PercentageDiscount, 
				25.f, 
				true,
				false
			);
			RuleFunctionData compulsoryFuncData = new RuleFunctionData(compulsoryProducts, result);

			try {
				RuleExecutor.calculateAndSetNewCost(firstAnyProduct, args1, compulsoryFuncData);
				RuleExecutor.calculateAndSetNewCost(secondAnyProduct, args2, compulsoryFuncData);
				RuleExecutor.calculateAndSetNewCost(thirdAnyProduct, args3, compulsoryFuncData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		};
		
		this.handlerStandard = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			return true;
		};
		
		this.ruleStandardTypes = new Hashtable<ProductType, Range>();
		this.ruleStandardTypes.put(ProductType.anyType, new Range(3,3));
		this.ruleStandardOptionalTypes = new Hashtable<ProductType, Range>();
		
		this.ruleStandard = new Rule(
			ruleFunctionStandard,
			handlerStandard, 
			ruleStandardTypes,
			ruleStandardOptionalTypes
		);
	}
	
	private void initCustomSetCostFuncTestData() {
		this.ruleFunctionCustom = (
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> compulsoryProducts,
				Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			Dictionary<IProduct, Pair<Float, Boolean>> result = new Hashtable<IProduct, Pair<Float, Boolean>>();
			
			SearchData searchData = new SearchData(ProductType.anyType, 0);
			GenerateCostFunc customFunc = (Float currentCost, Float startCost) -> {
				return currentCost + startCost;
			};

			RuleFunctionData compulsoryFuncData = new RuleFunctionData(compulsoryProducts, result);

			try {
				RuleExecutor.setNewCost(searchData, customFunc, false, compulsoryFuncData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		};
		
		this.handlerCustom = (
			Dictionary<ProductType, Vector<Pair<IProduct, Boolean>>> optionalProducts
		) -> {
			return true;
		};
		
		this.ruleCustomTypes = new Hashtable<ProductType, Range>();
		this.ruleCustomTypes.put(ProductType.anyType, new Range(1,1));
		this.ruleCustomOptionalTypes = new Hashtable<ProductType, Range>();
		
		this.ruleCustom = new Rule(
			ruleFunctionCustom,
			handlerCustom, 
			ruleCustomTypes,
			ruleCustomOptionalTypes
		);
	}
	
	
	private void initComplexTestData() {
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(rule1);
		rules.add(rule3);
		rules.add(rule4);
		rules.add(rule5);
		this.discountSystem = new RuleExecutor(rules);
	}
	
	
	
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
	void StandartSetCostFuncTest() {
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		productList.add(new CProduct(typeB));
		productList.add(new CProduct(typeC));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(ruleStandard);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costA = 95.f;
		final Float costB = 50.f;
		final Float costC = 75.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), costA);
		assertEquals(productWitNewCost.get(productList.get(1)).getFirst(), costB);
		assertEquals(productWitNewCost.get(productList.get(2)).getFirst(), costC);

		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(1)).getSecond(), false);
		assertEquals(productWitNewCost.get(productList.get(2)).getSecond(), false);
	}
	
	@Test
	void CustomSetCostFuncTest() {
		Vector<IProduct> productList = new Vector<IProduct>();
		productList.add(new CProduct(typeA));
		
		Vector<Rule> rules = new Vector<Rule>();
		rules.add(ruleCustom);
		RuleExecutor discountSystem = new RuleExecutor(rules);
		Dictionary<IProduct, Pair<Float, Boolean>> productWitNewCost = discountSystem.applyRules(productList);
		
		final Float costA = 200.f;
		assertEquals(productWitNewCost.get(productList.get(0)).getFirst(), costA);
		
		assertEquals(productWitNewCost.get(productList.get(0)).getSecond(), false);
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
