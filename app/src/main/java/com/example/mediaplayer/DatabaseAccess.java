package com.example.mediaplayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */

    public List<List<String>> getSingerList(){
        List<List<String>> singerList = new ArrayList<>();
        Cursor cursor = null;
        /////////////first we have to open the database to get the database instance
        open();
//        cursor = openHelper.getWritableDatabase().rawQuery("SELECT DISTINCT eng_singer_name,amh_singer_name FROM lyrics_table", null);
        cursor = database.rawQuery("SELECT DISTINCT eng_singer_name,amh_singer_name FROM lyrics_table", null);

        if (cursor!=null) {
            int index1 = cursor.getColumnIndex("eng_singer_name");
            int index2 = cursor.getColumnIndex("amh_singer_name");

            while (cursor.moveToNext()) {
                List<String> singleSingerList = new ArrayList<>();
                singleSingerList.add(cursor.getString(index1));
                singleSingerList.add(cursor.getString(index2));

                singerList.add(singleSingerList);
            }
            cursor.close();
        }
        close();
            return singerList;

        }

    public List<String> getAlbumList(String singerName){
        open();
        List<String> albumList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT album_list FROM lyrics_table where eng_singer_name = ? ", new String[]{singerName});
        int index1 = cursor.getColumnIndex("album_list");
        while (cursor.moveToNext()) {
            albumList.add(cursor.getString(index1));
        }
        close();
        return albumList;
    }

    public List<List<String>> getSongAndLyricsList(String singerName,String albumList){
        open();
        List<List<String>> singerList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT  song_list,song_lyrics FROM lyrics_table where eng_singer_name = ? and album_list =?", new String[]{singerName,albumList});

        int index1 = cursor.getColumnIndex("song_list");
        int index2 = cursor.getColumnIndex("song_lyrics");
        while (cursor.moveToNext()){
            List<String> singleSingerList = new ArrayList<>();
            singleSingerList.add(cursor.getString(index1));
            singleSingerList.add(cursor.getString(index2));

            singerList.add(singleSingerList);
        }
        cursor.close();
        close();
        return  singerList;
    }

    public List<List<String>> getQuoteList(){
        open();
        List<List<String>> singerList = new ArrayList<>();
        Cursor cursor1 = database.rawQuery("SELECT DISTINCT AmhCatagory FROM Quotes ", null);
        Cursor cursor2 = database.rawQuery("SELECT DISTINCT EngCatagory FROM Quotes ", null);
        int index1 = cursor1.getColumnIndex("AmhCatagory");
        int index2 = cursor2.getColumnIndex("EngCatagory");
        while (cursor1.moveToNext()&&cursor2.moveToNext()) {
            List<String> singleSingerList = new ArrayList<>();
            singleSingerList.add(cursor1.getString(index1));
            singleSingerList.add(cursor2.getString(index2));

            singerList.add(singleSingerList);

        }
        close();
        return singerList;
    }
    public List<String> getDisplayQuoteList(String singerName){
        open();
        List<String> QuoteList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Quote FROM Quotes where AmhCatagory = ? ", new String[]{singerName});
        int index1 = cursor.getColumnIndex("Quote");
        while (cursor.moveToNext()) {
            QuoteList.add(cursor.getString(index1));
        }
        close();
        return QuoteList;
    }
    public String getQuoteOfTheDayList(String quoteId){
        open();
        String quoteOfTheDay = "";
        Cursor cursor = database.rawQuery("SELECT Quote FROM Quotes where id = ? ", new String[]{quoteId});
        int index1 = cursor.getColumnIndex("Quote");
        while (cursor.moveToNext()) {
            quoteOfTheDay = cursor.getString(index1);
        }
        close();
        return quoteOfTheDay;
    }
    public boolean checkIsFav(String song_name){
        open();
        Log.e("song_Name", "checkIsFav: the song Data");
        boolean quoteOfTheDay;
        Cursor cursor = database.rawQuery("SELECT Song_name FROM Favourites where Song_name =?", new String[]{song_name});
//        Log.e("checkcursor", "checkIsFav: the cursor is "+(cursor==null) );
        int index1 = cursor.getColumnIndex("Song_name");

        if (cursor.moveToNext()){
            quoteOfTheDay = true;
//            Log.e("checkcursor", "checkIsFav: the index is "+index1 );
        }
        else {
            quoteOfTheDay = false;

        }
        close();
        return quoteOfTheDay;
    }
    public boolean insertIntoFav(String song_list){
        open();
        String sql = "INSERT INTO FAVOURITES(Singer_name_amh,Song_name,Song_lyrics,Singer_name_eng) select amh_singer_name,song_list,song_lyrics,eng_singer_name FROM lyrics_table where song_list =?";
//        database.rawQuery("INSERT INTO FAVOURITES(Singer_name_amh,Song_name,Song_lyrics,Singer_name_eng) select amh_singer_name,song_list,song_lyrics,eng_singer_name FROM lyrics_table where song_list =?", new String[]{song_list});
//        database.rawQuery("INSERT INTO FAVOURITES(Singer_name_amh,Song_name,Song_lyrics,Singer_name_eng) '"+"'values" +"( select amh_singer_name,song_list,song_lyrics,eng_singer_name FROM lyrics_table where song_list ='ያለ ፡ ምክንያት (Yale Mekniyat)'"), null);
        database.execSQL(sql, new String[]{song_list});
        close();
        return true;
    }

    public void deleteFav(String songData) {
        open();
        String sql = "DELETE FROM FAVOURITES where song_name =?";
        database.execSQL(sql, new String[]{songData});

//        database.rawQuery("DELETE FROM FAVOURITES where song_name =?", new String[]{songData});
        close();

    }
    public List<List<String>> getAllFavList(){
        open();
        List<List<String>> singerList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT  * FROM FAVOURITES",null);
        Log.e("iscursorisnull", "getAllFavList: is cursor null"+(cursor==null) );

        int index1 = cursor.getColumnIndex("Singer_name_amh");
        int index2 = cursor.getColumnIndex("Song_name");
        int index3 = cursor.getColumnIndex("Song_lyrics");
        int index4 = cursor.getColumnIndex("Singer_name_eng");
        while (cursor.moveToNext()){
            List<String> singleSingerList = new ArrayList<>();
            singleSingerList.add(cursor.getString(index1));
            singleSingerList.add(cursor.getString(index2));
            singleSingerList.add(cursor.getString(index3));
            singleSingerList.add(cursor.getString(index4));

            singerList.add(singleSingerList);
        }
        cursor.close();
        close();
        return  singerList;
    }

