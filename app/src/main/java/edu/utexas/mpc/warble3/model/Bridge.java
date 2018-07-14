package edu.utexas.mpc.warble3.model;

import java.util.Arrays;

import edu.utexas.mpc.warble3.model.feature.Accessor;

public abstract class Bridge extends Thing implements Accessor {
    public static final String TAG = "Bridge";

    public Bridge() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACCESSOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.ACCESSOR));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.BRIDGE));
    }
}
