package es.ucm.fdi.pad.android01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class CalculatorAddResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate iniciado");
        setContentView(R.layout.activity_calculator_add_result);

        TextView textViewResult = findViewById(R.id.textViewResult);
        Button buttonBack = findViewById(R.id.buttonBack);

        double result = getIntent().getDoubleExtra("RESULT", 0.0);
        Log.i(TAG, "Resultado recibido del Intent: " + result);

        DecimalFormat df = new DecimalFormat("0.#####");
        textViewResult.setText(df.format(result));

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Volviendo a MainActivity");
                finish();
            }
        });
    }
}