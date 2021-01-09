package com.hometasks.view.ui.profile;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hometasks.R;
import com.hometasks.controller.JsonResponse;
import com.hometasks.controller.OctetResponse;
import com.hometasks.controller.Service;
import com.hometasks.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private List<User> users;
    private RecyclerView profilesView;
    private ProfileAdapter profileAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        users = new ArrayList<>();
        profilesView = root.findViewById(R.id.profile_list);
        profileAdapter = new ProfileAdapter(users,this.getContext());
        profilesView.setAdapter(profileAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        profilesView.setLayoutManager(linearLayoutManager);

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, User> task = new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                JsonResponse respUser = Service.getObject(User.class, User.class, Service.login);
                User user = respUser.getEntity(User.class);
                OctetResponse picture = Service.getPicture(User.class, Service.login);
                user.setPicture(picture.getValue());
                return user;
            }
            protected void onPostExecute(User user){
                users.add(user);
                profileAdapter.notifyDataSetChanged();

            }

        };

        task.execute();

        final SearchView searchView = root.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final String input = searchView.getQuery().toString();
                @SuppressLint("StaticFieldLeak")
                AsyncTask<Void, Void, JsonResponse> task = new AsyncTask<Void, Void, JsonResponse>() {
                    @Override
                    protected JsonResponse doInBackground(Void... voids) {
                        return Service.getObject(User.class,User.class,input);
//                        User user = service1.getUser(input);
//                        User[] users = new Usuario[1];
//                        if(user != null){
//                            users[0] = user;
//                        }else{
//                            users = service1.getUsers(input);
//                        }
                    }
                    //User[]
                    @Override
                    protected void onPostExecute(JsonResponse result){
                        super.onPostExecute(result);
                        profilesView.removeAllViews();
                        users.clear();
                        User user = result.getEntity(User.class);
                        users.add(user);
                        profileAdapter.notifyDataSetChanged();
//                        User[] entities = result.getEntity(User[].class);
//                        for (User u : entities){
//                            if(!u.getIdUser().equals(Service.login))
//                                users.add(u);
//                            System.out.println(u);
//                        }
                    }
                };

                task.execute();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if((users.size() == 1) && (users.get(0).getIdUser().equals(Service.login))){
            @SuppressLint("StaticFieldLeak")
            AsyncTask<Void, Void, JsonResponse> task = new AsyncTask<Void, Void, JsonResponse>() {
                @Override
                protected JsonResponse doInBackground(Void... voids) {
                    return Service.getObject(User.class,User.class,Service.login);
                }
                protected void onPostExecute(JsonResponse result){
                    users.clear();
                    User entity = result.getEntity(User.class);
                    users.add(entity);
                    System.out.println(entity.toString());
                    profileAdapter.notifyDataSetChanged();
                }
            };

            task.execute();

        }

    }
}