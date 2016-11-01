package br.com.dauster.manga3.Http;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.database.DataUtil;

public class MangaIntentService extends IntentService {

    public static final String SINCRONIZAR = "sincronizar";
    public static final String SUCESSO = "sucesso";

    LocalBroadcastManager bcM;

    public MangaIntentService() {
        super("MangaIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getExtras().getBoolean(SINCRONIZAR)) {

            List<Manga> mangaList = MangaHttp.getListMangas(); // efetua a requisição no servidor

            if (mangaList.size() <= 0) {
                sendMessage(false); // enviar broadcast dizendo que falhou
                return;
            }

            // faz a inserção de registro no banco de dados
            DataUtil.handleListManga(getBaseContext().getContentResolver(),mangaList);
            sendMessage(false);// enviar broadcast dizendo que funcinou

        } else {
            sendMessage(false);// enviar broadcast dizendo que funcinou
        }
    }


    // funcao que cria uma mensagem de broadcast
    private void sendMessage(Boolean message) {
        Intent it = new Intent(SINCRONIZAR);
        bcM = LocalBroadcastManager.getInstance(this);
        it.putExtra(SUCESSO, message);
        bcM.sendBroadcast(it);
    }

}
