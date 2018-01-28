package com.ariful.roomexample;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arasthel.asyncjob.AsyncJob;
import com.ariful.roomexample.database.SampleDatabase;
import com.ariful.roomexample.model.College;
import com.ariful.roomexample.model.University;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView result;
    EditText id;
    EditText name;
    EditText universisty;
    Button save;
    Button update;
    Button delete;
    Button show;
    SampleDatabase sampleDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleDatabase=  SampleDatabase.getAppDatabase(this);
id=(EditText)findViewById(R.id.id);
        universisty=(EditText)findViewById(R.id.universisty);
        result=(TextView) findViewById(R.id.result);
        name=(EditText)findViewById(R.id.name);
        save=(Button) findViewById(R.id.save);
        update=(Button) findViewById(R.id.update);
        delete=(Button) findViewById(R.id.delete);
        show=(Button) findViewById(R.id.show);
        save.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        show.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.save:
                offlineData(1);
                break;
            case  R.id.update:
                offlineData(2);

                break;
            case  R.id.delete:
                offlineData(3);

                break;
            case  R.id.show:
                offlineData(4);

                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sampleDatabase.destroyInstance();
    }

    private void offlineData(final int type )
    {
        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        // Do some background work
                        try {
                            Gson gson=new Gson();
                            //Thread.sleep(1000);
                            University university=new University();
                            if(type==1) {
                                 university = new University();
                                university.setName(universisty.getText().toString().trim());
                                College college = new College();
                                college.setId(Integer.parseInt(id.getText().toString()));
                                college.setName(name.getText().toString().trim());
                                university.setCollege(college);

                                //Now access all the methods defined in DaoAccess with sampleDatabase object
                                sampleDatabase.daoAccess().insertOnlySingleRecord(university);
                            } else if(type==2)
                            {
                                university.setSlNo(1);
                                university.setName(universisty.getText().toString().trim());
                                sampleDatabase.daoAccess().updateRecord(university);
                                //Log.e("universities",""+gson.toJson(universities));
                            }
                            else if(type==3)
                            {
                                university = new University();
                                university.setName(universisty.getText().toString().trim());
                                College college = new College();
                                college.setId(Integer.parseInt(id.getText().toString()));
                                college.setName(name.getText().toString().trim());
                                university.setCollege(college);
                                sampleDatabase.daoAccess().deleteRecord(university);
                            }
                            else if(type==4)
                            {
                                List<University>universities=sampleDatabase.daoAccess().fetchAllData();
                                int total=sampleDatabase.daoAccess().countUsers();
                                result.setText(total+"\n"+gson.toJson(universities));

                                Log.e("universities",""+gson.toJson(universities));
                            }


                            //To update only name of university, change it and pass the object along with the primary key value.


                            //  List<University>universities=

                            //To delete this record set the primary key and this will delete the record matching that primary key value.
                            // university = new University();
                            // university.setSlNo(1);
                            // sampleDatabase.daoAccess().deleteRecord(university);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        Toast.makeText(getApplicationContext(), "Result was: " + result, Toast.LENGTH_SHORT).show();
                    }
                }).create().start();
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            University university = new University();
            university.setName("MyUniversity");

            College college = new College();
            college.setId(1);
            college.setName("MyCollege");

            university.setCollege(college);

            //Now access all the methods defined in DaoAccess with sampleDatabase object
            sampleDatabase.daoAccess().insertOnlySingleRecord(university);

            //To update only name of university, change it and pass the object along with the primary key value.
            university.setSlNo(1);
            university.setName("ABCUniversity");
            sampleDatabase.daoAccess().updateRecord(university);
            //  List<University>universities=

            //To delete this record set the primary key and this will delete the record matching that primary key value.
            university = new University();
            university.setSlNo(1);
            sampleDatabase.daoAccess().deleteRecord(university);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
    }
}
