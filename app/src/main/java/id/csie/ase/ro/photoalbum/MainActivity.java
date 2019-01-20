package id.csie.ase.ro.photoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton button;

    int[] _list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        button = findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailed = new Intent(getApplicationContext(), AddImageActivity.class);
                //detailed.putExtra("Image", imageLink);
                startActivityForResult(detailed, 2);
                // take me to
                // Get Random Photo from ImageService
                // Open camera and "Take Photo"
            }
        });

        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(manager);

        _list = new int[] {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3,
                R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7,
                R.drawable.image_8, R.drawable.image_9, R.drawable.image_10};

        CustomAdapter adapter = new CustomAdapter(MainActivity.this, _list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 &&  resultCode == RESULT_OK){
            
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
