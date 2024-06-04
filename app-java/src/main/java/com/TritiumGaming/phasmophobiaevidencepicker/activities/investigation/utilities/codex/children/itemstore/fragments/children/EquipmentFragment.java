package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.children;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.LayerDrawable;
import android.text.Html;
import android.view.View;
import android.view.ViewStub;
import android.widget.GridLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentGroupModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentItemModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView;

public class EquipmentFragment extends ItemStoreFragment {

    @SuppressLint({"ResourceType"})
    protected void buildStoreData() {
        if(getContext() == null) { return; }

        TypedArray typed_shop_list = getResources().obtainTypedArray(R.array.shop_equipment_array);
        //scrollViewPaginator.setRowCount(typed_shop_list.length());

        for (int i = 0; i < typed_shop_list.length(); i++) {
            @StringRes int equipmentName;
            @IntegerRes int buyCostData;
            @DrawableRes int equipmentIcon;
            //@DrawableRes ArrayList<Integer> tierImages = new ArrayList<>();

            ItemStoreEquipmentGroupModel groupData = new ItemStoreEquipmentGroupModel();

            TypedArray typed_shop =
                    getContext().getResources().obtainTypedArray(typed_shop_list.getResourceId(i, 0));

            equipmentName = typed_shop.getResourceId(0, 0);
            equipmentIcon = typed_shop.getResourceId(1, 0);
            buyCostData = typed_shop.getResourceId(6, 0);

            groupData.nameData = equipmentName;
            groupData.setPaginationIcon(equipmentIcon);
            groupData.buyCostData = buyCostData;

            TypedArray typed_equipment_image =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(2, 0));
            for (int j = 0; j < typed_equipment_image.length(); j++) {
                ItemStoreEquipmentItemModel itemData = new ItemStoreEquipmentItemModel();
                groupData.addItem(itemData);
                @DrawableRes int value = typed_equipment_image.getResourceId(j, 0);
                groupData.getItemDataAt(j).imageData = value;

                //tierImages.add(value);
                groupData.getItemDataAt(j).imageData = value;
            }
            typed_equipment_image.recycle();

            TypedArray typed_equipment_flavortext =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(3, 0));
            for (int j = 0; j < typed_equipment_flavortext.length(); j++) {
                @StringRes int value = typed_equipment_flavortext.getResourceId(j, 0);
                groupData.getItemDataAt(j).flavorData = value;
            }
            typed_equipment_flavortext.recycle();

