package Rule;

import DiscountModule.CostGenerator;

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
