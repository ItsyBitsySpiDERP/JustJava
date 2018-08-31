package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**This app displays an order form to order coffee.*/
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**This method is called when the order button is clicked.*/
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean  hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage =  createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**Calculates the price of the order.
     * @param addWhippedCream is weather or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return total price*/
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        //Add $2 if the user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        //Calculate the total order price by multiplying by quantity.
        return quantity * basePrice;

    }

    /**This posts the submit order summary
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is weather or not the user wants chocolate topping
     * @param name is where a customer enters their name
     * @return text summary*/
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
       return "Name: " + name +
               "\nAdd Whipped Cream? " + addWhippedCream +
               "\nAdd Chocolate? " + addChocolate +
               "\nQuantity: " + quantity +
               "\nTotal: $" + price + ".00" +
               "\nThank you!";
    }

    /**This method is called when the plus button is clicked.*/
    public int increment (View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
        if (quantity >= 100) {
            quantity = 99;
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
        return quantity;
    }

    /*** This method is called when the minus button is clicked.*/
    public int decrement (View view) {
        quantity = quantity - 1;
        displayQuantity(quantity);
        if (quantity <= 0){
            quantity = 1;
            Toast.makeText(this, "You cannot have less than 0 coffees", Toast.LENGTH_SHORT).show();
        }
        return quantity;
    }

    /** This method displays the given quantity value on the screen.*/
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
