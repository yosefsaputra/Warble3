package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.utexas.mpc.warble3.database.converter.ConnectionConverter;
import edu.utexas.mpc.warble3.database.converter.ThingAccessCredentialConverter;
import edu.utexas.mpc.warble3.database.converter.ThingConverter;
import edu.utexas.mpc.warble3.database.converter.UserConverter;
import edu.utexas.mpc.warble3.model.thing.component.THING_CONNECTION_STATE;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.user.User;
import edu.utexas.mpc.warble3.setup.AppDatabaseInterface;

@Database(entities = {UserDb.class, ThingDb.class, ConnectionDb.class, ThingAccessCredentialDb.class},
        version = 8,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase implements AppDatabaseInterface {
    private static final String TAG = "AppDatabase";

    private static AppDatabase INSTANCE;

    public abstract UserDbDao userDbDao();
    public abstract ThingDbDao thingDbDao();
    public abstract ConnectionDbDao connectionDbDao();
    public abstract ThingAccessCredentialDbDao thingAccessCredentialDbDao();

    public static void initializeDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public static AppDatabase getDatabase() throws NullPointerException {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        else {
            throw new NullPointerException("AppDatabase has not been initialized");
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    // User
    @Override
    public void addUser(User newUser) {
        getDatabase().userDbDao().insert(UserConverter.toUserDb(newUser));
    }

    private List<User> getUsers() {
        List<UserDb> userDbs = getDatabase().userDbDao().getAllUserDbs();

        if (userDbs.size() == 0) {
            return null;
        }
        else {
            return UserConverter.toUsers(userDbs);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        UserDb userDb = getDatabase().userDbDao().getUserDb(username);
        if (userDb == null) {
            return null;
        }
        else {
            return UserConverter.toUser(userDb);
        }
    }

    private User getUserFromDb(User user) {
        if (user == null)
            return null;
        else {
            User returnUser = null;

            String username = user.getUsername();

            if ((returnUser == null) && (username != null) && (!username.equals("")))
                returnUser = getUserByUsername(username);

            return returnUser;
        }
    }

    @Override
    public void deleteAllUsers() {
        getDatabase().userDbDao().deleteAllUserDbs();
    }


    // Thing
    @Override
    public List<Thing> getThings() {
        List<ThingDb> thingDbs = getDatabase().thingDbDao().getAllThingDbs();

        if (thingDbs.size() == 0) {
            return null;
        }
        else {
            return ThingConverter.toThings(thingDbs);
        }
    }

    public Thing getThingByDbid(long dbid) {
        return ThingConverter.toThing(getDatabase().thingDbDao().getThingDb(dbid));
    }

    public Thing getThingByUuid(String uuid) {
        return ThingConverter.toThing(getDatabase().thingDbDao().getThingDbByUuid(uuid));
    }

    private Thing getThingFromDb(Thing thing) {
        if (thing == null)
            return null;
        else {
            Thing returnThing = null;

            long thingDbid = thing.getDbid();
            if ((returnThing == null) && (thingDbid != 0))
                returnThing = getThingByDbid(thingDbid);

            String thingUuid = thing.getUuid();
            if ((returnThing == null) && (thingUuid != null) && (!thingUuid.equals(""))) {
                returnThing = getThingByUuid(thingUuid);
            }

            return returnThing;
        }
    }

    private void addThing(Thing thing) {
        getDatabase().thingDbDao().insert(ThingConverter.toThingDb(thing));
    }

    @Override
    public long saveThing(Thing thing) {
        long thingDbid = 0;

        if (thing != null) {
            Thing storedThing = getThingFromDb(thing);

            if (storedThing == null)
                thingDbid = getDatabase().thingDbDao().insert(ThingConverter.toThingDb(thing));
            else {
                ThingDb updatedThingDb = ThingConverter.toThingDb(thing);
                thingDbid = storedThing.getDbid();
                updatedThingDb.setDbid(thingDbid);
                getDatabase().thingDbDao().update(updatedThingDb);
            }
        }

        return thingDbid;
    }

    @Override
    public List<Long> saveThings(List<Thing> things) {
        List<Long> thingDbids = new ArrayList<>();
        if (things != null) {
            for (Thing thing : things) {
                thingDbids.add(saveThing(thing));
            }
        }

        if (thingDbids.size() == 0) {
            return null;
        }
        else {
            return thingDbids;
        }
    }

    @Override
    public void deleteAllThings() {
        getDatabase().thingDbDao().deleteAllThingDbs();
    }

    // Connection
    @Override
    public List<Connection> getConnections() {
        return null;
    }

    public Connection getConnectionByDbid(long dbid) {
        return ConnectionConverter.toConnection(getDatabase().connectionDbDao().getConnectionDbByDbid(dbid));
    }

    public List<Connection> getConnectionBySourceId(long thingDbid) {
        return ConnectionConverter.toConnections(getDatabase().connectionDbDao().getConnectionDbBySourceId(thingDbid));
    }

    private List<Connection> getConnectionByDestinationId(long thingDbid) {
        return ConnectionConverter.toConnections(getDatabase().connectionDbDao().getConnectionDbByDestinationId(thingDbid));
    }

    private Connection getConnectionFromDb(Connection connection) {
        if (connection == null)
            return null;
        else {
            Connection returnConnection = null;

            long connectionDbid = connection.getDbid();
            if ((returnConnection == null) && (connectionDbid != 0))
                returnConnection = getConnectionByDbid(connectionDbid);

            return returnConnection;
        }
    }

    @Override
    public long saveConnection(Connection connection) {
        long connectionDbid = 0;

        if (connection != null) {
            Connection storedConnection = getConnectionFromDb(connection);

            if (storedConnection == null)
                connectionDbid = getDatabase().connectionDbDao().insert(ConnectionConverter.toConnectionDb(connection));
            else {
                ConnectionDb updatedConnectionDb = ConnectionConverter.toConnectionDb(connection);
                connectionDbid = storedConnection.getDbid();
                updatedConnectionDb.setDbid(connectionDbid);
                getDatabase().connectionDbDao().update(updatedConnectionDb);
            }
        }

        return connectionDbid;
    }

    @Override
    public List<Long> saveConnections(List<Connection> connections) {
        if (connections != null) {
            List<Long> connectionDbids = new ArrayList<>();
            for (Connection connection : connections) {
                connectionDbids.add(saveConnection(connection));
            }

            if (connectionDbids.size() == 0) {
                return null;
            }
            else {
                return connectionDbids;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public void deleteAllConnections() {
        getDatabase().connectionDbDao().deleteAllConnectionDbs();
    }

    // ThingAccessCredential
    private void addThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        AppDatabase.getDatabase().thingAccessCredentialDbDao()
                .insert(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
    }

    @Override
    public List<ThingAccessCredential> getThingAccessCredentials() {
        return ThingAccessCredentialConverter.toThingAccessCredentials(AppDatabase.getDatabase().thingAccessCredentialDbDao().getAllThingAccessCredentialDbs());
    }

    public ThingAccessCredential getThingAccessCredentialByDbid(long dbid) {
        return ThingAccessCredentialConverter.toThingAccessCredential(AppDatabase.getDatabase().thingAccessCredentialDbDao().getThingAccessCredentialDbByDbid(dbid));
    }

    public List<ThingAccessCredential> getThingAccessCredentialsByThingId(long thingId) {
        return ThingAccessCredentialConverter.toThingAccessCredentials(AppDatabase.getDatabase().thingAccessCredentialDbDao().getThingAccessCredentialDbsByThingId(thingId));
    }

    private ThingAccessCredential getThingAccessCredentialFromDb(ThingAccessCredential thingAccessCredential) {
        if (thingAccessCredential == null)
            return null;
        else {
            ThingAccessCredential returnThingAccessCredential = null;

            long thingAccessCredentialDbid = thingAccessCredential.getDbid();
            if ((returnThingAccessCredential == null) && (thingAccessCredentialDbid != 0)) {
                returnThingAccessCredential = getThingAccessCredentialByDbid(thingAccessCredentialDbid);
            }

            return returnThingAccessCredential;
        }
    }

    @Override
    public void deleteAllThingAccessCredentials() {
        AppDatabase.getDatabase().thingAccessCredentialDbDao().deleteAllThingAccessCredentialDbs();
    }

    @Override
    public long saveThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        long thingAccessCredentialDbid = 0;

        if (thingAccessCredential != null) {
            ThingAccessCredential storedThingAccessCredential = getThingAccessCredentialFromDb(thingAccessCredential);

            if (storedThingAccessCredential == null)
                thingAccessCredentialDbid = getDatabase().thingAccessCredentialDbDao().insert(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
            else {
                ThingAccessCredentialDb updatedThingAccessCredentialDb = ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential);
                thingAccessCredentialDbid = storedThingAccessCredential.getDbid();
                updatedThingAccessCredentialDb.setDbid(thingAccessCredentialDbid);
                getDatabase().thingAccessCredentialDbDao().update(updatedThingAccessCredentialDb);
            }
        }

        return thingAccessCredentialDbid;
    }

    @Override
    public List<Long> saveThingAccessCredentials(List<ThingAccessCredential> thingAccessCredentials) {
        if (thingAccessCredentials != null) {
            List<Long> thingAccessCredentialDbids = new ArrayList<>();
            for (ThingAccessCredential thingAccessCredential : thingAccessCredentials) {
                thingAccessCredentialDbids.add(saveThingAccessCredential(thingAccessCredential));
            }

            if (thingAccessCredentialDbids.size() == 0) {
                return null;
            }
            else {
                return thingAccessCredentialDbids;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public void onInitialize() {

    }

    @Override
    public void onTerminate() {
        getDatabase().thingDbDao().updateAllConnectionStates(THING_CONNECTION_STATE.INITIAL);
    }

    // Logging
    @Override
    public String toString() {
        return "===== Database =====\n" +
                toStringUserDbs()
                + "\n" +
                toStringThingDbs()
                + "\n" +
                toStringConnectionDbs()
                + "\n" +
                toStringThingAccessCredentialDbs()
                + "\n====================";
    }

    private String toStringUserDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<UserDb> userDbs = getDatabase().userDbDao().getAllUserDbs();
        if (userDbs == null) {
            stringBuilder.append("Number of userDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of userDb : %d", userDbs.size()));
            for (UserDb userDb : userDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(userDb.toString());
            }
        }

        return stringBuilder.toString();
    }

    private String toStringThingDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ThingDb> thingDbs = getDatabase().thingDbDao().getAllThingDbs();
        if (thingDbs == null) {
            stringBuilder.append("Number of thingDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of thingDb : %d", thingDbs.size()));
            for (ThingDb thingDb : thingDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(thingDb.toString());
            }
        }

        return stringBuilder.toString();
    }

    private String toStringConnectionDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ConnectionDb> connectionDbs = getDatabase().connectionDbDao().getAllConnectionDbs();
        if (connectionDbs == null) {
            stringBuilder.append("Number of connectionDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of connectionDb : %d", connectionDbs.size()));
            for (ConnectionDb connectionDb : connectionDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(connectionDb.toString());
            }
        }

        return stringBuilder.toString();
    }

    private String toStringThingAccessCredentialDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ThingAccessCredentialDb> thingAccessCredentialDbs = getDatabase().thingAccessCredentialDbDao().getAllThingAccessCredentialDbs();
        if (thingAccessCredentialDbs == null) {
            stringBuilder.append("Number of thingAccessCredentialDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of thingAccessCredentialDbs : %d", thingAccessCredentialDbs.size()));
            for (ThingAccessCredentialDb thingAccessCredentialDb : thingAccessCredentialDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(thingAccessCredentialDb.toString());
            }
        }

        return stringBuilder.toString();
    }
}
