class LinkedList<T> {
    
    private Node<T> head;
    private Node<T> current;

    // Node inner class for the linked list
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Constructor
    public LinkedList() {
        head = null;
        current = null;
    }

    // Check if list is empty
    public boolean empty() {
        return head == null;
    }

    // Check if the node is at the end of the list 
    public boolean last() {
        return current != null && current.next == null;
    }

    // Make the current node the first position of the list
    public void findfirst() {
        current = head;
    }

    // Move to the next node of the current node in the list
    public void findnext() {
        if (current != null) { // Only move if current is not null
            current = current.next;
        }
    }

    // Retrieve the data of the current node
    public T retrieve() {
        if (current == null) {
            return null; // Return null if current is null, indicating the end or empty list
        }
    return current.data;
    }


    // Update the data of the current node 
    public void update(T val) {
        if (current == null) {
            throw new IllegalStateException("Current is null, cannot update data.");
        }
        current.data = val;
    }

    // Insert a node in the list (add)
    public void insert(T val) {
        Node<T> newNode = new Node<>(val);
        
        if (empty()) {
            head = newNode;
            current = head; // Set current to the new head
        } else {
            newNode.next = current.next; // Link the new node to the next
            current.next = newNode; // Insert the new node after current
            current = newNode; // Move current to the new node
        }
    }

    // Remove current node from the list
    public void remove() {
        if (current == null) {
            throw new IllegalStateException("Current is null, cannot remove.");
        }

        if (current == head) {
            head = head.next; // Update head if current is the first node
            current = head; // Move current to the new head
        } else {
            Node<T> tmp = head;

            while (tmp != null && tmp.next != current) {
                tmp = tmp.next; // Find the previous node
            }

            if (tmp != null) {
                tmp.next = current.next; // Bypass current node
            }

            current = (tmp != null) ? tmp.next : head; // Update current
        }
    }

    // Search for an element by value and return index if found or -1 if not found
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

    // Display elements of the list
    public void display() {
        Node<T> temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }

        System.out.println("null");
    }
}