package ch.cansulting.contactslist;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private ContactClickListener clickListener;
    private List<Contact> dataset;

    public interface ContactClickListener {
        void onContactClick(int position);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView firstname;
        public TextView lastname;
        public TextView contactletter;
        public GradientDrawable bgcolor;


        public ContactViewHolder(View itemView, final ContactClickListener clickListener) {
            super(itemView);
            firstname = (TextView) itemView.findViewById(R.id.textViewContactFirstname);
            contactletter = (TextView) itemView.findViewById(R.id.contactIcon);
            bgcolor = (GradientDrawable) contactletter.getBackground();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onContactClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public ContactsAdapter(ContactClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<Contact>();
    }

    public void setContacts(@NonNull List<Contact> contacts) {
        dataset = contacts;
        notifyDataSetChanged();
    }

    public Contact getContact(int position) {
        return dataset.get(position);
    }

    @Override
    public ContactsAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view, clickListener);

    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ContactViewHolder holder, int position) {
        Contact contact = dataset.get(position);
        // provide contact firstname+lastname
        holder.firstname.setText(contact.getFirstName()+" "+contact.getLastName());
        // get the first letter of the firstname and make uppercase
        holder.contactletter.setText( (contact.getFirstName().substring(0,1)).toUpperCase() );
        // update circle color for each contact
        holder.bgcolor.setColor(Color.parseColor(  contact.getColor() ));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}