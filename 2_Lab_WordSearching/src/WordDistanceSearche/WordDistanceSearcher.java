package WordDistanceSearche;

import java.util.Vector;

public class WordDistanceSearcher {
	private Integer packageCount;
	private Integer packageSize;
	
	public Pair<Integer, Integer> foundDistanceBetweenWords(
		SearchWords words, 
		PackageCreateProperty packageCreateProperty,
		String text
	) {
		this.packageCount = packageCreateProperty.packageCount;
		this.packageSize = packageCreateProperty.packageSize;

		PreviousPackageInfo previousPackageInfo = new PreviousPackageInfo(-1, -1);
		Pair<Integer, Integer> result = new Pair<Integer, Integer>(Integer.MAX_VALUE, Integer.MIN_VALUE);
		for(int packageIndex = 0; packageIndex < packageCount; packageIndex++) {
			PackageInfo packageInfo = new PackageInfo(
				text,//this.extractTextForPackage(text, packageIndex), 
				packageIndex,
				previousPackageInfo
			);
			result = this.setMinAndMax(
				this.processPackage(
					words, 
					packageInfo, 
					result
				),
				result
			);
		}
		return result;
	}
	
	private String extractTextForPackage(final String sourceText, final Integer packageIndex) {
		final Integer beginIndex = packageIndex * packageSize;
		return sourceText.substring(beginIndex, beginIndex + (packageSize - 1));
	}
	
	private Pair<Integer, Integer> processPackage(
		final SearchWords words, 
		final PackageInfo packageInfo,
		final Pair<Integer, Integer> previousResult
	) {
		Pair<Integer, Integer> result = new Pair<Integer, Integer>(0, 0);
		
		PreviousPackageInfo previousPackageInfo = new PreviousPackageInfo(-1, -1);
		PackageData packageData = this.extractPackageData(packageInfo, previousPackageInfo);
		Vector<Integer> word1Positions = this.foundPositions(words.word1, packageData); 
		
		for(int packageIndex = 0; packageIndex < packageCount; packageIndex++) {
			PackageInfo currentPackageInfo = new PackageInfo(packageInfo.referenceOnText, packageIndex, previousPackageInfo);
			this.processPackageAndFoundDistance(
				words, 
				currentPackageInfo, 
				previousPackageInfo,
				word1Positions,
				previousResult
			);
			previousPackageInfo = this.generatePreviousPackageInfo(currentPackageInfo);
		}
		return result;
	}

	private PreviousPackageInfo generatePreviousPackageInfo(final PackageInfo currentPackageInfo) {
		Vector<Pair<String, Integer>> foundedWords = this.foundWords(currentPackageInfo);
		final Integer lastWordIndex = foundedWords.get(foundedWords.size() - 1).getSecond();
		Integer lastPartialWordIndex = -1;
		
		final String text = currentPackageInfo.referenceOnText;
		if (text.substring(text.length() - 1) == " ") {
			lastPartialWordIndex = lastWordIndex;
		}
		return new PreviousPackageInfo(lastWordIndex, lastPartialWordIndex);
	}
	
	private Vector<Pair<String, Integer>> foundWords(final PackageInfo currentPackageInfo) {
		final String text = currentPackageInfo.referenceOnText;
		final String[] words = this.extractTextForPackage(
			text,
			currentPackageInfo.packageIndex
		).split(" ");
		Vector<Pair<String, Integer>> result = new Vector<Pair<String, Integer>>();
		Integer index = 0;
		for(String word : words) {
			result.add(new Pair<String, Integer>(word, index));
		}
		return result;
	}
	
	private PackageData extractPackageData(
		PackageInfo packageInfo, 
		PreviousPackageInfo previousPackageInfo
	) {
		return new PackageData(
			this.foundWords(packageInfo),
			packageInfo.packageIndex,
			previousPackageInfo.lastWordIndex,
			previousPackageInfo.lastPartialWordIndex
		);
	}
	
	private Vector<Integer> foundPositions(
		final String word, 
		final PackageData packageData 
	) {
		Vector<Integer> result = new Vector<Integer>();
		for(Pair<String, Integer> pair : packageData.wordList) {
			if (pair.getFirst() == word) {
				result.add(pair.getSecond());
			}
		}
		return result;
	}
	
	private Pair<Integer, Integer> processPackageAndFoundDistance(
		final SearchWords words,
		final PackageInfo packageInfo,
		PreviousPackageInfo previousPackageInfo,
		final Vector<Integer> word1Positions,
		final Pair<Integer, Integer> previousResult
	) {
		Pair<Integer, Integer> result = previousResult;
		
		PackageData packageData = this.extractPackageData(packageInfo, previousPackageInfo);
		Vector<Integer> word2Positions = this.foundPositions(words.word2, packageData); 
		
		for(int index = 0; index < word2Positions.size(); index++) {
			Pair<Integer, Integer> foundDistance = this.searchDistances(
				packageInfo, 
				word1Positions, 
				word2Positions
			);
			result = this.setMinAndMax(foundDistance, result);
		}
		return result;
	}

	private Pair<Integer, Integer> searchDistances(
		final PackageInfo packageInfo,
		final Vector<Integer> sourcewordPositions,
		final Vector<Integer> searchWordPositions
	) {
		Pair<Integer, Integer> result = new Pair<Integer, Integer>(Integer.MAX_VALUE, Integer.MIN_VALUE);
		
		if(searchWordPositions.size() > 0) {
			for(int sourceIndex = 0; sourceIndex < sourcewordPositions.size(); sourceIndex++) {
				for(int index = 0; index < searchWordPositions.size(); index++) {
					final Integer globalIndex = this.getGlobalIndex(index, packageInfo);
					final Integer distance = Math.abs(sourcewordPositions.get(sourceIndex) - globalIndex); 
					result = this.setMinAndMax(distance, result);
				}
			}

		}
		
		return result;
	}
	
	private Integer getGlobalIndex(final Integer index, final PackageInfo packageInfo) {
		return index + packageInfo.previousPackageInfo.lastWordIndex;
	}
	
	private Pair<Integer, Integer> setMinAndMax(
		final Integer distance,
		final Pair<Integer, Integer> previousResult
	) {
		return new Pair<Integer, Integer>(
			Math.min(previousResult.getFirst(), distance),
			Math.max(previousResult.getSecond(), distance)
		);
	}
	
	private Pair<Integer, Integer> setMinAndMax(
		final Pair<Integer, Integer> currentResult,
		final Pair<Integer, Integer> previousResult
	) {
		return new Pair<Integer, Integer>(
			Math.min(previousResult.getFirst(), currentResult.getFirst()),
			Math.max(previousResult.getSecond(), currentResult.getSecond())
		);
	}
}
