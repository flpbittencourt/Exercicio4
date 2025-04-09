package com.example.exercicio4;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnUpload;
    private EditText edtText;
    private TextView txtFinal;
    private SeekBar skbSizeFont;
    private TextView txtTamanho;
    private CheckBox checkBoxNegrito;
    private CheckBox checkBoxItalico;
    private CheckBox checkBoxSublinhado;
    private RadioGroup radioGroupCores;
    private RadioButton radioButtonVermelho;
    private RadioButton radioButtonVerde;
    private RadioButton radioButtonAzul;
    private Button btnLimparFormatacao;

    private int corPadrao = Color.BLACK; //cor padrão escolhida como Preta
    private int cor;
    private float tamanhoFonteAtual = 10;
    private float tamanhoFontePadrao = 10;

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

        //Inicializando views
        btnUpload = findViewById(R.id.btnUpload);
        edtText = findViewById(R.id.edtText);
        txtFinal = findViewById(R.id.textFinal);
        skbSizeFont = findViewById(R.id.skbSizeFont);
        txtTamanho = findViewById(R.id.txtTamanho);
        checkBoxNegrito = findViewById(R.id.checkBox);
        checkBoxItalico = findViewById(R.id.checkBox2);
        checkBoxSublinhado = findViewById(R.id.checkBox3);
        radioGroupCores = findViewById(R.id.radioGroupCores);
        radioButtonVermelho = findViewById(R.id.radioButtonVermelho);
        radioButtonVerde = findViewById(R.id.radioButtonVerde);
        radioButtonAzul = findViewById(R.id.radioButtonAzul);
        btnLimparFormatacao = findViewById(R.id.btnLimparFormatacao);

        txtTamanho.setText(tamanhoFonteAtual + "sp");

        //Image Button Upload event Listener
        btnUpload.setOnClickListener(this);

        //SeekBar event Listener
        skbSizeFont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tamanhoFonteAtual = progress;
                txtFinal.setTextSize(tamanhoFonteAtual);
                txtTamanho.setText(progress + "sp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Event Listener's para os checkboxes de Negrito, Itálico e Sublinhado
        checkBoxNegrito.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarEstilos());
        checkBoxItalico.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarEstilos());
        checkBoxSublinhado.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarEstilos());

        //EventListener para o RadioGroup de aplicação de cores
        radioGroupCores.setOnCheckedChangeListener((group, checkedId) -> aplicarCor());

        //EventListener para o botão Limpar Formatação para resetar formatação da TextView txtFinal e dos demais campos de seleção de formatação
        btnLimparFormatacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetarFormulario();
            }
        });

    }

    private void resetarFormulario() {
        txtFinal.setText("");
        txtFinal.setTextSize(tamanhoFontePadrao);
        txtFinal.setTextColor(corPadrao);

        edtText.setText("");

        txtTamanho.setText(tamanhoFontePadrao+"sp");

        skbSizeFont.setProgress((int) tamanhoFontePadrao);

        checkBoxNegrito.setChecked(false);
        checkBoxItalico.setChecked(false);
        checkBoxSublinhado.setChecked(false);

        radioGroupCores.clearCheck();
    }

    private void aplicarCor() {

        if(radioGroupCores.getCheckedRadioButtonId() == radioButtonVermelho.getId()){
            cor = Color.RED;
        }
        if(radioGroupCores.getCheckedRadioButtonId() == radioButtonVerde.getId()){
            cor = Color.GREEN;
        }
        if(radioGroupCores.getCheckedRadioButtonId() == radioButtonAzul.getId()){
            cor = Color.BLUE;
        }

        txtFinal.setTextColor(cor);
    }

    private void aplicarEstilos() {
        SpannableString spannableString = new SpannableString(txtFinal.getText());
        int estilo = Typeface.NORMAL;
        boolean temEstilo = false;

        if (checkBoxNegrito.isChecked()) {
            estilo |= Typeface.BOLD;
            temEstilo = true;
        }
        if (checkBoxItalico.isChecked()) {
            estilo |= Typeface.ITALIC;
            temEstilo = true;
        }

        if (temEstilo) {
            spannableString.setSpan(new StyleSpan(estilo), 0, spannableString.length(), 0);
        } else {
            Object[] spansToRemove = spannableString.getSpans(0, spannableString.length(), StyleSpan.class);
            for (Object span : spansToRemove) {
                spannableString.removeSpan(span);
            }
        }

        // Lógica para aplicar e desaplicar sublinhado
        Object[] underlineSpansToRemove = spannableString.getSpans(0, spannableString.length(), UnderlineSpan.class);
        for (Object span : underlineSpansToRemove) {
            spannableString.removeSpan(span);
        }
        if (checkBoxSublinhado.isChecked()) {
            spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        }

        txtFinal.setText(spannableString);
        txtFinal.setTextSize(tamanhoFonteAtual);
    }


    @Override
    public void onClick(View v) {
        txtFinal.setText(edtText.getText());
    }
}