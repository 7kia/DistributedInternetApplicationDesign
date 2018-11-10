package DiscountModule;

/**
 * Generate product cost. Content several function for generation of cost.
 */
public class CostGenerator {
	/*
	 * Use for cost setting.
	 * <pre>
	 * {@code
	 * CulculateCostArguments args = new CulculateCostArguments(
	 *		CostGenerator.Method.CostFactor, 
	 *		0.5f, 
	 *		true,
	 *		false
	 * );
	 * try {
	 *		RuleExecutor.calculateAndSetNewCost(firstAnyProduct, args, compulsoryFuncData);
	 * } catch (Exception e) {	
	 * 	 e.printStackTrace();
	 * }
	 * }
	 * </pre>
	 * 
	 * @see RuleExecutor#calculateAndSetNewCost
	 * @see CulculateCostArguments
	*/
	public enum Method {
		CostFactor,
		PercentageDiscount,
		AbsoluteDiscount
	}
	
	/**
	 * Generate product cost, multiply cost on cost factor
	 * 
	 * @param product - product which will have new cost
	 * @param costFactor - float number which will be multiply on product cost
	 * @param useStartCost - resolve that need use start product 
	 * cost if equal true else use current product cost
	 */
	public static final Float useCostFactor(
		IProduct product, 
		Float costFactor, 
		Boolean useStartCost
	) {
		if (useStartCost) {
			final Float startCost = product.getStartCost();
			return startCost * costFactor;
		} 
		final Float currentCost = product.getCurrentCost();
		return currentCost * costFactor;
	}
	
	/**
	 * Generate product cost, use percentage discount
	 * 
	 * @param product - product which will have new cost
	 * @param percent - number of percent
	 * @param useStartCost - resolve that need use start product 
	 * cost if equal true else use current product cost
	 */
	public static final Float usePercentageDiscount(
		IProduct product, 
		Float persent, 
		Boolean useStartCost
	) {
		return useCostFactor(product, (1.f - persent / 100.f), useStartCost);
	}
	
	/**
	 * Generate product cost, sum absolute discount with cost
	 * 
	 * @param product - product which will have new cost
	 * @param discount - absolute discount which will be sum with product cost
	 * @param useStartCost - resolve that need use start product 
	 * cost if equal true else use current product cost
	 */
	public static final Float useAbsoluteDiscount(
		IProduct product, 
		Float discount, 
		Boolean useStartCost
	) {
		if (useStartCost) {
			final Float startCost = product.getStartCost();
			return startCost - discount;
		} 
		final Float currentCost = product.getCurrentCost();
		return currentCost - discount;
	}
	
}
