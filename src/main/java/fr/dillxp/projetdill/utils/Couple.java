package fr.dillxp.projetdill.utils;

public class Couple<A,T> {
    private A first;
    private T second;

    public Couple(A first,T second){
        this.first=first;
        this.second=second;
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
