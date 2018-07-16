package edu.utexas.mpc.warble3.model.thingstructure;

import java.util.List;

import edu.utexas.mpc.warble3.model.connect.Connection;
import edu.utexas.mpc.warble3.model.discovery.Discovery;

public abstract class Thing {
    private static final String TAG = "Thing";

    private String name;
    private String friendlyName;

    private String uuid;

    private String accessName;
    private String accessUsername;
    private String accessPasscode;

    private String manufacturerSerialNumber;
    private String manufacturerModelName;
    private String manufacturerModelNumber;
    private String manufacturerName;

    private List<THING_MAIN_TYPE> mainTypes;
    private List<THING_FUNCTION_TYPE> functionTypes;
    private List<THING_CONCRETE_TYPE> concreteTypes;

    private List<Connection> connections;
    private List<Discovery> discoveries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getAccessUsername() {
        return accessUsername;
    }

    public void setAccessUsername(String accessUsername) {
        this.accessUsername = accessUsername;
    }

    public String getAccessPasscode() {
        return accessPasscode;
    }

    public void setAccessPasscode(String accessPasscode) {
        this.accessPasscode = accessPasscode;
    }

    public String getManufacturerSerialNumber() {
        return manufacturerSerialNumber;
    }

    public void setManufacturerSerialNumber(String manufacturerSerialNumber) {
        this.manufacturerSerialNumber = manufacturerSerialNumber;
    }

    public String getManufacturerModelName() {
        return manufacturerModelName;
    }

    public void setManufacturerModelName(String manufacturerModelName) {
        this.manufacturerModelName = manufacturerModelName;
    }

    public String getManufacturerModelNumber() {
        return manufacturerModelNumber;
    }

    public void setManufacturerModelNumber(String manufacturerModelNumber) {
        this.manufacturerModelNumber = manufacturerModelNumber;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public List<THING_MAIN_TYPE> getMainTypes() {
        return mainTypes;
    }

    public void setMainTypes(List<THING_MAIN_TYPE> mainTypes) {
        this.mainTypes = mainTypes;
    }

    public List<THING_FUNCTION_TYPE> getFunctionTypes() {
        return functionTypes;
    }

    public void setFunctionTypes(List<THING_FUNCTION_TYPE> functionTypes) {
        this.functionTypes = functionTypes;
    }

    public List<THING_CONCRETE_TYPE> getConcreteTypes() {
        return concreteTypes;
    }

    public void setConcreteTypes(List<THING_CONCRETE_TYPE> concreteTypes) {
        this.concreteTypes = concreteTypes;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Discovery> getDiscoveries() {
        return discoveries;
    }

    public void setDiscoveries(List<Discovery> discoveries) {
        this.discoveries = discoveries;
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s (friendlyName) - ", getFriendlyName());
        string += String.format("UUID: \"%s\"", getUuid());
        return string;
    }
}