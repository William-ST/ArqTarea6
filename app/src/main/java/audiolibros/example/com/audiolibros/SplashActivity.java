package audiolibros.example.com.audiolibros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this, MainActivity.class));

        //ImageView imageView = findViewById(R.id.iv_background);


        //BlurAnimation blurAnimation = new BlurAnimation(imageView, imageView.get);
    }
}
