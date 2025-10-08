package es.ucm.fdi.pad.android01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.DecimalFormat;


public class CalculatorAddResultActivity extends AppCompatActivity {
    private static final String ResultActivityTAG = "CALCULATOR_RESULT_LOG";
    private TextView resultado;
    private Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(ResultActivityTAG, "onCreate: Actividad de resultados iniciada");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator_add_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultado = findViewById(R.id.textView);
        double result = getIntent().getDoubleExtra("resultado",0);

        Log.d(ResultActivityTAG, "onCreate: Resultado recibido de Intent: " + result);

        if (result == 0 && !getIntent().hasExtra("resultado")) {
            Log.w(ResultActivityTAG, "onCreate: El Intent no contenía el extra 'resultado' o era 0. Usando valor por defecto");
        }

        DecimalFormat df = new DecimalFormat("0.#####");
        resultado.setText(df.format(result));

        volver = findViewById(R.id.button3);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ResultActivityTAG, "onClick: Volviendo a MainActivity");
                finish();
            }
        });
    }
}