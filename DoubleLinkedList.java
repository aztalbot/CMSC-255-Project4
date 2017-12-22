/**
 * PROJECT 4
 * Self-Organizing Lists
 * Name: Andrew Talbot
 * Class: CMSC 256 - Sec 901
 * Semester: Fall 2017
 *
 * @name DoubleLinkedList
 * Implementation of a DoubleLinkedList
 */

public class DoubleLinkedList<T> implements ListInterface<T> {
    private DoubleLinkedNode first;
    private DoubleLinkedNode last;
    private int numElements;

    public DoubleLinkedList() {
        initializeDataFields();
    }

    @Override
    public void add (T newEntry) {
        DoubleLinkedNode newNode = new DoubleLinkedNode(newEntry);

        if (isEmpty()) {
            first = newNode;
        } else {
            last.setNextNode(newNode);
            newNode.setPreviousNode(last);
        }

        last = newNode;
        numElements++;
    }

    @Override
    public void add (int newPosition, T newEntry) {
        addNodeAt(newPosition, new DoubleLinkedNode(newEntry));
    }

    @Override
    public T remove (int givenPosition) {
        return removeNodeAt(givenPosition).getData();  // Return removed entry
    }

    @Override
    public void clear () {
        initializeDataFields();
    }

    @Override
    public T replace (int givenPosition, T newEntry) {
        if ((givenPosition >= 1) && (givenPosition <= numElements))
        {
            assert !isEmpty();

            DoubleLinkedNode desiredNode = getNodeAt(givenPosition);
            T originalEntry = desiredNode.getData();
            desiredNode.setData(newEntry);
            return originalEntry;
        }
        else
            throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
    } // end replace

    @Override
    public T getEntry (int givenPosition) {
        if ((givenPosition >= 1) && (givenPosition <= numElements))	{
            assert !isEmpty();
            return getNodeAt(givenPosition).getData();
        }
        else
            throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    }

    @Override
    public T[] toArray() {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numElements];

        int index = 0;
        DoubleLinkedNode currentNode = first;
        while ((index < numElements) && (currentNode != null))
        {
            result[index] = currentNode.getData();
            currentNode = currentNode.getNextNode();
            index++;
        } // end while