//    public List<SingerModel>  getEmergency() {
//        List<SingerModel> emerModelList = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT * FROM Emergency", null);
////        cursor.moveToFirst();
////        while (!cursor.isAfterLast()) {
////            list.add(cursor.getString(0));
////            cursor.moveToNext();
////        }
//        int index1 = cursor.getColumnIndex("id");
//        int index2 = cursor.getColumnIndex("contact_name");
//        int index3 = cursor.getColumnIndex("contact_number");
//        while (cursor.moveToNext()){
//            emerModelList.add(new SingerModel(cursor.getString(index2),cursor.getString(index3),"2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//
//        }
//        cursor.close();
//        return emerModelList;
//    }
//    public List<LogModel>  getFavourites() {
//        List<LogModel> favModelList = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT * FROM Favourites", null);
//
//        int index1 = cursor.getColumnIndex("id");
//        int index2 = cursor.getColumnIndex("contact_name");
//        int index3 = cursor.getColumnIndex("contact_number");
//        while (cursor.moveToNext()){
//            favModelList.add(new LogModel(cursor.getString(index2),cursor.getString(index3),"2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//
//        }
//        cursor.close();
//        return favModelList;
//    }

//    public List<SingerModel>  getModelList(String type, List<SingerModel> Modellist) {
//        List<SingerModel> favModelList = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT * FROM "+ type, null);
//
//        int index1 = cursor.getColumnIndex("id");
//        int index2 = cursor.getColumnIndex("contact_name");
//        int index3 = cursor.getColumnIndex("contact_number");
//        int photo =  R.drawable.ic_person1;
//
//        if (type == "Favourites"){
//            photo =  R.drawable.ic_person1;
//        }
//        else if(type=="Emergency"){
//            photo = R.drawable.ic_user;
//        }else if(type=="Important"){
//            photo = R.drawable.ic_user;
//        }
//
//
//        while (cursor.moveToNext()){
//            Modellist.add(new LogModel(cursor.getString(index2),cursor.getString(index3),"2 min ago",photo,R.drawable.ic_call_missed));
//
//        }
//        cursor.close();
//        return Modellist;
//    }
//    public List<LogModel>  getDatas(String type,List<LogModel> list) {
//        List<LogModel> favModelList = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT * FROM '?' ", new String[]{type});
//
//        int index1 = cursor.getColumnIndex("id");
//        int index2 = cursor.getColumnIndex("contact_name");
//        int index3 = cursor.getColumnIndex("contact_number");
//        int photo =  R.drawable.ic_person1;
//        if (type == "favourites"){
//            photo =  R.drawable.ic_person1;
//        }
//        else if(type=="emergency"){
//            photo = R.drawable.ic_user;
//        }
//        while (cursor.moveToNext()){
//            favModelList.add(new LogModel(cursor.getString(index2),cursor.getString(index3),"2 min ago", photo,R.drawable.ic_call_missed));
//
//        }
//        cursor.close();
//        return favModelList;
//    }
//
//    public void removeFav(String favNum){
//
//    }
}