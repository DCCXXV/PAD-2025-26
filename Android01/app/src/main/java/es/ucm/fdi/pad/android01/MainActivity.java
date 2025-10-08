package es.ucm.fdi.pad.android01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private TextView Text1;
    private EditText editTextNumberDecimal1;
    private EditText editTextNumberDecimal2;
    private Button suma;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Text1 = findViewById(R.id.Text1);
        editTextNumberDecimal1 = findViewById(R.id.editTextNumberDecimal);
        editTextNumberDecimal2 = findViewById(R.id.editTextNumberDecimal2);
        suma = findViewById(R.id.button1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        suma.setOnClickListener(v -> {
            String s1 = editTextNumberDecimal1.getText().toString();
            String s2 = editTextNumberDecimal2.getText().toString();

            CalculatorAdd suma = new CalculatorAdd();

            if (!s1.isEmpty() && !s2.isEmpty()) {
                double num1 = Double.parseDouble(s1);
                double num2 = Double.parseDouble(s2);
                double resultado = suma.addNumbers(num1, num2);

                // Paso el resultado a la segunda Activity
                Intent intent = new Intent(MainActivity.this, CalculatorAddResultActivity.class);
                intent.putExtra("resultado", resultado);
                startActivity(intent);
            } else {
                Text1.setText("Debes poner 2 num");
            }
        });
    }
}