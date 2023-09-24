package com.example.mybulksms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;

import com.example.mybulksms.adapters.GroupListAdapter;
import com.example.mybulksms.domains.Group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupListActivity extends AppCompatActivity {

    private RecyclerView groupRecyclerView;
    private GroupListAdapter adapter;
    private List<Group> groupList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        getSupportActionBar().setTitle("My Groups");
        groupRecyclerView = findViewById(R.id.groupRecyclerView);
        groupList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("MyGroups", MODE_PRIVATE);

        // Retrieve stored groups from SharedPreferences and populate groupList
        retrieveGroupsFromSharedPreferences();

        // Create and set up the RecyclerView and its adapter.
        adapter = new GroupListAdapter(groupList);
        groupRecyclerView.setAdapter(adapter);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create Group Button
        Button createGroupButton = findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateGroupDialog();
            }
        });
    }

    private void showCreateGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_group, null);
        builder.setView(dialogView);

        final EditText groupNameEditText = dialogView.findViewById(R.id.groupNameEditText);
        final EditText groupDescriptionEditText = dialogView.findViewById(R.id.groupDescriptionEditText);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the group name and description
                String groupName = groupNameEditText.getText().toString();
                String groupDescription = groupDescriptionEditText.getText().toString();

                // Create a new Group object and add it to the list
                Group newGroup = new Group(groupName, groupDescription);
                groupList.add(newGroup);
                adapter.notifyItemInserted(groupList.size() - 1);

                // Store the new group in SharedPreferences
                storeGroupInSharedPreferences(newGroup);

                // Dismiss the dialog
                dialog.dismiss();

                // Show a toast message to indicate successful group creation
                Toast.makeText(GroupListActivity.this, "Group created successfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel button clicked, dismiss the dialog
                dialog.dismiss();
            }
        });


         // Set the button style
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black)); // Set text color from resources
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.color.white); // Set background color from resources
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void storeGroupInSharedPreferences(Group group) {
        // Retrieve existing groups from SharedPreferences
        Set<String> existingGroups = sharedPreferences.getStringSet("groups", new HashSet<String>());
        System.out.println(existingGroups);
        // Convert the Group object to a JSON representation (you may use a library like Gson)
        String groupJson = convertGroupToJson(group);

        // Add the new group to the existing set
        existingGroups.add(groupJson);

        // Store the updated set back in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("groups", existingGroups);
        editor.apply();
    }

    private void retrieveGroupsFromSharedPreferences() {
        Set<String> storedGroups = sharedPreferences.getStringSet("groups", new HashSet<String>());
        for (String groupJson : storedGroups) {
            Group group = convertJsonToGroup(groupJson);
            groupList.add(group);
        }
    }

    // You need to implement the conversion logic from Group object to JSON and vice versa
    // You may use a library like Gson for this purpose.
    // Example:
    private String convertGroupToJson(Group group) {
        // Create a Gson instance
        Gson gson = new Gson();

        // Convert the Group object to JSON format
        String groupJson = gson.toJson(group);

        return groupJson;
    }


    private Group convertJsonToGroup(String groupJson) {
        // Create a Gson instance
        Gson gson = new Gson();

        // Convert the JSON string back to a Group object
        Group group = gson.fromJson(groupJson, Group.class);

        return group;
    }

}
