package model.map;

import shared.definitions.ResourceType;
import shared.locations.*;

/**
 * This class represents the game object, ports
 * Created by jihoon on 9/17/16.
 */
public class Port {

    private HexLocation location;
    //resourceType of null implies a 3:1 generic port
    private ResourceType resourceType;
    private EdgeLocation edgeLocation;
    private EdgeDirection direction;
    private int ratio;

    public Port(ResourceType resourceType, HexLocation location, EdgeDirection direction, int ratio){
        this.resourceType = resourceType;
        this.location = location;
        this.direction = direction;
        this.ratio = ratio;
        edgeLocation = new EdgeLocation(location, direction);
    }

    /**
     * Constructor for port
     * @param location location of the selected port
     */
    public Port(HexLocation location) {
        this.location = location;
    }

    /**
     * Returns the edge location where the port is located
     * @return location
     */
    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public EdgeDirection getDirection() {
        return direction;
    }

    public void setDirection(EdgeDirection direction) {
        this.direction = direction;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public EdgeLocation getEdgeLocation() {
        return edgeLocation;
    }
}