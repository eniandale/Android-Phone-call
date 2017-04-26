package com.example.eniandale.phonecall;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    public final int PERMISSION_REQUEST_CODE = 200;
    private EditText textNumber;

    private final String[] permissionsList = {permission.CALL_PHONE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textNumber = (EditText)findViewById(R.id.textNumber);

        Button bCall = (Button)findViewById(R.id.bCall);

        bCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    realMainActivity();
                }
                else {
                    requestPermission();
                }
            };
       });

    }

    private void realMainActivity(){
        try {
            Uri uri = Uri.parse("tel:" + textNumber.getText().toString());
            startActivity(new Intent(Intent.ACTION_DIAL, uri));
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }




    private boolean checkPermission() {

        boolean isAllPermissionsGranted = true;

        for (String perms: permissionsList) {

            if(ContextCompat.checkSelfPermission(getApplicationContext(), perms) != PackageManager.PERMISSION_GRANTED){
                isAllPermissionsGranted = false;
                break;
            }
        }

        return isAllPermissionsGranted;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, permissionsList, PERMISSION_REQUEST_CODE);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean isAllPermissionsGranted = true;
                    for (int result:grantResults) {
                        if(result != PackageManager.PERMISSION_GRANTED){
                            isAllPermissionsGranted = false;
                            break;
                        }
                    }

                    if (isAllPermissionsGranted) {
                        //do the programm code
                        realMainActivity();
                    }

                    else{
                        //alltext.setText("I need both permissions");
                    }
                    break;
                }
        }
    }
}
