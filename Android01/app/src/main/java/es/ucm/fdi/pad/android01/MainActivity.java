package es.ucm.fdi.pad.android01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText editTextNum1;
    private EditText editTextNum2;
    private CalculatorAdd calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate iniciado");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        calculator = new CalculatorAdd();
        Log.d(TAG, "Objeto CalculatorAdd inicializado");

        editTextNum1 = findViewById(R.id.editTextNumberDecimal);
        editTextNum2 = findViewById(R.id.editTextNumberDecimal2);

        Button buttonAdd = findViewById(R.id.button);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Botón SUMAR presionado");
                performAddition();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void performAddition() {
        String num1Str = editTextNum1.getText().toString().trim();
        String num2Str = editTextNum2.getText().toString().trim();

        Log.d(TAG, "Parseo correcto: num1Str=" + num1Str + ", num2Str=" + num2Str);

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa ambos números", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            Log.d(TAG, "Parseo correcto: num1=" + num1 + ", num2=" + num2);

            double result = calculator.addNumbers(num1, num2);
            Log.i(TAG, "Resultado de la suma: " + result);

            Intent intent = new Intent(this, CalculatorAddResultActivity.class);
            intent.putExtra("RESULT", result);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Log.e(TAG, "Error al parsear los números: " + e.getMessage(), e);
            Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
        }
    }
}