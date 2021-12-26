package ru.job4j.threads.synchronize.dynamiclist.cloneaccess.package1;

import java.io.Serializable;
import java.util.Arrays;

public class ChildCloneAccess1 implements Cloneable, Serializable {

    public static void main(String[] args) throws CloneNotSupportedException {
        new CloneAccess().printSomething();
        new ChildCloneAccess1().printClone();
        Integer[] array = new Integer[10];
        array[0] = 22;
        array[1] = 24;
        array[2] = 52;
        array[3] = 11;
        array[4] = 30;
        Integer[] newArray = array.clone();
        array[2] = 111;

        System.out.println("newArray: " + Arrays.toString(newArray));
        System.out.println("array: " + Arrays.toString(array));
    }

    public void printClone() throws CloneNotSupportedException {
        System.out.println(super.clone());
    }

    public void printSomethingFromParent() {
        new CloneAccess().printSomething();
    }
}
