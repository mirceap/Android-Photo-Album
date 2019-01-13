package id.csie.ase.ro.photoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {
    private ImageView image;
    private String title;

    public DetailActivity() {
        System.out.println("Detail activity created!");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = findViewById(R.id.detailImageView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            image.setImageResource(bundle.getInt("Image"));
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent detailed = new Intent(DetailActivity.class, DetailActivity.class);
//                    detailed.putExtra("Image", _list[viewHolder.getAdapterPosition()]);
//                    mContext.startActivity(detailed);
                }
            });
        }
    }
}
