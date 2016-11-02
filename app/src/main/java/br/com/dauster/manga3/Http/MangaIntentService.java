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

            List<Manga> mangaList = MangaApi.getListMangas(); // efetua a requisição no servidor

            if (mangaList.size() <= 0) {
                DataUtil.sendMessage(SINCRONIZAR,false,this); // enviar broadcast dizendo que falhou
                return;
            }

            // faz a inserção de registro no banco de dados
            DataUtil.handleListManga(getBaseContext().getContentResolver(),mangaList);
            DataUtil.sendMessage(SINCRONIZAR,false,this);// enviar broadcast dizendo que funcinou

        } else {
            DataUtil.sendMessage(SINCRONIZAR,false,this);// enviar broadcast dizendo que funcinou
        }
    }


}
