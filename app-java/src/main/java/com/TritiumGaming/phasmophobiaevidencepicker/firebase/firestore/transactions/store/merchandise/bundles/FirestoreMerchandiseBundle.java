package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.bundles;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.FirestoreMerchandise;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreMerchandiseBundle {

    private static final String COLLECTION_BUNDLES = "Bundles";

    public static CollectionReference getBundlesCollection() throws Exception {
        return FirestoreMerchandise.getMerchandiseDocument().collection(COLLECTION_BUNDLES);
    }

    public static Task<QuerySnapshot> getBundleWhere(
            String filterField, String value, String orderField, Query.Direction order)
            throws Exception {

        Query query = FirestoreMerchandiseBundle.getBundlesCollection();
        if(filterField != null && value != null) {
            query = FirestoreMerchandiseBundle.getBundlesCollection()
                    .whereEqualTo(filterField, value);
        }

        if(orderField != null && order != null) {
            return query
                    .orderBy(orderField, order)
                    .get();
        }

        return query.get();
    }

}
