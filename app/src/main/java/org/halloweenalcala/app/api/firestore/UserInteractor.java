package org.halloweenalcala.app.api.firestore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.cloud.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.halloweenalcala.app.api.firestore.FirestoreHelper.COLLECTION_USERS;

public class UserInteractor extends BaseInteractor {

    public UserInteractor(Context context, BaseView baseView) {
        super(context, baseView);
    }

    public void addUser(User user, final CallbackPost callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_USERS)
                .document(user.getIdDevice())
                .set(user)
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

    public void getUser(String deviceId, final CallbackGetEntity<User> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_USERS)
                .whereEqualTo(User.FIELD_DEVICE_ID, deviceId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            if (documents.isEmpty()) {
                                callback.onEntityReceived(null);
                            } else {
                                callback.onEntityReceived(documents.get(0).toObject(User.class));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            callback.onError("Error getting documents." + task.getException().getMessage());
                        }
                    }
                });
    }

    public void setUserEmail(String deviceId, String email, final CallbackPost callback) {
        Map<String, Object> data = new HashMap<>();
        data.put(User.FIELD_EMAIL, email);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_USERS).document(deviceId)
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

    public void setUserPendingSlogans(String deviceId, int pendingSlogans) {
        Map<String, Object> data = new HashMap<>();
        data.put(User.FIELD_PENDING_SLOGANS, pendingSlogans);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_USERS).document(deviceId)
                .set(data, SetOptions.merge());
    }
}
