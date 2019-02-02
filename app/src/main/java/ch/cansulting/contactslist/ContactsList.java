package ch.cansulting.contactslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import org.greenrobot.greendao.database.Database;
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


//        Contact contact = new Contact();
//        contact.setFirstName("Adem");
//        contact.setLastName("Bilican");
//        contactDao.insert(contact);
//
//        Contact contact2 = new Contact();
//        contact2.setFirstName("Adem");
//        contact2.setLastName("Bilican");
//        contactDao.insert(contact2);

        // query all notes, sorted a-z by their text
        contactsQuery = contactDao.queryBuilder().orderAsc(ContactDao.Properties.FirstName).build();
        updateContacts();
    }

    private void updateContacts() {
        List<Contact> contacts = contactsQuery.list();
        contactsAdapter.setContacts(contacts);
    }

    public void addNewContact(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddContactView.class);
        startActivity(intent);
    }


    protected void setUpViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactsAdapter(contactClickListener);
        recyclerView.setAdapter(contactsAdapter);
//        addNoteButton = findViewById(R.id.buttonAdd);
//        //noinspection ConstantConditions
//        addNoteButton.setEnabled(false);
//
//        editText = (EditText) findViewById(R.id.editTextNote);
//        editText.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    addNote();
//                    return true;
//                }
//                return false;
//            }
//        });
//        editText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                boolean enable = s.length() != 0;
//                addNoteButton.setEnabled(enable);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    ContactsAdapter.ContactClickListener contactClickListener = new ContactsAdapter.ContactClickListener() {
        @Override
        public void onContactClick(int position) {
            Contact contact = contactsAdapter.getContact(position);
            Long noteId = contact.getId();

//            noteDao.deleteByKey(noteId);
//            Log.d("DaoExample", "Deleted note, ID: " + noteId);

            updateContacts();
        }
    };
}
