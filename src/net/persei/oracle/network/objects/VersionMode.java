package net.persei.oracle.network.objects;


public enum VersionMode {
	PRE_ALPHA, ALPHA, BETA, PRERELEASE, RELEASE;
	
	@Override
	public String toString() {
		if (this == PRE_ALPHA)
			return "PRE ALPHA";
		if (this == VersionMode.RELEASE)
			return "";
		return this.name();
	}
}
