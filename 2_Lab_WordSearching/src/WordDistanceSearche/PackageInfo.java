package WordDistanceSearche;

public class PackageInfo {
	public final String referenceOnText;
	public Integer packageIndex;
	public PreviousPackageInfo previousPackageInfo;
	
	public PackageInfo(
			final String referenceOnText,
			final Integer packageIndex,
			final PreviousPackageInfo previousPackageInfo
	) {
		this.referenceOnText = referenceOnText;
		this.packageIndex = packageIndex;
		this.previousPackageInfo = previousPackageInfo;
	}
}
