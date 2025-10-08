package es.ucm.fdi.pad.android01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private static final String MainActivityTAG = "CALCULATOR_APP_LOG";
    private EditText numero1, numero2;
    private Button suma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(MainActivityTAG, "onCreate: Actividad inicializada");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        listenerBoton();
    }

    private void initViews(){
        numero1 = findViewById(R.id.editNumero1);
        numero2 = findViewById(R.id.editNumero2);
        suma = findViewById(R.id.suma);

        Log.d(MainActivityTAG, "initViews: Vistas encontradas e inicializadas");

    }
    private void listenerBoton(){
        suma.setOnClickListener(v -> {

            String num1=numero1.getText().toString();
            String num2=numero2.getText().toString();

            if(num1.isEmpty() && num2.isEmpty()){
                Toast.makeText(this, "Por favor, ingresa ambos números", Toast.LENGTH_SHORT).show();
                Log.w(MainActivityTAG, "listenerBoton: Campos de números vacíos. No se realizará la suma");
            }
            if(!num1.isEmpty() || !num2.isEmpty()){
                try{
                    double n1 = Double.parseDouble(num1);
                    double n2 = Double.parseDouble(num2);

                    CalculatorAdd calculator = new CalculatorAdd();
                    double resultado = calculator.addNumbers(n1,n2);

                    Intent intent = new Intent(MainActivity.this, CalculatorAddResultActivity.class);

                    intent.putExtra("resultado", resultado);
                    startActivity(intent);
                }catch (NumberFormatException e) {
                    Log.e(MainActivityTAG, "listenerBoton: Error de formato de número. Revisar entrada.", e);
                    Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}