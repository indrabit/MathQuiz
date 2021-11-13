/*
    Node class
    Node <T> generic class
    <T> means generic type (i.e. the class accepts any reference data type as its data)
    IComparable interface contains one method: CompareTo()
    CompareTo() returns 1 of 3 integer values
    e.g. node.data.CompareTo(data)
    if result is > 0, then node.data > data
    if result is < 0, then node.data < data
    if result is 0, then node.data == data
    Developed by Hans Telford
  
   Author Hans Telford, 2019
  Version 1.1
   
*/

package NetworkMathQuiz;

import java.util.Comparator;
public class Node <T extends Comparable <T>> implements Comparable<Node<T>>{
    // data component
    public T data;
    // reference to the left child node (a memory address)
    public Node<T> leftChild;
    // reference to the right child node (a memory address)
    public Node<T> rightChild;

    // constructor method for Node()
    // initialise data
    // set right and left child nodes to null
    public Node(T data)
    {
        // when initialised, this default constructor sets up the input data
        // to get assigned to the new object's data
        // the references for the left and right child nodes are made null
        // as none exist at this point
        this.data = data;
        leftChild = null;
        rightChild = null;
    }
    
    @Override
    public String toString()
    {
        return data.toString();
    }

    // Search() method to find a particular data value
    // as this is a generic data type <T>, this uses a compareTo() method
    public boolean search(Node<T> node, T data)
    {
        // if node is null, then this is the root node
        if (node == null)
        {
            return false;
        }
        else
        {
            // existing node --- check if data is > node.data
            if (node.data.compareTo(data) > 0)
            {
                    return search(node.rightChild, data);
            }
            // existing data --- check if data is < node.data
            else if (node.data.compareTo(data) < 0)
            {
                return search(node.leftChild, data);
            }
            // if existing data is the same as node.data
            else
            {
                return true;
            }
        }
    }
    
    // implement compareTo() method
    public int compareTo (Node <T> otherNode)
    {
        // use Comparable interface built-in compareTo() method
        // returns  0 if this.data == otherNode.data
        // returns -1 if this.data < otherNode.data
        // returns  1 if this.data > otherNode.data
        return this.data.compareTo(otherNode.data);
    }
    
}

