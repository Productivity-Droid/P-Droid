package com.techov8.p_droid.FOCUS_TREE.ui;

import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.room.Room;

import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.techov8.p_droid.FOCUS_TREE.db.AppDatabase;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Skill;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Task;
import com.techov8.p_droid.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class SkillInfo extends AppCompatActivity {


    Skill skill;
    List<Task> tasks;
    TextView  NoRecordTexView;
            //TextView tvSkillPercentage, tvSkillDateCreated, tvSkillDateUpdated;
    ListView lvActivities;
    String skillAsString;
    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;
    AppDatabase db;
    //ImageButton ibtnDeleteSkill, ibtnEditSkill;
    Dialog editSkillDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_info);


        String skillname = getIntent().getStringExtra("skillname");

        setTitle(skillname);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#30D5C8"));

        //Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);



        //get reference to controls
        //tvSkillInfoTitle = findViewById(R.id.tvSCTitle);
       /*tvSkillPercentage = findViewById(R.id.tvSkillPercentage);
        tvSkillDateCreated = findViewById(R.id.tvSkillDateCreated);
        tvSkillDateUpdated = findViewById(R.id.tvSkillDateUpdated);*/
        lvActivities = findViewById(R.id.lvActivities);
      NoRecordTexView= findViewById(R.id.NoRecordtextView);
       // ibtnEditSkill = findViewById(R.id.ibtnEditSkill);


        Gson gson = new Gson();
        skillAsString = getIntent().getStringExtra("skill");
        skill = gson.fromJson(skillAsString, Skill.class);
        //tvSkillInfoTitle.setText(skill.getTitle());
        dateFormat = new SimpleDateFormat("dd/MM/yyyy'  'hh:mm:ss");
        timeFormat = new SimpleDateFormat("hh:mm:ss");
        /*tvSkillPercentage.setText("Completed: " + Long.toString(skill.getTimeSpent()/(1000*60*60)) + " hours");
        tvSkillDateCreated.setText("Date Started: " + dateFormat.format(skill.getDateCreated()));
        tvSkillDateUpdated.setText("Last Active at : " + dateFormat.format(skill.getDateUpdated()));
        */

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "20hours")
                .allowMainThreadQueries()
                .build();
        tasks = db.taskDao().getAll(skill.getId());

        //activities

        if(tasks.size()==0)
        {
            lvActivities.setVisibility(View.GONE);
            NoRecordTexView.setVisibility(View.VISIBLE);
        }
        final CustomAdapter customAdapter = new CustomAdapter();
        lvActivities.setAdapter(customAdapter);

       /* ibtnDeleteSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SkillInfo.this);
                //builder.setTitle("Are you sure you want to delete ?");
                builder.setMessage("Deleting the task will delete all related trees from forest.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.skillDao().delete(skill);
                            Intent intent = new Intent(getApplicationContext(), AllSkillsActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
            }
        });

        ibtnEditSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SkillInfo.this);
                final View mView = getLayoutInflater().inflate(R.layout.edit_skill_layout, null);
                final EditText edtEditSkillTitle = mView.findViewById(R.id.edtEditSkillTitle);
                CheckBox cbEditSkillTwentyHr = mView.findViewById(R.id.cbEditSkillTwentyHr);
                Button btnEditSkillUpdate = mView.findViewById(R.id.btnEditSkillUpdate);
                Button btnEditSkillCancel = mView.findViewById(R.id.btnEditSkillCancel);

                edtEditSkillTitle.setText(skill.getTitle());

                btnEditSkillUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = edtEditSkillTitle.getText().toString();
                        if(title.length() > 0){
                            skill.setTitle(title);
                            db.skillDao().update(skill);
                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                            editSkillDialog.cancel();
                        }else Toast.makeText(getApplicationContext(), "Title is empty", Toast.LENGTH_SHORT).show();
                    }
                });

                btnEditSkillCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtEditSkillTitle.setText("");
                        editSkillDialog.cancel();
                    }
                });

                mBuilder.setView(mView);
                editSkillDialog = mBuilder.create();
                editSkillDialog.show();
            }
        });
        */


    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public Object getItem(int i) {
            return R.layout.skill_adapter;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.skilltask_adapter, null);
            TextView tvTaskDate = view.findViewById(R.id.tvTaskDate);
            TextView tvTaskDone = view.findViewById(R.id.tvTaskDone);
            TextView tvTaskTarget = view.findViewById(R.id.tvTaskTarget);

            Task task = tasks.get(i);

            ///////////////////for target

            int seconds = (int)task.getTargetTime()/(1000);
            int h = seconds/3600;
            int rem = seconds%3600;
            int m = rem/60;
            rem = rem%60;
            int s = rem;
            String hr = Integer.toString(h), min = Integer.toString(m),sec = Integer.toString(s);

            if(h<=9) hr = "0" + Integer.toString(h);
            if(m<=9) min = "0" + Integer.toString(m);
            if(s<=9) sec = "0" + Integer.toString(s);


            ////////////////////////for time spent

            int secondss = (int)(task.getTimeSpent())/(1000);
            int hs = secondss/3600;
            int rems = secondss%3600;
            int ms = rems/60;
            rems = rems%60;
            int ss = rems;
            String hrs = Integer.toString(hs), mins = Integer.toString(ms),secs = Integer.toString(ss);

            if(hs<=9) hrs = "0" + Integer.toString(hs);
            if(ms<=9) mins = "0" + Integer.toString(ms);
            if(ss<=9) secs = "0" + Integer.toString(ss);

            ////////////////////////////////


            tvTaskDate.setText(dateFormat.format(task.getDateCreated()));

            if(task.getTargetTime() != task.getTimeSpent()) tvTaskDate.setTextColor(Color.RED);

            tvTaskDone.setText("Done: " +  hrs + ":" + mins+":"+secs);
            tvTaskTarget.setText("Target: " + hr + ":" + min+":"+sec);

            return view;
        }
    }



    /////////////////////////////////back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
