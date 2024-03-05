package at.foxel.greenstone.useful;

public class DoubleReferenceNode<T> {
    public DoubleReferenceNode<T> previews;
    public DoubleReferenceNode<T> next;
    public T item;

    public DoubleReferenceNode(T itemToStore) {
        item = itemToStore;
    }
}
