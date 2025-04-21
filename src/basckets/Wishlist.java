package basckets;

public class Wishlist extends Bucket {
    private Wishlist(){}

    private static Wishlist instance;

    public static Wishlist getInstance(){
        if(instance == null){
            instance = new Wishlist();
        }
        return instance;
    }
}
