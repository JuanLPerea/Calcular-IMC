package com.calculoimc;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // TODO ES RECOMENDABLE DECLARAR LOS COMPONENTES DE LA VISTA SOLO DONDE SE UTILIZA. EN ESTE CASO LO DEJAMOS AQUI PORQUE ES UN EJEMPLO MUY SENCILLO
    //  EditText alturaET;
    //  EditText pesoET;
    private TextView imcTV;
    private TextView complexionTV;
    private ImageView imagen;
    private NumberPicker alturaPK;
    private NumberPicker pesoPK;
    private static final String URL_BUSCADOR = "https://es.wikipedia.org/wiki/%C3%8Dndice_de_masa_corporal";


    @Override
    protected void onCreate(Bundle saquito) {
        super.onCreate(saquito);
        setContentView(R.layout.activity_main);


        // Ocultamos el teclado virtual para ver la pantalla completa
        hideKeyboard(this);

        //   alturaET = findViewById(R.id.alturaET);
        //    pesoET = findViewById(R.id.pesoET);
        imcTV = findViewById(R.id.imcTV);
        complexionTV = findViewById(R.id.complexionET);
        imagen = findViewById(R.id.imageView);
        alturaPK = findViewById(R.id.alturaPicker);
        pesoPK = findViewById(R.id.pesoPicker);


        alturaPK.setMinValue(0);
        alturaPK.setMaxValue(250);
        pesoPK.setMinValue(0);
        pesoPK.setMaxValue(300);


        //
        // Gestionar el cambio de orientación de la pantalla
        //
        if (saquito == null) {
            Log.d("MIAPP", "Es la primera vez que se ejecuta o no hay nada");

            alturaPK.setValue(160);
            pesoPK.setValue(70);


        } else {
            Log.d("MIAPP", "Hay cositas guardadas");
            Boolean valor_guardado = saquito.getBoolean("FIRSTRUN");
            int altura_guardada = saquito.getInt("ALTURA");
            int peso_guardado = saquito.getInt("PESO");

            pesoPK.setValue(peso_guardado);
            alturaPK.setValue(altura_guardada);

            calcularImc();

            Log.d("MIAPP", "Valor: " + valor_guardado);
        }


        alturaPK.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calcularImc();
            }
        });


        pesoPK.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calcularImc();
            }
        });


    }


    public void calcularImc() {

        // Comprobamos que haya datos en los campos
        //  if (!alturaET.getText().toString().equals("") && !pesoET.getText().toString().equals("")) {
        //    float altura = miParseFloat(alturaET.getText().toString());
        //    float peso = miParseFloat(pesoET.getText().toString());

        int altura_int = alturaPK.getValue();
        int peso = pesoPK.getValue();

        float altura = (float) altura_int / 100;


        Log.d("LOGIMC", altura + " - " + peso);

        int imc = (int) (peso / (altura * altura));

        imcTV.setText("" + imc);

        String complexion = "";

        // Imprimimos por pantalla el resultado
        if (imc >= 31) {
            complexion = getString(R.string.obeso);
            imagen.setImageResource(R.drawable.ofelia);
        } else {
            if (imc >= 25) {
                complexion = getString(R.string.sobrepeso);
                imagen.setImageResource(R.drawable.super_png);
            } else {
                if (imc >= 18) {
                    complexion = getString(R.string.ideal);
                    imagen.setImageResource(R.drawable.filemon);
                } else {
                    if (imc >= 16) {
                        complexion = getString(R.string.delgado);
                        imagen.setImageResource(R.drawable.mortadelo);
                    } else {
                        if (imc < 16) {
                            complexion = getString(R.string.desnutrido);
                            imagen.setImageResource(R.drawable.sacarino);
                        }
                    }
                }
            }
        }


        complexionTV.setText(complexion);




    }

    private float miParseFloat(String dato) {


        String parseDato = dato.replace(',', '.');

        float f = Float.parseFloat(parseDato);

        return f;

    }

    @Override
    public void onSaveInstanceState(Bundle saquito) {
        super.onSaveInstanceState(saquito);
        // En este momento podemos guardar cosas

        saquito.putBoolean("FIRSTRUN", true);
        saquito.putInt("ALTURA", alturaPK.getValue());
        saquito.putInt("PESO", pesoPK.getValue());


        Log.d("LOGIMC", "Saved: " + alturaPK.getValue() + " " + pesoPK.getValue());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_info:

                //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_BUSCADOR));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://es.wikipedia.org/wiki/%C3%8Dndice_de_masa_corporal"));
                startActivity(intent);

                /*
                // AQUI SE ASEGURA QUE HAYA UNA ACTIVIDAD CANDIDATA PARA LA ACCION QUE QUIERA REALIZAR
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                */

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
