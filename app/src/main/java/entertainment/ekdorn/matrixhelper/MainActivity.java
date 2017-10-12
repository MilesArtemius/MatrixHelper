package entertainment.ekdorn.matrixhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    Button creative_button;
    Button crt2Button;
    Button crt3Button;

    EditText customWidth;
    EditText customHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-8903017894899762~8178001139"); //ca-app-pub-8903017894899762/4701790554 //ca-app-pub-3940256099942544/6300978111

        creative_button = (Button) findViewById(R.id.creative_button);
        crt2Button = (Button) findViewById(R.id.crt2button);
        crt3Button = (Button) findViewById(R.id.crt3button);

        customWidth = (EditText) findViewById(R.id.custom_matrix_width);
        customHeight = (EditText) findViewById(R.id.custom_matrix_height);

        creative_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!customWidth.getText().toString().equals("")) && (!customHeight.getText().toString().equals(""))) {
                    startDrawing(Integer.parseInt(customWidth.getText().toString()), Integer.parseInt(customHeight.getText().toString()));
                } else {
                    Toast.makeText(MainActivity.this, "You must specify matrix dimensions", Toast.LENGTH_SHORT).show();
                }
            }
        });
        crt2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDrawing(2, 2);
            }
        });
        crt3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDrawing(3, 3);
            }
        });
    }

    private void startDrawing(int width, int height) {
        Intent intent = new Intent(this, MatrixActivity.class);
        intent.putExtra("WIDTH", width);
        intent.putExtra("HEIGHT", height);
        startActivity(intent);
    }
}
