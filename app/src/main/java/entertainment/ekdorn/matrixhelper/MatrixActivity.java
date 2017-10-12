package entertainment.ekdorn.matrixhelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MatrixActivity extends AppCompatActivity {

    int matrixWidth;
    int matrixHeight;

    TableLayout matrix;
    double [][] grMatrix;

    TextView significator;
    double sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        Intent intent = getIntent();
        matrixWidth = intent.getIntExtra("WIDTH", 1);
        matrixHeight = intent.getIntExtra("HEIGHT", 1);

        grMatrix = new double [matrixHeight][matrixWidth];

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        significator = (TextView) findViewById(R.id.significator);
        significator.setSingleLine();

        matrix = (TableLayout) findViewById(R.id.matrix_container);
        matrix.setWeightSum(1);
        fill(matrix);

        sign = countSignificator(grMatrix);
        significator.setText(Double.toString(sign));
    }

    private void fill(TableLayout empty) {
        for (int i = 0; i < matrixHeight; i++) {
            TableRow tar = new TableRow(this);
            tar.setWeightSum(1);

            for (int j = 0; j < matrixWidth; j++) {
                EditText field = new EditText(this);
                field.setText("0");
                field.setBackground(null);
                field.setSingleLine();
                field.setMaxWidth(field.getWidth());
                field.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                final int I = i;
                final int J = j;
                field.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            grMatrix[I][J] = Integer.parseInt(s.toString());
                            sign = countSignificator(grMatrix);
                            significator.setText(Double.toString(sign));
                        } catch (Exception e) {
                            significator.setText("Some fields are empty");
                        }
                    }
                });

                grMatrix[i][j] = 0;

                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, (1 / (float) matrixWidth));
                params.setMargins(0, 0, 0, 0);
                tar.addView(field, params);
            }

            TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,  (1 / (float) matrixHeight));
            params.setMargins(0, 0, 0, 0);
            empty.addView(tar, params);
        }
    }

    private double countSignificator(double [][] matrix) {
        double significator = 0;

        if (matrix.length == 2) {
            significator = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        } else {
            for (int i = 0; i < matrix[0].length; i++) {
                significator -= Math.pow(-1, i) * matrix[0][i] * countSignificator(countSubMatrix(matrix, i));
            }
        }

        return significator;
    }

    private double[][] countSubMatrix(double [][] matrix, int column) {
        double [][] submatrix = new double [matrix.length - 1][matrix[0].length - 1];
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                if (j < column) {
                    submatrix[i][j] = matrix[i+1][j];
                } else if (j >= column) {
                    submatrix[i][j] = matrix[i+1][j+1];
                }
            }
        }
        return submatrix;
    }
}
