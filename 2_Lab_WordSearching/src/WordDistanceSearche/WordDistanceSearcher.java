package WordDistanceSearche;

import java.util.Vector;

public class WordDistanceSearcher {
	private final static Integer NOT_FOUND = -1;

	private Integer packageCount;
	private Integer packageSize;
	public Pair<Integer, Integer> foundDistanceBetweenWords(
		SearchWords words, 
		PackageCreateProperty packageCreateProperty,
		String text
	) {
		this.packageCount = packageCreateProperty.packageCount;
		this.packageSize = packageCreateProperty.packageSize;

		PreviousPackageInfo previousPackageInfo = new PreviousPackageInfo(
			NOT_FOUND, 
			new Pair<String, Integer>(null, NOT_FOUND)
		);
		Pair<Integer, Integer> result = new Pair<Integer, Integer>(Integer.MAX_VALUE, Integer.MIN_VALUE);
		for(int packageIndex = 0; packageIndex < packageCount; packageIndex++) {
			PackageInfo packageInfo = new PackageInfo(
				text,
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
			previousPackageInfo = this.generatePreviousPackageInfo(packageInfo);
		}
		return result;
	}
	
	private String extractTextForPackage(final String sourceText, final Integer packageIndex) {
		final Integer beginIndex = packageIndex * packageSize;
		Integer endIndex = beginIndex + packageSize;
		if (endIndex >= sourceText.length()) {
			endIndex = sourceText.length();
		}
		return sourceText.substring(beginIndex, endIndex);
	}
	
	private Pair<Integer, Integer> processPackage(
		final SearchWords words, 
		PackageInfo packageInfo,
		Pair<Integer, Integer> previousResult
	) {
		Pair<Integer, Integer> result = new Pair<Integer, Integer>(Integer.MAX_VALUE, Integer.MIN_VALUE);
		
		PackageData packageData = this.extractPackageData(packageInfo, packageInfo.previousPackageInfo);
		Vector<Integer> word1Positions = this.foundPositions(words.word1, packageData); 
		
		
		
		if (word1Positions.size() > 0) {
			PreviousPackageInfo previousPackageInfo = new PreviousPackageInfo(
				NOT_FOUND, 
				new Pair<String, Integer>(null, NOT_FOUND)
			);
			PackageInfo currentPackageInfo = null;
			for(int packageIndex = 0; packageIndex < packageCount; packageIndex++) {
				currentPackageInfo = new PackageInfo(
					packageInfo.referenceOnText,
					packageIndex,
					previousPackageInfo
				);
				result = this.setMinAndMax(
					this.processSecondPackageAndFoundDistance(
						words, 
						currentPackageInfo, 
						previousPackageInfo,
						word1Positions,
						previousResult
					), 
					result
				);
				previousPackageInfo = this.generatePreviousPackageInfo(currentPackageInfo);
			}
		}
		
		return result;
	}

	private PreviousPackageInfo generatePreviousPackageInfo(final PackageInfo currentPackageInfo) {
//		final Integer lastWordIndex = this.getLastWordIndex(currentPackageInfo);
//		final Integer lastPartialWordIndex = this.getLastPartialWordIndex(currentPackageInfo);
//		PreviousPackageInfo previousPackageInfo = new PreviousPackageInfo(
//				NOT_FOUND, 
//				new Pair<String, Integer>(null, NOT_FOUND)
//			);
		return currentPackageInfo.previousPackageInfo;
	}
	
	private Integer getLastWordIndex(final PackageInfo currentPackageInfo) {
		Vector<Pair<String, Integer>> foundedWords = this.foundWords(currentPackageInfo);
		return foundedWords.get(foundedWords.size() - 1).getSecond();
	}
	
	private Integer getLastPartialWordIndex(final PackageInfo currentPackageInfo) {
		final String text = this.extractTextForPackage(currentPackageInfo.referenceOnText, currentPackageInfo.packageIndex);
		if (!text.substring(text.length() - 1).equals(" ")) {
			return this.getLastWordIndex(currentPackageInfo);
		}
		return NOT_FOUND;
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
			index++;
		}
		return result;
	}
	
	private PackageData extractPackageData(
		final PackageInfo packageInfo, 
		PreviousPackageInfo previousPackageInfo
	) {
		Vector<Pair<String, Integer>> foundWords = this.foundWords(packageInfo);
		int start = 0;
		if (previousPackageInfo.lastPartialWord.getFirst() != null) {
			final Pair<String, Integer> partialWord = previousPackageInfo.lastPartialWord;
			final Pair<String, Integer> firstWord = foundWords.get(0);
			final Pair<String, Integer> fullWord = new Pair<String, Integer>(
				partialWord.getFirst() + firstWord.getFirst(),
				partialWord.getSecond()
			);
			foundWords.set(0, fullWord);
			start = 1;
		}
		
		final Integer lastPartialWordIndex = this.getLastPartialWordIndex(packageInfo);
		final Boolean isLastPackage = (packageInfo.packageIndex >= (this.packageCount - 1));
		
		for(int i = start; i < foundWords.size(); i++) {
			foundWords.get(i).setSecond(this.getGlobalIndex(i, packageInfo));
		}
		if (!isLastPackage && (lastPartialWordIndex != NOT_FOUND)) {
			previousPackageInfo.lastPartialWord = foundWords.remove(foundWords.size() - 1);
		}
		if (isLastPackage) {//|| (lastPartialWordIndex != NOT_FOUND)
			previousPackageInfo.lastPartialWord = new Pair<String, Integer>(null, NOT_FOUND);
		}
		previousPackageInfo.lastWordIndex = foundWords.get(foundWords.size() - 1).getSecond();
		return new PackageData(
			foundWords,
			packageInfo.packageIndex,
			previousPackageInfo.lastWordIndex,
			previousPackageInfo.lastPartialWord
		);
	}
	
	private Vector<Integer> foundPositions(
		final String word, 
		final PackageData packageData 
	) {
		Vector<Integer> result = new Vector<Integer>();
		for(Pair<String, Integer> pair : packageData.wordList) {
			if (pair.getFirst().equals(word)) {
				result.add(pair.getSecond());
			}
		}
		return result;
	}
	
	private Pair<Integer, Integer> processSecondPackageAndFoundDistance(
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
					final Integer distance = Math.abs(sourcewordPositions.get(sourceIndex) - searchWordPositions.get(index)); 
					result = this.setMinAndMax(distance - 1, result);
				}
			}

		}
		
		return result;
	}
	
	private Integer getGlobalIndex(final Integer index, final PackageInfo packageInfo) {
		if (packageInfo.previousPackageInfo.lastWordIndex >= 0) {
			return index + packageInfo.previousPackageInfo.lastWordIndex + 1;
		}
		return index;
		
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
