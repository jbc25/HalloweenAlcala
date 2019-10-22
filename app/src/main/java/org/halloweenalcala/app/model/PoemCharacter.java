package org.halloweenalcala.app.model;

/**
 * Created by julio on 6/10/17.
 */

public class PoemCharacter {

    public static final String TYPE_OPEN = "OPEN";
    public static final String TYPE_RESPONSE = "RESPONSE";
    public static final String TYPE_PLACE = "PLACE";


    private int id;
    private int poemTitleId;
    private int poemSubtitleId;
    private int poemTextId;
    private int imageId;
    private String typeId;

    private int characterDrawableId;
    private int responsesArrayId;

    private int idPlaceServer;


    public static PoemCharacter newOpenPoem(int id, int poemTitleId, int poemSubtitleId, int poemTextId, int imageId) {
        PoemCharacter poemCharacter = new PoemCharacter();
        poemCharacter.setId(id);
        poemCharacter.setPoemTitleId(poemTitleId);
        poemCharacter.setPoemSubtitleId(poemSubtitleId);
        poemCharacter.setPoemTextId(poemTextId);
        poemCharacter.setImageId(imageId);
        poemCharacter.setTypeId(TYPE_OPEN);
        return poemCharacter;
    }

    public static PoemCharacter newResponsePoem(int id, int poemTitleId, int poemTextId, int characterDrawableId, int responsesArrayId) {
        PoemCharacter poemCharacter = new PoemCharacter();
        poemCharacter.setId(id);
        poemCharacter.setPoemTitleId(poemTitleId);
        poemCharacter.setPoemTextId(poemTextId);
        poemCharacter.setTypeId(TYPE_RESPONSE);
        poemCharacter.setCharacterDrawableId(characterDrawableId);
        poemCharacter.setResponsesArrayId(responsesArrayId);
        return poemCharacter;
    }

    public static PoemCharacter newPlacePoem(int id, int poemTitleId, int poemTextId, int idPlaceServer) {
        PoemCharacter poemCharacter = new PoemCharacter();
        poemCharacter.setId(id);
        poemCharacter.setPoemTitleId(poemTitleId);
        poemCharacter.setPoemTextId(poemTextId);
        poemCharacter.setTypeId(TYPE_PLACE);
        poemCharacter.setIdPlaceServer(idPlaceServer);
        return poemCharacter;
    }

    public boolean isOpen() {
        return TYPE_OPEN.equals(getTypeId());
    }


    // --------------

    public PoemCharacter() {

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

    public int getPoemTitleId() {
        return poemTitleId;
    }

    public void setPoemTitleId(int poemTitleId) {
        this.poemTitleId = poemTitleId;
    }


    public int getResponsesArrayId() {
        return responsesArrayId;
    }

    public void setResponsesArrayId(int responsesArrayId) {
        this.responsesArrayId = responsesArrayId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getIdPlaceServer() {
        return idPlaceServer;
    }

    public void setIdPlaceServer(int idPlaceServer) {
        this.idPlaceServer = idPlaceServer;
    }

    public int getPoemSubtitleId() {
        return poemSubtitleId;
    }

    public void setPoemSubtitleId(int poemSubtitleId) {
        this.poemSubtitleId = poemSubtitleId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
