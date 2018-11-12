package WordDistanceSearche;

/*
 * @param lastWordIndex - is global index
 * @param lastPartialWordIndex - is global index
 */
public class PreviousPackageInfo {
	public Integer lastWordIndex;
	public Pair<String, Integer> lastPartialWord;
	
	public PreviousPackageInfo(
		final Integer lastWordIndex,
		Pair<String, Integer> lastPartialWord 
	) {
		this.lastWordIndex = lastWordIndex;
		this.lastPartialWord = lastPartialWord;
	}
}
