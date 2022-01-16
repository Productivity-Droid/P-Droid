package com.techov8.p_droid.FOCUS_TREE.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.gms.ads.AdView;
import com.techov8.p_droid.FOCUS_TREE.db.AppDatabase;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Skill;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Task;
import com.techov8.p_droid.FOCUS_TREE.ui.AllSkillsActivity;
import com.techov8.p_droid.FOCUS_TREE.ui.SkillActivity;
import com.techov8.p_droid.FOCUS_TREE.ui.SkillInfo;
import com.techov8.p_droid.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import static com.techov8.p_droid.FOCUS_TREE.ui.AllSkillsActivity.customAdapter;
import static com.techov8.p_droid.FOCUS_TREE.ui.AllSkillsActivity.refreshitem;

public class ForestAdapter extends RecyclerView.Adapter<ForestAdapter.ViewHolder> {



    private Context context;
    private ArrayList<Skill> skill;
    private int preselectedposition = -1;
    Dialog editSkillDialog;








    public ForestAdapter(@NonNull Context context, ArrayList<Skill> skill) {

        this.context = context;
        this.skill = skill;


        //this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.skill_adapter, parent, false);



        return new ForestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ForestAdapter.ViewHolder holder, int position) {
        //  Gson gson = new Gson();


        // Gson gson = new Gson();
        // String skillAsString = gson.toJson(skill.get(position));

        // skill = gson.fromJson(Skill.class);
        SimpleDateFormat dateFormat;
        SimpleDateFormat timeFormat;
        dateFormat = new SimpleDateFormat("hh:mm:ss' 'dd/MM/yyyy");
        // timeFormat = new SimpleDateFormat("hh:mm:ss");

        //////////////////////////////

        ////////////////////////////

        int seconds = (int)skill.get(position).getTimeSpent()/(1000);
        int h = seconds/3600;
        int rem = seconds%3600;
        int m = rem/60;
        rem = rem%60;
        int s = rem;
        String hr = Integer.toString(h), min = Integer.toString(m), sec = Integer.toString(s);
        if(h<=9) hr = "0" + Integer.toString(h);
        if(m<=9) min = "0" + Integer.toString(m);
        if(s<=9) sec = "0" + Integer.toString(s);

        //////////////////////////////


      holder.TasktextView.setText(skill.get(position).getTitle());

        holder.StarttextView.setText("Started On " + dateFormat.format(skill.get(position).getDateCreated()));
        holder.LasttextView.setText("Completed: " +hr + ":" + min + ":" + sec);
       // holder.LasttextView.setText("Last Active at : " + dateFormat.format(skill.get(position).getDateUpdated()));



        holder.AllSkillsOptions(position);



    }


    @Override
    public int getItemCount() {
        return skill.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {
        TextView TasktextView;
        TextView StarttextView;
        TextView TaskProgresstextView;
        TextView LasttextView;
        TextView GotextView;
        ImageView more;
        private LinearLayout optioncontainer;

        TextView recordsText, removeText, editTextt;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            TasktextView = itemView.findViewById(R.id.taskname);
            StarttextView = itemView.findViewById(R.id.startdate);
           // TaskProgresstextView = itemView.findViewById(R.id.completed);
            LasttextView = itemView.findViewById(R.id.lastactive);
            GotextView = itemView.findViewById(R.id.go);
            more = itemView.findViewById(R.id.moree);
            optioncontainer = itemView.findViewById(R.id.option_container);
            recordsText = itemView.findViewById(R.id.recordstextview);
            removeText = itemView.findViewById(R.id.removeetextView);
            editTextt = itemView.findViewById(R.id.editttextView);


        }


        public void AllSkillsOptions(int position) {

            //convert skill to string
            Gson gson = new Gson();
            String skillAsString = gson.toJson(skill.get(position));

            AppDatabase db;
            List<Task> tasks;
            Skill skilll;
            skilll = gson.fromJson(skillAsString, Skill.class);


            db = Room.databaseBuilder(context, AppDatabase.class, "20hours")
                    .allowMainThreadQueries()
                    .build();
            tasks = db.taskDao().getAll(skill.get(position).getId());

///////////////////////////////////////////////////////////////////////////////////////////////
            editTextt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                    final View mView = LayoutInflater.from(context).inflate(R.layout.edit_skill_layout, null);
                    final EditText edtEditSkillTitle = mView.findViewById(R.id.edtEditSkillTitle);
                    CheckBox cbEditSkillTwentyHr = mView.findViewById(R.id.cbEditSkillTwentyHr);
                    Button btnEditSkillUpdate = mView.findViewById(R.id.btnEditSkillUpdate);
                    Button btnEditSkillCancel = mView.findViewById(R.id.btnEditSkillCancel);

                    edtEditSkillTitle.setText(skilll.getTitle());

                    btnEditSkillUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String title = edtEditSkillTitle.getText().toString();
                            if (title.length() > 0) {
                                skilll.setTitle(title);
                                db.skillDao().update(skilll);
                                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                customAdapter.notifyDataSetChanged();
                                refreshitem(preselectedposition, preselectedposition);
                                preselectedposition = -1;
                                //////////////////////////////bugggg
                                Intent intent = new Intent(context, AllSkillsActivity.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                                editSkillDialog.cancel();
                            } else
                                Toast.makeText(context, "Title is empty", Toast.LENGTH_SHORT).show();
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            removeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //builder.setTitle("Are you sure you want to delete ?");
                    builder.setMessage("Deleting the task will delete all related trees from forest.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.skillDao().delete(skilll);
                                    ////////  refresh(position);buggggggggggggggggg
                                    customAdapter.notifyDataSetChanged();
                                    Intent intent = new Intent(context, AllSkillsActivity.class);
                                    context.startActivity(intent);
                                    ((Activity)context).finish();



                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
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

//////////////////////////////////////////////////////////////////////////////////////////////////
            recordsText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SkillInfo.class);
                    //convert skill to string

                    String skillAsString = gson.toJson(skill.get(position));
                    intent.putExtra("skill", skillAsString);
                    intent.putExtra("skillname", skill.get(position).getTitle());
                    refreshitem(preselectedposition, preselectedposition);
                    preselectedposition = -1;
                    itemView.getContext().startActivity(intent);
                }
            });

            GotextView.setEnabled(true);


            GotextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    optioncontainer.setVisibility(View.GONE);
                    refreshitem(preselectedposition, preselectedposition);
                    preselectedposition = -1;

                    Intent intent = new Intent(context, SkillActivity.class);

                    //convert skill to string
                    /// Gson gson = new Gson();
                    // String skillAsString = gson.toJson(skill.get(position));

                    //pass string to intent
                    intent.putExtra("skill", skillAsString);
                    context.startActivity(intent);

                }
            });
//////////////////////////////////////////////////////////////////////////////////////////////////
            optioncontainer.setVisibility(View.GONE);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optioncontainer.setVisibility(View.VISIBLE);
                    GotextView.setEnabled(false);

                    refreshitem(preselectedposition, preselectedposition);

                    preselectedposition = position;/////////////////////////////BUGGGGGGGGGGGGGGGGGGGG

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshitem(preselectedposition, preselectedposition);
                    GotextView.setEnabled(true);
                    preselectedposition = -1;

                }
            });
        }
    }




}

