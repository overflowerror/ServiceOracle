package net.persei.oracle.network.objects;

import java.io.Serializable;
import java.util.List;

public class Service implements Serializable {
	private static final long serialVersionUID = -5125680499936971528L;
	
	private String name;
	private String description;
	private boolean enabled;
	private List<Port> ports;
	private Contact contact;
}
