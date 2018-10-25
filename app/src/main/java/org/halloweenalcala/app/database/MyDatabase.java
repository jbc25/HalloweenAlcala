package org.halloweenalcala.app.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import org.halloweenalcala.app.model.News;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.model.Performance;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.model.cloud.SloganRating;

@Database(entities = {Participant.class, News.class, Performance.class, Place.class, SloganRating.class}, version = 7, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract ParticipantDao participantDao();

    public abstract NewsDao newsDao();

    public abstract PerformanceDao performanceDao();

    public abstract PlaceDao placeDao();

    public abstract SloganRatingDao sloganRatingDao();


//    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//
//            database.execSQL("CREATE TABLE 'product' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , 'name' VARCHAR(50), 'name_normalized' VARCHAR(50), 'idCategory' INTEGER DEFAULT 0, 'months_season' VARCHAR(12) DEFAULT 222222222222, 'marked' INTEGER DEFAULT 0, 'added' INTEGER DEFAULT 0, 'completed' INTEGER DEFAULT 0, 'amount' FLOAT DEFAULT 0, 'g_kg_ud' INTEGER DEFAULT 1, 'order_weight' INTEGER DEFAULT 0, 'remains' INTEGER DEFAULT 0);");
////            database.execSQL("INSERT INTO 'product'('_id', 'name', 'category', 'months_season', 'marked', 'added', 'completed', 'amount', 'g_kg_ud', 'order_weight', 'remains') SELECT * FROM 'frutas';");
//            database.execSQL("CREATE TABLE 'category' ('id' INTEGER, 'name' TEXT, 'color' INTEGER, 'order' INTEGER, PRIMARY KEY('id'));");
//
//        }
//    };

}
