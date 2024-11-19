class LinkedList<T> {
    private Node<T> head;
    private Node<T> current;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedList() {
        head = null;
        current = null;
        size = 0;
    }

    // Returns the size of the linked list
    public int size() {
        return size;
    }

    // Checks if the list is empty
    public boolean empty() {
        return head == null;
    }

    // Checks if the current node is the last node in the list
    public boolean last() {
        return current != null && current.next == null;
    }

    // Sets the current node to the head of the list
    public void findfirst() {
        current = head;
    }

    // Moves the current node to the next node in the list
    public void findnext() {
        if (current != null) {
            current = current.next;
        }
    }

    // Retrieves the data of the current node
    public T retrieve() {
        return current != null ? current.data : null;
    }

    // Updates the data of the current node
    public void update(T val) {
        if (current == null) {
            throw new IllegalStateException("Current is null, cannot update data.");
        }
        current.data = val;
    }

    // Inserts a new node with the given value after the current node
    public void insert(T val) {
        Node<T> newNode = new Node<>(val);
        if (empty()) {
            head = newNode;
            current = head;
        } else {
            newNode.next = current.next;
            current.next = newNode;
            current = newNode;
        }
        size++;
    }

    // Removes the current node from the list
    public void remove() {
        if (current == null) {
            throw new IllegalStateException("Current is null, cannot remove.");
        }

        if (current == head) {
            head = head.next;
            current = head;
        } else {
            Node<T> tmp = head;
            while (tmp != null && tmp.next != current) {
                tmp = tmp.next;
            }
            if (tmp != null) {
                tmp.next = current.next;
            }
            current = (tmp != null) ? tmp.next : head;
        }
        size--;
    }

    // Searches for an element by value and returns true if found
    public boolean search(T data) {
        Node<T> temp = head;
        while (temp != null) {
            if (temp.data.equals(data)) {
                return true;
            }
            temp = temp.next;
        }
        return false; 
    }

    // Displays all elements of the list
    public void display() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    // Returns a string representation of the document list
    public String displayDocs() {
        Node<T> temp = head;
        String docs = "";
        while (temp != null) {
            docs += temp.data + " -> ";
            temp = temp.next;
        }
        docs += "null";
        return docs;
    }
}
