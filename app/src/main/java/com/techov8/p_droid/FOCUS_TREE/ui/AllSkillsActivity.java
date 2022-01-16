package com.techov8.p_droid.FOCUS_TREE.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.techov8.p_droid.FOCUS_TREE.Adapter.ForestAdapter;
import com.techov8.p_droid.FOCUS_TREE.db.AppDatabase;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Skill;
import com.techov8.p_droid.R;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static android.view.View.VISIBLE;


public class AllSkillsActivity extends AppCompatActivity {



    private RecyclerView gvSkills;

    private TextView Notask;
    private ArrayList<Skill> skills;
   // TextView  ibtnAbout, ibtnSettings, ibtnNew;
    //ImageView ibtnForest;
    AppDatabase db;
   public static ForestAdapter customAdapter;
    AlertDialog newSkillDialog;

    //Dialog editSkillDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_skills);




        setTitle("All Task");
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







        gvSkills = findViewById(R.id.gvSkills);
        Notask=findViewById(R.id.NoTasktextView);
        //ibtnForest = findViewById(R.id.ibtnForest);
       // ibtnNew = findViewById(R.id.ibtnNew);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        gvSkills.setLayoutManager(layoutManager);


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "20hours")
                .allowMainThreadQueries()
                .build();

        skills = (ArrayList<Skill>) db.skillDao().getAll();

try {

    if (skills.size() == 0) {
        Notask.setVisibility(VISIBLE);
        gvSkills.setVisibility(View.GONE);


    }
}catch (Exception e) {Log.e("test","err"+e);}

        customAdapter = new ForestAdapter(this, skills);
        gvSkills.setAdapter(customAdapter);









        /*gvSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), SkillActivity.class);

                //convert skill to string
                Gson gson = new Gson();
                String skillAsString = gson.toJson(skills.get(i));

                //pass string to intent
                intent.putExtra("skill", skillAsString);
                startActivity(intent);
            }
        });*/


       /* ibtnForest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Forest.class);
                startActivity(intent);
            }
        });

        */

      /*  ibtnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AllSkillsActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.new_skill_layout, null);
                final EditText edtNewSkill = mView.findViewById(R.id.edtNewSkill);
                CheckBox cbTwentyHr = mView.findViewById(R.id.cbTwentyHr);
                Button btnAdd = mView.findViewById(R.id.btnAdd);
                Button btnNewSkillCancel = mView.findViewById(R.id.btnNewSkillCancel);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = edtNewSkill.getText().toString();
                        if(title.length() > 0){
                            Skill skill = new Skill(title, 0, System.currentTimeMillis(), System.currentTimeMillis(), false);
                            db.skillDao().insertAll(skill);
                            skills = (ArrayList<Skill>) db.skillDao().getAll();
                            customAdapter.notifyDataSetChanged();
                            newSkillDialog.cancel();

                        }else{
                            Toast.makeText(getApplicationContext(), "Add a title", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnNewSkillCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtNewSkill.setText("");
                        newSkillDialog.cancel();
                    }
                });
                mBuilder.setView(mView);
                newSkillDialog = mBuilder.create();
                newSkillDialog.show();
            }

        });

       */


    }


    public void gotoNewSkill(View view) {
//        Intent in = new Intent(getApplicationContext(), NewSkillActivity.class);
//        startActivity(in);

    }
    /*class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return skills.size();
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
            view = getLayoutInflater().inflate(R.layout.skill_adapter, null);
            TextView tvSkillName = view.findViewById(R.id.tvSCTitle);


            tvSkillName.setText(skills.get(i).getTitle());


            //calculate percentage
            // x % of 20 hour = skills.get(i).getMillisDone()



            return view;
        }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
    }

     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.forest){
            openForestActivity();
        }else if (item.getItemId() ==android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else if (item.getItemId() ==R.id.addbtnn)
        {
            AddTASK();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddTASK() {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AllSkillsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.new_skill_layout, null);
        final EditText edtNewSkill = mView.findViewById(R.id.edtNewSkill);
        CheckBox cbTwentyHr = mView.findViewById(R.id.cbTwentyHr);
        Button btnAdd = mView.findViewById(R.id.btnAdd);
        Button btnNewSkillCancel = mView.findViewById(R.id.btnNewSkillCancel);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtNewSkill.getText().toString();
                if(title.length() > 0){
                    Skill skill = new Skill(title, 0, System.currentTimeMillis(), System.currentTimeMillis(), false);
                    db.skillDao().insertAll(skill);
                    skills = (ArrayList<Skill>) db.skillDao().getAll();
                    customAdapter.notifyDataSetChanged();
                    newSkillDialog.cancel();
                    Toast.makeText(AllSkillsActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AllSkillsActivity.this, AllSkillsActivity.class);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(getApplicationContext(), "Add a title", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnNewSkillCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtNewSkill.setText("");
                newSkillDialog.cancel();
            }
        });
        mBuilder.setView(mView);
        newSkillDialog = mBuilder.create();
        newSkillDialog.show();
    }

    private void openForestActivity(){
        Intent intent = new Intent(getApplicationContext(), Forest.class);
        startActivity(intent);
    }


    public  static void refreshitem(int select,int deselect)
    {
        customAdapter.notifyItemChanged(deselect);
        customAdapter.notifyItemChanged(select);

    }



}

