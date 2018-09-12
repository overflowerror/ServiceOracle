package net.persei.oracle.network.objects;

import java.io.Serializable;
import java.util.Arrays;

import net.persei.tools.Base62;

public class HostAuthKey implements Serializable {
	private static final long serialVersionUID = 5365701218557758110L;
	
	private byte key[];
	
	public HostAuthKey(String key) {
		this.key = Base62.decode(key);
	}

	@Override
	public String toString() {
		return Base62.encode(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(key);
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
		HostAuthKey other = (HostAuthKey) obj;
		if (!Arrays.equals(key, other.key))
			return false;
		return true;
	}
	
}
