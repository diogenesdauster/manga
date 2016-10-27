package br.com.dauster.manga3.Http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.dauster.manga3.Adapter.MangaTypeAdapter;
import br.com.dauster.manga3.Model.Manga;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MangaHttp {


    public static final String API_MANGA_LIST   ="https://doodle-manga-scraper.p.mashape.com/mangareader.net?cover=1";
    public static final String API_MANGA_SEARCH ="https://doodle-manga-scraper.p.mashape.com/mangareader.net/search?cover=1&l=10&q=%s";
    public static final String API_MANGA_ID     ="https://doodle-manga-scraper.p.mashape.com/mangareader.net/manga/%s";
    public static final String API_TOKEN = "TMTqnGSYxKmshenTZsI67Z1UBzkcp1TllXwjsncQSYl8DWs0Cw";

    // Lista de Mangas
    //curl --get 'https://doodle-manga-scraper.p.mashape.com/mangareader.net?cover=1'
    //siteid      STRING mangareader.net
    //cover       NUMBER 1 (imagem 1 SIM )
    //info        STRING 1 (sobre  1 SIM)

    // Busca de Manga por Nome
    //curl --get 'https://doodle-manga-scraper.p.mashape.com/mangareader.net/search?cover=1&g=0&info=0&l=10'
    //siteid STRING mangareader.net
    //cover  NUMBER 1 Change value to 1 to include cover data
    //g      STRING 0 (genres)
    //info   NUMBER 0 Change value to 1 to include cover data
    //l      STRING 10 limit
    //q      STRING optional query

    // Busca de Manga por Id
    //curl --get 'https://doodle-manga-scraper.p.mashape.com/mangareader.net/manga/naruto/'
    //siteid      STRING mangareader.net
    //mangaid     STRING naruto

    // Busca Capitulos por Id
    //curl --get 'https://doodle-manga-scraper.p.mashape.com/mangareader.net/manga/naruto/1'
    //siteid      STRING mangareader.net
    //mangaid     STRING naruto
    //chapterid   NUMBER 1


    public static List<Manga> getListMangas(){

        // cria lista vazia que será alimentada com retorno do JSON
        List<Manga> Mangas = new ArrayList<>();
        // variavel que recebera o retorno da requisiçao da internet
        Response response = null;

        try{

            response = onMangaResquet(API_MANGA_LIST); // recebe o retorno da internet
            String json = response.body().string();    // pega o retorno da internet
            JSONArray jsonArray = new JSONArray(json); // pega a lista de objetos
            String jsonList = jsonArray.toString();    // converte a lista de objeto em string

            Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(Manga.class,
                    new MangaTypeAdapter()).create(); // objeto gson para parse

            Manga[] mangasArray = gson.fromJson(jsonList, Manga[].class); // cria uma lista de objetos com base num lista
            Mangas.addAll(Arrays.asList(mangasArray)); // adiciona os objetos criados a lista de mangas

            /*
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonManga = jsonArray.getJSONObject(i);
                Manga m = new Manga();
                m.setMangaId(jsonManga.optString("mangaId"));
                m.setName(jsonManga.optString("name"));
                m.setCover(jsonManga.optString("cover"));
                Mangas.add(m);
            }
            */


        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return Mangas;
    }


    public static List<Manga> searchManga(String name){
        // cria lista vazia que será alimentada com retorno do JSON
        List<Manga> Mangas = new ArrayList<>();
        // variavel que recebera o retorno da requisiçao da internet
        Response response = null;

        try{
            String url = String.format(API_MANGA_SEARCH, name);
            response = onMangaResquet(url); // recebe o retorno da internet
            String json = response.body().string();    // pega o retorno da internet
            JSONArray jsonArray = new JSONArray(json); // pega a lista de objetos
            String jsonList = jsonArray.toString();    // converte a lista de objeto em string

            Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(Manga.class,
                    new MangaTypeAdapter()).create(); // objeto gson para parse

            Manga[] mangasArray = gson.fromJson(jsonList, Manga[].class); // cria uma lista de objetos com base num lista
            Mangas.addAll(Arrays.asList(mangasArray)); // adiciona os objetos criados a lista de mangas


        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return Mangas;
    }



    public static Manga searchMangaById(String id){
        // cria lista vazia que será alimentada com retorno do JSON
        Manga manga = null;
        // variavel que recebera o retorno da requisiçao da internet
        Response response = null;

        try{
            String url = String.format(API_MANGA_ID, id);
            response = onMangaResquet(url); // recebe o retorno da internet
            String json = response.body().string();    // pega o retorno da internet
            //JSONArray jsonArray = new JSONArray(json); // pega a lista de objetos
            //String jsonList = jsonArray.toString();    // converte a lista de objeto em string

            Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(Manga.class,
                    new MangaTypeAdapter()).create(); // objeto gson para parse

            manga = gson.fromJson(json, Manga.class); // cria uma lista de objetos com base num lista

        }catch (IOException e){
            e.printStackTrace();
        }


        return manga;
    }


    private static Response onMangaResquet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient(); // criar uma client para conexao
        Request request = new Request.Builder().url(url) // cria uma requisicao passando a url e parametros adicionais
                .addHeader("X-Mashape-Key",API_TOKEN)
                .addHeader("Accept","text/plain")
                .build();
        return client.newCall(request).execute(); // executa a requisicao e retorna
    }



}