            TypedArray typed_equipment_infotext =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(4, 0));
            for (int j = 0; j < typed_equipment_infotext.length(); j++) {
                @StringRes int value = typed_equipment_infotext.getResourceId(j, 0);
                groupData.getItemDataAt(j).infoData = value;
            }
            typed_equipment_infotext.recycle();

            TypedArray typed_equipment_attributes =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(5, 0));
            for (int j = 0; j < typed_equipment_attributes.length(); j++) {

                TypedArray typed_equipment_attributes_positive =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes.getResourceId(j, 0));
                TypedArray typed_equipment_attributes_positive_list =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes_positive.getResourceId(0, 0));
                for (int l = 0; l < typed_equipment_attributes_positive_list.length(); l++) {
                    @StringRes int value = typed_equipment_attributes_positive_list.getResourceId(l, 0);
                    ((ItemStoreEquipmentItemModel)groupData.getItemDataAt(j)).addPositiveAttribute(value);
                }
                typed_equipment_attributes_positive_list.recycle();
                typed_equipment_attributes_positive.recycle();

                TypedArray typed_equipment_attributes_negative =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes.getResourceId(j, 0));
                TypedArray typed_equipment_attributes_negative_list =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes_negative.getResourceId(1, 0));
                for (int l = 0; l < typed_equipment_attributes_negative_list.length(); l++) {
                    @StringRes int value = typed_equipment_attributes_negative_list.getResourceId(l, 0);
                    ((ItemStoreEquipmentItemModel)groupData.getItemDataAt(j)).addNegativeAttribute(value);
                }
                typed_equipment_attributes_negative_list.recycle();
                typed_equipment_attributes_negative.recycle();
            }
            typed_equipment_attributes.recycle();

            TypedArray typed_equipment_upgradelevel =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(7, 0));
            for (int j = 0; j < typed_equipment_upgradelevel.length(); j++) {
                @IntegerRes int value = typed_equipment_upgradelevel.getResourceId(j, 0);
                ((ItemStoreEquipmentItemModel)groupData.getItemDataAt(j)).setUpgradeLevel(value);
            }
            typed_equipment_upgradelevel.recycle();

            TypedArray typed_equipment_upgradecost =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(8, 0));
            for (int j = 0; j < typed_equipment_upgradecost.length(); j++) {
                @IntegerRes int value = typed_equipment_upgradecost.getResourceId(j, 0);
                ((ItemStoreEquipmentItemModel)groupData.getItemDataAt(j)).upgradeCostData = value;
            }
            typed_equipment_upgradecost.recycle();

            typed_shop.recycle();

            storeData.addGroup(groupData);
        }
        typed_shop_list.recycle();

    }

    protected void setPageTitle(@NonNull AppCompatTextView titleView) {
        titleView.setText(R.string.store_title_equipment);
    }

    protected void setDataViewLayout(@NonNull View view) {
        ViewStub dv = view.findViewById(R.id.item_safehouse_itemstore_itemData);
        dv.setLayoutResource(R.layout.layout_itemstore_itemdata_equipment);
        dataView = dv.inflate();
        dataView.setVisibility(View.INVISIBLE);
    }

    protected void createGroup(
            @NonNull LinearLayoutCompat parent,
            @NonNull ItemStoreGroupModel group
    ) {
        ItemStoreGroupListView itemStoreGroup = new ItemStoreGroupListView(requireContext());
        itemStoreGroup.build(R.drawable.equipment_tier_item, group, true);
        itemStoreGroup.setVisibility(View.INVISIBLE);
        itemStoreGroup.setAlpha(0);
        parent.addView(itemStoreGroup);
    }

    @SuppressLint("ResourceType")
    protected void buildGroupViews(@NonNull LinearLayoutCompat parent, @NonNull GridLayout scrollViewPaginator) {
        if(getContext() == null) { return; }

        scrollViewPaginator.setRowCount(storeData.getGroups().size());

        for (ItemStoreGroupModel group: storeData.getGroups()) {

            try {
                requireActivity().runOnUiThread(() -> {
                    addPaginatorIcon(scrollViewPaginator, group.getPaginationIcon());
                    createGroup(parent, group);
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

    }

    protected void buildDataPopupView(@NonNull View dataView, int groupIndex, int itemIndex) {

        ItemStoreEquipmentGroupModel groupData = (ItemStoreEquipmentGroupModel) storeData.getGroupAt(groupIndex);
        ItemStoreEquipmentItemModel itemData = (ItemStoreEquipmentItemModel)groupData.getItemDataAt(itemIndex);

        AppCompatTextView itemNameView = dataView.findViewById(R.id.safehouse_shop_tool_label);
        AppCompatTextView flavortextView = dataView.findViewById(R.id.textview_itemshop_flavor);
        AppCompatTextView infotextView = dataView.findViewById(R.id.textview_itemshop_info);
        AppCompatTextView attrtextView = dataView.findViewById(R.id.textview_itemshop_attributes);
        AppCompatTextView buycostView = dataView.findViewById(R.id.textview_itemcost);
        AppCompatTextView upgradeCostView = dataView.findViewById(R.id.textview_upgradecost);
        AppCompatTextView upgradeLevelView = dataView.findViewById(R.id.textview_unlocklevel);
        //ComposeView itemImageView = dataView.findViewById(R.id.itemStoreEquipmentItemData);
        ItemStoreItemView itemImageView = dataView.findViewById(R.id.itemStoreEquipmentItemData);//.findViewById(R.id.tier_item);

        StringBuilder buyCost = new StringBuilder("$");
        buyCost.append(getResources().getInteger(groupData.buyCostData));

        int upcst = getResources().getInteger(itemData.upgradeCostData);
        StringBuilder upgradeCost = new StringBuilder();
        if(upcst > 0) {
            upgradeCost.append("$").append(upcst);
        } else {
            upgradeCost.append("-");
        }

        int uplvl = getResources().getInteger(itemData.getUpgradeLevelData());
        StringBuilder upgradeLevel = new StringBuilder();
        upgradeLevel.append((uplvl > 0) ? uplvl : "-");

        itemNameView.setText(getString(groupData.nameData));
        flavortextView.setText(Html.fromHtml(getString(itemData.flavorData)));
        infotextView.setText(Html.fromHtml(getString(itemData.infoData)));
        buycostView.setText(buyCost);
        upgradeCostView.setText(upgradeCost);
        upgradeLevelView.setText(upgradeLevel);
        attrtextView.setText(Html.fromHtml(itemData.getAllAttributesAsFormattedHTML(requireContext())));
        /*ItemStoreComposablesKt.setEquipmentSimple(
                itemImageView,
                itemData.getImageData(),
                itemIndex+1
        );*/
        LayerDrawable layerDrawable = (LayerDrawable) (itemImageView.getDrawable());
        layerDrawable.setDrawableByLayerId(R.id.ic_type, ResourcesCompat.getDrawable(getResources(), itemData.imageData, getContext().getTheme()));
        layerDrawable.setLevel(itemIndex+1);

        itemImageView.invalidate();

    }

    @Override
    public void softReset() {

    }

    @Override
    protected void saveStates() {

    }

}
