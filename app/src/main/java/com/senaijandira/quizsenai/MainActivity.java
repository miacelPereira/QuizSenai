package com.senaijandira.quizsenai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView txtPergunta, txtRelogio;
    Button btn1, btn2;

    int indexPergunta;

    int qtdeAcertos = 0;

    int qtdeErros = 0;

    //Iniciar música
    MediaPlayer mediaPlayer;

    //Base de perguntas
    String[] perguntas = {"Onde se passa a série Breaking Bad?",
            "Qual a personagem principal da série?",
            "Quantos temporadas tem a série?",
            "Qual era o nome da lanchonete de Gustavo Frig?"
    };

    //Base de respostas
    String[][] respostas = {
            {"Albuquerque", "New York"},
            {"Saul Goodman", "Walter White"},
            {"5 temporadas", "3 temporadas"},
            {"Burguer King", "Los Pollos Hermanos"}

    };

    int[] gabarito = {0, 1, 0, 1};



    //Fim de Jogo
    private void gameOver(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Game Over");
        alert.setMessage("Quantidades de acertos:"+qtdeAcertos+"\nQuantidades de erros:"+qtdeErros);

        alert.setNegativeButton("finalizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); //Fecha o app
            }
        });

        alert.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Reiniciar o jogo
                iniciarJogo();
            }
        });
        alert.create().show();
    }


    //
    private void proximaPergunta(){
        if(indexPergunta == perguntas.length-1){
            //Fim de jogo
            gameOver();
            return;
        }


        indexPergunta++;

        txtPergunta.setText(perguntas[indexPergunta]);

        btn1.setText(respostas[indexPergunta][0]);

        btn2.setText(respostas[indexPergunta][1]);

        btn1.setOnClickListener(clickResposta);

        btn2.setOnClickListener(clickResposta);

        //Iniciando o timer novamente em uma nova pergunta | reiniciar o tempo
        timer.start();
        //Reiniciar a música
        mediaPlayer = MediaPlayer.create(this,R.raw.countdown);
        mediaPlayer.start();
    }



    private void iniciarJogo(){
        indexPergunta = 0;
        qtdeErros = 0;
        qtdeAcertos = 0;

        txtPergunta.setText(perguntas[indexPergunta]);

        btn1.setText(respostas[indexPergunta][0]);

        btn2.setText(respostas[indexPergunta][1]);

        btn1.setOnClickListener(clickResposta);

        btn2.setOnClickListener(clickResposta);

        //Iniciar a contagem do relógio
        timer.start();

        //Iniciar música
        mediaPlayer = MediaPlayer.create(this,R.raw.countdown);
        mediaPlayer.start();
    }


    //Código de relógio, contar 30 segundos
    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            //Atualizando o valor do contador na tela
            txtRelogio.setText(""+ millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            alert("Você demorou demais, lerdão!", "Tempo esgotado!");
            qtdeErros++;
        }
    };

    //Ações do botão
    View.OnClickListener clickResposta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int)v.getTag();

            int respCorreta = gabarito[indexPergunta];

            if(tag == respCorreta){
                alert("Resposta Correta \uD83D\uDE09", "Correto!");
                qtdeAcertos++;
            } else {
                alert("Resposta Errada \uD83D\uDE41", "Errado!");
                qtdeErros++;
            }


            //alert("Resposta Correta", "Correto!");
            //Parar o relógio
            timer.cancel();
            mediaPlayer.stop();
        }
    };


    //função para Alert
    private void alert(String msg, String titulo){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(titulo);
        alert.setMessage(msg);

        //Não deixar o usuario  fechar o alert clicando fora
        alert.setCancelable(false);
        alert.setPositiveButton("Próximo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                proximaPergunta();
            }
        });

        alert.create().show();
    }


    //A instanciação deve ocorrer aqui
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Criar a txtPergunta
        txtPergunta = findViewById(R.id.txtPergunta);

        //Contador
        txtRelogio = findViewById(R.id.txtRelogio);

        //Botões de resposta
        btn1 = findViewById(R.id.btn1);;
        btn1.setTag(0);

        btn2 = findViewById(R.id.btn2);;
        btn2.setTag(1); //Relacionando o o botão ao número 1

        iniciarJogo();

    }
}
