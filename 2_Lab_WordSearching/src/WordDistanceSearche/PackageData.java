package WordDistanceSearche;

import java.util.Vector;

public class PackageData extends PreviousPackageInfo {
	public Vector<Pair<String, Integer>> wordList;
	public Integer packageIndex;
	
	public PackageData(
			final Vector<Pair<String, Integer>> wordList,
			final Integer packageIndex,
			final Integer lastWordIndex,
			final Integer lastPartialWordIndex
	) {
		super(lastWordIndex, lastPartialWordIndex);
		this.wordList = wordList;
		this.packageIndex = packageIndex;
	}
}
