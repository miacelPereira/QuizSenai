package com.senaijandira.quizsenai;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class InicioActivity extends Activity {
   /* Animação */
   ImageView imgQuiz;
    /* Colocando musica */
    MediaPlayer mediaPlayer;
    /* Animação de quando tocar */
    Animation shakeAnim;
    // Tela de inicio
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*  Passando a tela criada pelo xml */
        setContentView(R.layout.activity_inicio);

        /* Tocando a música */
        mediaPlayer = MediaPlayer.create(this,R.raw.game_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        /*Iniciando Animação*/
        imgQuiz = findViewById(R.id.imgQuiz);
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);

        imgQuiz.startAnimation(fadein);

        /* Animação do toque */
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        imgQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQuiz.startAnimation(shakeAnim);
            }
        });
    }

    //Pausando a música quando o app está em segundo plano
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    //Voltando a música quando o usuário volta a tela
    @Override
    protected void onResume() {
        super.onResume();
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    /* Abrindo outra tela pelo botão */
    public void iniciarJogo(View v){
        /* Parando a música da abertura */
        mediaPlayer.stop();
       /* Passando a tela para a variavel */
       Intent intent = new Intent(this, MainActivity.class);

       /* Abrindo a tela */
       startActivity(intent);

       /* Tirando a tela da pilha de nevegação */
       finish();

        /* Torrada (meio aleatorio)HEHEHE */
        /* Toast.makeText(this, "HEHEHE", Toast.LENGTH_SHORT).show();*/
    }
}
