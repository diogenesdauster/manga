package br.com.dauster.manga3.Adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.Model.Manga;


public class MangaTypeAdapter extends TypeAdapter<Manga> {
    @Override
    public void write(JsonWriter out, Manga value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginObject();

        out.beginArray().name("artist");
        for (String Artist : value.getArtist()) {
            out.value(Artist);
        }
        out.endArray();

        out.beginArray().name("author");
        for (String Author : value.getAuthor()) {
            out.value(Author);
        }
        out.endArray();

        out.beginArray().name("chapters");
        for (Chapter chapter : value.getChapters()) {
            out.beginObject();
            out.name("chapterId").value(chapter.getChapterId());
            out.name("name").value(chapter.getName());
            out.endObject();
        }
        out.endArray();

        out.name("cover").value(value.getCover());

        out.beginArray().name("genres");
        for (String Genres : value.getGenres()) {
            out.value(Genres);
        }
        out.endArray();
        out.name("href").value(value.getHref());
        out.name("info").value(value.getInfo());
        out.name("lastUpdate").value(value.getLastUpdate());
        out.name("name").value(value.getName());
        out.name("status").value(value.getStatus());
        out.name("yearOfRelease").value(value.getYearOfRelease());

    }


    @Override
    public Manga read(JsonReader in) throws IOException {
        final Manga mManga = new Manga();

        List<String> mArtists = new ArrayList<String>();
        List<String> mAuthors = new ArrayList<String>();
        List<Chapter> mChapters = new ArrayList<Chapter>();
        List<String> mGenres = new ArrayList<String>();

        in.beginObject();
        while (in.hasNext()) {
            String jsonTag = in.nextName();
            switch (jsonTag) {
                case "artist":
                    in.beginArray();
                    while (in.hasNext()) {
                        mArtists.add(in.nextString());
                    }
                    in.endArray();
                    mManga.setArtist(mArtists);
                    break;
                case "author":
                    in.beginArray();
                    while (in.hasNext()) {
                        mAuthors.add(in.nextString());
                    }
                    in.endArray();
                    mManga.setAuthor(mAuthors);
                    break;
                case "genres":
                    in.beginArray();
                    while (in.hasNext()) {
                        mGenres.add(in.nextString());
                    }
                    in.endArray();
                    mManga.setGenres(mGenres);
                    break;
                case "chapters":
                    in.beginArray();
                    while (in.hasNext()) {
                        mChapters.add(readChapter(in));
                    }
                    in.endArray();
                    mManga.setAuthor(mAuthors);
                    break;
                case "cover":
                    mManga.setCover(in.nextString());
                    break;
                case "href":
                    mManga.setHref(in.nextString());
                    break;
                case "mangaId":
                    mManga.setHref(in.nextString());
                    break;
                case "info":
                    mManga.setInfo(in.nextString());
                    break;
                case "lastUpdate":
                    mManga.setLastUpdate(in.nextString());
                    break;
                case "name":
                    mManga.setName(in.nextString());
                    break;
                case "status":
                    mManga.setStatus(in.nextString());
                    break;
                case "yearOfRelease":
                    if(in.peek() != JsonToken.NULL) {
                        mManga.setYearOfRelease(in.nextLong());
                    }else{
                        in.nextNull();
                    }
                    break;
                default:
                    in.skipValue();
            }


        }
        in.endObject();
        return mManga;
    }


    public Chapter readChapter(JsonReader reader) throws IOException {
        long chapterId = -1;
        String name = null;
        Chapter chapter = new Chapter();

        reader.beginObject();
        while (reader.hasNext()) {
            String attr = reader.nextName();
            if (attr.equals("chapterId")) {
                chapterId = reader.nextLong();
            } else if (attr.equals("name")) {
                name = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        chapter.setChapterId(chapterId);
        chapter.setName(name);

        return chapter;

    }



}
