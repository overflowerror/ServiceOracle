package net.persei.oracle.network.objects;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.Instant;
import java.util.List;

public class Host implements Serializable {
	private static final long serialVersionUID = 5533783857823198187L;

	private HostAuthKey key;
	private Instant started;
	private String hostname;
	private List<InetAddress> addresses;
	private List<Service> services;
}
