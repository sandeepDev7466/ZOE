package com.ztp.app.Data.Local.Room.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Dao.CSOAllRequestDao;
import com.ztp.app.Data.Local.Room.Dao.CSOShiftResponseDao;
import com.ztp.app.Data.Local.Room.Dao.CalendarDataDao;
import com.ztp.app.Data.Local.Room.Dao.CountryDao;
import com.ztp.app.Data.Local.Room.Dao.CsoMyFollowerResponseDao;
import com.ztp.app.Data.Local.Room.Dao.DocumentListResponseDao;
import com.ztp.app.Data.Local.Room.Dao.EventDetailResponseDao;
import com.ztp.app.Data.Local.Room.Dao.EventTypeDao;
import com.ztp.app.Data.Local.Room.Dao.GetEventsResponseDao;
import com.ztp.app.Data.Local.Room.Dao.GetProfileResponseDao;
import com.ztp.app.Data.Local.Room.Dao.SchoolDao;
import com.ztp.app.Data.Local.Room.Dao.SearchEventResponseDao;
import com.ztp.app.Data.Local.Room.Dao.ShiftDetailResponseDao;
import com.ztp.app.Data.Local.Room.Dao.StateDao;
import com.ztp.app.Data.Local.Room.Dao.TimezoneDao;
import com.ztp.app.Data.Local.Room.Dao.UpcomingEventsDao;
import com.ztp.app.Data.Local.Room.Dao.UpcomingVolunteerEventsDao;
import com.ztp.app.Data.Local.Room.Dao.VolunteerAllResponseDao;
import com.ztp.app.Data.Local.Room.Dao.VolunteerShiftListDao;
import com.ztp.app.Data.Local.Room.Dao.VolunteerTargetResponseDao;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;
import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerTargetResponse;

@Database(entities =
        {
                CountryResponse.Country.class,
                StateResponse.State.class,
                SchoolResponse.School.class,
                TimeZoneResponse.Timezone.class,
                EventTypeResponse.EventType.class,
                VolunteerDashboardCombineResponse.EventData.class,
                GetProfileResponse.ResData.class,
                GetEventsResponse.Event.class,
                CsoDashboardCombinedResponse.EventData.class,
                CsoDashboardCombinedResponse.CalendarData.class,
                GetEventDetailResponse.EventDetail.class,
                GetShiftDetailResponse.ShiftDetail.class,
                CSOAllResponse.CSOAllRequest.class,
                CsoMyFollowerResponse.SeeFollower.class,
                SearchEventResponse.SearchedEvent.class,
                VolunteerAllResponse.VolunteerResponse.class,
                GetCSOShiftResponse.ResDatum.class,
                GetVolunteerShiftListResponse.ShiftData.class,
                VolunteerTargetResponse.TargetData.class,
                DocumentListResponse.Document.class


        }, version = 1, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {

    private static final String DB_NAME = "ZTPDB.db";
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
                .fallbackToDestructiveMigration()
                .build();
        //.addMigrations(MIGRATION_1_2).build();
    }

   /* static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
           *//* database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");*//*
        }
    };
*/
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

    public abstract StateDao getStateDao();

    public abstract SchoolDao getSchoolDao();

    public abstract TimezoneDao getTimeZoneDao();

    public abstract EventTypeDao getEventTypeDao();

    public abstract UpcomingVolunteerEventsDao getUpcomingVolunteerEventsDao();

    public abstract GetProfileResponseDao getProfileResponseDao();

    public abstract GetEventsResponseDao getEventsResponseDao();

    public abstract UpcomingEventsDao getUpcomingEventsDao();

    public abstract CalendarDataDao getCalendarDataDao();

    public abstract EventDetailResponseDao getEventDetailResponseDao();

    public abstract ShiftDetailResponseDao getShiftDetailResponseDao();

    public abstract CSOAllRequestDao getCSOAllRequestDao();

    public abstract CsoMyFollowerResponseDao getCsoMyFollowerResponse();

    public abstract SearchEventResponseDao getSearchEventResponseDao();

    public abstract VolunteerAllResponseDao getVolunteerAllResponseDao();

    public abstract CSOShiftResponseDao getCSOShiftResponseDao();

    public abstract VolunteerShiftListDao getVolunteerShiftListDao();

    public abstract VolunteerTargetResponseDao getVolunteerTargetResponseDao();

    public abstract DocumentListResponseDao getDocumentListResponseDao();


}
