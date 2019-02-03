package ch.cansulting.contactslist;

import android.app.Application;
import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // Creating database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "contacts-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}