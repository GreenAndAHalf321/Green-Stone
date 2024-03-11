package at.foxel.greenstone.useful;

public class DoubleLinkedList<T> {
    private int count = 0;
    private DoubleReferenceNode<T> head;
    private DoubleReferenceNode<T> tail;
    public DoubleLinkedList() {

    }

    public DoubleReferenceNode<T> getFirst() {
        return head;
    }

    public DoubleReferenceNode<T> getLast() {
        return tail;
    }

    public void addLast(T newItem) {

        DoubleReferenceNode<T> itemToStore = new DoubleReferenceNode<>(newItem);

        if(count == 0) {
            head = itemToStore;
        }else {
            tail.next = itemToStore;
            itemToStore.previews = tail;
        }
        tail = itemToStore;

        count++;
    }

    public void addFirst(T newItem) {

        DoubleReferenceNode<T> itemToStore = new DoubleReferenceNode<>(newItem);

        if(count == 0) {
            tail = itemToStore;
        }else {
            head.previews = itemToStore;
            itemToStore.next = head;
        }
        head = itemToStore;

        count++;
    }

    public void removeLast() {

        if(count == 0)
            return;

        tail.previews.next = null;
        tail = tail.previews;

        count--;
    }

    public void removeFirst() {

        if(count == 0)
            return;

        head.next.previews = null;
        head = head.next;

        count--;
    }
    public DoubleReferenceNode<T> getByIndex(int index) {
        if(index > count)
            throw new IndexOutOfBoundsException();

        DoubleReferenceNode<T> currentNode;
        if(index < count/2) {
            currentNode = head;
            for(int i = 0; i < index; i++)
                currentNode = currentNode.next;
            return currentNode;
        }

        currentNode = tail;
        for(int i = 0; i < count - index; i++)
            currentNode = currentNode.previews;
        return currentNode;
    }
    public T getItemByIndex(int index) {
        return getByIndex(index).item;
    }

    public void removeByIndex(int index) {
        DoubleReferenceNode<T> node = getByIndex(index);
        node.previews.next = node.next;
        node.next.previews = node.previews;
    }
    public int getCount() {
        return count;
    }
}
