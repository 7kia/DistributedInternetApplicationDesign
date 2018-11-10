package Rule;

/*
 * Function for cost setting.
 * 
 * @see RuleExecutor
 */
@FunctionalInterface
public interface GenerateCostFunc {
    public Float generateCost(Float currentCost, Float startCost);
}
