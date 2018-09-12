package net.persei.oracle.network.objects;

import java.io.Serializable;

public class Version implements Serializable, Comparable<Version> {
	private static final long serialVersionUID = -287445485984060288L;

	private int major;
	private int minor;
	private int patch;
	private VersionMode mode;
	
	public Version(int major, int minor, int patch, VersionMode mode) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.mode = mode;
	}
	
	public Version(int major, int minor, int patch) {
		this(major, minor, patch, VersionMode.RELEASE);
	}
	
	public Version(int major, int minor) {
		this(major, minor, 0);
	}
	
	public Version(int major) {
		this(major, 0);
	}
	
	public Version(int major, int minor, VersionMode mode) {
		this(major, minor, 0, mode);
	}
	
	public Version(int major, VersionMode mode) {
		this(major, 0, 0, mode);
	}

	@Override
	public int compareTo(Version o) {
		if (this.major > o.major)
			return 1;
		if (this.major < o.major)
			return -1;
		if (this.minor > o.minor)
			return 1;
		if (this.minor < o.minor)
			return -1;
		if (this.patch > o.patch)
			return 1;
		if (this.patch < o.patch)
			return -1;
		return this.mode.compareTo(o.mode);
	}
	
	@Override
	public String toString() {
		return "V " + major + "." + minor + "." + patch + "." + mode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + patch;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Version other = (Version) obj;
		if (major != other.major)
			return false;
		if (minor != other.minor)
			return false;
		if (mode != other.mode)
			return false;
		if (patch != other.patch)
			return false;
		return true;
	}
	
	
}
