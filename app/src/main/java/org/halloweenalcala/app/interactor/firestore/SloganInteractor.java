package org.halloweenalcala.app.interactor.firestore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.retrofit.FirebasePushNotificationApi;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.cloud.Slogan;
import org.halloweenalcala.app.model.cloud.SloganRating;
import org.halloweenalcala.app.model.cloud.User;
import org.halloweenalcala.app.model.notifications.NotificationPush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.halloweenalcala.app.api.firestore.FirestoreHelper.COLLECTION_SLOGANS;
import static org.halloweenalcala.app.api.firestore.FirestoreHelper.COLLECTION_SLOGAN_RATINGS;

public class SloganInteractor extends BaseInteractor {


    public SloganInteractor(Context context, BaseView baseView) {
        super(context, baseView);
    }

    public void getSlogans(final CallbackGetList<Slogan> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection(COLLECTION_SLOGANS)
                .orderBy(Slogan.FIELD_TIMESTAMP, Query.Direction.DESCENDING)
                .whereEqualTo(Slogan.FIELD_DENOUNCED, false);

        launchQuerySlogans(query, callback);

    }

    private void launchQuerySlogans(Query query, final CallbackGetList<Slogan> callback) {
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            final List<Slogan> slogans = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Slogan slogan = document.toObject(Slogan.class);
                                slogan.setId(document.getId());
                                slogans.add(slogan);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            callback.onListReceived(slogans);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            callback.onError("Error getting documents." + task.getException().getMessage());
                        }

                        baseView.hideProgressDialog();

                    }
                });
    }

    public void getRankingSlogans(final CallbackGetList<Slogan> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection(COLLECTION_SLOGANS)
                .orderBy(Slogan.FIELD_RATING, Query.Direction.DESCENDING)
                .whereEqualTo(Slogan.FIELD_DENOUNCED, false);

        launchQuerySlogans(query, callback);
    }


    public void getSlogansDenounced(final CallbackGetList<Slogan> callback) {

        final List<Slogan> slogans = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection(COLLECTION_SLOGANS)
                .whereEqualTo(Slogan.FIELD_DENOUNCED, true)
                .whereEqualTo(Slogan.FIELD_DELETED, false)
                ;

        launchQuerySlogans(query, callback);

    }


    public void checkSloganDontExistsAndSend(final Slogan slogan, final CallbackPost callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION_SLOGANS)
                .whereEqualTo(Slogan.FIELD_TEXT_NORMALIZED, slogan.getText_normalized())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                addSlogan(slogan, callback);
                            } else {
                                callback.onError(context.getString(R.string.slogan_already_exists));
                            }
                        } else {
                            callback.onError(task.getException().getMessage());
                        }
                    }
                });

    }

    private void addSlogan(Slogan slogan, final CallbackPost callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGANS)
                .add(slogan)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        baseView.hideProgressDialog();
                        if (task.isSuccessful()) {
                            callback.onSuccess(null);
                        } else {
                            callback.onError(task.getException().getMessage());
                        }
                    }
                });



    }


    public void addSloganRating(final SloganRating sloganRating, final CallbackPost callback) {

        // Example from https://firebase.google.com/docs/firestore/solutions/aggregation?hl=es-419

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference sloganRef = db.collection(COLLECTION_SLOGANS).document(sloganRating.getIdSlogan());

        // Create reference for new rating, for use inside the transaction
        final DocumentReference ratingRef = db.collection(COLLECTION_SLOGAN_RATINGS).document();

//        db.collection(COLLECTION_SLOGANS)
////                .add(slogan);
//                .document(sloganRating.getIdSlogan())
//                .collection(COLLECTION_SLOGAN_RATINGS) // todo change this
//                .add(sloganRating)
//                .addOnCompleteListener(listener);


        // In a transaction, add the new rating and update the aggregate totals
        Task<Void> task = db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                Slogan slogan = transaction.get(sloganRef).toObject(Slogan.class);

                // Compute new number of ratings
                int newNumRatings = slogan.getNumRatings() + 1;

                // Compute new average rating
                float oldRatingTotal = slogan.getAvgRating() * slogan.getNumRatings();
                float newAvgRating = (oldRatingTotal + sloganRating.getRating()) / (float) newNumRatings;

                // Set new restaurant info
                slogan.setNumRatings(newNumRatings);
                slogan.setAvgRating(newAvgRating);

                // Update restaurant
                transaction.set(sloganRef, slogan);

                transaction.set(ratingRef, sloganRating);

                return null;
            }
        });

        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    if (callback != null) {
                        callback.onSuccess(null);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(task.getException().getMessage());
                    }

                    Crashlytics.logException(task.getException());
                }
            }
        });
    }

    public void getSlogansOfDevice(String deviceId, final CallbackGetList<Slogan> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGANS)
                .whereEqualTo(User.FIELD_DEVICE_ID, deviceId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            List<Slogan> slogans = new ArrayList<>();
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            for (DocumentSnapshot documentSnapshot : documents) {
                                Slogan slogan = documentSnapshot.toObject(Slogan.class);
                                slogan.setId(documentSnapshot.getId());
                                slogans.add(slogan);
                            }
                            callback.onListReceived(slogans);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            callback.onError("Error getting documents." + task.getException().getMessage());
                        }
                    }
                });
    }

    public void getValuationOfDevice(String featureKeyId, String deviceId, final CallbackGetEntity<SloganRating> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGAN_RATINGS)
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


    public void setSloganDenounced(String sloganId, boolean denounced, final CallbackPost callback) {
        Map<String, Object> data = new HashMap<>();
        data.put(Slogan.FIELD_DENOUNCED, denounced);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGANS).document(sloganId)
                .set(data, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        baseView.hideProgressDialog();
                        if (task.isSuccessful()) {
                            callback.onSuccess(null);
                        } else {
                            callback.onError(task.getException().getMessage());
                        }
                    }
                });
    }


    public void setSloganDeleted(String sloganId, boolean deleted, final CallbackPost callback) {
        Map<String, Object> data = new HashMap<>();
        data.put(Slogan.FIELD_DELETED, deleted);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_SLOGANS).document(sloganId)
                .set(data, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        baseView.hideProgressDialog();
                        if (task.isSuccessful()) {
                            callback.onSuccess(null);
                        } else {
                            callback.onError(task.getException().getMessage());
                        }
                    }
                });
    }


    public void sendNotification(NotificationPush notificationPush, final CallbackPost callback) {

        String authKeyHeader = "key=" + context.getString(R.string.firebase_sender_id);

        getApi(FirebasePushNotificationApi.class).sendNotification(authKeyHeader, notificationPush)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(actionTerminate)
                .subscribe(new Observer<Response<Void>>() {

                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {

                                   callback.onError(e.getMessage());
                               }

                               @Override
                               public void onNext(Response<Void> apiResponse) {

                                   // Only one row in configuration
                                   callback.onSuccess(null);
                               }
                           }


                );

    }
}
