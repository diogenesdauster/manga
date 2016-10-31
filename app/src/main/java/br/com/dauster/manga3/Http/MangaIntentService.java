package br.com.dauster.manga3.Http;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.database.DataContract;

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


//            Cursor cursor = getBaseContext().getContentResolver().query(
//                    DataContract.MangaContract.CONTENT_URI,
//                    DataContract.MangaContract.COLUMNS_LIST_MAIN, null, null, null);

//            if (cursor.getCount() == 0) {

                Intent it = new Intent(SINCRONIZAR); // cria uma intent para sincronizar
                bcM = LocalBroadcastManager.getInstance(this); // cria broadcast para enviar quando terminar
                List<Manga> mangaList = MangaHttp.getListMangas(); // efetua a requisição no servidor

                if (mangaList.size() <= 0) {
                    sendMessage(false); // enviar broadcast dizendo que falhou
                    return;
                }

                // faz a inserção de registro no banco de dados
                handleActionManga(mangaList);

            }else{
                sendMessage(true);// enviar broadcast dizendo que funcinou
            }
//        }
    }

    private void handleActionManga(List<Manga> mangas) {


        for (Manga manga : mangas) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataContract.MangaContract.COLUMN_HREF, manga.getHref());
            contentValues.put(DataContract.MangaContract.COLUMN_NAME, manga.getName());
            contentValues.put(DataContract.MangaContract.COLUMN_COVER, manga.getCover());

            getBaseContext().getContentResolver().insert(
                    DataContract.MangaContract.CONTENT_URI, contentValues);

        }

        sendMessage(true);// enviar broadcast dizendo que funcinou

    }

    // funcao que cria uma mensagem de broadcast
    private void sendMessage(Boolean message) {
        Intent it = new Intent(SINCRONIZAR);
        bcM = LocalBroadcastManager.getInstance(this);
        it.putExtra(SUCESSO, message);
        bcM.sendBroadcast(it);
    }

}