        return result;
    }

    @Override
    public boolean contains (T anEntry) {
        boolean found = false;
        DoubleLinkedNode currentNode = first;

        while (!found && (currentNode != null))
        {
            if (anEntry.equals(currentNode.getData()))
                found = true;
            else
                currentNode = currentNode.getNextNode();
        } // end while

        return found;
    }

    @Override
    public int getLength() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        boolean result;

        if (numElements == 0) // Or getLength() == 0
        {
            assert (first == null) && (last == null);
            result = true;
        }
        else
        {
            assert (first != null) && (last != null);
            result = false;
        } // end if

        return result;
    }

    // Initializes the class's data fields to indicate an empty list.
    private void initializeDataFields()  {
        first = null;
        last = null;
        numElements = 0;
    }

    protected DoubleLinkedNode getFirst() {
        return first;
    }

    protected DoubleLinkedNode getLast() {
        return last;
    }

    protected void setFirst(DoubleLinkedNode node) {
        first = node;
    }


    protected void setLast(DoubleLinkedNode node) {
        last = node;
    }


    // Returns a reference to the node at a given position.
    // Precondition: The chain is not empty;
    //               1 <= givenPosition <= numberOfEntries.
    protected DoubleLinkedNode getNodeAt(int givenPosition)	{
        assert (first != null) && (1 <= givenPosition) && (givenPosition <= numElements);
        DoubleLinkedNode currentNode = first;

        if (givenPosition == numElements)
            currentNode = last;
        else if (givenPosition >1)	{
            assert givenPosition < numElements;
            // Traverse the chain to locate the desired node
            for (int counter = 1; counter < givenPosition; counter++)
                currentNode = currentNode.getNextNode();
        }

        assert currentNode != null;
        return currentNode;
    }

    /**
     *  Because the nodes have a count, it's good to have a way
     *  to remove and then add a node somewhere else. Otherwise,
     *  we would need the subclass to traverse to the new position
     *  in order to set its count.
     *
     * @param givenPosition
     * @return result
     */
    protected DoubleLinkedNode removeNodeAt(int givenPosition) {
        DoubleLinkedNode result = null;

        if ((givenPosition >= 1) && (givenPosition <= numElements))
        {
            if (givenPosition == 1)                 // Case 1: Remove first entry
            {
                result = first;        // Save node to be removed
                first = first.getNextNode();
                if(givenPosition < numElements)
                    first.setPreviousNode(null);
                if (numElements == 1)
                    last = null;                  // Solitary entry was removed
            }
            else                                 // Case 2: Not first entry
            {
                DoubleLinkedNode nodeBefore = getNodeAt(givenPosition - 1);
                DoubleLinkedNode nodeToRemove = nodeBefore.getNextNode();
                DoubleLinkedNode nodeAfter = nodeToRemove.getNextNode();
                nodeBefore.setNextNode(nodeAfter);// Disconnect the node to be removed

                nodeToRemove.setNextNode(null);
                nodeToRemove.setPreviousNode(null); // Return node without links
                result = nodeToRemove;  // Save entry to be removed

                if (givenPosition == numElements)
                    last = nodeBefore;         // Last node was removed
                else
                    nodeAfter.setPreviousNode(nodeBefore);
            } // end if

            numElements--;
        }
        else
            throw new IndexOutOfBoundsException("Illegal position given to remove operation.");

        return result;                             // Return removed entry
    }

    protected void addNodeAt(int newPosition, DoubleLinkedNode newNode) {
        if ((newPosition >= 1) && (newPosition <= numElements + 1))
        {
            if (isEmpty())
            {
                first = newNode;
                last = newNode;
            }
            else if (newPosition == 1)
            {
                newNode.setNextNode(first);
                first.setPreviousNode(newNode);
                first = newNode;
            }
            else if (newPosition == numElements + 1)
            {
                last.setNextNode(newNode);
                newNode.setPreviousNode(last);
                last = newNode;
            }
            else
            {
                DoubleLinkedNode nodeBefore = getNodeAt(newPosition - 1);
                DoubleLinkedNode nodeAfter = nodeBefore.getNextNode();
                newNode.setNextNode(nodeAfter);
                newNode.setPreviousNode(nodeBefore);
                nodeBefore.setNextNode(newNode);
                nodeAfter.setPreviousNode(newNode);
            } // end if
            numElements++;
        }
        else
            throw new IndexOutOfBoundsException("Illegal position given to add operation.");
    }

    protected class DoubleLinkedNode {
        private T data;
        private int count;                 // to allow organizing by count
        private DoubleLinkedNode next;  	 // Link to next node
        private DoubleLinkedNode previous; // Link to previous node

        private DoubleLinkedNode(T dataPortion) {
            this(null, dataPortion, null);
        }
        private DoubleLinkedNode(DoubleLinkedNode previousNode, T dataPortion, DoubleLinkedNode nextNode) {
            data = dataPortion;
            count = 0;
            next = nextNode;
            previous = previousNode;
        }

        protected T getData(){
            return data;
        }

        private void setData(T newData){
            data = newData;
        }

        protected DoubleLinkedNode getNextNode(){
            return next;
        }

        private void setNextNode(DoubleLinkedNode nextNode){
            next = nextNode;
        }

        protected DoubleLinkedNode getPreviousNode(){
            return previous;
        }

        private void setPreviousNode(DoubleLinkedNode previousNode){
            previous = previousNode;
        }

        /* *****************************************
        ********************************************
        ** MODIFICATIONS FOR SELF-ORGANIZING LIST **
        ********************************************
        ***************************************** */
        protected int getCount() {
            return this.count;
        }

        protected void incrementCount() {
            this.count++;
        }
    }
}