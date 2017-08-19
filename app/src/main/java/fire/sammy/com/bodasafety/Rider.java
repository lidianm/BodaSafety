package fire.sammy.com.bodasafety;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Rider extends AppCompatActivity {

    Button send, capture;
    Bitmap thumbnail;
    File pic;
    ImageView mage;
    EditText address, subject, emailtext;
    static  final  int camera_int=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send=(Button) findViewById(R.id.btn_save);
        address=(EditText) findViewById(R.id.id_destination);
        subject=(EditText) findViewById(R.id.id_trustee);
        emailtext=(EditText) findViewById(R.id.id_number);
        mage=(ImageView)findViewById(R.id.imageView2) ;
      capture=(Button)findViewById(R.id.btn_capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file=getFile();
                i.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                startActivityForResult(i,camera_int);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailtext.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT,"Going to");


                i.putExtra(android.content.Intent.EXTRA_TEXT, "I am "+ subject.getText().toString()+"\n"+
                        "am going to"+address.getText().toString());
                i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/boda_app/riding.jpg"));
                i.setType("image/png");
                startActivity(Intent.createChooser(i,"choose a mailing choice "));
            }
        });










    }

    private File getFile() {

        File folder= new File("sdcard/boda_app");

        if(!folder.exists()){

            folder.mkdir();

        }

        File imageFile=new File(folder,"riding.jpg");
        return imageFile;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path="sdcard/boda_app/riding.jpg";
        mage.setImageDrawable(Drawable.createFromPath(path));
    }
}