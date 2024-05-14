package com.mycompany.g17project;

class Student {
    String name;
    int id;
    double grade;
    Student leftChild;
    Student rightChild;
    
    public Student(String n, int i, double g){
        name = n;
        id = i;
        grade = g;
        leftChild = null;
        rightChild = null;
    }
    
    public void displayStudent(){
        if(id != -1)
            System.out.print(name + " " + id + " "  + grade + " | ");
        else
            System.out.print("Student Not Found! | ");
    }
    
}

class Tree {
    public Student root;
    
    public Tree(){
        root = null;
    }
    
    public Student find(int key){
        Student current = root;
        
        while(key != current.id){
            if(key < current.id)
                current = current.leftChild;
            else
                current = current.rightChild;
            if(current == null)
                return null;
        }
        
        return current;
    }
    
    public boolean isEmpty(){
        return root == null;
    }
    
    public void insert(String n, int i, double g){
        Student newStudent = new Student(n, i, g);
        
        if(root == null)
            root = newStudent;
        else{
            Student current = root;
            Student parent = null;
            
            while(current != null){
                if(newStudent.id < current.id){
                    parent = current;
                    current = current.leftChild;
                }else{
                    parent = current;
                    current = current.rightChild;
                }
            }

            if(newStudent.id < parent.id)
                parent.leftChild = newStudent;
            else
                parent.rightChild = newStudent;
        }
    }
    
    public void inOrder(Student localRoot){
        
        if(localRoot != null){
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.name + ", " + localRoot.id + ", " + localRoot.grade + " | ");
            inOrder(localRoot.rightChild);
        }
    }
    
    public Student delete(int key){
        
        Student current = root;
        Student parent = root;
        boolean isLeft = true;
        
        while(current.id != key){
            parent = current;
            
            if(key < current.id){
                isLeft = true;
                current = current.leftChild;
            }
            else{
                isLeft = false;
                current = current.rightChild;
            }
            
            if(current == null)
                return null;
        }
        
        if(current.leftChild == null && current.rightChild == null){
            
            if(current == root)
                root = null;
            else if(isLeft)
                parent.leftChild = null;
            else
                parent.rightChild = null;
            
        }else if(current.rightChild == null){
            if(current == root)
                root = current.leftChild;
            else if(isLeft)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;
                
        }else if(current.leftChild == null){
            if(current == root)
                root = current.rightChild;
            else if(isLeft)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;
        }else{
            Student successor = getSuccessor(current);
            if(current == root)
                root = successor;
            else if(isLeft)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;
            successor.leftChild = current.leftChild;
        }
        
        return current;
    }
    
    public Student getSuccessor(Student delStudent){
        Student successor = delStudent;
        Student successorParent = delStudent;
        Student current = delStudent.rightChild;
        
        while(current != null){
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        
        if(successor != delStudent.rightChild){
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delStudent.rightChild;
        }
        return successor;
    }

}

class HashTable {
    public Tree[] hashArray;
    public int arraySize;
    public Tree nonItem;
    
    public HashTable(int size){
        arraySize = size;
        hashArray = new Tree[arraySize];
        nonItem = new Tree();
        nonItem.insert("nonItem", -1, 0);
    }
    
    public void displayTable(){
        System.out.println("\n==========================================================================");
        System.out.println("\nTable:");
        for(int i = 0; i < arraySize; i++){
            if(hashArray[i] != null && hashArray[i] != nonItem){
                hashArray[i].inOrder(hashArray[i].root);
                System.out.println("\n//////////////////////////////");
            }else{
                System.out.print("** | ");
                System.out.println("\n//////////////////////////////");
            }    
        }
            System.out.println("\n==========================================================================\n");
    }
    
    public int hashFunc(int key){
        return key % arraySize;
    }
    
    public void insert(String n, int i, double g){
        int key = i;
        int hashVal = hashFunc(key);
        if(hashArray[hashVal] == null || hashArray[hashVal] == nonItem){
            Tree tree = new Tree();
            tree.insert(n, i, g);
            hashArray[hashVal] = tree;
        }else{
            hashArray[hashVal].insert(n, i, g);
        }
    }
    
    public Student delete(int key){
        int hashVal = hashFunc(key);
        if(hashArray[hashVal] != null && hashArray[hashVal] != nonItem && hashArray[hashVal].find(key) != null){
            Student temp = hashArray[hashVal].delete(key);
            if(hashArray[hashVal].isEmpty())
                hashArray[hashVal] = nonItem;
            return temp;
        }
        System.out.print(" Can Not Delete, ");
        return nonItem.root;
    }
    
    public Student find(int key){
        int hashVal = hashFunc(key);
        if(hashArray[hashVal] != null && hashArray[hashVal] != nonItem && hashArray[hashVal].find(key) != null){
            return hashArray[hashVal].find(key);
        }
        return nonItem.root;
    }
    
    
}

public class G17Project {

    public static void main(String[] args) {
        
        HashTable hTable = new HashTable(10);
        hTable.insert("M", 50, 312);
        hTable.insert("M", 48, 312);
        hTable.insert("M", 18, 312);
        hTable.insert("M", 8, 312);
        hTable.insert("M", 28, 312);
        hTable.insert("M", 68, 312);
        hTable.insert("M", 58, 312);
        hTable.insert("M", 38, 312);
        hTable.insert("M", 118, 312);
        hTable.insert("M", 58, 312);
        hTable.insert("M", 60, 312);
        hTable.insert("M", 3, 312);
        hTable.insert("M", 63, 312);
        hTable.insert("M", 12, 312);
        hTable.insert("M", 7, 312);
        hTable.insert("M", 37, 312);
        hTable.insert("M", 34, 312);
        hTable.insert("M", 18, 312);
        
        hTable.displayTable();
        
        hTable.find(51).displayStudent();
        hTable.find(668).displayStudent();
        hTable.find(60).displayStudent();
        System.out.println("\n//////////////////////////////////////////////////////////");
        
        hTable.delete(7).displayStudent();
        hTable.delete(8).displayStudent();
        hTable.delete(118).displayStudent();
        hTable.delete(48).displayStudent();
        hTable.delete(558).displayStudent();
        hTable.delete(63).displayStudent();
        hTable.delete(12).displayStudent();
        System.out.println("\n//////////////////////////////////////////////////////////");
        
        hTable.displayTable();
        
    }
}
