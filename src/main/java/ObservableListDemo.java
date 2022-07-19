import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ObservableListDemo {
    public static void main(String[] args) {
        ArrayList<Integer> objects = new ArrayList<>();
        ObservableList<Integer> olnumbers = FXCollections.observableArrayList();


        olnumbers.addListener(new ListChangeListener<Integer>() { // This method is subscribed the olnumbers
            // if there is a change happens in olnumbers this method will get to know the change
            @Override
            public void onChanged(Change<? extends Integer> change) {
                System.out.println("Subscriber 1"+change);
            }
        });
        olnumbers.addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> change) {
                System.out.println("Subscriber 2");
            }
        });
        olnumbers.add(10);
        olnumbers.add(20);
        olnumbers.add(30);
        olnumbers.add(40);
    }
}
