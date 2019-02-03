package ch.cansulting.contactslist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddContactView extends AppCompatActivity {
    // Instantiation
    String firstname;
    String lastname;
    String phone;
    private String color = null;
    private TextView contact_selected_color;
    private ContactDao contactDao;
    // color definition
    private String purple = "#AB47BC";
    private String ligth_blue = "#26C6DA";
    private String green = "#43A047";
    private String red = "#E53935";
    private String yellow = "#FBC02D";
    private String orange = "#FB8C00";
    private String black = "#000000";
    // color buttons
    ImageButton button_purple;
    ImageButton button_blue;
    ImageButton button_green;
    ImageButton button_red;
    ImageButton button_orange;
    ImageButton button_yellow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_view);

        // Adding the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create Contact");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        // adding PhoneNumberFormattingTextWatcher to format the phone number
        TextView phone_text = (TextView) findViewById(R.id.phone);
        phone_text.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        // get contact color circle
        contact_selected_color = (TextView) findViewById(R.id.contact_selected_color);


        // connect to DB
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        contactDao = daoSession.getContactDao();

        // OnClick eventlistener to close button (-> go back)
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    // Inflating menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);
        return true;
    }

    @Override
    // Menu action to add new contact
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add){
            addContact();
        }
        return super.onOptionsItemSelected(item);
    }

    // save new contact to database
    public void addContact(){
        // get values
        firstname  = ((EditText) findViewById(R.id.firstname)).getText().toString();
        lastname  = ((EditText) findViewById(R.id.lastname)).getText().toString();
        phone  = ((EditText) findViewById(R.id.phone)).getText().toString();

        // show dialog if information is missing
        if ( firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || color == null ){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Missing information")
                    .setMessage("All the fields are required!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
        }

        // if all the information is provided -> save
        else {
            // save new contact to DB
            Contact contact = new Contact();
            contact.setFirstName(firstname);
            contact.setLastName(lastname);
            contact.setPhoneNumber(phone);
            contact.setColor(color);
            contactDao.insert(contact);
            // go back to main view
            finish();
        }

    }

    // open the color picker
    public void showColorPicker(View view) {
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("color_picker_manager");
        ColorPicker cpicker = new ColorPicker();
        cpicker.show(manager,"color_picker_manager" );
    }

    // clean all checked color buttons
    public void cleanView (View v){
        // get parent view
        View parent = ((View) v.getParent());
        // get all color buttons
        button_purple = (ImageButton) parent.findViewById(R.id.button_purple);
        button_blue = (ImageButton) parent.findViewById(R.id.button_blue);
        button_green = (ImageButton) parent.findViewById(R.id.button_green);
        button_red = (ImageButton) parent.findViewById(R.id.button_red);
        button_orange = (ImageButton) parent.findViewById(R.id.button_orange);
        button_yellow = (ImageButton) parent.findViewById(R.id.button_yellow);
        // remove check from all buttons
        button_purple.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
        button_blue.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
        button_green.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
        button_red.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
        button_orange.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
        button_yellow.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
    }

    // check and update the selecte color
    public void onColorClick(View v) {
        // clear all checked buttons
        cleanView(v);
        // create a new drawable to fill the color in the "Create Contact" page according to user's selection
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(100);

        // swithc checked color and set the contact's color
        switch(v.getId()) {
            case R.id.button_purple:
                button_purple.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                drawable.setColor( Color.parseColor(purple));
                color = purple;
                break;
            case R.id.button_blue:
                button_blue.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                drawable.setColor( Color.parseColor(ligth_blue));
                color = ligth_blue;
                break;
            case R.id.button_green:
                button_green.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                drawable.setColor( Color.parseColor(green));
                color = green;
                break;
            case R.id.button_red:
                button_red.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                drawable.setColor( Color.parseColor(red));
                color = red;
                break;
            case R.id.button_orange:
                button_orange.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                drawable.setColor( Color.parseColor(orange));
                color = orange;
                break;
            case R.id.button_yellow:
                button_yellow.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                drawable.setColor( Color.parseColor(yellow));
                color = yellow;
                break;

        }
        contact_selected_color.setBackground(drawable);

    }
}
