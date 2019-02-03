package ch.cansulting.contactslist;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ContactsListTest {

    private AddContactView addContactView;
    private ContactsList contactsList;

    @Before
    public void setup() {
        contactsList = Robolectric.buildActivity(ContactsList.class)
                .create().start().visible().get();
        addContactView = Robolectric.buildActivity(AddContactView.class)
                .create().start().visible().get();
    }

    @Test
    public void addContactView_shouldNotBeNull() throws Exception {
        assertNotNull(addContactView);
    }

    @Test
    public void contactsList_shouldNotBeNull() throws Exception {
        assertNotNull(contactsList);
    }

    @Test
    public void switchToAddContact_shouldBeTrue() throws Exception {
        contactsList.findViewById(R.id.fab).performClick();
        Intent expectedIntent = new Intent(contactsList, AddContactView.class);
        ShadowActivity shadowActivity = shadowOf(contactsList);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void addContactViewActivity_shouldHaveCorrectView() {
        EditText firstname = (EditText) addContactView.findViewById(R.id.firstname);
        assertNotNull("TextView could not be found", firstname);
        assertTrue("TextView contains incorrect text", "First Name".equals(firstname.getHint().toString()));

        EditText lastname = (EditText) addContactView.findViewById(R.id.lastname);
        assertNotNull("TextView could not be found", lastname);
        assertTrue("TextView contains incorrect text", "Last Name".equals(lastname.getHint().toString()));

        EditText phone = (EditText) addContactView.findViewById(R.id.phone);
        assertNotNull("TextView could not be found", phone);
        assertTrue("TextView contains incorrect text", "Phone".equals(phone.getHint().toString()));

        TextView setcolor = (TextView) addContactView.findViewById(R.id.set_color_text);
        assertNotNull("TextView could not be found", setcolor);
        assertTrue("TextView contains incorrect text", "Set color".equals(setcolor.getText().toString()));

    }

    @Test
    public void contactsListActivity_shouldHaveToolbar() {
        Toolbar toolbar = (Toolbar) contactsList.findViewById(R.id.toolbar);
        assertNotNull("Toolbar could not be found", toolbar);
    }
}