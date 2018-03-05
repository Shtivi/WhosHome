package bl.identifiers;

public abstract class IdentificationData {
	@Override
	public boolean equals(Object obj) {
		// Check type matching
		if (!obj.getClass().equals(this.getClass())) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
