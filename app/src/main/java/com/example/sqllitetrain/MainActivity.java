package com.example.sqllitetrain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.SplittableRandom;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Button btnSubmit,btnView,btnUpdate,btnDelete;
    EditText etName,etSurname,etMarks,etId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);


        btnSubmit = findViewById(R.id.button);
        etName = findViewById(R.id.textView);
        etSurname = findViewById(R.id.textView2);
        etMarks = findViewById(R.id.textView3);
        btnView = findViewById(R.id.button2);
        etId = findViewById(R.id.editTextTextPersonName);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(etName.getText().toString(),etSurname.getText().toString(),etMarks.getText().toString());
                if(isInserted == true)
                {
                    Toast.makeText(MainActivity.this,"Data insertion unsuccesful",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Data inserted succesfully",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cur = myDb.getAllData();

                if(cur.getCount() == 0)
                {
                    Toast.makeText(MainActivity.this,"Empty",Toast.LENGTH_LONG).show();
                }

                StringBuffer buffer = new StringBuffer();
                while (cur.moveToNext())
                {
                    buffer.append("ID : " + cur.getString(0)+ "\n");
                    buffer.append("Name : " + cur.getString(1)+ "\n");
                    buffer.append("Surname : " + cur.getString(2)+ "\n");
                    buffer.append("Marks : " + cur.getString(3)+ "\n");
                }

                showMessage("Data",buffer.toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean is = myDb.updateData(etId.getText().toString(),etName.getText().toString(),etSurname.getText().toString(),etMarks.getText().toString());
                if(is == true)
                {
                    Toast.makeText(MainActivity.this,"Data Updated ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer dele = myDb.deleteData(etId.getText().toString());
                if(dele > 0 )
                {
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Data Not Deleted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessage(String title , String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}