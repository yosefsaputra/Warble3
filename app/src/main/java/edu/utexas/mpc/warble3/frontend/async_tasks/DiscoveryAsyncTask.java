package edu.utexas.mpc.warble3.frontend.async_tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import edu.utexas.mpc.warble3.model.resource.Resource;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.util.Logging;

public class DiscoveryAsyncTask extends AsyncTask<Void, Void, List<Thing>> {
    private static final String TAG = "DiscoveryAsyncTask";
    private DiscoveryAsyncTaskComplete mCallback;
    private Resource resource = Resource.getInstance();

    public DiscoveryAsyncTask(DiscoveryAsyncTaskComplete context) {
        mCallback = context;
    }

    @Override
    protected List<Thing> doInBackground(Void... voids) {
        if (Logging.DEBUG) Log.d(TAG, "Executing DiscoveryAsyncTask ...");

        resource.discoverThings();
        return resource.getThings();
    }

    @Override
    protected void onPostExecute(List<Thing> things) {
        mCallback.onDiscoveryTaskComplete(things);
    }
}