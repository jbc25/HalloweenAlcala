package org.halloweenalcala.app.api.firestore;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.halloweenalcala.app.model.cloud.Slogan;
import org.halloweenalcala.app.model.cloud.SloganRating;

import java.util.ArrayList;
import java.util.List;

public class FirestoreHelper {

    private static final String TAG = "FirestoreHelper";

    public static final String COLLECTION_USERS = "users";
    public static final String COLLECTION_SLOGANS = "slogans";
    public static final String COLLECTION_SLOGAN_RATINGS = "slogan_ratings";



    public interface CallbackGetList<T> {
        void onListReceived(List<T> list);

        void onError(String error);
    }

    public interface CallbackGetEntity<T> {
        void onEntityReceived(T entity);

        void onError(String error);
    }


    public static void getSlogans(final CallbackGetList<Slogan> callback) {

        final List<Slogan> slogans = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION_SLOGANS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Slogan slogan = document.toObject(Slogan.class);
                                slogans.add(slogan);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            callback.onListReceived(slogans);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            callback.onError("Error getting documents." + task.getException().getMessage());
                        }

                    }
                });

    }


    public static void addSlogan(Slogan slogan) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGANS)
                .add(slogan);

    }

    public static void addSloganRating(SloganRating sloganRating, OnCompleteListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGANS)
//                .add(slogan);
                .document(sloganRating.getIdSlogan())
                .collection(COLLECTION_SLOGAN_RATINGS) // todo change this
                .add(sloganRating)
                .addOnCompleteListener(listener);
    }


    public static void getValuationOfDevice(String featureKeyId, String deviceId, final CallbackGetEntity<SloganRating> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGANS)
                .document(featureKeyId)
                .collection(COLLECTION_SLOGAN_RATINGS)
//                .whereEqualTo(SloganRating.FIELD_DEVICE_ID, deviceId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            if (documents.isEmpty()) {
                                callback.onEntityReceived(null);
                            } else {
                                callback.onEntityReceived(documents.get(0).toObject(SloganRating.class));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            callback.onError("Error getting documents." + task.getException().getMessage());
                        }
                    }
                });
    }
}
