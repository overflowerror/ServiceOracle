package net.persei.oracle.network.objects;

import java.io.Serializable;
import java.time.Instant;

public class HeartBeat implements Serializable {
	private static final long serialVersionUID = -9072594881334560762L;

	private Instant time;
	
	public HeartBeat() {
		time = Instant.now();
	}
	
	public Instant getTime() {
		return time;
	}
}
