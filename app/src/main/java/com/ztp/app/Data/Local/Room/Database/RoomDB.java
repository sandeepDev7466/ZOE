package com.ztp.app.Data.Local.Room.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Dao.CountryDao;
import com.ztp.app.Data.Local.Room.Dao.EventTypeDao;
import com.ztp.app.Data.Local.Room.Dao.StateDao;
import com.ztp.app.Data.Local.Room.Dao.TimezoneDao;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;

@Database(entities = {CountryResponse.Country.class, StateResponse.State.class, TimeZoneResponse.Timezone.class, EventTypeResponse.EventType.class}, version = 1, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {

    private static final String DB_NAME = "ZTPDatabase.db";
    private static volatile RoomDB instance;

    public static RoomDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static RoomDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                RoomDB.class,
                DB_NAME)
                .addMigrations(MIGRATION_1_2).build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
           /* database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");*/
        }
    };

    /*static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Book "
                    + " ADD COLUMN pub_year INTEGER");
        }
    };

    static final Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Book "
                    + " ADD COLUMN pub_year INTEGER");
        }
    };*/

    public abstract CountryDao getCountryDao();

    public abstract TimezoneDao getTimeZoneDao();

    public abstract StateDao getStateDao();

    public abstract EventTypeDao getEventTypeDao();



}
