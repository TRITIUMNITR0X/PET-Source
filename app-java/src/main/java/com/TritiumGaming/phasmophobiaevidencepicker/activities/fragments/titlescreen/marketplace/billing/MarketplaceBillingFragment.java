package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace.billing;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.FirestoreFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace.MarketplaceListLayout;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.billable.MarketplaceMtxItem;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions.billables.FirestoreMicrotransactionBillables;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccountCredit;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transaction.FirestorePurchaseHistory;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ImmutableList;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceBillingFragment extends FirestoreFragment {

    private BillingClient billingClient;

    private AppCompatTextView label_account_credits;
    private ConstraintLayout constraint_requestlogin;

    private LinearLayout list_mtx_items;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_marketplace_billing, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AppCompatImageView back_button = view.findViewById(R.id.button_back);
        constraint_requestlogin =
                view.findViewById(R.id.constraint_requestlogin);
        label_account_credits = view.findViewById(R.id.label_credits_actual);
        list_mtx_items = view.findViewById(R.id.list_marketplace_items);

        initAccountView();

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        list_mtx_items.setVisibility(VISIBLE);

        back_button.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        requireActivity().runOnUiThread(() -> new Thread(this::initAccountCreditListener).start());

        initBillingClient();

    }

    private void initAccountView() {
        FirebaseUser firebaseUser = null;
        try {
            firebaseUser = FirestoreUser.getCurrentFirebaseUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        constraint_requestlogin.setVisibility(firebaseUser != null ? GONE : VISIBLE);
    }

    private void initAccountCreditListener() {
        try {
            DocumentReference creditDoc = FirestoreAccountCredit.getCreditsDocument();
            creditDoc.get()
                    .addOnCompleteListener(task -> {
                        Long credits_read = task.getResult().get(FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long.class);
                        long user_credits = credits_read != null ? credits_read : 0;

                        label_account_credits.setText(String.valueOf(user_credits));
                    });

            creditDoc.addSnapshotListener((documentSnapshot, error) -> {
                if(documentSnapshot == null) { return; }

                Long credits_read = documentSnapshot.get(FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long.class);
                long user_credits = credits_read != null ? credits_read : 0;

                label_account_credits.setText(String.valueOf(user_credits));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initBillingClient() {
        createBillingClient();
        connectToGooglePlayBilling();
    }

    private void createBillingClient() {
        billingClient = BillingClient.newBuilder(requireContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        Log.d("Billing", "Pending purchases will be handled.");
    }

    private void connectToGooglePlayBilling() {
        Log.d("Billing", "Attempting to setup Billing...");

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d("Billing", "Billing setup finished successfully!");
                    // The BillingClient is ready. You can query purchases here.
                    try {
                        queryMarketplaceItems();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Billing", "Billing setup unsuccessful.\nCode: " +
                            billingResult.getResponseCode() + "\nDebug: " +
                            billingResult.getDebugMessage());
                }

                billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder()
                                .setProductType(BillingClient.ProductType.INAPP).build(),
                        (result, list) -> handlePendingPurchases(result, list));
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d("Billing", "Billing service disconnected.");

                connectToGooglePlayBilling();
            }
        });

    }

    // Google Billing Library
    private final PurchasesUpdatedListener purchasesUpdatedListener = this::handlePendingPurchases;

    private void handlePendingPurchases(BillingResult billingResult,
                                        List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK &&
                list != null && !list.isEmpty()) {

            Log.d("Billing", "Processing OK purchase");

            for(Purchase purchase: list) {
                if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED &&
                        !purchase.isAcknowledged()) {

                    try {
                        requireActivity().runOnUiThread(() -> Toast.makeText(
                                        requireActivity(),
                                        "Purchase successful!",
                                        Toast.LENGTH_LONG)
                                .show());
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    StringBuilder productString = new StringBuilder();
                    for (String product : purchase.getProducts()) {
                        productString.append(product).append("\n");
                    }

                    Log.d("Billing", "Purchase successful: " + productString);

                    //Consume item process
                    handlePurchase(purchase);
                } else {
                    Log.d("Billing", "Pending error: " + purchase.getPurchaseState());
                }
            }
        } else {
            Log.d("Billing", "Purchase error: Purchases list is empty");
        }
    }

    private void handlePurchase(Purchase purchase) {

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = (billingResult, purchaseToken) -> {

            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                CollectionReference billablesCollection = null;
                try {
                    billablesCollection = FirestoreMicrotransactionBillables.getBillablesCollection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(billablesCollection == null) { return; }

                StringBuilder purchasesString = new StringBuilder();
                for (String p : purchase.getProducts()) {

                    try {
                        billablesCollection
                            .whereEqualTo("product_id", p)
                                .addSnapshotListener(
                                    (querySnapshot, error) -> {
                                        if(querySnapshot == null) { return; }
                                        for(DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()) {
                                            String reward_item = documentSnapshot.get(
                                                            "reward_item", String.class);
                                            Long reward_amount = documentSnapshot.get(
                                                            "reward_amount", Long.class);

                                            if(reward_item == null || reward_amount == null) {
                                                return;
                                            }

                                            switch (reward_item) {
                                                case "credit" -> {
                                                    try {
                                                        FirestoreAccountCredit.addCredits(reward_amount, null);
                                                        FirestorePurchaseHistory.addPurchaseDocument(
                                                                documentSnapshot.getReference(),
                                                                purchase.getOrderId(), new OnFirestoreProcessListener() {
                                                                    @Override
                                                                    public void onSuccess() {
                                                                        Log.d("Billable", "Reward added successfully!");
                                                                    }
                                                                    @Override
                                                                    public void onFailure() {
                                                                        Log.d("Billable", "Reward adding failed!");
                                                                    }
                                                                });
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                case "" -> {
                                                    Log.d("Billable", "Reward type is empty");
                                                }
                                            }
                                        }
                                    }
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    purchasesString.append(p).append(" ");
                }

                Log.d("Billing", "Purchase consumed: " + purchasesString);
            } else {
                Log.d("Billing", "Purchase error: "  +
                        purchase.getProducts().get(0) + " ->\nError: [" +
                        billingResult.getResponseCode() + "]\n" +
                        billingResult.getDebugMessage() + " -> \n");
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }

    public void queryMarketplaceItems() throws Exception {
        Log.d("Billing", "Obtaining list of Marketplace items from database...");

        Task<QuerySnapshot> billablesQuery = null;
        try {
            billablesQuery = FirestoreMicrotransactionBillables.getBillablesWhere(
                    "type", "credits", "tier", Query.Direction.ASCENDING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(billablesQuery == null) {
            Log.d("Billing", "Microtransaction query snapshot DNE.");
            return;
        }

        billablesQuery
            .addOnSuccessListener(snapshot -> {

                List<QueryProductDetailsParams.Product> productsQueryList =
                        new ArrayList<>(snapshot.size());

                for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {

                    if (!documentSnapshot.exists()) {
                        Log.d("Billing", "Microtransaction document snapshot DNE.");
                        continue;
                    }

                    String purchase_id = documentSnapshot.get("product_id", String.class);
                    if (purchase_id == null) {
                        continue;
                    }

                    Long tier = documentSnapshot.get("tier", Long.class);
                    if (tier == null) {
                        continue;
                    }

                    Log.d("Billing", "Building item " + purchase_id);
                    QueryProductDetailsParams.Product product =
                            QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId(purchase_id)
                                    .setProductType(BillingClient.ProductType.INAPP)
                                    .build();

                    productsQueryList.add(tier.intValue(), product);
                }

                QueryProductDetailsParams queryProductDetailsParams =
                        QueryProductDetailsParams.newBuilder()
                                .setProductList(productsQueryList)
                                .build();

                billingClient.queryProductDetailsAsync(
                    queryProductDetailsParams,
                    (billingResult, productDetailsList) -> {
                        // check billingResult
                        // process returned productDetailsList
                        if (billingResult.getResponseCode() ==
                            BillingClient.BillingResponseCode.OK &&
                            !productDetailsList.isEmpty()) {

                            Log.d("Billing", "Finished querying Marketplace with " +
                                    productDetailsList.size() + " results.");
                            try {
                                requireActivity().runOnUiThread(() ->
                                        buildMtxProducts(productDetailsList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            })
            .addOnFailureListener(e -> {
                Log.d("Billing", "Microtransaction query failed!");
                e.printStackTrace();
            }).addOnCompleteListener(task -> {
                Log.d("Billing", "Microtransaction query completed!");
                ProgressBar progressbar = requireView().findViewById(R.id.market_progressbar);
                progressbar.setVisibility(GONE);
            });
    }

    private void buildMtxProducts(List<ProductDetails> productDetailsList) {

        MarketplaceListLayout marketplaceList =
                new MarketplaceListLayout(getContext(), null);
        marketplaceList.setVisibility(VISIBLE);
        /*marketplaceList.setLabel("Companion Credits");
        marketplaceList.showLabel(VISIBLE);*/

        list_mtx_items.addView(marketplaceList);

        for (ProductDetails productDetails : productDetailsList) {
            MarketplaceMtxItem mtxItem =
                    new MarketplaceMtxItem(productDetails);
            Log.d("Billing", "Adding " + mtxItem);

            try {
                MarketplaceMtxView marketplaceMtxView =
                        buildMarketplaceMtxView(mtxItem);

                list_mtx_items.addView(marketplaceMtxView);
            } catch (Exception e) {
                Log.d("Billing", "Failed! ");
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private MarketplaceMtxView buildMarketplaceMtxView(MarketplaceMtxItem mtxItem) {
        MarketplaceMtxView marketplaceMtxView =
            new MarketplaceMtxView(requireContext(), null);

        marketplaceMtxView.setBillableItem(mtxItem);
        marketplaceMtxView.setBuyButtonListener(v -> {

            MarketplaceMtxItem item = marketplaceMtxView.getBillableItem();
            if(item == null) { return; }
            ProductDetails productDetail =
                    item.getProductDetails();

            ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                    ImmutableList.of(
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                    .setProductDetails(productDetail)
                                    .build()
                    );

            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build();

            // Launch the billing flow
            billingClient.launchBillingFlow(requireActivity(), billingFlowParams);
        });
        return marketplaceMtxView;
    }
}


