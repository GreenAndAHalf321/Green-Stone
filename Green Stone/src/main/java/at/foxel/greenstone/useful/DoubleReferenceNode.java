// © 2024 Florian Wallner
//
// SPDX-License-Identifier: GPL-3.0-or-later

package at.foxel.greenstone.useful;

public class DoubleReferenceNode<T> {
    public DoubleReferenceNode<T> previews;
    public DoubleReferenceNode<T> next;
    public T item;

    public DoubleReferenceNode(T itemToStore) {
        item = itemToStore;
    }
}
