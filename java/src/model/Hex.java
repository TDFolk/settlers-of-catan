package model;

import shared.locations.HexLocation;
import model.ResourceCard.Resource;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Hex {
	private Resource resource;
	private HexLocation location;
	private int numberToken;
	
	public Hex(Resource resource, HexLocation location, int numberToken) {
		
	}
	
	
	
	public Resource getResourceType() {
		return resource;
	}
	
	public HexLocation getLocation() {
		return location;
	}
	
	public int getNumberToken() {
		return numberToken;
	}
	
	
}
