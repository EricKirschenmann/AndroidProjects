package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * @author Eric Kirschenmann
 * @version 1.0
 * @since 2015-09-18
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;       //variable that tracks the quantity of coffees
    String message = null;  //variable that tracks the order message
    String name = null;     //variable that tracks the name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create Fab and set action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check to make sure there is a message created at least once
                if(message != null) {
                    // Use an intent to launch an email app.
                    // Send the order summary in the email body.
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:1cube.solver@gmail.com")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
                    intent.putExtra(Intent.EXTRA_TEXT, message);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } else {
                    //alert user if there is no order
                    Snackbar.make(view, "Sorry, please create an order.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            reset();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that is called when the Order button is pressed
     * @param view get the parent view
     */
    public void submitOrder(View view) {

        //Figure out if the user wants whipped cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if the user wants chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Get the user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        name = nameField.getText().toString();

        //calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        //create the message
        message = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        //display the message
        displayMessage(message);

    }

    /**
     * Method that displays the given quantity value on the screen.
     * @param numberOfCoffees contains the number to be displayed in the textview
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText(String.format("%d", numberOfCoffees));
    }

    /**
     * Method that displays a string in the textview
     * @param message string that contains the message to be displayed
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Method that increments the quantity when the button is pressed
     * @param view needs the parent view
     */
    public void increment(View view) {
        //check to make sure there isn't a negative quantity
        if(quantity == 100) {
            Snackbar.make(view, "Quantity must be one hundred or fewer!", Snackbar.LENGTH_SHORT).show();
        } else {
            quantity++;
            displayQuantity(quantity);
        }
    }

    /**
     * Method that decrements the quantity when the button is pressed
     * @param view needs the parent view
     */
    public void decrement(View view) {
        //check to make sure there isn't a negative quantity
        if(quantity == 1) {
            Snackbar.make(view, "Quantity must be one or more!", Snackbar.LENGTH_SHORT).show();
        } else {
            quantity--;
            displayQuantity(quantity);
        }
    }

    /**
     * Method that calculates the total cost by quantity and toppings
     * @param hasWhippedCream boolean if there is whipped cream
     * @param hasChocolate boolean if there is chocolate
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5; //set starting price of coffee

        //if there is whipped cream add $1
        if(hasWhippedCream) {
            price += 1;
        }

        //if there is chocolate add $2
        if(hasChocolate) {
            price += 2;
        }

        //return total price
        return price * quantity;
    }

    /**
     * Method that creates a string with information to display
     * @param price the total price
     * @param hasWhippedCream boolean whether there is whipped cream or not
     * @param hasChocolate boolean whether there is chocolate or not
     * @param name contains the user's name
     * @return the new string
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String whipped = getString(R.string.no);
        String chocolate = getString(R.string.no);

        if(hasWhippedCream)
            whipped = getString(R.string.yes);
        if(hasChocolate)
            chocolate = getString(R.string.no);

        String priceMessage = getString(R.string.order_name) + " " + name;
        priceMessage += "\n"+ getString(R.string.order_whipped_cream) + " "  + whipped;
        priceMessage += "\n" + getString(R.string.order_chocolate) + " "  + chocolate;
        priceMessage += "\n" + getString(R.string.order_quantity) + " "  + quantity;
        priceMessage += "\n" + getString(R.string.order_total) + " "  + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + getString(R.string.order_thanks);

        return priceMessage;
    }

    /**
     * Method that completely resets the form screen
     * Probably a quicker way of doing this but it works
     */
    private void reset() {
        //reset globals
        quantity = 1;
        name = null;
        message = null;

        //Clear Whipped Cream checkbox
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        whippedCreamCheckBox.setChecked(false);

        //Clear Chocolate checkbox
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        chocolateCheckBox.setChecked(false);

        //Clear name field
        EditText nameField = (EditText) findViewById(R.id.name_field);
        nameField.setText(name);

        //reset the textviews
        displayQuantity(quantity);
        displayMessage("Total: $0.00");

    }

}
