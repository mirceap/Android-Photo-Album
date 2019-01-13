package id.csie.ase.ro.photoalbum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    int[] _list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(manager);

        _list = new int[] {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3,
                R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7,
                R.drawable.image_8, R.drawable.image_9, R.drawable.image_10};

        CustomAdapter adapter = new CustomAdapter(MainActivity.this, _list);
        recyclerView.setAdapter(adapter);
    }
}
