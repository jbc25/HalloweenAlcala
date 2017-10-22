package org.halloweenalcala.app.ui.poems;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.model.PoemCharacter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by julio on 21/10/17.
 */

public class PoemBook {
//1	Plaza de Cervantes
//2	Plaza de los Irlandeses
//3	Plaza de los Santos Niños
//4	Plaza de Palacio

    public static final int ID_PZA_CERVANTES = 1;
    public static final int ID_PZA_IRLANDESES = 2;
    public static final int ID_PZA_SANTOS_NINOS = 3;
    public static final int ID_PZA_PALACIO = 4;

    public static List<PoemCharacter> poemBook = new ArrayList<>();

    public static Map<Integer, String> codesPlaces = new HashMap<>();
    static {
        codesPlaces.put(ID_PZA_CERVANTES, "NSL");
        codesPlaces.put(ID_PZA_IRLANDESES, "JBC");
        codesPlaces.put(ID_PZA_SANTOS_NINOS, "SGB");
        codesPlaces.put(ID_PZA_PALACIO, "JSB");
    }

    static {
        poemBook.add(PoemCharacter.newOpenPoem(1, R.string.poem_title_manual_supervivencia, R.string.poem_text_manual_supervivencia));
        poemBook.add(PoemCharacter.newOpenPoem(2, R.string.poem_title_guia_credulos, R.string.poem_text_guia_credulos));
        poemBook.add(PoemCharacter.newOpenPoem(3, R.string.poem_title_zombies_criaturas, R.string.poem_text_zombies_criaturas));
        poemBook.add(PoemCharacter.newResponsePoem(4, R.string.poem_title_espartacus, R.string.poem_text_espartacus, R.mipmap.profiles_espartacus, R.array.responses_espartacus));
        poemBook.add(PoemCharacter.newResponsePoem(5, R.string.poem_title_sor_citroen, R.string.poem_text_sor_citroen, R.mipmap.profiles_sorcitroen, R.array.responses_sor_citroen));
        poemBook.add(PoemCharacter.newResponsePoem(6, R.string.poem_title_pulp_fiction, R.string.poem_text_pulp_fiction, R.mipmap.profiles_jules, R.array.responses_pulp_fiction));
        poemBook.add(PoemCharacter.newResponsePoem(7, R.string.poem_title_indiana_jones, R.string.poem_text_indiana_jones, R.mipmap.profiles_indy, R.array.responses_indiana_jones));
        poemBook.add(PoemCharacter.newResponsePoem(8, R.string.poem_title_punado_dolares, R.string.poem_text_punado_dolares, R.mipmap.profiles_clint, R.array.responses_punado_dolares));
        poemBook.add(PoemCharacter.newOpenPoem(9, R.string.poem_title_evil_dead, R.string.poem_text_evil_dead));
        poemBook.add(PoemCharacter.newOpenPoem(10, R.string.poem_title_the_road, R.string.poem_text_the_road));
        poemBook.add(PoemCharacter.newPlacePoem(11, R.string.poem_title_truco_trato, R.string.poem_text_truco_trato, ID_PZA_PALACIO));
        poemBook.add(PoemCharacter.newResponsePoem(12, R.string.poem_title_tiempos_modernos, R.string.poem_text_tiempos_modernos, R.mipmap.profiles_chaplin, R.array.responses_tiempos_modernos));
        poemBook.add(PoemCharacter.newResponsePoem(13, R.string.poem_title_mary_poppins, R.string.poem_text_mary_poppins, R.mipmap.profiles_poppins, R.array.responses_mary_poppins));
        poemBook.add(PoemCharacter.newOpenPoem(14, R.string.poem_title_tiburon, R.string.poem_text_tiburon));
        poemBook.add(PoemCharacter.newPlacePoem(15, R.string.poem_title_faldas_loco, R.string.poem_text_faldas_loco, ID_PZA_SANTOS_NINOS));
        poemBook.add(PoemCharacter.newResponsePoem(16, R.string.poem_title_leia, R.string.poem_text_leia, R.mipmap.profiles_leia, R.array.responses_leia));
        poemBook.add(PoemCharacter.newOpenPoem(17, R.string.poem_title_cantinflas, R.string.poem_text_cantinflas));
        poemBook.add(PoemCharacter.newOpenPoem(18, R.string.poem_title_paul_naschy, R.string.poem_text_paul_naschy));
        poemBook.add(PoemCharacter.newOpenPoem(19, R.string.poem_title_chef, R.string.poem_text_chef));
        poemBook.add(PoemCharacter.newPlacePoem(20, R.string.poem_title_piratas_caribe, R.string.poem_text_piratas_caribe, ID_PZA_IRLANDESES));
        poemBook.add(PoemCharacter.newOpenPoem(21, R.string.poem_title_joker, R.string.poem_text_joker));
        poemBook.add(PoemCharacter.newPlacePoem(22, R.string.poem_title_walked_zombies, R.string.poem_text_walked_zombies, ID_PZA_CERVANTES));
        poemBook.add(PoemCharacter.newOpenPoem(23, R.string.poem_title_fundido_negro, R.string.poem_text_fundido_negro));
        poemBook.add(PoemCharacter.newOpenPoem(24, R.string.fin, R.string.poems_author));
    }
}
