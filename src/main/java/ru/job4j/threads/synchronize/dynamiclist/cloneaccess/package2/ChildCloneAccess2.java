package ru.job4j.threads.synchronize.dynamiclist.cloneaccess.package2;

import ru.job4j.threads.synchronize.dynamiclist.cloneaccess.package1.ChildCloneAccess1;
import ru.job4j.threads.synchronize.dynamiclist.cloneaccess.package1.CloneAccess;

import java.io.*;
import java.util.StringJoiner;

public class ChildCloneAccess2 extends CloneAccess implements Cloneable, Serializable {

    private String name;

    private ChildCloneAccess1 childCloneAccess1;

    public ChildCloneAccess2() {
    }

    public ChildCloneAccess2(String name, ChildCloneAccess1 childCloneAccess1) {
        this.name = name;
        this.childCloneAccess1 = childCloneAccess1;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        new ChildCloneAccess2().printSomething();
        new ChildCloneAccess2().printSomethingFromMe();
        new ChildCloneAccess2().printSomethingFromParent();
        ChildCloneAccess1 childCloneAccess1 = new ChildCloneAccess1();
        System.out.println("child1 orig " + childCloneAccess1);
        ChildCloneAccess2 childCloneAccess2 = new ChildCloneAccess2("name2", childCloneAccess1);
        ChildCloneAccess2 childCloneAccess2Clone = null;
        System.out.println(childCloneAccess2);

        try {
            childCloneAccess2Clone = new ChildCloneAccess2().getClone(childCloneAccess2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(childCloneAccess2Clone);

        childCloneAccess2Clone = (ChildCloneAccess2) childCloneAccess2.copy();
        System.out.println(childCloneAccess2Clone);
    }

    private ChildCloneAccess2 getClone(ChildCloneAccess2 childCloneAccess2) throws IOException, ClassNotFoundException {
        return (ChildCloneAccess2) convertFromBytes(convertToBytes(childCloneAccess2));
    }

    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    @Override
    public void printSomething() {
        System.out.println("From me!");
    }

    public void printSomethingFromParent() {
        super.printSomething();
    }

    public void printSomethingFromMe() {
        printSomething();
    }

    private Object copy() throws CloneNotSupportedException {
        return clone();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ChildCloneAccess2.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("childCloneAccess1=" + childCloneAccess1)
                .toString();
    }
}
