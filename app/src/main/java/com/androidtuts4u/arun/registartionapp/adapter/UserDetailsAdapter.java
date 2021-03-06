package com.androidtuts4u.arun.registartionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtuts4u.arun.registartionapp.R;
import com.androidtuts4u.arun.registartionapp.UpdateActivity;
import com.androidtuts4u.arun.registartionapp.database.UserDatabaseContract.UserDatabase;
import com.androidtuts4u.arun.registartionapp.database.UserDatabaseHelper;
import com.androidtuts4u.arun.registartionapp.model.UserDetails;

import java.util.List;

/**
 * Created by arun on 23-08-2017.
 */

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.UserViewHolder> {

    List<UserDetails> userDetailsList;
    Context context;
    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;

    public UserDetailsAdapter(List<UserDetails> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View iteView = inflater.inflate(R.layout.list_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(iteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {

        UserDetails userDetails = userDetailsList.get(position);
        holder.tvName.setText(userDetails.getName());
        holder.tvAddress.setText(userDetails.getAddress());
        holder.tvPhone.setText(userDetails.getMobileNo());
        holder.tvProfession.setText(userDetails.getProfessiion());
        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserDetails userDetails = userDetailsList.get(position);
                final int userId = userDetails.getUserId();
                dbHelper = new UserDatabaseHelper(context);
                db = dbHelper.getWritableDatabase();
                PopupMenu menu = new PopupMenu(context, holder.ivMenu);

                menu.inflate(R.menu.popup_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                db.delete(UserDatabase.TABLE_NAME,UserDatabase._ID + " = " + userId,null);
                                notifyItemRangeChanged(position,userDetailsList.size());
                                userDetailsList.remove(position);
                                notifyItemRemoved(position);
                                db.close();
                                break;
                            case R.id.update:
                                Intent intent = new Intent(context, UpdateActivity.class);
                                intent.putExtra("USERID", userId);
                                context.startActivity(intent);
                                break;


                        }


                        return false;
                    }
                });
                menu.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return userDetailsList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {


        TextView tvName, tvAddress, tvPhone, tvProfession;
        ImageView ivMenu;

        public UserViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvProfession = (TextView) itemView.findViewById(R.id.tv_profession);
            ivMenu = (ImageView) itemView.findViewById(R.id.iv_menu);
        }


    }
}
