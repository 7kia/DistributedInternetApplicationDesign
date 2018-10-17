package DiscountModule;

public class Range {
	private Integer min;
	private Integer max;
	public Range(Integer min, Integer max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean contains(Integer value) {
		return (this.min <= value) && (value <= this.max);
	}
}
