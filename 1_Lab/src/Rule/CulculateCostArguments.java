package Rule;

import DiscountModule.CostGenerator;

/*
 * Need for simplify set cost process.
 * 
 * @param method - one from CostGenerator methods
 * @param argument - number for calculates
 * @param useStartCost - if true use start product cost, else use current cost
 * @param canParticipantToDiscount - if true product after using can will use to other discount
 * 
 * @see CostGenerator
 */
public class CulculateCostArguments {
	public CostGenerator.Method method;
	public Float argument;
	public Boolean useStartCost;
	public Boolean canParticipantToDiscount;
	
	public CulculateCostArguments(
		CostGenerator.Method method,
		Float argument,
		Boolean useStartCost,
		Boolean canParticipantToDiscount
	) {
		this.method = method;
		this.argument = argument;
		this.useStartCost = useStartCost;
		this.canParticipantToDiscount = canParticipantToDiscount;
	}
}
