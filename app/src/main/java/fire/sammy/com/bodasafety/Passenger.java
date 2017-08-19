package fire.sammy.com.bodasafety;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;

public class Passenger extends Activity {
    EditText Dest;
    EditText plate;
    EditText id_email;
    ImageView imageView2;
    private URI mImageUri;
    private File imageFile;
    static  final  int camera_int=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        plate =(EditText)findViewById(R.id.id_plateno);
        Dest =(EditText)findViewById(R.id.dEST);
        id_email =(EditText)findViewById(R.id.id_email);
        Button btn_save=(Button)findViewById(R.id.btn_save);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        Button btn_capture=(Button)findViewById(R.id.btn_capture);
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   Intent i =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file=getFile();
                i.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                startActivityForResult(i,camera_int);


            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String path ="sdcard/boda_app/come_image.jpg ";
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{id_email.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT,"Going to");


                i.putExtra(android.content.Intent.EXTRA_TEXT, "I am going to"+ Dest.getText().toString()+"\n"+
                "with this boda boda"+plate.getText().toString());
                i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/boda_app/come_image.jpg"));
                i.setType("image/png");
                startActivity(Intent.createChooser(i,"S"));

            }
        });

    }
    private File getFile(){
        File folder= new File("sdcard/boda_app");

        if(!folder.exists()){

  folder.mkdir();

        }

        File imageFile=new File(folder,"come_image.jpg");
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path="sdcard/boda_app/come_image.jpg";
        imageView2.setImageDrawable(Drawable.createFromPath(path));
    }

}
