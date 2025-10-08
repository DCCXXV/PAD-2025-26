package es.ucm.fdi.pad.android01;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CalculatorAddResultActivity extends AppCompatActivity {

    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculator_add_result_activity);

        tvResultado = findViewById(R.id.resultado);

        // Recuperamos el número enviado desde MainActivity
        double resultado = getIntent().getDoubleExtra("resultado", 0);
        tvResultado.setText("" + resultado);
    }
}
