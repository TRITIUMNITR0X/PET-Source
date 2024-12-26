package com.tritiumgaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore

abstract class ItemStoreListModel {

    val groups: ArrayList<ItemStoreGroupModel> = ArrayList()

    fun addGroup(groupItems: ItemStoreGroupModel) {
        groups.add(groupItems)
    }

    fun getItemAt(groupIndex: Int, itemIndex: Int): ItemStoreItemModel {
        val item: ItemStoreItemModel?

        val group = groups[groupIndex]
        item = group.getItemDataAt(itemIndex)

        return item
    }

    fun getGroupAt(groupIndex: Int): ItemStoreGroupModel {
        return groups[groupIndex]
    }
}