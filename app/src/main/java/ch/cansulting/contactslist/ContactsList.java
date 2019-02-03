package ch.cansulting.contactslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.greendao.query.Query;
import java.util.List;

public class ContactsList extends AppCompatActivity {

    private ContactDao contactDao;
    private Query<Contact> contactsQuery;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        setUpViews();

        // get the note DAO
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        contactDao = daoSession.getContactDao();

        // some test contact information
//        Contact contact = new Contact();
//        contact.setFirstName("Adem");
//        contact.setLastName("Bilican");
//        contact.setColor("#851d00");
//        contact.setPhoneNumber("0123456789");
//        contactDao.insert(contact);
//        Contact contact2 = new Contact();
//        contact2.setFirstName("John");
//        contact2.setLastName("Doe");
//        contact2.setColor("#00133c");
//        contact2.setPhoneNumber("0123456789");
//        contactDao.insert(contact2);

        // query all notes, sorted a-z by their text
        contactsQuery = contactDao.queryBuilder().orderAsc(ContactDao.Properties.FirstName).build();
        updateContacts();
    }

    // redraw activity after a new contact is added
    @Override
    protected void onResume() {
        super.onResume();
        updateContacts();
    }

    // update the list of contacts
    private void updateContacts() {
        List<Contact> contacts = contactsQuery.list();
        contactsAdapter.setContacts(contacts);

        // show/hide the "No contact yet" message
        TextView no_contact_yet = (TextView) findViewById(R.id.no_contact_yet);
        if (contactsAdapter.getItemCount() == 0){
            no_contact_yet.setVisibility(View.VISIBLE);
        }
        else{
            no_contact_yet.setVisibility(View.INVISIBLE);
        }

    }

    // switch to addContactView when user presses the + button
    public void addNewContact(View view) {
        Intent intent = new Intent(this, AddContactView.class);
        startActivity(intent);
    }

    protected void setUpViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Contacts");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactsAdapter(contactClickListener);
        recyclerView.setAdapter(contactsAdapter);
    }

    // Possbility to add an Edit function for contact information
    ContactsAdapter.ContactClickListener contactClickListener = new ContactsAdapter.ContactClickListener() {
        @Override
        public void onContactClick(int position) {
            Contact contact = contactsAdapter.getContact(position);
            Long noteId = contact.getId();
//            editContact();
        }
    };
}
