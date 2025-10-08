package es.ucm.fdi.pad.android01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText numero1, numero2;
    private Button suma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Log.d("Main activity", "initViews: Vistas inicializadas");

    }
    private void listenerBoton(){
        suma.setOnClickListener(v -> {

            String num1=numero1.getText().toString();
            String num2=numero2.getText().toString();

            if(!num1.isEmpty() || !num2.isEmpty()){
                double n1 = Double.parseDouble(num1);
                double n2 = Double.parseDouble(num2);

                CalculatorAdd calculator = new CalculatorAdd();
                double resultado = calculator.addNumbers(n1,n2);



                Intent intent = new Intent(MainActivity.this, CalculatorAddResultActivity.class);

                intent.putExtra("resultado", resultado);
                startActivity(intent);
            }

        });
    }
}