package com.example.mediaplayer;

public class SingerModel {
    private String volumeCounter;
    private String SongName,SongArtistName,SongUrl;
    private String EngName;
    private String AmhName;
    private String SongTitle;

    public String getVolumeCounter() {
        return volumeCounter;
    }

    public void setVolumeCounter(String volumeCounter) {
        this.volumeCounter = volumeCounter;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getSongArtistName() {
        return SongArtistName;
    }

    public void setSongArtistName(String songArtistName) {
        SongArtistName = songArtistName;
    }

    public String getSongUrl() {
        return SongUrl;
    }

    public void setSongUrl(String songUrl) {
        SongUrl = songUrl;
    }

    public String getSongTitle() {
        return SongTitle;
    }

    public void setSongTitle(String songTitle) {
        SongTitle = songTitle;
    }

    public String getAlbumTitle() {
        return AlbumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        AlbumTitle = albumTitle;
    }

    private String AlbumTitle;
    private int AlbumPhoto;

    public SingerModel() {
    }

    public SingerModel(String EngName, String AmhName, int AlbumPhoto) {
        this.EngName = EngName;
        this.AmhName = AmhName;
        this.AlbumPhoto = AlbumPhoto;
    }
    public SingerModel(String songname, String artistname, String songUrl) {
        this.SongName = songname;
        this.SongArtistName = artistname;
        this.SongUrl = songUrl;
    }
    public SingerModel( String volumeCounter,String songTitle) {
        this.SongTitle = songTitle;
        this.volumeCounter = volumeCounter;
    }
    public SingerModel(String songTitle) {
        this.SongTitle = songTitle;
    }

    public String getEngName() {
        return EngName;
    }

    public void setEngName(String engName) {
        EngName = engName;
    }

    public String getAmhName() {
        return AmhName;
    }

    public void setAmhName(String amhName) {
        AmhName = amhName;
    }

    public int getAlbumPhoto() {
        return AlbumPhoto;
    }

    public void setAlbumPhoto(int albumPhoto) {
        AlbumPhoto = albumPhoto;
    }







}
