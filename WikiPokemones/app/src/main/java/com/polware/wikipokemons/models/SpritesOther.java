package com.polware.wikipokemons.models;

import com.google.gson.annotations.SerializedName;

public class SpritesOther {

    @SerializedName("official-artwork")
    private OfficialArtwork official_artwork;

    public OfficialArtwork getOfficial_artwork() {
        return official_artwork;
    }

    public void setOfficial_artwork(OfficialArtwork official_artwork) {
        this.official_artwork = official_artwork;
    }

}
