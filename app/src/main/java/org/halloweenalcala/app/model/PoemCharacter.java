package org.halloweenalcala.app.model;

import org.halloweenalcala.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julio on 6/10/17.
 */

public class PoemCharacter {

    private int id;
    private int poemTitleId;
    private int poemTextId;
    private int characterDrawableId;
    private int characterNameId;


    public static final List<PoemCharacter> poemsCharacters = new ArrayList<>();

    static {
        poemsCharacters.add(new PoemCharacter(1, R.string.poem_title_espartacus, R.string.poem_text_espartacus, R.mipmap.img_character_default, 0));
        poemsCharacters.add(new PoemCharacter(2, R.string.poem_title_sor_citroen, R.string.poem_text_sor_citroen,  R.mipmap.img_character_default, 0));
        poemsCharacters.add(new PoemCharacter(3, R.string.poem_title_pulp_fiction, R.string.poem_text_pulp_fiction, R.mipmap.img_character_mary_poppins, R.string.character_mary_poppins));
        poemsCharacters.add(new PoemCharacter(4, R.string.poem_title_punado_dolares, R.string.poem_text_punado_dolares, R.mipmap.img_character_blondin, 0));
    }

    public PoemCharacter(int id, int poemTitleId, int poemTextId, int characterDrawableId, int characterNameId) {
        this.id = id;
        this.poemTitleId = poemTitleId;
        this.poemTextId = poemTextId;
        this.characterDrawableId = characterDrawableId;
        this.characterNameId = characterNameId;
    }

    public boolean isOpen() {
        return getCharacterNameId() == 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoemTextId() {
        return poemTextId;
    }

    public void setPoemTextId(int poemTextId) {
        this.poemTextId = poemTextId;
    }

    public int getCharacterDrawableId() {
        return characterDrawableId;
    }

    public void setCharacterDrawableId(int characterDrawableId) {
        this.characterDrawableId = characterDrawableId;
    }

    public int getCharacterNameId() {
        return characterNameId;
    }

    public void setCharacterNameId(int characterNameId) {
        this.characterNameId = characterNameId;
    }

    public static List<PoemCharacter> getPoemsCharacters() {
        return poemsCharacters;
    }

    public int getPoemTitleId() {
        return poemTitleId;
    }

    public void setPoemTitleId(int poemTitleId) {
        this.poemTitleId = poemTitleId;
    }
}
