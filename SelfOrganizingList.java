/**
 * PROJECT 4
 * Self-Organizing Lists
 * Name: Andrew Talbot
 * Class: CMSC 256 - Sec 901
 * Semester: Fall 2017
 *
 * Class Name: SelfOrganizingList
 * A self organizing list based on count of accesses on each item.
 *
 * Access: when getEntry or contains methods call for an entry
 * Not allowed: add and replace methods
 *
 *  Reasoning:
 *  Chosen method: count number of accesses.
 *  I have chosen to implement this self-sorting list using a count for number of accesses on each term. I did not
 *  choose to have each term placed at the front after each access (method 2, move-to-front) because this would perform
 *  less well for more random patterns of access. Count also matches the performance of move-to-front for simple examples,
 *  say, when you have 10 items and only two of them are accessed over and over. Count moves items to the front in that case
 *  just like move-to-front. The difference, though, is that count can preserve high performance for frequently chosen items
 *  even after several new items from the remainder of the list have been accessed, since those new items won't be moved all
 *  the way to the front just because they were last chosen. Hence, move-to-front can end up being far less efficient
 *  if noise continually moves to the top and demotes the frequently accessed data.
 *
 *  Moreover, any argument that move-to-front can prevent slow retrieval of less frequently accessed data should not
 *  be given much weight because the initial retrieval would be slow, and the subsequent retrieval of the data from
 *  the front of the list would very likely be unnecessary afterwards. One strong case for move-to-front would be
 *  a burst of interest in rarely accessed data, in which case it would perform well. However, count could match this
 *  behavior if it were time aware and analyzed frequency of accesses based on trends over time. But this project would
 *  not be a good format for this kind of implementation.
 *
 *  Transpose, in my opinion sounds like the worst option. Let's say we are literally comparing apples and oranges
 *  (i.e. a list of fruits, probably with images so we can visually compare) and they are both at the end of the list.
 *  To get apple, we have to traverse the whole list, get it, then transpose it with orange. Okay, now let's get orange
 *  -- uh-oh! We then transpose orange with apple. Now they both have been accessed more than the rest of the list, yet
 *  they are both in their original positions at the back of the list. Count doesn't have this problem. The more we compare
 *  apples and oranges, the more efficient list access becomes for these two items. If the 20/80 rule holds true, we
 *  hould want the first 20% of items in the list to be the most frequently accessed. Therefore accessed items need to
 *  be able to move quickly to the front (so don't use transpose), and the other 80% of items should be treated mostly
 *  as noise and not elevated beyond data known to be frequently accessed (don't use move-to-front).
 */

public class SelfOrganizingList<T> extends DoubleLinkedList<T>{

    // CONSTRUCTOR
    SelfOrganizingList() {
        super();
    }

    /**
     * Method: resortList
     *
     * Will ensure the last accessed item
     * finds it's sorted place in the list. Because all
     * nodes start with count 0 and nodes are sorted after each access
     * we can assume the list is always sorted. Therefore, we can
     * peek at the count for First and previousNode to determine
     * where we start comparing counts. This adds efficiency. We can't efficiently
     * jump to the middle of the list since getNodeAt iterates anyway, so we
     * just need to pick a starting point.
     *
     * @param lastAccessedNode
     */
    private void resortList(DoubleLinkedNode lastAccessedNode, int lastAccessedPosition) {
        lastAccessedNode.incrementCount();
        if(getLength() > 1 && getFirst() != lastAccessedNode) {
            int thisAccessCount = lastAccessedNode.getCount();
            int frontCount = getFirst().getCount();
            int previousCount = lastAccessedNode.getPreviousNode().getCount();

            if (thisAccessCount >= frontCount) {
                moveNode(lastAccessedPosition, 1); // entry moves to front
            } else if (previousCount <= thisAccessCount) {
                boolean whichEnd = (thisAccessCount - previousCount) < (frontCount - thisAccessCount);
                sortEntry(lastAccessedNode, lastAccessedPosition, whichEnd);
            } // otherwise the entry should stay put because it is less than previous node
        }
    }

    /**
     * Method: moveEntry
     * Moves an entry from back to it's sorted position if true,
     * otherwise starts from front and moves it to it's sorted position
     *
     * @param entry
     * @param mode
     */
    private void sortEntry(DoubleLinkedNode entry, int position, boolean mode) {
        int count = entry.getCount();
        int newPosition = position - 1;

        if(mode) { // SORT FROM BACK
            DoubleLinkedNode previous = entry.getPreviousNode();
            while(count > previous.getCount()) {
                previous = previous.getPreviousNode();
                newPosition--;
            }
            if(count < previous.getCount()) {
                moveNode(position,newPosition + 1);
            } else {
                moveNode(position, newPosition);
            }
        } else { // SORT FROM FRONT
            DoubleLinkedNode runnersUp = getFirst().getNextNode();
            newPosition = 2;

            while(count < runnersUp.getCount()) {
                runnersUp = runnersUp.getNextNode();
                newPosition++;
            }
            if(count > runnersUp.getCount()) {
                moveNode(position, newPosition);
            } else {
                moveNode(position, newPosition);
            }
        }
    }

    private void moveNode(int currentPosition, int newPosition) {
        addNodeAt(newPosition, removeNodeAt(currentPosition));
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for(int i = 1; i <= getLength(); i++)
            string.append(String.valueOf(super.getEntry(i)) + " " + getNodeAt(i).getCount() + "\n");
        return string.toString();
    }

    /* ********************************************
    ************ OVERRIDDEN METHODS ***************
    ******************************************** */
    @Override
    public void add(T newEntry) {
        // add as normal if the entry is not present
        if(!contains(newEntry))
            super.add(newEntry);
        // do nothing if the entry is already listed
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        int position = 1;
        DoubleLinkedNode currentNode = getFirst();

        while (!found && (currentNode != null))
        {
            if (anEntry.equals(currentNode.getData())) {
                found = true;
                // Increment the count and resort!
                resortList(currentNode, position);
            } else {
                currentNode = currentNode.getNextNode();
                position++;
            }
        } // end while

        return found;
    }

    @Override
    public T getEntry(int givenPosition) {
        if ((givenPosition >= 1) && (givenPosition <= getLength()))	{
            // Increment access count and resort list before return data
            DoubleLinkedNode currentNode = getNodeAt(givenPosition);
            // Increment the count and resort!
            resortList(currentNode, givenPosition);
            return currentNode.getData();
        }
        else
            throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    }

    /* ********************************************
    ********** UNSUPPORTED OPERATIONS *************
    ******************************************** */
    @Override
    public void add(int newPosition, T newEntry) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T replace(int givenPosition, T newEntry) {
        throw new UnsupportedOperationException();
    }
}
