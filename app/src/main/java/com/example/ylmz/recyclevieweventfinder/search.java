package com.example.ylmz.recyclevieweventfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity  {
    private String catg;
    private String date;
    private String key;
    private String location;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.categories:
                    Intent intent2=new Intent(search.this,Categories.class);
                    startActivity(intent2);

                    break;
                case R.id.events:
                    Intent intent=new Intent(search.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searching:


                    break;

            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView navig=(BottomNavigationView)findViewById(R.id.navigation3);
        navig.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu=navig.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);

        Spinner cats=findViewById(R.id.cats);
        List<String> catgs=new ArrayList<>();



        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.spinner_item,getResources().getStringArray(R.array.categories));

        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cats.setAdapter(adp);

         cats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 String itemval=parent.getItemAtPosition(position).toString();
                 catg=itemval.replaceAll(" ", "+");


             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        Spinner dates=findViewById(R.id.dates);

        ArrayAdapter<String> adp2=new ArrayAdapter<String>(this,R.layout.spinner_item,getResources().getStringArray(R.array.dates));

        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dates.setAdapter(adp2);

        dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemval=parent.getItemAtPosition(position).toString();

                date=itemval.replaceAll(" ", "+");



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText search=(EditText)findViewById(R.id.editText);
        final EditText search2=(EditText)findViewById(R.id.editText2);

        Log.v("button1",search.getText().toString());





        Button SearchButton=(Button)findViewById(R.id.searchbutton);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                location=search.getText().toString();
                key=search2.getText().toString();
                Log.v("button",catg);
                Log.v("button1",date);
                Log.v("button2",location);
                Log.v("button3",key);

                Intent appInfo = new Intent(search.this, MainActivity.class);
                appInfo.putExtra("catg",catg);
                appInfo.putExtra("date",date);
                appInfo.putExtra("location",location);
                appInfo.putExtra("key",key);
                startActivity(appInfo);

            }
        });



    }



}
