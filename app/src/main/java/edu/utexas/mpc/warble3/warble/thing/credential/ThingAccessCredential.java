/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package edu.utexas.mpc.warble3.warble.thing.credential;

import java.io.Serializable;

import edu.utexas.mpc.warble3.database.interfaces.Storeable;
import edu.utexas.mpc.warble3.database.interfaces.TextStoreable;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.user.User;

public abstract class ThingAccessCredential implements Serializable, Storeable, TextStoreable {
    public static final String TAG = "ThingAccessCredential";

    private User user;
    private Thing thing;

    private long dbid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    @Override
    public void onPostStore(long dbid) {
        setDbid(dbid);
    }

    @Override
    public void onPostLoad(long dbid) {
        setDbid(dbid);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof ThingAccessCredential)) {
            return false;
        }

        ThingAccessCredential t = (ThingAccessCredential) object;

        return (this.user.equals(t.user)) &&
                (this.thing.equals(t.thing));
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s", TAG);
        return string;
    }
}
