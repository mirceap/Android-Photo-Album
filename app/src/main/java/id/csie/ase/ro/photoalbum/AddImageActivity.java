package id.csie.ase.ro.photoalbum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class AddImageActivity extends AppCompatActivity {
    private static final ArrayList<String> serviceProviders = new ArrayList<String>() {{ add("Unsplash"); add("Other"); }};
    private static final int CAMERA_REQUEST = 1888;
    private ArrayAdapter<String> adapter;
    private static final int SERVICE_REQUEST = 1889;
    private ImageView image;
    private Bitmap imageResult;
    private Button getImageButton;
    private Button openCameraButton;
    private Button okButton;
    private Spinner serviceProviderSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photos_activity);

        getImageButton = findViewById(R.id.getImage);
        openCameraButton = findViewById(R.id.openCamera);
        okButton = findViewById(R.id.buttonOK);
        serviceProviderSpinner = findViewById(R.id.spinner);
        image = findViewById(R.id.imageViewAdd);
        image.setVisibility(View.INVISIBLE);

        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, serviceProviders);
        serviceProviderSpinner.setAdapter(adapter);

        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        getImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageServiceResult imageWorker = new ImageServiceResult();
                imageWorker.execute();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (imageResult != null){
                    intent.putExtra("ResultImage", imageResult);
                    setResult(RESULT_OK, intent);
                    finishActivity(2);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( data.getExtras() == null){
            Toast.makeText(this, "Image not saved!", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        imageResult = thumbnail;
        image.setImageBitmap(thumbnail);
        image.setVisibility(View.VISIBLE);
        saveImage(thumbnail);
        Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                 getBaseContext().getExternalFilesDir(null).getAbsolutePath() );
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e) {
            Log.d("TAG", "File NOT Saved::--->" + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }


    class ImageServiceResult extends AsyncTask<String, Integer, ImageServiceResult> {

        @Override
        protected ImageServiceResult doInBackground(String... strings) {
            String imageTemplateURL = "https://api.unsplash.com/photos/random/?client_id=12ed2b8802919a862ae886048781c5776641f9fba0e43ab2b5a682cbbd8c6276";
            ImageServiceResult imageWorkerResult;
            String downloadSmallImageLink = null;
            try {
                downloadSmallImageLink = getRandomImage(imageTemplateURL);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Bitmap img = null;
            if (downloadSmallImageLink != null){
                try {
                    img = downloadImage(downloadSmallImageLink);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (img != null){
                    imageResult = img;
                }
            }
            return null;
        }

        private String getRandomImage(String address) throws IOException, JSONException {
            URL url = new URL(address);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            connection.disconnect();
            is.close();
            String response = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(response);
            return (String) ((JSONObject) jsonObject.get("urls")).get("regular");
        }

        private Bitmap downloadImage(String link) throws IOException {
            URL url = new URL(link);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            is.close();
            return bitmap;
        }

        @Override
            protected void onPostExecute(ImageServiceResult imageServiceResult) {
                image.setImageBitmap(imageResult);
                image.setVisibility(View.VISIBLE);
                //super.onPostExecute(imageServiceResult);
        }
    }
}
