package id.csie.ase.ro.photoalbum;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class MetadataActivity extends AppCompatActivity {
    private ImageView image;
    private TextView filename;
    private TextView width;
    private TextView height;
    private Button button;
    private int initialHeight;
    private int initialWidth;
    private String initialPath;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_metadata);

        image = findViewById(R.id.imageView);
        filename = findViewById(R.id.filename);
        width = findViewById(R.id.width);
        height = findViewById(R.id.height);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                CharSequence filenameText = filename.getText().toString();
                if (!Objects.equals(initialPath, filenameText)) {
                    intent.putExtra("filename", filenameText);
                }
                String heightVal = height.getText().toString();
                if (initialHeight !=  Integer.parseInt(heightVal)){
                    intent.putExtra("height", heightVal);
                }
                String widthVal = width.getText().toString();
                if (initialWidth !=  Integer.parseInt(widthVal)){
                    intent.putExtra("width", widthVal);
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            image.setImageResource(bundle.getInt("Image"));

            int resourceId = bundle.getInt("Image");
            Resources resources = this.getResources();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            File externalFile = null;
            try {
                externalFile = createExternalStoragePrivateFile(resourceId, resources.getResourceEntryName(resourceId));
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing file " + resources.getResourceName(resourceId) + " to external storage.", e);
            }
            BitmapFactory.decodeFile(externalFile.getAbsolutePath(), options);
            int imageHeight = options.outHeight;
            initialHeight = imageHeight;
            int imageWidth = options.outWidth;
            initialWidth = imageWidth;
            initialPath = externalFile.getAbsolutePath();
            filename.setText(externalFile.getAbsolutePath());

            width.setText(Integer.toString(imageWidth));
            height.setText(Integer.toString(imageHeight));
        }
    }



    private File createExternalStoragePrivateFile(int resourceId, String filename) throws IOException {
        File file = new File(getExternalFilesDir(null), filename.concat(".jpg"));

        InputStream is = getResources().openRawResource(resourceId);
        OutputStream os = new FileOutputStream(file);
        byte[] data = new byte[is.available()];
        is.read(data);
        os.write(data);
        is.close();
        os.close();
        return file;
    }

    void deleteExternalStoragePrivateFile(String filename) {
        File file = new File(getExternalFilesDir(null), filename.concat(".jpg"));
        if (file != null) {
            file.delete();
        }
    }

    boolean hasExternalStoragePrivateFile(String filename) {
        File file = new File(getExternalFilesDir(null),  filename.concat(".jpg"));
        if (file != null) {
            return file.exists();
        }
        return false;
    }
}
