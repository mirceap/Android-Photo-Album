package id.csie.ase.ro.photoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private ImageView image;
    private RatingBar ratingBar;
    private int imageLink;
    private float rating;

    public DetailActivity() {
        System.out.println("Detail activity created!");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = findViewById(R.id.detailImageView);
        ratingBar = findViewById(R.id.ratingBar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            imageLink = bundle.getInt("Image");
            rating = bundle.getFloat("Rating");
            ratingBar.setRating(rating);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    //viewHolder.updateRating(v);
                }
            });
            image.setImageResource(imageLink);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailed = new Intent(getApplicationContext(), MetadataActivity.class);
                    detailed.putExtra("Image", imageLink);
                    startActivityForResult(detailed, 1);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            if (data.getStringExtra("filename") != null){
                Toast.makeText(this, "Path to file changed!", Toast.LENGTH_LONG).show();
            }
            if (data.getStringExtra("width") != null){
                Toast.makeText(this, "Width new value: " + data.getStringExtra("width"), Toast.LENGTH_LONG).show();
            }
            if (data.getStringExtra("height") != null){
                Toast.makeText(this, "Height new value: " + data.getStringExtra("height"), Toast.LENGTH_LONG).show();
            }
        }
    }
}
