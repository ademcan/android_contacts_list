package ch.cansulting.contactslist;

/**
 * Created by abilican on 02.02.19.
 */

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

        public ContactViewHolder(View itemView, final ContactClickListener clickListener) {
            super(itemView);
            firstname = (TextView) itemView.findViewById(R.id.textViewContactFirstname);
            lastname = (TextView) itemView.findViewById(R.id.textViewContactLastname);
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

    public void setContacts(@NonNull List<Contact> notes) {
        dataset = notes;
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
        holder.firstname.setText(contact.getFirstName());
        holder.lastname.setText(contact.getLastName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}